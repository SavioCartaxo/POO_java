package Classes;

import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Usuario {
	private String nome;
	private String email;
	private String senha;
	private String bio;
	private String ID;
	
	// CheckIn e Desafio
	private List<CheckIn> listaCheckIn;
	private List<Desafio> listaDesafios;
	
	
	// OK
	public Usuario(String nome, String email, String bio, String senha, String ID) {
		
		if (nome == null || email == null || bio == null || senha == null || ID == null) {
	        throw new IllegalArgumentException("Nenhum campo pode ser nulo");
	    }
		
		// TrataDados
		nome = nome.trim();
		email = email.trim();
		bio = bio.trim();
		ID = ID.trim();
				
		// Checa parametros vazios // Senha pode ser "      " sem problema -> Eu acho
		if (nome.isEmpty()) throw new IllegalArgumentException("Por favor, informe o nome");
	    if (email.isEmpty()) throw new IllegalArgumentException("Por favor, informe o email");
	    if (bio.isEmpty()) throw new IllegalArgumentException("Por favor, informe a bio");
	    if (ID.isEmpty()) throw new IllegalArgumentException("ID inválido");

		// checa o tamanho da senha
		if (senha.length() < 6) 
			throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
		
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.bio = bio;
		this.ID = ID;
		this.listaCheckIn = new ArrayList<>();
		this.listaDesafios = new ArrayList<>();
	}
	
	
	// OK
	public void ingressaNoDesafio(Desafio desafio) {
		if (listaDesafios.contains(desafio)) {return;}
		
		this.listaDesafios.add(desafio);
	}
	
	
	// OK
	public void adicionaCheckIn(CheckIn checkIn) {
		this.listaCheckIn.add(checkIn);
	}
	
	// OK
	public String listarCheckins() {
	    // Usuario nao esta em desafios
		if (this.listaDesafios.size() == 0)
	    	return "Usuário não participa de nenhum desafio.";
		
		// Usuario nao fez nenhum Chekin
		if (this.listaCheckIn.size() == 0)
			return "Ainda não houve registro de check-in.";
		
		List<CheckIn> lista = new ArrayList<>(this.listaCheckIn);

	    lista.sort(Comparator.comparing(CheckIn::getDataHora));
	    
	    String out = "";
	    for (int i = 0; i < lista.size(); i++) {
	        
	    	out += lista.get(i).toString() + '\n';
	        out += "Desafio: " + lista.get(i).getDesafio().getTitulo();
	        
	        if (i < lista.size() - 1) {
	            out += '\n';
	        }
	    }

	    return out;
	}
	
	
	public double calculaPontuacao(Desafio desafio) {
		ArrayList<CheckIn> listaCheckInDoDesafio = new ArrayList<>();
		
		for (CheckIn c : this.listaCheckIn) {
			if (desafio.equals(c.getDesafio())) {
				listaCheckInDoDesafio.add(c);
			}
		}
		
		return desafio.calculaPontuacaoIndividual(listaCheckInDoDesafio);
	}
	
	
	public String gerarRelatorioAtividades(LocalDate inicio, LocalDate fim) {
		String out = "";
		boolean gerou = false;
		ArrayList<CheckIn> listaC = new ArrayList<>();
		
		out += "Atividades de " + this.nome + " (" + inicio + " a " + fim +")\n";
		out += "--------------------";
		
		if (ChronoUnit.DAYS.between(inicio, fim) > 90) 
		    throw new IllegalArgumentException("Período máximo é 90 dias");

		// Como Chekin é inserido em ordem de chegada, não preciso organizar a ordem
		for (CheckIn c : this.listaCheckIn) {
			if (c.estaDentroDoPeriodo(inicio, fim)) {
				listaC.add(c);
				gerou = true;
			}
		}
		
		listaC.sort(
			    Comparator.comparing(CheckIn::getTituloDesafio) // ordena por título
			              .thenComparing(CheckIn::getDataHora)  // ordena por data/hora
			);
		
		for (int i = 0; i < listaC.size(); i++) {
			out += listaC.get(i).formataParaRelatorio();
			
			if (i < listaC.size() - 1) {
	            out += '\n';
	        }
		}
		
		if (!gerou)
			out = "";
		
		return out;
    }
	
	
	// OK
	public boolean participaDoDesafio(Desafio d) {
		return this.listaDesafios.contains(d);
	}
	
	
	// Gets e Sets -> não precisa de teste de unidade
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		if (senha.length() < 6)
			throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
		this.senha = senha;
	}

	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getID() {
		return this.ID;
	}
	
	public List<CheckIn> getListaCheckIn() {
		return listaCheckIn;
	}

	public List<Desafio> getListaDesafios() {
		return listaDesafios;
	}
	
	public int getCheckInsNoDesafio(Desafio desafio) {
	    int total = 0;
	    for (CheckIn c : this.listaCheckIn) {
	        if (c.getDesafio().equals(desafio)) {
	            total++;
	        }
	    }
	    return total;
	}
	

	//Overrides
	// OK
	@Override 
	public String toString() {
		String out = "";
		// [USR12] Marilene (marilene@splab.ufcg.edu.br) Corredora de rua e ama plantas.

		out += "[" + this.ID + "] ";
		out += this.nome + " ";
		out += "(" + this.email + ") ";
		out += this.bio;
		
		return out;
	}

	
	@Override
	// OK
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
	
}
