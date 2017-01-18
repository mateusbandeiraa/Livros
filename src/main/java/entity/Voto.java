package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

@Entity
public class Voto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column
	@Expose
	private Integer id;
	@ManyToOne
	private Livro livro;
	@ManyToOne
	private Usuario usuario;
	@Column(nullable = false)
	@Expose
	private Integer rate;

	@Override
	public String toString() {
		return "Voto [id=" + id + ", livro=" + livro + ", usuario=" + usuario + ", rate=" + rate + "]";
	}

	public Voto() {
		// TODO Auto-generated constructor stub
	}

	public Voto(Integer id, Livro livro, Usuario usuario, Integer rate) {
		super();
		this.id = id;
		this.livro = livro;
		this.usuario = usuario;
		this.rate = rate;
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

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
