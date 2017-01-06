package control;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import entity.TicketSenha;
import entity.Usuario;
import persistence.TicketDao;
import persistence.UsuarioDao;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String ref;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		ref = request.getHeader("referer");
		try {
			ref = ref.substring(ref.indexOf("livros/") + 7);
			if(ref.equals(""))
				throw new Exception();
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
		case "ajaxCreateTicket":
			generateTicket(request, response);
			break;
		case "ajaxRescSenha":
			rescSenha(request, response);
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

		System.out.println("logout");
		if (!ref.contains("adm")) {
			response.sendRedirect(ref);
		} else {
			response.sendRedirect("/livros/");
		}

	}

	protected void generateTicket(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String userEmail = request.getParameter("userEmail");

			UsuarioDao ud = new UsuarioDao();

			Usuario user = ud.findByEmail(userEmail);

			if (user == null)
				throw new Exception("Usuário não encontrado!");

			String ticketPass = BCrypt.hashpw(userEmail, BCrypt.gensalt()); // Gera
																			// código
																			// do
																			// ticket
			TicketSenha ts = new TicketSenha(null, user, ticketPass, new Date());

			TicketDao td = new TicketDao();
			td.create(ts);

			CEM.resgateSenha(ts);

			response.getWriter().write("Um link para a redefinição da senha foi enviado para o seu e-mail");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getWriter().write("Erro: " + ex.getMessage());
		}

	}

	protected void rescSenha(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String ticketCode = request.getParameter("ticketCode");

			TicketDao td = new TicketDao();

			TicketSenha ts = td.findByPass(ticketCode);
			if(ts == null)
				throw new Exception("Código de redefinição de senha inválido");

			Date agora = new Date();
			Long msPorDia = 24 * 60 * 60 * 1000L;
			if (Math.abs(ts.getData().getTime() - agora.getTime()) > msPorDia) {
				throw new Exception("O pedido de redefinição de senha expirou.");
			}

			Usuario user = ts.getUsuario();

			String senha = request.getParameter("senhaUsuario");

			user.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));

			System.out.println(ts);
			
			new UsuarioDao().update(user);
			td.delete(ts);
			response.getWriter().write("Senha alterada com sucesso.");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getWriter().write("Erro: " + ex.getMessage());
		}

	}

}
