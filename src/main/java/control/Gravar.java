package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

import entity.Autor;
import entity.Comentario;
import entity.Livro;
import entity.Usuario;
import entity.Voto;
import persistence.AutorDao;
import persistence.ComentarioDao;
import persistence.LivroDao;
import persistence.UsuarioDao;
import persistence.VotoDao;

@WebServlet("/Gravar")
@MultipartConfig
public class Gravar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String ref;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // Para acertar os acentos
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String cmd = request.getParameter("cmd");
		ref = request.getHeader("referer");
		try {
			ref = ref.substring(ref.indexOf("livros/") + 7, ref.indexOf(".jsp") + 4);
		} catch (Exception e) {
			ref = "index.jsp";
		}
		switch (cmd) {
		case "livro":
			gravarLivro(request, response);
			break;

		case "autor":
			gravarAutor(request, response);
			break;

		case "usuario":
			gravarUsuario(request, response);
			break;

		case "comentario":
			gravarComentario(request, response);
			break;
		case "editLivro":
			editarLivro(request, response);
			break;

		case "editAutor":
			editarAutor(request, response);
			break;

		case "editUsuario":
			editarUsuario(request, response);
			break;

		case "deleteLivro":
			apagarLivro(request, response);
			break;

		case "deleteAutor":
			apagarAutor(request, response);
			break;

		case "ajaxDeleteUsuario":
			ajaxApagarUsuario(request, response);
			break;
		
		case "ajaxDeleteComentario":
			ajaxApagarComentario(request, response);
			break;

		case "voto":
			gravarVoto(request, response);
			break;

		default:
			break;
		}
	}

	protected void gravarLivro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String CAMINHO = getServletContext().getRealPath("/").concat("src/main/webapp/img/");
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

			File diretorio = new File(CAMINHO);
			if (!diretorio.isDirectory())
				diretorio.mkdirs();

			File file = new File(CAMINHO + l.getIsbn() + extArq);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file.getPath());
			System.out.println(file.getPath());
			int i = 0;
			while ((i = is.read()) != -1) {
				fos.write(i);
			}
			fos.close();
			l.setImagem(l.getIsbn() + extArq);
			new LivroDao().create(l);

			response.sendRedirect(
					ref + "?sucessoLivro=true&msgLivro=Dados gravados com sucesso!&idCriada=" + l.getId());

		} catch (Exception ex) {
			response.sendRedirect(ref + "?msgLivro=Erro: " + ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}

	protected void editarLivro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String CAMINHO = getServletContext().getRealPath("/").concat("/src/main/webapp/img/");
		try {
			// INSTANCIA E PREENCHE OBJ LIVRO
			Livro l = new Livro();
			l.setId(Integer.valueOf(request.getParameter("idLivro")));
			l.setNome(request.getParameter("nomeLivro"));
			l.setIsbn(request.getParameter("isbn"));
			l.setEditora(request.getParameter("editora"));
			l.setDescricao(request.getParameter("descricaoLivro"));
			Autor a = new Autor();
			System.out.println(request.getParameter("autorLivro"));
			a.setId(Integer.valueOf(request.getParameter("autorLivro")));
			l.setAutor(a);

			// CHECA SE ISBN MUDOU PARA MUDAR IMAGEM
			Livro l2 = new LivroDao().findByCode(l.getId());
			if (l.getIsbn() != l2.getIsbn()) {
				String novoNome = CAMINHO + l.getIsbn() + l2.getImagem().substring(l2.getImagem().length() - 4);
				File file1 = new File(CAMINHO + l2.getImagem());
				File file2 = new File(novoNome);
				file1.renameTo(file2);
				l.setImagem(l.getIsbn() + l2.getImagem().substring(l2.getImagem().length() - 4));
			}

			// TRABALHA COM A IMAGEM ENVIADA
			Part arq = request.getPart("capaLivro");

			if (arq.getSize() != 0) {
				String extArq = arq.getSubmittedFileName();
				extArq = extArq.substring(extArq.lastIndexOf("."));
				InputStream is = arq.getInputStream();

				File diretorio = new File(CAMINHO);
				if (!diretorio.isDirectory())
					diretorio.mkdirs();

				File file = new File(CAMINHO + l.getIsbn() + extArq);
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file.getPath());
				int i = 0;
				while ((i = is.read()) != -1) {
					fos.write(i);
				}
				fos.close();
				l.setImagem(l.getIsbn() + extArq);
			}

			new LivroDao().update(l);

			response.sendRedirect(
					ref + "?sucessoLivro=true&msgLivro=Livro editado com sucesso!&item=livro&id=" + l.getId());

		} catch (Exception ex) {
			response.sendRedirect(ref + "?msgLivro=Erro: " + ex.getLocalizedMessage() + "&item=livro&id="
					+ request.getAttribute("idLivro"));
			ex.printStackTrace();
		}
	}

	protected void apagarLivro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Livro l = new Livro();
			l.setId(Integer.valueOf(request.getParameter("id")));
			new LivroDao().delete(l);

			response.sendRedirect("/livros/adm/");

		} catch (Exception ex) {
			response.sendRedirect(ref + "?msgLivro=Erro: " + ex.getLocalizedMessage() + "&item=livro&id="
					+ request.getParameter("id"));
			ex.printStackTrace();
		}
	}

	protected void gravarAutor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String CAMINHO = getServletContext().getRealPath("/").concat("src/main/webapp/img/");
		try {
			// INSTANCIA E PREENCHE OBJ LIVRO
			Autor a = new Autor();
			a.setNome(request.getParameter("nomeAutor"));
			a.setDescricao(request.getParameter("descricaoAutor"));
			AutorDao ad = new AutorDao();
			ad.create(a);

			// TRABALHA COM A IMAGEM ENVIADA
			try {
				Part arq = request.getPart("fotoAutor");
				String extArq = arq.getSubmittedFileName();
				extArq = extArq.substring(extArq.lastIndexOf("."));
				InputStream is = arq.getInputStream();
				File diretorio = new File(CAMINHO);
				if (!diretorio.isDirectory())
					diretorio.mkdirs();
				File file = new File(CAMINHO + "autor-" + a.getId() + extArq);
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file.getPath());
				int i = 0;
				while ((i = is.read()) != -1) {
					fos.write(i);
				}
				fos.close();
				a.setImagem("autor-" + a.getId() + extArq);
				ad.update(a);
			} catch (Exception ex) {
				ad.delete(a);
				throw ex;
			}

			response.sendRedirect(
					ref + "?sucessoAutor=true&msgAutor=Dados gravados com sucesso!&idCriada=" + a.getId());

		} catch (Exception ex) {
			response.sendRedirect(ref + "?msgAutor=Erro: " + ex.getLocalizedMessage());

			ex.printStackTrace();
		}
	}

	protected void editarAutor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String CAMINHO = getServletContext().getRealPath("/").concat("/src/main/webapp/img/");
		try {
			// INSTANCIA E PREENCHE OBJ LIVRO
			Autor a = new Autor();
			a.setId(Integer.valueOf(request.getParameter("idAutor")));
			a.setNome(request.getParameter("nomeAutor"));
			a.setDescricao(request.getParameter("descricaoAutor"));
			AutorDao ad = new AutorDao();
			a.setImagem(ad.findByCode(a.getId()).getImagem());

			// TRABALHA COM A IMAGEM ENVIADA
			Part arq = request.getPart("fotoAutor");
			System.out.println("arq.getSize() -> " + arq.getSize());
			if (arq.getSize() != 0) {
				String extArq = arq.getSubmittedFileName();
				extArq = extArq.substring(extArq.lastIndexOf("."));
				InputStream is = arq.getInputStream();
				File file = new File(CAMINHO + "autor-" + a.getId() + extArq);
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file.getPath());
				int i = 0;
				while ((i = is.read()) != -1) {
					fos.write(i);
				}
				fos.close();
				a.setImagem("autor-" + a.getId() + extArq);
			}

			ad.update(a);

			response.sendRedirect(
					ref + "?sucessoAutor=true&msgAutor=Autor editado com sucesso!&item=autor&id=" + a.getId());

		} catch (Exception ex) {
			response.sendRedirect(ref + "?msgAutor=Erro: " + ex.getLocalizedMessage() + "&item=autor&id="
					+ request.getParameter("idAutor"));
			ex.printStackTrace();
		}
	}

	protected void apagarAutor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Autor a = new Autor();
			a.setId(Integer.valueOf(request.getParameter("id")));
			new AutorDao().delete(a);

			response.sendRedirect("/livros/adm/");

		} catch (Exception ex) {
			response.sendRedirect(ref + "?msgAutor=Erro: " + ex.getLocalizedMessage() + "&item=autor&id="
					+ request.getParameter("id"));
			ex.printStackTrace();
		}
	}

	protected void gravarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			Usuario u = new Usuario();
			u.setNome(request.getParameter("nomeUsuario"));
			u.setEmail(request.getParameter("emailUsuario"));
			String senha = request.getParameter("senhaUsuario");
			senha = BCrypt.hashpw(senha, BCrypt.gensalt());
			u.setSenha(senha);
			u.setPerfil("usu");

			if (u.getEmail().equalsIgnoreCase("mateusbandeiraa@gmail.com"))
				u.setPerfil("adm");

			new UsuarioDao().create(u);
			session.setAttribute("userID", u.getId());
			session.setAttribute("userProf", u.getPerfil());
			CEM.cadastro(u.getEmail(), u.getNome());
			response.sendRedirect(ref + "?msgUsuario=Cadastro efetuado com sucesso!");
		} catch (Exception ex) {
			response.sendRedirect(ref + "?msgUsuario=" + ex.getMessage());
		}
	}

	protected void editarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Usuario u = new Usuario();
			UsuarioDao ud = new UsuarioDao();
			u = ud.findByCode(Integer.valueOf(request.getParameter("idUsuario")));
			u.setNome(request.getParameter("nomeUsuario"));
			u.setEmail(request.getParameter("emailUsuario"));
			u.setPerfil(request.getParameter("perfilUsuario"));

			if (u.getEmail().equalsIgnoreCase("admin@admin.com"))
				u.setPerfil("adm");

			ud.update(u);
			response.sendRedirect(ref + "?item=usuario&id=" + u.getId() + "&msgUsuario="
					+ URLEncoder.encode("Usuário editado com sucesso!", "UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendRedirect(
					ref + "?item=usuario&id=" + request.getParameter("idUsuario") + "msgUsuario=" + ex.getMessage());
		}
	}

	protected void ajaxApagarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Usuario u = new Usuario();
			u.setId(Integer.valueOf(request.getParameter("idUsuario")));

			new UsuarioDao().delete(u);
			response.getWriter().write("Usuario apagado com sucesso");
		} catch (Exception ex) {
			response.getWriter().write("Erro: " + ex.getMessage());
		}
	}

	protected void gravarVoto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Integer idUsuario = Integer.valueOf(request.getParameter("userID"));
			Usuario u = new Usuario();
			u.setId(idUsuario);

			Integer idLivro = Integer.valueOf(request.getParameter("livroID"));
			Livro l = new Livro();
			l.setId(idLivro);

			Integer voto = Integer.valueOf(request.getParameter("nota"));
			if (voto < 1) {
				VotoDao vd = new VotoDao();
				Voto v = vd.findByUserNBook(idUsuario, idLivro);
				vd.delete(v);
			} else {
				Voto v = new Voto(null, l, u, voto);

				new VotoDao().create(v);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			response.sendRedirect(ref + "?id=" + request.getParameter("livroID"));
		}
	}

	protected void gravarComentario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Comentario c = new Comentario();
			Usuario u = new Usuario();
			u.setId(Integer.valueOf(request.getParameter("userID")));
			Livro l = new Livro();
			l.setId(Integer.valueOf(request.getParameter("livroID")));
			c.setUsuario(u);
			c.setLivro(l);
			c.setContent(request.getParameter("commentContent"));
			new ComentarioDao().create(c);
			response.sendRedirect(ref + "?id=" + l.getId());
		} catch (Exception ex) {
			response.sendRedirect(
					ref + "?id=" + request.getParameter("livroID") + "&msgComentario=Erro: " + ex.getMessage());
		}
	}
	
	protected void ajaxApagarComentario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Comentario c = new Comentario();
			c.setId(Integer.valueOf(request.getParameter("idComentario")));

			new ComentarioDao().delete(c);
			response.getWriter().write("Comentário apagado com sucesso");
		} catch (Exception ex) {
			response.getWriter().write("Erro: " + ex.getMessage());
		}
	}

}
