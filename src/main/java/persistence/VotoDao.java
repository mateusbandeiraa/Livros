package persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Livro;
import entity.Usuario;
import entity.Voto;

public class VotoDao {
	Session session;
	Transaction transaction;
	Query query;
	Criteria criteria;

	public void create(Voto v) {
		//Verifica se o usuario já votou nesse livro. Evita votos duplicados.
		if (findByUserNBook(v.getUsuario().getId(), v.getLivro().getId()) != null) {
			v.setId(findByUserNBook(v.getUsuario().getId(), v.getLivro().getId()).getId());
			System.out.println("Entrou no lugar certo");
			System.out.println(v);
			update(v);
			return;
		}		
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.save(v);
		transaction.commit();
		session.close();
	}
	
	public void update(Voto v){
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(v);
		transaction.commit();
		session.close();
	}

	public void delete(Voto v) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(v);
		transaction.commit();
		session.close();
	}

	public Voto findByCode(Integer cod) {
		session = HibernateUtil.getSessionFactory().openSession();
		Voto v = (Voto) session.get(new Voto().getClass(), cod);
		session.close();
		return v;
	}

	@SuppressWarnings("unchecked")
	public List<Voto> findByUser(Integer id) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Voto where usuario_id like :uid");
		query.setParameter("uid", id);
		List<Voto> v = query.list();
		session.close();
		return v;
	}

	@SuppressWarnings("unchecked")
	public List<Voto> findByBook(Integer id) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Voto where livro_id like :lid");
		query.setParameter("lid", id);
		List<Voto> v = query.list();
		session.close();
		return v;
	}

	public Voto findByUserNBook(Integer uid, Integer lid) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Voto where usuario_id like :uid and livro_id like :lid");
		query.setParameter("uid", uid);
		query.setParameter("lid", lid);
		Voto v = (Voto) query.uniqueResult();
		session.close();
		return v;
	}

	public List<Usuario> findAll() {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Usuario");
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = query.list();
		session.close();
		return usuarios;
	}

	public Double getMediaLivro(Livro l) {
		List<Voto> votos = findByBook(l.getId());
		Double media = 0.;

		for (Voto v : votos) {
			media += v.getRate();
		}

		media = (media / votos.size()) * 10;
		media = (double) Math.round(media);
		media = media / 10;

		return media;

	}

	public Integer getNVotos(Livro l) {
		List<Voto> votos = findByBook(l.getId());
		return votos.size();

	}
}
