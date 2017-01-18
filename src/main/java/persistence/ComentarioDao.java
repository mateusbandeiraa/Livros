package persistence;

import java.util.List;

import entity.Comentario;
import entity.Livro;
import entity.Usuario;

public class ComentarioDao extends Dao<Comentario> {
	public ComentarioDao() {
		super(new Comentario());
	}

	@SuppressWarnings("unchecked")
	public List<Comentario> findByUser(Integer id) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Comentario where usuario_id like :uid");
		query.setParameter("uid", id);
		List<Comentario> c = query.list();
		session.close();
		return c;
	}

	@SuppressWarnings("unchecked")
	public List<Comentario> findByBook(Integer id) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Comentario where livro_id like :lid");
		query.setParameter("lid", id);
		List<Comentario> c = query.list();
		session.close();
		return c;
	}

	@SuppressWarnings("unchecked")
	public List<Comentario> findByUserNBook(Integer uid, Integer lid) {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Comentario where usuario_id like :uid and livro_id like :lid");
		query.setParameter("uid", uid);
		query.setParameter("lid", lid);

		List<Comentario> c = query.list();
		session.close();
		return c;
	}

	public static void main(String[] args) {
		Usuario u = new Usuario();
		u.setId(2);
		Livro l = new Livro();
		l.setId(4);
		Comentario c = new Comentario(null, l, u, "EU AMO ESSE LIVRO");

		new ComentarioDao().create(c);

		System.out.println("Deu tudo certo");

	}
}
