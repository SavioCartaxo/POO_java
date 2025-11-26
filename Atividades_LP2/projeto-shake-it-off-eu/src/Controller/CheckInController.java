package Controller;

import java.util.HashMap;
import java.util.Map;

import Classes.CheckIn;
import Classes.Desafio;
import Classes.Usuario;

public class CheckInController {

	private Map<String, CheckIn> listaCheckins;
	
	public CheckInController(){
		this.listaCheckins = new HashMap<>();
	}
	
	
	// Cria CheckIn
	public String  adicionachekin(String idUsuario, String senha,
			String idDesafio, String dataHora, String atividade, String intensidade, 
			int duracao, int calorias, int distancia, 
			UsuarioController uc, DesafioController dc){
		
		uc.autenticaUsuario(idUsuario, senha);
		dc.autenticaDesafio(idDesafio);
		
		Desafio desafio = dc.getDesafio(idDesafio);
		Usuario usuario = uc.getUsuario(idUsuario);
		
		// usuario nao esta escrito no desafio
		if (!desafio.temUsuario(usuario)) {
			return "Usuario não esta registrado no desafio.";
		}
		
		// Desafio nao esta em andamento
		if (!desafio.desafioEmAndamento()) {
			return "Desafio não está em andamento no momento";
		}
		
		String ID = "CHK" + this.listaCheckins.size() + 1;
		CheckIn checkin = new CheckIn(ID, dataHora, atividade, duracao, intensidade, 
				calorias, distancia, usuario, desafio);
		
		this.listaCheckins.put(ID,checkin);
		
		// Um pequeno problema da minha arquiteturta, mas preferi fazer assim mesmo para aumentar coesão
		desafio.AdicionaCheckIn(checkin);
		usuario.adicionaCheckIn(checkin);
		
		return ID;
	}
	
	
	// lista Todos os CheckIns do desafio
	public String listarCheckinsDoDesafio(String idUsuario, String senha, String idDesafio, 
			DesafioController dc, UsuarioController uc){
		uc.autenticaUsuario(idUsuario, senha);
		dc.autenticaDesafio(idDesafio);
		
		Desafio desafio = dc.getDesafio(idDesafio);
		return desafio.listaCheckins();
	}
	
	
	public String listarCheckinsDoUsuario(String idUsuario, String senha, String idUsuarioList,
			UsuarioController uc){
		uc.autenticaUsuario(idUsuario, senha);
		
		Usuario usuario = uc.getUsuario(idUsuarioList);
		return usuario.listarCheckins();
	}

	
	public void autenticaCheckIn(String id) {
		if (!this.listaCheckins.containsKey(id)) {
			throw new IllegalArgumentException("Check-in inexistente");
		}
	}
	
	public void curtirCheckin(String idUsuario, String senha, String idDesafio, String idCheckin,
			DesafioController dc, UsuarioController uc) {
		
		uc.autenticaUsuario(idUsuario, senha);
		dc.autenticaDesafio(idDesafio);
		this.autenticaCheckIn(idCheckin);
		
		CheckIn c = this.listaCheckins.get(idCheckin);
		c.curtirCheckin(uc.getUsuario(idUsuario));
	}
	
	
	public String comentarCheckin(String idUsuario, String senha, String idDesafio, String idCheckin,
			DesafioController dc, UsuarioController uc, String comentario) {
		uc.autenticaUsuario(idUsuario, senha);
		dc.autenticaDesafio(idDesafio);
		this.autenticaCheckIn(idCheckin);
		
		Usuario u = uc.getUsuario(idUsuario);
		CheckIn c = this.listaCheckins.get(idCheckin);
		return c.adicionaComentario(u, comentario);
	}
	
	
	// Get -> não precisa de teste de unidade
	public Map<String, CheckIn> getListaCheckins() {
		return listaCheckins;
	}
}
