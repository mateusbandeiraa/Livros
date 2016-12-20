package persistence;

import java.util.List;

import entity.Livro;
import entity.Usuario;
import entity.Voto;

public class VotoDao extends Dao<Voto> {

	public VotoDao() {
		super(new Voto());
	}

	public void create(Voto v) {
		// Verifica se o usuario já votou nesse livro. Evita votos duplicados.
		Usuario u = v.getUsuario();
		Livro l = v.getLivro();
		if (findByUserNBook(u.getId(), l.getId()) != null) {
			v.setId(findByUserNBook(u.getId(), l.getId()).getId());
			update(v);
			return;
		}
		
		super.create(v);
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
