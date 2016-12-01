package persistence;

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

	public void create(Livro l) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
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
		if (!nome.contains(" ")) {
			if (nome.length() != 1) { //Caso o termo tenha apenas uma letra, retorna resultados dessa maneira
				query = session.createQuery("from Livro L where L.nome LIKE '%'|| :nome || '%'");
				query.setParameter("nome", nome);
			} else {
				query = session.createQuery("from Livro L where L.nome LIKE :nome || ' %' OR L.nome LIKE '% '|| :nome || ' %' ");
				query.setParameter("nome", nome);
			}
			
		} else {
			String[] nomes = nome.split(" ");
			String quer = "from Livro L where";

			for (int i = 0; i < nomes.length; i++) {
				if (i != 0)
					quer += " OR";
				if (nomes[i].length() != 1) { //Caso o termo tenha apenas uma letra, retorna resultados dessa maneira
					quer += " L.nome like '%' || ? || '%'";
				} else {
					quer += " L.nome like ?";
				}
			}

			query = session.createQuery(quer);

			for (int i = 0; i < nomes.length; i++) {
				query.setString(i, nomes[i]);
			}
		}
		System.out.println(query.getQueryString());
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
