package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

import persistence.LivroDao;
import persistence.VotoDao;

@Entity
public class Livro implements Serializable, Pesquisavel {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column
	@Expose
	private Integer id;
	@Column(length = 50)
	@Expose
	private String nome;
	@Column
	@Type(type = "text")
	@Expose
	private String descricao;
	@Column(length = 17, unique = true)
	@Expose
	private String isbn;
	@Column
	@Expose
	private String editora;
	@Column
	@Expose
	private String imagem;

	@ManyToOne
	private Autor autor;

	@OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
	private List<Voto> votos;
	
	@OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
	private List<Comentario> comentarios;

	@Expose
	private transient Double mediaVotos;

	public Livro() {
		// TODO Auto-generated constructor stub
	}

	public Livro(Integer id, String nome, String descricao, String isbn, String editora, String imagem, Autor autor) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.isbn = isbn;
		this.editora = editora;
		this.imagem = imagem;
		this.autor = autor;
	}

	public Livro(Integer id, String nome, String descricao, String isbn, String editora, String imagem, Autor autor,
			List<Voto> votos) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.isbn = isbn;
		this.editora = editora;
		this.imagem = imagem;
		this.autor = autor;
		this.votos = votos;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", nome=" + nome + ", isbn=" + isbn + ", editora=" + editora + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Double getMediaVotos() {
		mediaVotos = new VotoDao().getMediaLivro(this);
		return mediaVotos;
	}

	public void setMediaVotos(Double mediaVotos) {
		this.mediaVotos = mediaVotos;
	}
	
	public static void main(String[] args) {
		System.out.println(new LivroDao().findByCode(4).getVotos());
	}

}
