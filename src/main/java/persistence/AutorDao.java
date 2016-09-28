package persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Autor;

public class AutorDao {
	private Session session;
	private Transaction transaction;
	private Criteria criteria;
	private Query query;

	public void create(Autor a) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.save(a);
		transaction.commit();
		session.close();
	}

	public void update(Autor a) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(a);
		transaction.commit();
		session.close();
	}

	public void delete(Autor a) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(a);
		transaction.commit();
		session.close();
	}

	public Autor findByCode(Integer cod) {
		session = HibernateUtil.getSessionFactory().openSession();
		Autor a = (Autor) session.get(new Autor().getClass(), cod);
		session.close();
		return a;
	}

	public List<Autor> findAll() {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Autor");
		@SuppressWarnings("unchecked")
		List<Autor> autores = query.list();
		session.close();
		return autores;
	}

	public List<Autor> findByName(String nome) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from autor A where A.nome like '%' || :nome || '%'");
		query.setParameter("nome", nome);
		@SuppressWarnings("unchecked")
		List<Autor> autores = query.list();
		session.close();
		return autores;
	}
	
	public static void main(String[] args) {
		System.out.println(new AutorDao().findByCode(2));
	}
}
