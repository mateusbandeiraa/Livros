package config;

import entity.Usuario;
import persistence.UsuarioDao;

public class Main {

	public static void main(String[] args) {
		

		geraBanco();
	}

	private static void geraBanco() {
//		Autor a1 = new Autor(null, "Andy Weir", "Autor novato que escreveu The Martian", null, null);
//		Livro l1 = new Livro(null, "Perdido em Marte", "Há seis dias, o astronauta Mark Watney se tornou a décima sétima pessoa a pisar em Marte. E, provavelmente, será a primeira a morrer no planeta vermelho. Depois de uma forte tempestade de areia, a missão Ares 3 é abortada e a tripulação vai embora, certa de que Mark morreu em um terrível acidente. Ao despertar, ele se vê completamente sozinho, ferido e sem ter como avisar às pessoas na Terra que está vivo. E, mesmo que conseguisse se comunicar, seus mantimentos terminariam anos antes da chegada de um possível resgate. Ainda assim, Mark não está disposto a desistir. Munido de nada além de curiosidade e de suas habilidades de engenheiro e botânico e um senso de humor inabalável , ele embarca numa luta obstinada pela sobrevivência. Para isso, será o primeiro homem a plantar batatas em Marte e, usando uma genial mistura de cálculos e fita adesiva, vai elaborar um plano para entrar em contato com a Nasa e, quem sabe, sair vivo de lá. Com um forte embasamento científico real e moderno, Perdido em Marte é um suspense memorável e divertido, impulsionado por uma trama que não para de surpreender o leitor.", "858-0-41-335-4", "Intrínseca", null, null);
//		Autor a2 = new Autor(null, "J. R. R. Tolkien", "Um dos maiores autores do século XX", null, null);
//		Livro l2 = new Livro(null, "O Senhor dos Anéis",
//				"O Senhor dos Anéis (The Lord of the Rings) é um romance de fantasia criado pelo escritor, professor e filólogo britânico J.R.R. Tolkien. A história começa como seqüência de um livro anterior de Tolkien, O Hobbit (The Hobbit), e logo se desenvolve numa história muito maior.",
//				"978-3-16-14841-0","Martins Fontes",null, null);
//		LivroDao ld = new LivroDao();
//
//		ld.create(l1, a1);
//		ld.create(l2, a2);

		Usuario u = new Usuario(null, "admin", "admin@adm.com", "admin", "adm");
		new UsuarioDao().create(u);

	}
}
