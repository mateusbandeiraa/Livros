package manager;

import java.io.File;
import java.io.FileOutputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.UploadedFile;

import entity.Autor;
import entity.Livro;
import persistence.AutorDao;
import persistence.LivroDao;

@ManagedBean(name = "mbc")
@RequestScoped
public class CadastroManagerBean {
	private Livro livro;
	private Autor autor;
	private UploadedFile capaLivro;
	private UploadedFile fotoAutor;

	@PostConstruct
	private void init() {
		livro = new Livro();
		autor = new Autor();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public UploadedFile getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(UploadedFile capaLivro) {
		this.capaLivro = capaLivro;
	}

	public UploadedFile getFotoAutor() {
		return fotoAutor;
	}

	public void setFotoAutor(UploadedFile fotoAutor) {
		this.fotoAutor = fotoAutor;
	}

	public void cadastrarLivro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			String arq = livro.getNome().replaceAll("\\W+", "") + ".jpg";
			ServletContext context = (ServletContext) fc.getExternalContext().getContext();
			String path = context.getRealPath("/img");
			FileOutputStream output = new FileOutputStream(path + "/" + arq);
			output.write(capaLivro.getContents());
			output.close();

			livro.setImagem(arq);

			new LivroDao().create(livro, autor);
			livro = new Livro();
			autor = new Autor();
			fc.addMessage("form1", new FacesMessage("Livro cadastrado com sucesso"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fc.addMessage("form1", new FacesMessage("Erro: " + ex.getLocalizedMessage()));
		}
	}

	public void cadastrarAutor() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			String arq = autor.getNome().replaceAll("\\W+", "") + ".jpg";
			ServletContext context = (ServletContext) fc.getExternalContext().getContext();
			String path = context.getRealPath("/img");
			System.out.println(path);
			FileOutputStream output = new FileOutputStream(new File(path + "\\" + arq));
			output.write(fotoAutor.getContents());
			output.close();

			autor.setImagem(arq);

			new AutorDao().create(autor);
			autor = new Autor();
			fc.addMessage("form2", new FacesMessage("Autor cadastrado com sucesso"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fc.addMessage("form2", new FacesMessage("Erro: " + ex.getMessage()));
		}
	}

	public void apagaAutor() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Autor a = (Autor) fc.getExternalContext().getRequestMap().get("autor");
		try {
			new AutorDao().delete(a);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void apagaLivro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Livro l = (Livro) fc.getExternalContext().getRequestMap().get("livro");
		try {
			new LivroDao().delete(l);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
