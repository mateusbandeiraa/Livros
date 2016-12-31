package persistence;

import java.util.List;

import entity.Autor;

public class AutorDao extends Dao<Autor>{
	
	public AutorDao() {
		super(new Autor());
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
}
