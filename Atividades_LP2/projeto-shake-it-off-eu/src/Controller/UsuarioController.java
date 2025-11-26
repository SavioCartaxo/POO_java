package Controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import Classes.Usuario;

public class UsuarioController {
	private Map<String, Usuario> listaUsuarios; // ID, Usuario
	
	
	public UsuarioController() {
		this.listaUsuarios = new HashMap<>();
	}
	
	
	// OK
	// Cria Usuário e adiciona na lista
	public String adicionaUsuario(String nome, String email, String bio, String senha) {
		
		// checa se email ja existe
		if (this.listaUsuarios.values().stream()
		        .anyMatch(u -> u.getEmail().equals(email.trim()))) {
		    return "Email já cadastrado.";
		}

		String ID = "USR" + (listaUsuarios.size() + 1);
		Usuario usuario = new Usuario(nome, email, bio, senha, ID);
	
		this.listaUsuarios.put(ID, usuario);
		return ID;
	}
	
	
	// Atualiza informações do perfil
	// OK
	public String editarPerfil(String idUsuario, String senha, String novaSenha, String novoNome, 
			String novoEmail,String novaBio) {
		
		this.autenticaUsuario(idUsuario, senha);
		Usuario u = this.getUsuario(idUsuario);
		
		//checagem
		novoNome = novoNome.trim();
		novoEmail = novoEmail.trim();
		novaBio = novaBio.trim();
		
		
		// Checagens
		// checa se a senha cumpre os requisitos
		if (novaSenha.length() < 6) 
			throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
		// checa se a email ainda não existe
		if (this.jaExisteUsuarioComEsseEmail(novoEmail)) 
			return "Não foi possível atualizar o perfil: email já cadastrado.";
		
		
		// Atualiza
		if (!novaSenha.isEmpty())
			u.setSenha(novaSenha);
			
		if (!novoNome.isEmpty())
		    u.setNome(novoNome);

		if (!novoEmail.isEmpty())
		    u.setEmail(novoEmail);

		if (!novaBio.isEmpty())
		    u.setBio(novaBio);
		
		return "Perfil atualizado";
	}
	
	
	// OK
	// Lista usuários em ordem alfabética, utilizando o toString de Usuário
	public String listarUsuarios(String idUsuario,String senha) {
		this.autenticaUsuario(idUsuario, senha);
		
		// preparando ordenação
		List<Usuario> usuarios = new ArrayList<>(this.listaUsuarios.values());
		usuarios.sort(Comparator.comparing(Usuario::getNome));
		
		// Organizando saida
		String out = "";
		
		for (int i = 0; i < usuarios.size(); i++) {
			out += usuarios.get(i).toString();
			if (i < usuarios.size() - 1)
				out += '\n';			
		}
		
		return out;
	}
	
	
	public String gerarRelatorioAtividades(String idUsuario, String senha, LocalDate inicio, 
			LocalDate fim) {
		this.autenticaUsuario(idUsuario, senha);
		
		if (fim.isBefore(inicio))
		    throw new IllegalArgumentException("Período inválido: a data final deve ser igual ou maior que a data inicial.");
		
		if (ChronoUnit.DAYS.between(inicio, fim) > 90) 
		    throw new IllegalArgumentException("Período máximo é 90 dias");
		
		Usuario u = this.getUsuario(idUsuario);
		
		return u.gerarRelatorioAtividades(inicio, fim);
	}
	
	
	// Metodos de apoio
	
	// Retorna True se usuario for autenticado, caso contrário lança exceção
	// OK
	public boolean autenticaUsuario(String idUsuario, String senha) {
		Usuario usuario = this.getUsuario(idUsuario);
		if (usuario.getSenha().equals(senha)) {
			return true;
		}
			
		throw new IllegalArgumentException("Usuario nao autenticado");
	}
	
	// OK
	public boolean jaExisteUsuarioComEsseEmail(String email) {
		boolean existe = false;
		for (Usuario u : this.listaUsuarios.values()) {
		    if (u.getEmail().equals(email)) {
		        existe = true;
		        break;
		    }
		}
		return existe;
	}
	
	// Gets -> não precisa de teste
	public String getUsuarioNome(String idUsuario, String senha) {
		this.autenticaUsuario(idUsuario, senha);
		return this.getUsuario(idUsuario).getNome();
	}
	
	public Usuario getUsuario(String ID) {
		return this.listaUsuarios.get(ID);
	}

	public Map<String, Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	
}
