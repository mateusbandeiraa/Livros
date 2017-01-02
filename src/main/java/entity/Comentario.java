package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Comentario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column
	private Integer id;
	@ManyToOne
	private Livro livro;
	@ManyToOne
	private Usuario usuario;
	@Column
	@Type(type = "text")
	private String content;

	@Override
	public String toString() {
		return "Comentario [id=" + id + ", livro=" + livro + ", usuario=" + usuario + ", content=" + content + "]";
	}

	public Comentario() {
		// TODO Auto-generated constructor stub
	}

	public Comentario(Integer id, Livro livro, Usuario usuario, String content) {
		super();
		this.id = id;
		this.livro = livro;
		this.usuario = usuario;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
