package persistence;

import entity.Usuario;

public class UsuarioDao extends Dao<Usuario>{
	
	public UsuarioDao() {
		super(new Usuario());
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
