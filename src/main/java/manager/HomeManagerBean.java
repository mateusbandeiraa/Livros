package manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Autor;
import entity.Livro;
import persistence.AutorDao;
import persistence.LivroDao;
import persistence.VotoDao;

public class HomeManagerBean {
	private List<Livro> topLivros;
	private List<Autor> topAutores;

	public HomeManagerBean() {
		topLivros = new ArrayList<Livro>();
		topAutores = new ArrayList<Autor>();
	}

	public List<Livro> getTopLivros() {
		LivroDao ld = new LivroDao();
		topLivros = ld.findAll();
		for (Livro l : topLivros) {
			Double m = new VotoDao().getMediaLivro(l);
			l.setMediaVotos(m);
		}
		topLivros.sort(Comparator.comparing(Livro::getMediaVotos).reversed());
		if (topLivros.size() < 10) {
			return topLivros;
		}
		return topLivros.subList(0, 10);
	}

	public void setTopLivros(List<Livro> topLivros) {
		this.topLivros = topLivros;
	}

	public List<Autor> getTopAutores() {
		AutorDao ad = new AutorDao();
		topAutores = ad.findAll();
		for (Autor a : topAutores) {
			Double somaMediaLivros = 0.;
			for (Livro l : a.getLivros()) {
				somaMediaLivros += new VotoDao().getMediaLivro(l);
			}
			Double media = somaMediaLivros / a.getLivros().size();
			System.out.println("Autor: " + a.getNome());
			System.out.println("Media: " + media);
			if (!media.isNaN()) {
				a.setMediaAutor(media);
			} else {
				a.setMediaAutor(0.);
			}
		}
		topAutores.sort(Comparator.comparing(Autor::getMediaAutor).reversed());
		if (topAutores.size() < 10) {
			return topAutores;
		}
		return topAutores.subList(0, 10);
	}

	public void setTopAutores(List<Autor> topAutores) {
		this.topAutores = topAutores;
	}

}
