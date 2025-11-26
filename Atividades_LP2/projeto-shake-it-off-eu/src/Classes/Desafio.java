package Classes;

import java.time.*;
import java.util.*;

public class Desafio {
	private String titulo;
	private String descricao;
	private String ID;
	private String ranking;
	private LocalDate inicio;
	private LocalDate fim;
	private int metaDiariaMin;
	private boolean rankingFinal;
	private Usuario criador;
	private ArrayList<Usuario> listaUsuarios;
	private ArrayList<CheckIn> listaCheckin;
	private TipoDePontuacao estrategiaPontuacao; // interface
	

	// OK
	public Desafio(String titulo, String descricao, LocalDate inicio, LocalDate fim, 
			int metaDiariaMin, String estrategiaPontuacao,String ID, Usuario criador) {
		
		//Tratando os dados
		titulo = titulo.trim();
		descricao = descricao.trim();
		estrategiaPontuacao = estrategiaPontuacao.trim().toUpperCase();
		ID = ID.trim();
		
		// Validações de campos
		if (titulo.isEmpty()) 
		    throw new IllegalArgumentException("Por favor, informe o título do desafio");

		if (descricao.isEmpty())
		    throw new IllegalArgumentException("Por favor, informe a descrição do desafio");

		if (estrategiaPontuacao.isEmpty()) 
		    throw new IllegalArgumentException("Por favor, informe a estratégia de pontuação");

		if (ID.isEmpty())
		    throw new IllegalArgumentException("Por favor, informe o ID do desafio");
		
		// fim deve ser posterior ao inicio
		if (!inicio.isBefore(fim))
		    throw new IllegalArgumentException("A data de fim deve ser posterior à data de início");

		// Validando Estrategia dePontuação
		if (!estrategiaPontuacao.equals("FREQUENCIA") && !estrategiaPontuacao.equals("DISTANCIA")
				&& !estrategiaPontuacao.equals("TEMPO")) {

			throw new IllegalArgumentException(
					"Estrategia de pontuação deve ser uma dessas 3: FREQUENCIA, DISTANCIA ou TEMPO");
		}
		
		// criador nao pode ser nulo
		if (criador == null) 
		    throw new IllegalArgumentException("O criador do desafio não pode ser nulo");
		
		// Meta diaria deve ser >= 0
		if (metaDiariaMin < 0)
			throw new IllegalArgumentException("A meta diaria nao pode ser negativa");
		
		
		if (estrategiaPontuacao.equals("FREQUENCIA")) 
		    this.estrategiaPontuacao = new Frequencia();
		
		else if (estrategiaPontuacao.equals("DISTANCIA"))
			this.estrategiaPontuacao = new Distancia();
		
		else
		    this.estrategiaPontuacao = new Tempo();
		

		this.titulo = titulo;
		this.descricao = descricao;
		this.inicio = inicio;
		this.fim = fim;
		this.metaDiariaMin = metaDiariaMin;
		this.ID = ID;
		this.criador = criador;
		this.ranking = "";
		this.listaUsuarios = new ArrayList<>();
		this.listaCheckin = new ArrayList<>();
		this.rankingFinal = false;
	}
	
	
	// booleano de se o desafio está em andamento
	// OK
	public boolean desafioEmAndamento() {
		return (LocalDate.now().isEqual(this.inicio) || LocalDate.now().isAfter(this.inicio)) 
		        && (LocalDate.now().isEqual(this.fim) || LocalDate.now().isBefore(this.fim));
	}
	
	
	// Adiciona Usuário no desafio
	// OK
	public String ingressaNoDesafio(Usuario usuario) {
		
		if (!this.desafioEmAndamento() || // Evento nao esta em andamento
				this.listaUsuarios.contains(usuario)) { // Usuario ja participa do desafio
			return "Não foi possível entrar no desafio.";
		}
		
		this.listaUsuarios.add(usuario);
		usuario.ingressaNoDesafio(this);
		return "Você está participando!";
	}
	
	
	// retorna True se Usuario já está no desafio
	// OK
	public boolean temUsuario(Usuario usuario ) {
		return this.listaUsuarios.contains(usuario);
	}
	
	// Adiciona CheckIn
	// OK
	public void AdicionaCheckIn(CheckIn checkIn) {
		this.listaCheckin.add(checkIn);
	}
	
	// retorna String com os toStrings de ckeckIns
	// OK
	public String listaCheckins() {
	    List<CheckIn> listaC = new ArrayList<>(this.listaCheckin);

	    listaC.sort(Comparator.comparing(CheckIn::getDataHora));

	    String out = "";
	    for (int i = 0; i < listaC.size(); i++) {
	        out += listaC.get(i).toString();
	        if (i < listaC.size() - 1) {
	            out += "\n";
	        }
	    }
	    
	    if (out.trim().equals("")) {
	    	out = "Ainda não houve registro de check-in.";
	    }

	    return out;
	}
	

	// OK
	public boolean encerra() {
		this.calculaRanking();
		if (this.desafioEmAndamento()) {
			this.fim = LocalDate.now();
			return true;
		} 
		return false;
	}
	
	
	// OK
	public boolean calculaRanking() {
	    if (this.rankingFinal) {
	    	return false;
	    }
		
		// Calculando a pontuação de cada usuário
	    Map<Usuario, Double> rankingTemp = new HashMap<>();
	    for (Usuario u : listaUsuarios) {
	        double pontos = u.calculaPontuacao(this);
	        rankingTemp.put(u, pontos);
	    }

	    // Ordena lista de usuários
	    listaUsuarios.sort(
	            Comparator
	                .comparingDouble((Usuario u) -> rankingTemp.get(u))
	                .reversed()
	                .thenComparingInt((Usuario u) -> u.getCheckInsNoDesafio(this))
	                .reversed()
	        );
	    
	    // Monta saída
	    String rank = "";
	    rank += this.titulo + "\n";
	    rank += "--------------------\n";

	    for (int i = 0; i < listaUsuarios.size(); i++) {
	        Usuario u = listaUsuarios.get(i);
	        rank += "#" + (i + 1) + " " + u.getID() + " " + u.getNome() + " — "
	             + (int) Math.round(rankingTemp.get(u)) + " pts";

	        if (i != listaUsuarios.size() - 1) {
	        	rank += "\n";
	        }
	    }

	    this.ranking = rank;
	    
	    if (!this.desafioEmAndamento() && LocalDate.now().isAfter(this.inicio)) {
	    	this.rankingFinal = true;
	    }
	    
	    return true;
	}

	
	public double calculaPontuacaoIndividual(ArrayList<CheckIn> listaC) {
		return this.estrategiaPontuacao.calculaPontuacao(listaCheckin);
	}
	
	// OK
	public boolean desafioTerminou() {
		return LocalDate.now().isAfter(this.fim);
	}
	
	
	// Gets e Sets -> não precisa testar
	
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getInicio() {
		return this.inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFim() {
		return this.fim;
	}

	public void setFim(LocalDate fim) {
		this.fim = fim;
	}

	public int getMetaDiariaMin() {
		return this.metaDiariaMin;
	}

	public void setMetaDiariaMin(int metaDiariaMin) {
		this.metaDiariaMin = metaDiariaMin;
	}

	public TipoDePontuacao getEstrategiaPontuacao() {
		return this.estrategiaPontuacao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public String getID() {
		return ID;
	}

	public Usuario getCriador() {
		return criador;
	}

	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public ArrayList<CheckIn> getListaCheckIn() {
		return listaCheckin;
	}
	
	public boolean isRankingFinal() {
		return rankingFinal;
	}

	public ArrayList<CheckIn> getListaCheckin() {
		return listaCheckin;
	}
	
	public String getRanking() {
		this.calculaRanking();
		return ranking;
	}
	
	public int getNumeroDeParticipantes() {
		return this.listaUsuarios.size();
	}
	
	
	// Overrides
	
	// OK
	@Override
	public String toString(){
		String out = "";
		if (this.desafioTerminou()) {
			this.calculaRanking();
			out += "[FINALIZADO]";
		}
		
		out += "[" + this.ID + "]";
		out += this.titulo;
		out += "(" + this.inicio + " a " + this.fim;
		out += "), criado por: " + this.criador.getNome() + '\n';
		out += this.descricao + '\n';
		out += "Meta diaria mínima: " + this.metaDiariaMin + "min; ";
		out += "Pontuação por: " + this.estrategiaPontuacao.toString();
		
		return out;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(ID, criador, descricao, estrategiaPontuacao, fim, inicio, listaCheckin, listaUsuarios,
				metaDiariaMin, titulo);
	}
	
	
	// OK
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Desafio other = (Desafio) obj;
		return Objects.equals(ID, other.ID);
	}

}
