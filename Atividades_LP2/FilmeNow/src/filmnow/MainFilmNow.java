package filmnow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Interface com menus texto para manipular o sistema FilmNow.
 * 
 * @author eliane
 *
 */
public class MainFilmNow {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		FilmNow fn = new FilmNow();

		System.out.println("Carregando filmes ...");
		
		try {
		
			/*
			 * Essa é a maneira de lidar com possíveis erros por falta do arquivo. 
			 */
			carregaFilmes("filmes_inicial.csv", fn);
		
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Erro lendo arquivo: " + e.getMessage());
		}

		String escolha = "";
		while (true) {
			escolha = menu(scanner);
			comando(escolha, fn, scanner);
		}

	}

	/**
	 * Exibe o menu e captura a escolha do/a usuário/a.
	 * 
	 * @param scanner Para captura da opção do usuário/a.
	 * @return O comando escolhido.
	 */
	
	private static String menu(Scanner scanner) {
		System.out.println(
				"\n---\nMENU\n" + 
						"(A)Adicionar filme\n" + 
						"(M)Mostrar todos\n" + 
						"(D)Detalhar filme\n" +
						"(E)Exibir HotList\n" +
						"(H)Atribuir Hot\n" +
						"(R)Remover Hot\n" +
						"(S)Sair\n" + 
						"\n" + 
						"Opção: ");
		return scanner.nextLine().toUpperCase();
	}

	/**
	 * Interpreta a opção escolhida por quem está usando o sistema.
	 * 
	 * @param opcao   Opção digitada.
	 * @param fn  O sistema FilmNow que estamos manipulando.
	 * @param scanner Objeto scanner para o caso do comando precisar de mais input.
	 */
	private static void comando(String opcao, FilmNow fn, Scanner scanner) {
		switch (opcao) {
		case "A":
			adicionaFilme(fn, scanner);
			break;
		case "M":
			mostrarFilmes(fn);
			break;
		case "D":
			detalharFilme(fn, scanner);
			break;
		case "E":
			exibirHotList(fn);
			break;
		case "H":
			atribuiHot(fn, scanner);
			break;
		case "R":
			RemoverHot(fn, scanner);
			break;
		case "S":
			sai();
			break;
		default:
			System.out.println("Opção inválida!");
		}
	}


	private static void adicionaFilme(FilmNow fn, Scanner scanner){
			System.out.print("\nPosição no sistema: ");
			String posicao = scanner.nextLine();			
			System.out.print("\nNome: ");
			String nome = scanner.nextLine();
			System.out.print("\nAno: ");
			String ano = scanner.nextLine();
			System.out.print("\nLocal de Exibição: ");
			String local = scanner.nextLine();

			System.out.println(fn.adicionaFilme(nome, Integer.parseInt(ano), local, Integer.parseInt(posicao)));
	}


	private static void mostrarFilmes(FilmNow fn) {
		System.out.print(fn.mostrarFilmes());
	}


	private static void detalharFilme(FilmNow fn, Scanner scanner) {
		System.out.print("\nPosicao do filme na Lista: ");
		String posicao = scanner.nextLine();
		
		System.out.println(fn.detalharFilme(Integer.parseInt(posicao)));
	}
	

	private static void exibirHotList(FilmNow fn) {
		
		System.out.println(fn.exibirHotList());
	}

	private static void atribuiHot(FilmNow fn, Scanner scanner) {
		System.out.print("Posicao do Filme na Lista: ");
		String posicaoFilmeLista = scanner.nextLine();
		System.out.print("posicao da Hot: ");
		String posicaoHotList = scanner.nextLine();

		System.out.println(fn.atribuiHot(Integer.parseInt(posicaoFilmeLista), Integer.parseInt(posicaoHotList)));
	}

	private static void RemoverHot(FilmNow fn, Scanner scanner) {

		System.out.print("Posicao: ");
		String posicao = scanner.nextLine();
		
		System.out.println(fn.RemoverHot(Integer.parseInt(posicao)));
	}

	/**
	 * Sai da aplicação.
	 */
	private static void sai() {
		System.out.println("\nVlw flw o/");
		System.exit(0);
	}

	/**
	 * Lê carga de filmes de um arquivo csv. 
	 * 
	 * @param arquivoFilmes O caminho para o arquivo.
	 * @param fn O sistema FilmNow a ser populado com os dados.
	 * @throws IOException Caso o arquivo não exista ou não possa ser lido.
	 */
	private static void carregaFilmes(String arquivoFilmes, FilmNow fn) throws FileNotFoundException, IOException {
		LeitorFilmNow leitor = new LeitorFilmNow();
		
		int carregados =  leitor.carregaContatos(arquivoFilmes, fn);
		System.out.println("Carregamos " + carregados + " registros.");
	}
}
