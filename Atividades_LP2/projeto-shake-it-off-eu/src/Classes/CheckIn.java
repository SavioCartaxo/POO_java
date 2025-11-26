package Classes;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class CheckIn {
	private int duracaoMin;
	private int calorias;
	private int distancia;
	private String ID;
	private String atividade;
	private String intensidade;
	private LocalDateTime dataHora;
	private Usuario usuario;
	private Desafio desafio;
	private ArrayList<String> comentarios;
	private ArrayList<Usuario> listaCurtidas;
	
	
	// OK
	public CheckIn(String ID, String dataHora, String atividade,int duracaoMin, String intensidade, 
			int calorias, int distancia, Usuario usuario, Desafio desafio) {
		
		// Tratar todas as Strings
	    ID = ID.trim();
	    dataHora = dataHora.trim();
	    atividade = atividade.trim();
	    intensidade = intensidade.trim().toUpperCase();
	    
	    
	    // Validações de Strings obrigatórias
	    if (ID.isEmpty()) {
	        throw new IllegalArgumentException("O ID do check-in não pode ser vazio");
	    }
	    
	    if (dataHora.isEmpty()) {
	        throw new IllegalArgumentException("A data/hora do check-in não pode ser vazia");
	    }
	    
	    if (atividade.isEmpty()) {
	        throw new IllegalArgumentException("A atividade do check-in não pode ser vazia");
	    }
	    
	    if (intensidade.isEmpty()) {
	        throw new IllegalArgumentException("A intensidade do check-in não pode ser vazia");
	    }

		// Intensidade precisa ser de uma dessas classes
		if (!intensidade.equals("LEVE") &&
			!intensidade.equals("MODERADA") &&
			!intensidade.equals("ALTA") &&
			!intensidade.equals("")
				) {
			throw new IllegalArgumentException("Intensidade Não cumpre requisitos");
		}
		
		// Calorias não podem ser negativas
		if (calorias < 0) {
			throw new IllegalArgumentException("Calorias precisam ser maior ou igual a 0");
		}
		
		// Duração não pode ser negativa
		if (duracaoMin < 0) {
			throw new IllegalArgumentException("Calorias precisam ser maior ou igual a 0");
		}
		
		// Distaância não pode ser negativa
		if (distancia < 0) {
			throw new IllegalArgumentException("Distancia precisam ser maior ou igual a 0");
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");   
	    LocalDateTime ldt = LocalDateTime.parse(dataHora, formatter);
	    LocalDateTime agora = LocalDateTime.now();
	    
	    //Verifica se o check-in é hoje
	    if (!ldt.toLocalDate().equals(agora.toLocalDate()))
	         throw new IllegalArgumentException("O check-in deve ser realizado hoje");

	    // Verifica se está dentro do período do desafio
	    if (ldt.isBefore(desafio.getInicio().atStartOfDay()) 
	    		|| ldt.isAfter(desafio.getFim().atTime(23, 59, 59))) {
	         throw new IllegalArgumentException("O check-in deve estar dentro do período do desafio");
	    }
	     
		this.ID = ID;
		this.dataHora = ldt;
		this.atividade = atividade;
		this.duracaoMin = duracaoMin;
		this.intensidade = intensidade;
		this.calorias = calorias;
		this.distancia = distancia;
		this.usuario = usuario;
		this.desafio = desafio;
		this.listaCurtidas = new ArrayList<>();
		this.comentarios = new ArrayList<>();
	}
	
	
	// OK
	public boolean estaDentroDoPeriodo(LocalDate i, LocalDate f) {
		 LocalDate data = this.dataHora.toLocalDate();
		 return ( !data.isBefore(i) ) && ( !data.isAfter(f) );
	}
	

	// OK
	public String formataParaRelatorio() {
	    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    String out = "";

	    out += dataHora.format(df) + " ";
	    out += desafio.getTitulo() + " ";
	    out += "#" + ID + " ";
	    out += atividade + " ";
	    out += duracaoMin + "min ";
	    out += intensidade;

	    return out;
	}
	
	
	// OK
	public String curtirCheckin(Usuario usuario) {
		if (!usuario.participaDoDesafio(this.desafio)) 
			return "Deve participar do desafio para curtir check in dele.";
		
		if (this.listaCurtidas.contains(usuario)) 
			return "Um usuário só pode curtir uma vez um check-in.";
		
		this.listaCurtidas.add(usuario);
		return "Like adicionado";
	}
	
	
	// OK
	public String adicionaComentario(Usuario usuario, String txt) {
		if (!usuario.participaDoDesafio(this.desafio)) 
			return "Deve participar do desafio para comentar check in dele.";
		
		if (txt.trim().equals(""))
			return "Comentário não pode ser vazio.";
		
		String s = " - " + usuario.getID() + ' ' + usuario.getNome() + ' ' + txt;
		this.comentarios.add(s);
		
		return this.formataComentarios();
	}
	
	
	// OK
	public String formataComentarios() {
	    String out = "";
	    out += this.toString() + "\n";
	    out += "Comentários:\n";
	    
	    for (int i = 0; i < this.comentarios.size(); i++) {
	        out += this.comentarios.get(i);
	        if (i != this.comentarios.size() - 1) {
	            out += "\n";
	        }
	    }

	    return out;
	}
	
	
	// Gets e Sets -> não precisa de teste de unidade
	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public int getDuracao() {
		return duracaoMin;
	}

	public String getIntensidade() {
		return intensidade;
	}

	public int getDistancia() {
		return distancia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Desafio getDesafio() {
		return this.desafio;
	}
	
	public String getTituloDesafio() {
		return this.desafio.getTitulo();
	}
	
	public int getDuracaoMin() {
		return duracaoMin;
	}

	public int getCalorias() {
		return calorias;
	}

	public String getID() {
		return ID;
	}

	public String getAtividade() {
		return atividade;
	}

	public ArrayList<String> getComentarios() {
		return comentarios;
	}

	public ArrayList<Usuario> getListaCurtidas() {
		return listaCurtidas;
	}
	
	public int getCurtidas() {
		return this.listaCurtidas.size();
	}


	// Overrides
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return String.format("[%s] %s — [%s] %s — %s — %dmin — INTENSIDADE: %s — %d kcal\nlikes: %s",
	            this.ID,
	            this.dataHora.format(formatter),
	            this.usuario.getID(),
	            this.usuario.getNome(),
	            this.atividade,
	            this.duracaoMin,
	            this.intensidade.toUpperCase(),
	            this.calorias,
	            this.listaCurtidas.size());
	}


	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckIn other = (CheckIn) obj;
		return Objects.equals(ID, other.ID);
	}
	
}
