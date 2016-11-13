package persistence;

import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Autor;
import entity.Livro;

public class LivroDao {
	private Session session;
	private Transaction transaction;
	private Query query;
	private Criteria criteria;

	public void create(Livro l, Autor a) {
		l.setAutor(a);
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.save(a);
		session.save(l);
		transaction.commit();
		session.close();
	}

	public void update(Livro l) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(l);
		transaction.commit();
		session.close();
	}

	public void delete(Livro l) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(l);
		transaction.commit();
		session.close();
	}

	public Livro findByCode(Integer cod) {
		session = HibernateUtil.getSessionFactory().openSession();
		Livro l = (Livro) session.get(new Livro().getClass(), cod);
		session.close();
		return l;
	}

	public List<Livro> findByAuthor(Autor a) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Livro where autor_id = :id");
		query.setParameter("id", a.getId());
		@SuppressWarnings("unchecked")
		List<Livro> livros = query.list();
		session.close();
		return livros;
	}

	public List<Livro> findAll() {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Livro");
		@SuppressWarnings("unchecked")
		List<Livro> livros = query.list();
		session.close();
		return livros;
	}

	public List<Livro> findListByName(String nome) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Livro L where L.nome LIKE '%'|| :nome || '%'");
		query.setParameter("nome", nome);
		@SuppressWarnings("unchecked")
		List<Livro> livros = query.list();
		session.close();
		return livros;
	}

	public Livro findByName(String nome) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Livro L where L.nome LIKE '%'|| :nome || '%'");
		query.setString("nome", nome);
		Livro livro = (Livro) query.uniqueResult();
		session.close();
		System.out.println(livro);
		return livro;
	}

}
