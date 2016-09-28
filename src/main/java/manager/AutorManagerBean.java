package manager;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import entity.Autor;
import entity.Livro;
import persistence.AutorDao;
import persistence.LivroDao;

@ManagedBean(name = "mba")
@RequestScoped
public class AutorManagerBean {
	private Integer id;
	private Autor autor;
	private List<Livro> livros;

	@PostConstruct
	public void init() {
		autor = new Autor();
		id = new Integer(0);
	}

	public void checkId() {
		if (id == null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("linha 37/" + id);
	}

	public Autor getAutor() {
		System.out.println("linha 41/" + id);
		autor = new AutorDao().findByCode(id);
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Livro> getLivros() {
		livros = new LivroDao().findByAuthor(autor);
		return livros;
		
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

}
