package manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import entity.Usuario;
import persistence.UsuarioDao;

@ManagedBean(name = "mbu")
@RequestScoped
public class UsuarioManagerBean {
	private Usuario usuario;
	private Usuario usuLogado;
	private List<Usuario> usuarios;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuLogado() {
		return usuLogado;
	}

	public void setUsuLogado(Usuario usuLogado) {
		this.usuLogado = usuLogado;
	}

	public List<Usuario> getUsuarios() {
		usuarios = new UsuarioDao().findAll();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void cadastrar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			new UsuarioDao().create(usuario);
			fc.addMessage("form1", new FacesMessage("Usuário cadastrado com sucesso"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fc.addMessage("form1", new FacesMessage("Erro: " + ex.getLocalizedMessage()));
		}
	}

	public void apagaUsuario() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Usuario u = (Usuario) fc.getExternalContext().getRequestMap().get("usuario");
		if (u.getEmail().equals("admin@adm.com")) {
			fc.addMessage("apagaUserForm", new FacesMessage("Você não pode apagar esse usuário!"));
		} else {
			try {
				new UsuarioDao().delete(u);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void login() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Usuario u = new UsuarioDao().findByEmail(usuario.getEmail());
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
			if (BCrypt.checkpw(usuario.getSenha(), u.getSenha())) {
				fc.addMessage("form1", new FacesMessage("Usuário Autenticado"));
				session.setAttribute("logado", true);
				session.setAttribute("userId", u.getId());
				fc.getExternalContext().redirect("adm/index.jsf");
			} else {
				fc.addMessage("form1", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha incorreta.", null));
				session.setAttribute("logado", null);
			}
		} catch (java.lang.NullPointerException ex) {
			fc.addMessage("form1", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado.", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			fc.addMessage("form1",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: " + ex.getLocalizedMessage(), null));
		}
	}
	
	public void logout(){
		FacesContext fc = FacesContext.getCurrentInstance();
		
		try {
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("logado", null);
			session.setAttribute("userId", null);
			fc.getExternalContext().redirect("../index.jsf");
		} catch (Exception ex) {
			
		}
	}

}
