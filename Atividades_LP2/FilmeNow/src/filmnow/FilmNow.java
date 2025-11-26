package filmnow;

/**
 * Sistema que mantém os seus filmes prediletos. Podem existir 100 filmes. 
 * 
 * @author eliane
 * @author SavioCartaxo
 * 
 */
public class FilmNow {
	
	private static final int TAMANHO = 100;
	private static final int TAMANHOHOTLIST = 10;
	private Filme[] filmes;

	public FilmNow() {
		this.filmes = new Filme[TAMANHO];
	}
	

	public String adicionaFilme(String nome, int ano, String local, int posicao) {
		nome = nome.trim();
		local = local.trim();
		
		if (nome.equals("")) {
			return "FILME INVALIDO";
		}

		if (local.equals("")) {
			return "FILME INVALIDO";
		}

		// Checa se o filme pode ser adicionado na lista e adiciona(se possivel)
		if (posicao > TAMANHO || posicao < 1) {
			return "POSIÇÃO INVÁLIDA";
		}
				
		// Percorre a lista e checa de o filme ja existe
		Filme filmeQueEstamosTentandoAdicionar = new Filme(nome, ano, local);
		boolean jaExiste = false;
		
		for (int i = 0; i < TAMANHO; i++) {
			if (filmeQueEstamosTentandoAdicionar.equals(this.filmes[i])) {
				jaExiste = true;
				break;
			}
		}
		
		// Se o Filme não existe, é adicionado na lista, se já existe, é informado ao usuário
		
		if (!jaExiste) { 
			this.cadastraFilme(posicao, nome, ano, local);
			return "Filme adicionado na posicao " + posicao;
		
		} else {
			
			return "FILME JA ADICIONADO";
		
		}
		
	}



	public String mostrarFilmes() {
		
		String out = "";

		for (int i = 0; i < TAMANHO; i++) {
			if (this.getFilme(i) != null) {			
				out += this.formataFilme(i);
				out += "\n";
			}
		}

		return out;
	}

	private String formataFilme(int posicao) {
		String out = "";	
		posicao++;

		out += posicao + " "; 
		
		out += "- ";

		if (this.getHotFilme(posicao - 1)) {
			out += "🔥 ";
		}
		
		out += this.getNomeFilme(posicao - 1);

		return out;
	}


	public String detalharFilme(int posicao) {
		
		if (this.getFilme(posicao - 1) == null) {
			return "\nPOSIÇÃO INVÁLIDA!";
		}
		
		return this.GettoStringFilme(posicao - 1);

	}


	public String atribuiHot(int posicaoFilmeLista, int posicaoHotList) {

		// Hotlist só tem 10 espaços
		if(posicaoHotList > TAMANHOHOTLIST) {
			return "Posicao inválida na Hotlist";
		}

		// Se o Filme já está na HotList, não podemos adiciona-lo novamente
		if (this.getFilme(posicaoFilmeLista - 1) != null) {	
			if (this.getHotFilme(posicaoFilmeLista -1 )) {
				return "Filme já está na HotList";
			}
		
		} else {
			return "Posicao de Filme Inválida";
		}

		// Se já existir um filme nessa posicao da hotlist é preciso remover o status de Hot desse filme
		for (int i = 0; i < TAMANHO; i++) {
			if (this.getFilme(i) != null) {	
				if (this.getHotFilme(i)) {
					if (this.getPosicionHotFilme(i) == posicaoHotList) {
						this.getFilme(i).setHot(false);
						this.getFilme(i).setPosicionHot(-1); // isso não é necessário, coloquei apenas para que de erro se a hot nao funcionar como o esperado
						break;
					}
				}
			}
		}

		this.getFilme(posicaoFilmeLista - 1).setHot(true);
		this.getFilme(posicaoFilmeLista - 1).setPosicionHot(posicaoHotList);

		return "Filme adicionado na posicao " + posicaoHotList + " da HotList";
	}


	public String exibirHotList() {
		String out = "";
		
		for (int posicaoHotList = 1; posicaoHotList <= TAMANHOHOTLIST; posicaoHotList++) {
			for (int i = 0; i < TAMANHO; i++) {
				
				if (this.getFilme(i) != null) {
					// Isso faz com que a HotList seja printada em ordem
					if (this.getPosicionHotFilme(i) == posicaoHotList) {	
						if (this.getHotFilme(i)) {
							out += this.getPosicionHotFilme(i) + " ";
							out += this.getNomeFilme(i) + " "; 
							out += this.getAnoFilme(i) + " ";
							out += "\n";
						}
					
					}			
				
				}
			
			}
		
		}

		return out;
	}


	public String RemoverHot(int posicao) {
		posicao--;

		if (this.getHotFilme(posicao)) {
			this.getFilme(posicao).setHot(false);
			this.getFilme(posicao).setPosicionHot(-1);

			return "Filme " + this.getFilme(posicao) + " removido da Hotlist";
		}

		return "Filme nao esta na HotList";
	}


	private Filme getFilme(int posicao) {
		return this.filmes[posicao];
	}

	public void cadastraFilme(int posicao, String nome, int ano, String local) {
		if (posicao <= 100) {
			this.filmes[posicao - 1] = new Filme(nome, ano, local);
		}
		
	}
	
	private String getNomeFilme(int posicao) {
		return this.filmes[posicao].getNome();
	}

	private String GettoStringFilme(int posicao) {
		return this.filmes[posicao].toString();
	}

	private int getPosicionHotFilme(int posicao) {
		return this.filmes[posicao].getPosicionHot();
	}

	private int getAnoFilme(int posicao) {
		return this.filmes[posicao].getAno();
	}

	private boolean getHotFilme(int posicao) {
		return this.filmes[posicao].getHot();
	}
}
