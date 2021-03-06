package persistence;

import java.util.List;

import entity.Autor;
import entity.Comentario;
import entity.Livro;
import entity.Voto;

public class LivroDao extends Dao<Livro> {

	public LivroDao() {
		super(new Livro());
	}

	@Override
	public void delete(Livro c) {
		// Apaga os votos do livro
		VotoDao vd = new VotoDao();
		List<Voto> votos = vd.findByBook(c.getId());

		for (Voto v : votos) {
			vd.delete(v);
		}
		// Apaga os comemtários do livro
		ComentarioDao cd = new ComentarioDao();
		List<Comentario> comentarios = cd.findByBook(c.getId());
		for (Comentario com : comentarios) {
			cd.delete(com);
		}
		// Apaga o Livro
		super.delete(c);
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

	public List<Livro> findListByName(String nome) {
		session = HibernateUtil.getSessionFactory().openSession();
		if (!nome.contains(" ")) {
			if (nome.length() != 1) { // Caso o termo tenha apenas uma letra,
										// retorna resultados dessa maneira
				query = session.createQuery("from Livro L where L.nome LIKE '%'|| :nome || '%'");
				query.setParameter("nome", nome);
			} else {
				query = session.createQuery(
						"from Livro L where L.nome LIKE :nome || ' %' OR L.nome LIKE '% '|| :nome || ' %' ");
				query.setParameter("nome", nome);
			}

		} else {
			String[] nomes = nome.split(" ");
			String quer = "from Livro L where";

			for (int i = 0; i < nomes.length; i++) {
				if (i != 0)
					quer += " OR";
				if (nomes[i].length() != 1) { // Caso o termo tenha apenas uma
												// letra, retorna resultados
												// dessa maneira
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
		return livro;
	}

}
