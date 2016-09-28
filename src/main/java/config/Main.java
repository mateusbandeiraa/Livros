package config;

import entity.Usuario;
import persistence.UsuarioDao;

public class Main {

	public static void main(String[] args) {
		

		geraBanco();
	}

	private static void geraBanco() {
//		Autor a1 = new Autor(null, "Andy Weir", "Autor novato que escreveu The Martian", null, null);
//		Livro l1 = new Livro(null, "Perdido em Marte", "H� seis dias, o astronauta Mark Watney se tornou a d�cima s�tima pessoa a pisar em Marte. E, provavelmente, ser� a primeira a morrer no planeta vermelho. Depois de uma forte tempestade de areia, a miss�o Ares 3 � abortada e a tripula��o vai embora, certa de que Mark morreu em um terr�vel acidente. Ao despertar, ele se v� completamente sozinho, ferido e sem ter como avisar �s pessoas na Terra que est� vivo. E, mesmo que conseguisse se comunicar, seus mantimentos terminariam anos antes da chegada de um poss�vel resgate. Ainda assim, Mark n�o est� disposto a desistir. Munido de nada al�m de curiosidade e de suas habilidades de engenheiro e bot�nico e um senso de humor inabal�vel , ele embarca numa luta obstinada pela sobreviv�ncia. Para isso, ser� o primeiro homem a plantar batatas em Marte e, usando uma genial mistura de c�lculos e fita adesiva, vai elaborar um plano para entrar em contato com a Nasa e, quem sabe, sair vivo de l�. Com um forte embasamento cient�fico real e moderno, Perdido em Marte � um suspense memor�vel e divertido, impulsionado por uma trama que n�o para de surpreender o leitor.", "858-0-41-335-4", "Intr�nseca", null, null);
//		Autor a2 = new Autor(null, "J. R. R. Tolkien", "Um dos maiores autores do s�culo XX", null, null);
//		Livro l2 = new Livro(null, "O Senhor dos An�is",
//				"O Senhor dos An�is (The Lord of the Rings) � um romance de fantasia criado pelo escritor, professor e fil�logo brit�nico J.R.R. Tolkien. A hist�ria come�a como seq��ncia de um livro anterior de Tolkien, O Hobbit (The Hobbit), e logo se desenvolve numa hist�ria muito maior.",
//				"978-3-16-14841-0","Martins Fontes",null, null);
//		LivroDao ld = new LivroDao();
//
//		ld.create(l1, a1);
//		ld.create(l2, a2);

		Usuario u = new Usuario(null, "admin", "admin@adm.com", "admin", "adm");
		new UsuarioDao().create(u);

	}
}
