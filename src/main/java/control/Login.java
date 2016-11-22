package control;

import java.io.IOException;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		System.out.println(cmd);
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
		} catch (Exception ex) {
			request.setAttribute("logMsg", ex.getMessage());
			ex.printStackTrace();
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}

		response.sendRedirect("index.jsp");
	}

	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("userID", null);
		session.setAttribute("userProf", null);
		request.setAttribute("logMsg", null);

		response.sendRedirect("index.jsp");
	}

}
