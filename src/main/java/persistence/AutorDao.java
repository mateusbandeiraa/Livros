package persistence;

import java.util.List;

import entity.Autor;
import entity.Livro;

public class AutorDao extends Dao<Autor>{
	
	public AutorDao() {
		super(new Autor());
	}
	
	@Override
	public void delete(Autor c) {
		// Apaga os livros do autor
		LivroDao ld = new LivroDao();
		List<Livro> livros = ld.findByAuthor(c);
		for(Livro l : livros){
			ld.delete(l);
		}
		// Apaga o Autor
		super.delete(c);
	}

		public List<Autor> findListByName(String nome) {
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
		public Autor findByName(String nome) {
			session = HibernateUtil.getSessionFactory().openSession();
			query = session.createQuery("from Autor A where A.nome LIKE '%'|| :nome || '%'");
			query.setString("nome", nome);
			Autor autor = (Autor) query.uniqueResult();
			session.close();
			return autor;
		}
}
