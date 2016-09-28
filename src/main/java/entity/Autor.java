package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

@Entity
public class Autor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column
	private Integer id;
	@Column(length = 50, unique = true)
	private String nome;
	@Column
	@Type(type = "text")
	private String descricao;
	@Column
	private String imagem;

	@OneToMany(mappedBy = "autor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Livro> livros;

	public void addLivro(Livro l) {
		if (livros == null)
			livros = new ArrayList<Livro>();
		livros.add(l);
	}
	public void removeLivro(Livro l){
		try {
			livros.remove(l);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public Autor() {
		// TODO Auto-generated constructor stub
	}

	public Autor(Integer id, String nome, List<Livro> livros) {
		super();
		this.id = id;
		this.nome = nome;
		this.livros = livros;
	}

	public Autor(Integer id, String nome, String descricao, String imagem, List<Livro> livros) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.imagem = imagem;
		this.livros = livros;
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nome=" + nome + "]";
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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

}
