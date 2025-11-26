package Facade;

import java.time.*;
import Controller.*;
public class Facade {
	private UsuarioController uc;
	private DesafioController dc;
	private CheckInController cc; // resolvi criar a classe cc para aumentar coesão
	
	public Facade() {
		this.uc = new UsuarioController();
		this.dc = new DesafioController();
		this.cc = new CheckInController();
	}
	
	
	
	public String registrarUsuario(String nome, String email, String bio, String senha) {
		return uc.adicionaUsuario(nome, email, bio, senha);
	}
	
	
	public String editarPerfil(String idUsuario, String senha, String novaSenha, String novoNome,
            String novoEmail, String novaBio) {
		return uc.editarPerfil(idUsuario, senha, novaSenha, novoNome, novoEmail, novaBio);
	}

	
	public String listarUsuarios(String idUsuario, String senha) {
		return uc.listarUsuarios(idUsuario, senha);
	}

	
	public String criarDesafio(String idUsuario, String senha, String titulo, String descricao,
            LocalDate inicio, LocalDate fim, int metaDiariaMin, String estrategiaPontuacao) {
		
		return this.dc.criarDesafio(idUsuario, senha, titulo, descricao, inicio, fim, 
				metaDiariaMin, estrategiaPontuacao, this.uc);
	}
	
	
	public String IngressarNoDesafio(String idUsuario, String senha, String idDesafio) {
		return this.dc.IngressarNoDesafio(idUsuario, senha, idDesafio, this.uc);
	}
	
	
	public String listarDesafios(String idUsuario, String senha) {
		return dc.listaDesafios(idUsuario, senha, uc);
	}

	
	public String listarDesafiosAtivos(String idUsuario, String senha) {
		return dc.listarDesafiosAtivos(idUsuario, senha, uc);
	}

	
	public String registrarCheckin(String idUsuario, String senha, String idDesafio, 
			String dataHora, String atividade, int duracaoMin, String intensidade, 
			int calorias, int distancia) {
		
		return cc.adicionachekin(idUsuario, senha, idDesafio, dataHora, atividade,
				intensidade, duracaoMin, calorias, distancia, this.uc, this.dc);
	}

	
	public String listarCheckinsDoDesafio(String idUsuario, String senha, String idDesafio) {
		return cc.listarCheckinsDoDesafio(idUsuario, senha, idDesafio, this.dc, this.uc);
	}

	
	public String listarCheckinsDoUsuario(String idUsuario, String senha, String idUsuarioList) {
		return cc.listarCheckinsDoUsuario(idUsuario, senha, idUsuarioList, this.uc);
	}

	
	public String finalizarDesafio(String idUsuario, String senha, String idDesafio) {
		return dc.finalizarDesafio(idUsuario, senha, idDesafio, this.uc);
	}

	
	public String calcularRankingDesafio(String idUsuario, String senha, String idDesafio) {
	    return dc.calcularRankingDesafio(idUsuario, senha, idDesafio, this.uc);
	}

	
	public String visualizarRankingDesafio(String idUsuario, String senha, String idDesafio, int topN) {
		return dc.visualizarRankingDesafio(idUsuario,senha, idDesafio, topN, this.uc);
	}

	
	public String gerarRelatorioAtividades(String idUsuario, String senha, LocalDate inicio, LocalDate fim){
		return uc.gerarRelatorioAtividades(idUsuario, senha, inicio, fim);
	}
	
	
	public void curtirCheckin(String idUsuario, String senha, String idDesafio, String idCheckin) {
		cc.curtirCheckin(idUsuario, senha, idDesafio, idCheckin, dc, uc);
	}
	
	
	public String comentarCheckin(String idUsuario, String senha, String idDesafio, String idCheckin, String comentario) {
		return cc.comentarCheckin(idUsuario, senha, idDesafio, idCheckin, dc, uc, comentario);
	}
		
}