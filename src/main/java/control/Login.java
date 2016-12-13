package control;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import entity.Usuario;
import persistence.UsuarioDao;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String ref;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		ref = request.getHeader("referer");
		try {
			ref = ref.substring(ref.indexOf("livros/") + 7, ref.indexOf(".jsp") + 4);
		} catch (Exception e) {
			ref = "index.jsp";
		}
		switch (cmd) {
		case "login":
			login(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		default:
			break;
		}
	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = request.getParameter("emailUsuario");
		String senha = request.getParameter("senhaUsuario");

		try {
			Usuario u = new UsuarioDao().findByEmail(email);

			if (u == null) {
				throw new Exception("E-mail não encontrado");
			}
			if (!BCrypt.checkpw(senha, u.getSenha())) {
				throw new Exception("Senha incorreta");
			}

			session.setAttribute("userID", u.getId());
			session.setAttribute("userProf", u.getPerfil());

			String refParam = request.getParameter("ref");
			System.out.println("REF: " + refParam);
			if (refParam != null) {
				response.sendRedirect(refParam);
			} else {
				response.sendRedirect(ref);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendRedirect(ref + "?logMsg=" + URLEncoder.encode(ex.getMessage(), "UTF-8"));
			return;
		}
	}

	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("userID", null);
		session.setAttribute("userProf", null);
		request.setAttribute("logMsg", null);

		response.sendRedirect(ref);
	}

}
