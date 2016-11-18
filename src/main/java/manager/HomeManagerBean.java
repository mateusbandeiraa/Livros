package manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Livro;
import persistence.LivroDao;
import persistence.VotoDao;

public class HomeManagerBean {
	private List<Livro> topLivros;
	
	public HomeManagerBean() {
		topLivros = new ArrayList<Livro>();
	}

	public List<Livro> getTopLivros() {
		LivroDao ld = new LivroDao();
		topLivros = ld.findAll();
		for(Livro l: topLivros){
			Double m = new VotoDao().getMediaLivro(l);
			l.setMediaVotos(m);
		}
		topLivros.sort(Comparator.comparing(Livro::getMediaVotos).reversed());
		if(topLivros.size() < 10){
			return topLivros;
		}
		return topLivros.subList(0, 10);
	}

	public void setTopLivros(List<Livro> topLivros) {
		this.topLivros = topLivros;
	}
	
	
}
