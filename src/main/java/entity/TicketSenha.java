package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TicketSenha {
	@Id
	@GeneratedValue
	private Integer id;
	@OneToOne
	@JoinColumn(unique = true)
	private Usuario usuario;
	@Column
	private String ticketPass;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date data;

	@Override
	public String toString() {
		return "TicketSenha [id=" + id + ", usuario=" + usuario + ", ticketPass=" + ticketPass + ", data=" + data + "]";
	}

	public TicketSenha() {

	}

	public TicketSenha(Integer id, Usuario usuario, String ticketPass, Date data) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.ticketPass = ticketPass;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTicketPass() {
		return ticketPass;
	}

	public void setTicketPass(String ticketPass) {
		this.ticketPass = ticketPass;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
