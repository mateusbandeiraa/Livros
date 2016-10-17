package manager;

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

@ManagedBean(name = "mbe")
@RequestScoped
public class EditManagerBean {
	private Livro livro;
	private Autor autor;
	private UploadedFile capaLivro;
	private UploadedFile fotoAutor;

	@PostConstruct
	public void init() {
		livro = new Livro();
		autor = new Autor();
	}
	
	public void checkLivroId(){
		if (livro.getId() != null){
			livro = new LivroDao().findByCode(livro.getId());
		} else{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../index.jsf");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void checkAutorId(){
		if (autor.getId() != null){
			autor = new AutorDao().findByCode(autor.getId());
		} else{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../index.jsf");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Autor getAutor() {
		if (livro.getAutor() != null) {
			autor = livro.getAutor();
		}
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
	
	public Integer getISBNType(){
		try {
			String isbn = livro.getIsbn().replaceAll("[^\\d.]",""); //remove os traços
			if (isbn.length() > 10){
				return 1;
			}else{
				return 0;
			}
		} catch (Exception e) {
			return 1;
		}
	}

	public void editarLivro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println("-----------------------"+ livro.getIsbn());
		try {
			if (capaLivro.getContents() != null) {
				String arq = livro.getNome().replaceAll("\\W+", "") + ".jpg";
				ServletContext context = (ServletContext) fc.getExternalContext().getContext();
				String path = context.getRealPath("/img");
				FileOutputStream output = new FileOutputStream(path + "/" + arq);
				output.write(capaLivro.getContents());
				output.close();

				livro.setImagem(arq);
			}
			livro.setAutor(new AutorDao().findByCode(autor.getId()));
			new LivroDao().update(livro);

			fc.getPartialViewContext().getRenderIds().add("panelLivro");
			fc.addMessage("form1", new FacesMessage("Livro editado com sucesso"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fc.addMessage("form1", new FacesMessage("Erro: " + ex.getLocalizedMessage()));
		}
	}

	public void editarAutor() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (fotoAutor.getContents() != null) {
				String arq = autor.getNome().replaceAll("\\W+", "") + ".jpg";
				ServletContext context = (ServletContext) fc.getExternalContext().getContext();
				String path = context.getRealPath("/img");
				FileOutputStream output = new FileOutputStream(path + "/" + arq);
				output.write(fotoAutor.getContents());
				output.close();

				autor.setImagem(arq);
			}

			new AutorDao().update(autor);

			fc.getPartialViewContext().getRenderIds().add("painelAutor");
			fc.addMessage("form1", new FacesMessage("Autor editado com sucesso"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fc.addMessage("form1", new FacesMessage("Erro: " + ex.getLocalizedMessage()));
		}
	}

}
