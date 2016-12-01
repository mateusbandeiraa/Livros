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

		if (!nome.contains(" ")) { // SE A STRING NÃO CONTEM ESPAÇO
			if(nome.length() != 1){
			query = session.createQuery("from Autor A where A.nome like '%' || :nome || '%'");
			query.setString("nome", nome);
			}else{
				query = session.createQuery("from Autor A where A.nome LIKE :nome || ' %' OR A.nome LIKE '% '|| :nome || ' %' ");
				query.setParameter("nome", nome);
			}
		} else { // CASO CONTENHA ESPAÇO
			String[] nomes = nome.split(" ");
			String quer = "from Autor A where";

			for (int i = 0; i < nomes.length; i++) {
				if(i!=0)
					quer += " OR";
				if(nomes[i].length() != 1){
				quer += " A.nome like '%' || ? || '%'";
				}else{
					quer += " A.nome like ?";
				}
			}
			query = session.createQuery(quer);

			for (int i = 0; i < nomes.length; i++) {
				query.setString(i, nomes[i]);
			}
		}
		@SuppressWarnings("unchecked")
		List<Autor> autores = query.list();
		session.close();
		return autores;
	}

	public static void main(String[] args) {
		System.out.println(new AutorDao().findByCode(2));
	}
}
