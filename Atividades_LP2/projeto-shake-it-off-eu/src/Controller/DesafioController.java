package Controller;

import java.time.LocalDate;
import java.util.*;

import Classes.*;

public class DesafioController {
	
	private Map<String, Desafio> listaDesafios; // String é o ID
	
	public DesafioController(){
		this.listaDesafios = new HashMap<>();
	}
	
	
	// Cria o desafio
	// OK
	public String criarDesafio(String idUsuario, String senha, String titulo, 
			String descricao, LocalDate inicio, LocalDate fim, int metaDiariaMin, 
			String estrategiaPontuacao, UsuarioController uc){
		
		uc.autenticaUsuario(idUsuario, senha);
		String ID = "DSF" + (this.listaDesafios.size() + 1 );
		
		Usuario usuario = uc.getUsuario(idUsuario);
		Desafio desafio = new Desafio(titulo, descricao, inicio, fim, metaDiariaMin,
				estrategiaPontuacao, ID, usuario);
		
		usuario.ingressaNoDesafio(desafio);
		this.listaDesafios.put(ID, desafio);
		return ID;
	}
	
	
	// cadastra usuario no desafio
	public String IngressarNoDesafio(String idUsuario, String idDesafio, String senha, 
			UsuarioController uc) {
		
		uc.autenticaUsuario(idUsuario, senha);
		this.autenticaDesafio(idDesafio);
		
		Usuario usuario = uc.getUsuario(idUsuario);
		return this.getDesafio(idDesafio).ingressaNoDesafio(usuario);
	}

	
	// Lista desafios na ordem de inclusão
	public String listaDesafios(String idUsuario, String senha, UsuarioController uc) {
	    uc.autenticaUsuario(idUsuario, senha);

	    List<Desafio> desafios = new ArrayList<>(listaDesafios.values());
	    String out = "";

	    for (int i = 0; i < desafios.size(); i++) {
	        Desafio d = desafios.get(i);
	        out += d.toString() + "\n" + "Participantes: " + d.getNumeroDeParticipantes();
	        
	        if (i < desafios.size() - 1) 
	        	out += "\n";
	    }

	    return out;
	}
	
	
	// Lista apenas os desafios ativos em ordem de inclusão
	public String listarDesafiosAtivos(String idUsuario, String senha, UsuarioController uc){
	    uc.autenticaUsuario(idUsuario, senha);

	    List<Desafio> desafios = new ArrayList<>(listaDesafios.values());
	    String out = "";

	    List<Desafio> ativos = new ArrayList<>();
	    for (Desafio d : desafios) {
	        if (d.desafioEmAndamento()) {
	            ativos.add(d);
	        }
	    }

	    for (int i = 0; i < ativos.size(); i++) {
	        Desafio desafio = ativos.get(i);
	        
	        out += desafio.toString() + "\n";
	        out += "Participantes: " + desafio.getNumeroDeParticipantes();

	        if (i != ativos.size() - 1) {
	            out += "\n";
	        }
	    }

	    return out;
	}
	
	
	public String finalizarDesafio(String idUsuario, String senha, String IdDesafio,
			UsuarioController uc){
		uc.autenticaUsuario(idUsuario, senha);
		
		Usuario usuario = uc.getUsuario(idUsuario);
		Desafio desafio = this.getDesafio(IdDesafio);
		
		if (!usuario.equals(desafio.getCriador()))
			return "Apenas o criador pode finalizar o desafio.";
		
		if (!desafio.encerra()) {
			return "Não é possível finalizar o desafio nesse momento.";
		}
		
		return "Desafio Finalizado";
	}
	
	
	// Calcula ranking
	public String calcularRankingDesafio(String idUsuario, String senha, String idDesafio, 
			UsuarioController uc) {
		// Checagens iniciais
		uc.autenticaUsuario(idUsuario, senha);
		this.autenticaDesafio(idDesafio);
		
		Desafio desafio = this.getDesafio(idDesafio);
		Usuario usuario = uc.getUsuario(idUsuario);		
		
		if (!desafio.temUsuario(usuario)) {
			return "Deve participar do desafio para calcular ranking.";
		}
		
		if (LocalDate.now().isBefore(desafio.getInicio())) {
		    return "O desafio ainda não começou.";
		}
		
		desafio.calculaRanking();
		return "Ranking calculado.";
	}
	
	
	// eu deveria implementar isso aqui em desafio, se der tempo
	public String visualizarRankingDesafio(String idUsuario, String senha, String idDesafio, 
			int topN, UsuarioController uc) {
		
		uc.autenticaUsuario(idUsuario, senha);
		this.autenticaDesafio(idDesafio);
		
		Desafio d = this.getDesafio(idDesafio);
		Usuario u = uc.getUsuario(idUsuario);
		
		if (!d.temUsuario(u)) {
			return "Deve participar do desafio para calcular ranking.";
		}
		
		String rank = d.getRanking();
		String[] linhas = rank.split("\n");
		String out = "";
		
		for (int i = 0; i < linhas.length && i < topN + 2; i++) {
		    out += linhas[i];
		    if (i != linhas.length - 1 && i != topN + 1) {
		        out += "\n";
		    }
		}
		
		return out;
	}

	
	// checa se o Desafio existe
	public void autenticaDesafio(String idDesafio) {
		if (this.listaDesafios.get(idDesafio) == null) {
			throw new IllegalArgumentException("Falha de autenticação Desafio");
		}
	}
	
	
	public boolean temUsuario(Desafio d, Usuario u) {
		return d.temUsuario(u);
	}

	// Gets -> não precisa de teste de unidade
	public Map<String, Desafio> getListaDesafios() {
		return listaDesafios;
	}
	
	public Desafio getDesafio(String IDDesafio) {
		return this.listaDesafios.get(IDDesafio);
	}
}