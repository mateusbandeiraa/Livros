package control;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Autor;
import entity.Comentario;
import entity.Livro;
import entity.Usuario;
import manager.HomeManagerBean;
import persistence.AutorDao;
import persistence.ComentarioDao;
import persistence.LivroDao;
import persistence.UsuarioDao;

@WebServlet("/WS")
public class WS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gsonLazy = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).excludeFieldsWithoutExposeAnnotation().create();

	Integer idLivro = null;
	Integer idAutor = null;
	Integer idUsuario = null;
	Integer idComentario = null;
	String nomeLivro = null;
	String nomeAutor = null;
	String emailUsuario = null;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text; charset=UTF-8");
		
		String cmd = request.getParameter("cmd");
		// RESGATA PARÂMETROS
		try {
			idLivro = Integer.valueOf(request.getParameter("idLivro"));
		} catch (NumberFormatException e) {
		}
		try {
			idAutor = Integer.valueOf(request.getParameter("idAutor"));
		} catch (NumberFormatException e) {
			
		}
		try {
			idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
		} catch (NumberFormatException e) {
			
		}
		try {
			idComentario = Integer.valueOf(request.getParameter("idComentario"));
		} catch (NumberFormatException e) {
			
		}
		nomeLivro = request.getParameter("nomeLivro");
		nomeAutor = request.getParameter("nomeAutor");
		emailUsuario = request.getParameter("emailUsuario");
		
		switch (cmd) {
		case "getTopLivros":
			getTopLivros(request, response);
			break;
		case "getTopAutores":
			getTopAutores(request, response);
			break;
		case "getLivro":
			getLivro(request, response);
			break;
		case "getAutor":
			getAutor(request, response);
			break;
		case "getUsuario":
			getUsuario(request, response);
			break;
		case "getComentario":
			getComentario(request, response);
			break;
		default:
			response.getWriter().append("REQUEST INVÁLIDO");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	protected void getTopLivros(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Livro> lst = new HomeManagerBean().getTopLivros();
		response.getWriter().append(gsonLazy.toJson(lst));

	}

	protected void getTopAutores(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Autor> lst = new HomeManagerBean().getTopAutores();
		response.getWriter().append(gsonLazy.toJson(lst));

	}

	protected void getLivro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Livro l = null;
		
		if(idLivro != null){
			l = new LivroDao().findByCode(idLivro);
		}else if(nomeLivro != null){
			l = new LivroDao().findByName(nomeLivro);
		}else{
			response.getWriter().append(gsonLazy.toJson(new LivroDao().findAll()));
			return;
		}
		
		response.getWriter().append(gsonLazy.toJson(l));
		
	}
	protected void getAutor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Autor a = null;
		
		if(idAutor != null){
			a =  new AutorDao().findByCode(idAutor);
		}else if(nomeAutor != null){
			a = new AutorDao().findByName(nomeAutor);
		}else{
			response.getWriter().append(gsonLazy.toJson(new AutorDao().findAll()));
			return;
		}
		
		response.getWriter().append(gsonLazy.toJson(a));
		
	}
	protected void getUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario u = null;
		if(idUsuario != null){
			u = new UsuarioDao().findByCode(idUsuario);
		}else if(emailUsuario != null){
			u = new UsuarioDao().findByEmail(emailUsuario);
		}else{
			response.getWriter().append(gsonLazy.toJson(new UsuarioDao().findAll()));
			return;
		}
		response.getWriter().append(gsonLazy.toJson(u));
		
	}
	
	protected void getComentario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Comentario> lst = null;
		if(idLivro != null && idUsuario!= null){
			lst = new ComentarioDao().findByUserNBook(idUsuario, idLivro);
		}else if(idLivro != null){
			lst = new ComentarioDao().findByBook(idLivro);
		}else if(idUsuario != null){
			lst = new ComentarioDao().findByUser(idUsuario);
		} else if(idComentario != null){
			Comentario c = new ComentarioDao().findByCode(idComentario);
			response.getWriter().append(gsonLazy.toJson(c));
			return;
		}else{
			response.getWriter().append(gsonLazy.toJson(new ComentarioDao().findAll()));
			return;
		}
		response.getWriter().append(gsonLazy.toJson(lst));
		
	}
}
