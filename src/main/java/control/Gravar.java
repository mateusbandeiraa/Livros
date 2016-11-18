package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entity.Autor;
import entity.Livro;
import persistence.AutorDao;
import persistence.LivroDao;

@WebServlet("/Gravar")
@MultipartConfig
public class Gravar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // Para acertar os acentos
		String cmd = request.getParameter("cmd");

		switch (cmd) {
		case "livro":
			gravarLivro(request, response);
			break;

		case "autor":
			gravarAutor(request, response);
			break;
		default:
			break;
		}
	}

	protected void gravarLivro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String CAMINHO= getServletContext().getRealPath("/").concat("/src/main/webapp/img/");
		try {
			// INSTANCIA E PREENCHE OBJ LIVRO
			Livro l = new Livro();
			l.setNome(request.getParameter("nomeLivro"));
			l.setIsbn(request.getParameter("isbn"));
			l.setEditora(request.getParameter("editora"));
			l.setDescricao(request.getParameter("descricaoLivro"));
			Autor a = new Autor();
			a.setId(Integer.valueOf(request.getParameter("autor")));
			l.setAutor(a);
			// TRABALHA COM A IMAGEM ENVIADA
			Part arq = request.getPart("capaLivro");
			String extArq = arq.getSubmittedFileName();
			extArq = extArq.substring(extArq.lastIndexOf("."));
			InputStream is = arq.getInputStream();
			File file = new File(CAMINHO + l.getIsbn() + extArq);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file.getPath());
			System.out.println(file.getPath());
			int i = 0;
			while((i = is.read()) != -1){
				fos.write(i);
			}
			
			l.setImagem(l.getIsbn() + extArq);
			new LivroDao().create(l);

			request.setAttribute("msgLivro", "Dados gravados com sucesso!");
			request.setAttribute("sucessoLivro", true);
			request.setAttribute("idCriada", l.getId());
			
		} catch (Exception ex) {
			request.setAttribute("msgLivro", "Erro: " + ex.getLocalizedMessage());
			request.setAttribute("sucesso", false);
			ex.printStackTrace();
		}finally{
			request.getRequestDispatcher("cadastro.jsp").forward(request, response);
		}
	}
	
	protected void gravarAutor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String CAMINHO= getServletContext().getRealPath("/").concat("/src/main/webapp/img/");
		try {
			// INSTANCIA E PREENCHE OBJ LIVRO
			Autor a = new Autor();
			a.setNome(request.getParameter("nomeAutor"));
			a.setDescricao(request.getParameter("descricaoAutor"));
			AutorDao ad = new AutorDao();
			ad.create(a);
			// TRABALHA COM A IMAGEM ENVIADA
			Part arq = request.getPart("fotoAutor");
			String extArq = arq.getSubmittedFileName();
			extArq = extArq.substring(extArq.lastIndexOf("."));
			InputStream is = arq.getInputStream();
			File file = new File(CAMINHO + "autor-"+ a.getId() + extArq);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file.getPath());
			System.out.println(file.getPath());
			int i = 0;
			while((i = is.read()) != -1){
				fos.write(i);
			}
			
			a.setImagem("autor-"+ a.getId() + extArq);
			ad.update(a);

			request.setAttribute("msgAutor", "Dados gravados com sucesso!");
			request.setAttribute("sucessoAutor", true);
			request.setAttribute("idCriada", a.getId());
			
		} catch (Exception ex) {
			request.setAttribute("msgAutor", "Erro: " + ex.getLocalizedMessage());
			request.setAttribute("sucessoAutor", false);
			ex.printStackTrace();
		}finally{
			request.getRequestDispatcher("cadastro.jsp").forward(request, response);
		}
	}

}
