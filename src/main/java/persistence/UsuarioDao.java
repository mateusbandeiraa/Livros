package persistence;

import java.util.List;

import entity.Comentario;
import entity.TicketSenha;
import entity.Usuario;
import entity.Voto;

public class UsuarioDao extends Dao<Usuario> {

	public UsuarioDao() {
		super(new Usuario());
	}

	@Override
	public void delete(Usuario c) {
		// Apaga os votos do usuario
		VotoDao vd = new VotoDao();
		List<Voto> votos = vd.findByUser(c.getId());
		for (Voto v : votos) {
			vd.delete(v);
		}
		// Apaga os comemtários do usuario
		ComentarioDao cd = new ComentarioDao();
		List<Comentario> comentarios = cd.findByUser(c.getId());
		for (Comentario com : comentarios) {
			cd.delete(com);
		}
		// Apaga um ticket de redefinição de senha (caso exista)
		TicketDao td = new TicketDao();
		TicketSenha ts = td.findByUser(c.getId());
		td.delete(ts);
		// Apaga o usuario
		super.delete(c);
	}

	public Usuario findByEmail(String email) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Usuario where email like :email");
		query.setParameter("email", email);
		Usuario u = (Usuario) query.uniqueResult();
		session.close();
		return u;
	}

	public static void main(String[] args) {
		System.out.println(new UsuarioDao().findAll());
	}

}
