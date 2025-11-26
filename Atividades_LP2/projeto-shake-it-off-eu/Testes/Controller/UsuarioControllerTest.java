package Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.CheckIn;
import Classes.Desafio;
import Classes.Usuario;

class UsuarioControllerTest {
	
	private UsuarioController uc;
	
	@BeforeEach
	void prepara() {
		uc = new UsuarioController();
	}
	
	
	// adicionaUsuario
	@Test
	void adicionaUsuarioComSucesso() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    assertEquals("USR1", id);
	    assertNotNull(uc.getUsuario(id));
	}

	@Test
	void adicionaUsuarioComEmailsDiferentes() {
	    String id1 = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    String id2 = uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio outra", "senha123");

	    assertEquals("USR1", id1);
	    assertEquals("USR2", id2);
	    assertNotEquals(id1, id2);
	}

	@Test
	void adicionaUsuarioComEmailDuplicado() {
	    uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    String resultado = uc.adicionaUsuario("Maria", "jota@gmail.com", "Bio outra", "senha123");

	    assertEquals("Email já cadastrado.", resultado);
	}

	@Test
	void getUsuarioExistentePorId() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    Usuario u = uc.getUsuario(id);
	    assertNotNull(u);
	    assertEquals("Jota", u.getNome());
	}

	@Test
	void getUsuarioInexistenteRetornaNull() {
	    Usuario u = uc.getUsuario("USR99");
	    assertNull(u);
	}
	
	
	// editarPerfil
	@Test
	void alteraPerfilComSucesso() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");

	    String resultado = uc.editarPerfil(id, "senha123", "novaSenha1", "Novo Jota", "jota2@gmail.com", "Nova bio");

	    Usuario u = uc.getUsuario(id);
	    assertEquals("Perfil atualizado", resultado);
	    assertEquals("Novo Jota", u.getNome());
	    assertEquals("jota2@gmail.com", u.getEmail());
	    assertEquals("Nova bio", u.getBio());
	    assertEquals("novaSenha1", u.getSenha());
	}

	@Test
	void editarPerfilComEmailExistenteRetornaErro() {
	    String id1 = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio Maria", "senha456");

	    String resultado = uc.editarPerfil(id1, "senha123", "novaSenha1", "Novo Jota", "maria@gmail.com", "Nova bio");

	    assertEquals("Não foi possível atualizar o perfil: email já cadastrado.", resultado);
	}

	@Test
	void editarPerfilComSenhaCurtaLancaExcecao() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");

	    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
	            uc.editarPerfil(id, "senha123", "123", "Novo Jota", "novo@gmail.com", "Nova bio")
	    );

	    assertEquals("A senha deve ter pelo menos 6 caracteres", ex.getMessage());
	}

	@Test
	void editarPerfilComNomeVazioNaoAlteraNome() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");

	    uc.editarPerfil(id, "senha123", "novaSenha1", "", "novo@gmail.com", "Nova bio");

	    Usuario u = uc.getUsuario(id);
	    assertEquals("Jota", u.getNome());
	}

	@Test
	void editarPerfilComEmailVazioNaoAlteraEmail() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");

	    uc.editarPerfil(id, "senha123", "novaSenha1", "Novo Jota", "", "Nova bio");

	    Usuario u = uc.getUsuario(id);
	    assertEquals("jota@gmail.com", u.getEmail());
	}

	@Test
	void editarPerfilComBioVaziaNaoAlteraBio() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");

	    uc.editarPerfil(id, "senha123", "novaSenha1", "Novo Jota", "novo@gmail.com", "");

	    Usuario u = uc.getUsuario(id);
	    assertEquals("Bio teste", u.getBio());
	}

	
	//listarUsuarios
	@Test
	public void listarUsuariosRetornaEmOrdemAlfabetica() {
	    String id1 = uc.adicionaUsuario("Zeca", "zeca@gmail.com", "Bio Zeca", "senha123");
	    String lista = uc.listarUsuarios(id1, "senha123");

	    assertTrue(lista.indexOf("Carlos") < lista.indexOf("Zeca"));
	}

	@Test
	public void listarUsuariosRetornaToStringDeCadaUsuario() {
	    String id = uc.adicionaUsuario("Bia", "bia@gmail.com", "Bio Bia", "senha123");
	    String lista = uc.listarUsuarios(id, "senha123");

	    assertTrue(lista.contains("Bia"));
	    assertTrue(lista.contains("bia@gmail.com"));
	}

	@Test
	public void listarUsuariosLancaErroSeUsuarioNaoAutenticado() {
	    String id = uc.adicionaUsuario("Lucas", "lucas@gmail.com", "Bio Lucas", "senha123");

	    assertThrows(IllegalArgumentException.class, () -> {
	        uc.listarUsuarios(id, "senhaErrada");
	    });
	}

	@Test
	public void listarUsuariosComUmUnicoUsuarioRetornaApenasEsseUsuario() {
	    String id = uc.adicionaUsuario("Paulo", "paulo@gmail.com", "Bio Paulo", "senha123");

	    String lista = uc.listarUsuarios(id, "senha123");

	    assertTrue(lista.contains("Paulo"));
	    assertEquals(lista, uc.getUsuario(id).toString());
	}
	

	// autenticaUsuario
	@Test
	public void autenticaUsuarioComCredenciaisValidasRetornaTrue() {
	    String id = uc.adicionaUsuario("João", "joao@gmail.com", "Bio João", "senha123");

	    assertTrue(uc.autenticaUsuario(id, "senha123"));
	}

	@Test
	public void autenticaUsuarioComSenhaInvalidaLancaExcecao() {
	    String id = uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio Maria", "senha123");

	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	        uc.autenticaUsuario(id, "senhaErrada");
	    });

	    assertEquals("Usuario nao autenticado", thrown.getMessage());
	}

	
	// jaExisteUsuarioComEsseEmail
	@Test
	public void retornaTrueQuandoEmailJaExiste() {
	    uc.adicionaUsuario("João", "joao@gmail.com", "Bio João", "senha123");

	    assertTrue(uc.jaExisteUsuarioComEsseEmail("joao@gmail.com"));
	}

	@Test
	public void retornaFalseQuandoEmailNaoExiste() {
	    uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio Maria", "senha123");

	    assertFalse(uc.jaExisteUsuarioComEsseEmail("naoexiste@gmail.com"));
	}

	
	//
	@Test
	public void gerarRelatorioAtividadesValido() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    String senha = "senha123";

	    Usuario u = uc.getUsuario(id);

	    LocalDate inicioDesafio = LocalDate.now().minusDays(1);
	    LocalDate fimDesafio = LocalDate.now().plusDays(1);
	    Desafio d = new Desafio("Corrida", "Teste Relatório", inicioDesafio, fimDesafio, 10, "FREQUENCIA", "DSX", u);
	    u.ingressaNoDesafio(d);

	    String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	    CheckIn c = new CheckIn("CHK1", dataHora, "Corrida", 30, "MODERADA", 300, 5, u, d);
	    u.adicionaCheckIn(c);

	    LocalDate inicio = LocalDate.now().minusDays(5);
	    LocalDate fim = LocalDate.now();

	    String relatorio = uc.gerarRelatorioAtividades(id, senha, inicio, fim);

	    assertNotNull(relatorio);
	    assertTrue(relatorio.contains("Atividades de " + u.getNome()));
	    assertTrue(relatorio.contains("Corrida"));
	}


	@Test
	public void gerarRelatorioAtividadesPeriodoInvalido() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    String senha = "senha123";

	    LocalDate inicio = LocalDate.now();
	    LocalDate fim = LocalDate.now().minusDays(1);

	    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
	        uc.gerarRelatorioAtividades(id, senha, inicio, fim);
	    });

	    assertEquals("Período inválido: a data final deve ser igual ou maior que a data inicial.", ex.getMessage());
	}

	@Test
	public void gerarRelatorioAtividadesPeriodoMaiorQue90Dias() {
	    String id = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio teste", "senha123");
	    String senha = "senha123";

	    LocalDate inicio = LocalDate.now().minusDays(100);
	    LocalDate fim = LocalDate.now();

	    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
	        uc.gerarRelatorioAtividades(id, senha, inicio, fim);
	    });

	    assertEquals("Período máximo é 90 dias", ex.getMessage());
	}


}
