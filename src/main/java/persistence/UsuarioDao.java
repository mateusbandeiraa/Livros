package persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import entity.Usuario;

public class UsuarioDao {
	Session session;
	Transaction transaction;
	Query query;
	Criteria criteria;

	public void create(Usuario u) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		u.setSenha(BCrypt.hashpw(u.getSenha(), BCrypt.gensalt()));
		session.save(u);
		transaction.commit();
		session.close();
	}

	public void update(Usuario u) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(u);
		transaction.commit();
		session.close();
	}

	public void delete(Usuario u) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(u);
		transaction.commit();
		session.close();
	}

	public Usuario findByCode(Integer cod) {
		session = HibernateUtil.getSessionFactory().openSession();
		Usuario u = (Usuario) session.get(new Usuario().getClass(), cod);
		session.close();
		return u;
	}

	public Usuario findByEmail(String email) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Usuario where email like :email");
		query.setParameter("email", email);
		Usuario u = (Usuario) query.uniqueResult();
		session.close();
		return u;
	}

	public List<Usuario> findAll() {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Usuario");
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = query.list();
		session.close();
		return usuarios;
	}
}
