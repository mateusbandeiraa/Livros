package persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Dao <Classe>{
	Session session;
	Transaction transaction;
	Criteria criteria;
	Query query;
	
	Classe classe;
	
	public Dao(Classe classe) {
		this.classe = classe;
	}
	
	public void create(Classe c){
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.save(c);
		transaction.commit();
		session.close();
	}
	
	public void update(Classe c){
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(c);
		transaction.commit();
		session.close();
	}
	
	public void delete(Classe c){
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(c);
		transaction.commit();
		session.close();
	}
	
	public List<Classe> findAll(){
		session = HibernateUtil.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Classe> lst = session.createCriteria(classe.getClass().getName()).list(); //TODO PODE DAR ERRADO
		session.close();
		return lst;
	}
	
	public Classe findByCode(int cod){
		session = HibernateUtil.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		Classe c = (Classe) session.get(classe.getClass().getName(), cod); //TODO PODE DAR ERRADO
		session.close();
		return c;
	}
}
