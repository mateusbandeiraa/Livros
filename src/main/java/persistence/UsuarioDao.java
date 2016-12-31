package persistence;

import java.util.List;

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
