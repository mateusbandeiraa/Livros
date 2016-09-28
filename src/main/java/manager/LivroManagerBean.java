package manager;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entity.Autor;
import entity.Livro;
import entity.Usuario;
import entity.Voto;
import persistence.AutorDao;
import persistence.LivroDao;
import persistence.UsuarioDao;
import persistence.VotoDao;
//teste
@ManagedBean(name = "mb")
@RequestScoped
public class LivroManagerBean {
	private Livro livro;
	private Autor autor;
	private List<Livro> livros;
	private List<Autor> autores;
	private Double media;
	private Integer voto;

	@PostConstruct
	public void init() {
		livro = new Livro();
		autor = new Autor();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> getLivros() {
		if (livros == null)
			livros = new LivroDao().findAll();
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public List<Autor> getAutores() {
		if (autores == null)
			autores = new AutorDao().findAll();
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Double getMedia() {
		VotoDao vd = new VotoDao();
		media = vd.getMediaLivro(livro);
		return media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}
	
	public Integer getVoto() {
		return voto;
	}

	public void setVoto(Integer voto) {
		this.voto = voto;
	}

	public void votar(){
		FacesContext fc = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		VotoDao vd = new VotoDao();
		Usuario u = new UsuarioDao().findByCode((Integer)session.getAttribute("userId"));
		Voto v = new Voto(null, livro, u, voto);
		vd.create(v);
	}

	public void checkId() {
		if (livro.getId() == null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("./index.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			livro = new LivroDao().findByCode(livro.getId());
		}
	}
}
