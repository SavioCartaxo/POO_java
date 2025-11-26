package Classes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckInTest {

	private Usuario u1;
	private Usuario u2;
	private Desafio d1;
	private CheckIn c1;
	
	@BeforeEach
	public void inicia() {
	    u1 = new Usuario("Jota", "Jota@gmail.com", "Ola! sou monitor de P2", "senha123", "USR1");
	    u2 = new Usuario("Eliane", "Eliane@gmail.com", "Ola! sou professora de P2", "elinda1", "USR2");

	    LocalDate inicio = LocalDate.now().minusDays(1);
	    LocalDate fim = LocalDate.now().plusDays(1);

	    d1 = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS1", u1);
	    u1.ingressaNoDesafio(d1);
	    
	    String dataHoraStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	    c1 = new CheckIn("CHK1", dataHoraStr, "Corrida", 30, "MODERADA", 300, 5, u1, d1);

	}

	// Construtor
	@Test
	public void checkInValido() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    CheckIn checkIn = new CheckIn("CHK1", dataHora, "Corrida", 30, "MODERADA",
	                                    200, 5, u1, d1);

	    assertNotNull(checkIn);
	    assertEquals("CHK1", checkIn.getID());
	    assertEquals("Corrida", checkIn.getAtividade());
	    assertEquals("MODERADA", checkIn.getIntensidade());
	}
	
	@Test
	public void checkInIDVazio() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("   ", dataHora, "Corrida", 30, "MODERADA", 200, 5, u1, d1);
	    });
	}

	@Test
	public void checkInDataHoraVazia() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", "  ", "Corrida", 30, "MODERADA", 200, 5, u1, d1);
	    });
	}

	@Test
	public void checkInAtividadeVazia() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "   ", 30, "MODERADA", 200, 5, u1, d1);
	    });
	}

	@Test
	public void checkInIntensidadeVazia() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", 30, "   ", 200, 5, u1, d1);
	    });
	}
	
	@Test
	public void checkInIntensidadeInvalida() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", 30, "EXTREMA", 200, 5, u1, d1);
	    });
	}
	
	@Test
	public void checkInCaloriasNegativas() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", 30, "MODERADA", -1, 5, u1, d1);
	    });
	}

	@Test
	public void checkInDuracaoNegativa() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", -10, "MODERADA", 200, 5, u1, d1);
	    });
	}

	@Test
	public void checkInDistanciaNegativa() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHora = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", 30, "MODERADA", 200, -5, u1, d1);
	    });
	}

	@Test
	public void checkInDataHoraNaoHoje() {
	    LocalDateTime ontem = LocalDateTime.now().minusDays(1);
	    String dataHora = ontem.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", 30, "MODERADA", 200, 5, u1, d1);
	    });
	}

	@Test
	public void checkInAntesDoInicioDesafio() {
	    LocalDateTime antesDoInicio = d1.getInicio().minusDays(1).atStartOfDay();
	    String dataHora = antesDoInicio.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", 30, "MODERADA", 200, 5, u1, d1);
	    });
	}

	@Test
	public void checkInDepoisDoFimDesafio() {
	    LocalDateTime depoisDoFim = d1.getFim().plusDays(1).atStartOfDay();
	    String dataHora = depoisDoFim.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	    assertThrows(IllegalArgumentException.class, () -> {
	        new CheckIn("CHK1", dataHora, "Corrida", 30, "MODERADA", 200, 5, u1, d1);
	    });
	}

	
	// estaDentroDoPeriodo
	@Test
	public void periodoDentroDoIntervalo() {
	    LocalDate inicio = LocalDate.now().minusDays(1);
	    LocalDate fim = LocalDate.now().plusDays(1);

	    assertTrue(c1.estaDentroDoPeriodo(inicio, fim)); // dataHora está dentro
	}

	@Test
	public void periodoNoInicioDoIntervalo() {
	    LocalDate inicio = c1.getDataHora().toLocalDate(); // igual ao início
	    LocalDate fim = LocalDate.now().plusDays(1);

	    assertTrue(c1.estaDentroDoPeriodo(inicio, fim));
	}

	@Test
	public void periodoNoFimDoIntervalo() {
	    LocalDate inicio = LocalDate.now().minusDays(2);
	    LocalDate fim = c1.getDataHora().toLocalDate(); // igual ao fim

	    assertTrue(c1.estaDentroDoPeriodo(inicio, fim));
	}

	@Test
	public void periodoAntesDoInicio() {
	    LocalDate inicio = LocalDate.now();
	    LocalDate fim = LocalDate.now().plusDays(1);

	    assertFalse(c1.estaDentroDoPeriodo(inicio.plusDays(1), fim));
	}

	@Test
	public void periodoDepoisDoFim() {
	    LocalDate inicio = LocalDate.now().minusDays(5);
	    LocalDate fim = LocalDate.now().minusDays(1);

	    assertFalse(c1.estaDentroDoPeriodo(inicio, fim));
	}

	
	// formataParaRelatorio
	@Test
	public void formataParaRelatorioFormatoCorreto() {
	    LocalDateTime agora = LocalDateTime.now();
	    String dataHoraFormatada = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	    CheckIn c = new CheckIn("CHK1", dataHoraFormatada, "Corrida", 30, "ALTA", 300, 5, u1, d1);

	    String dataFormatada = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	    String esperado = dataFormatada + " " + d1.getTitulo() + " #CHK1 Corrida 30min ALTA";

	    assertEquals(esperado, c.formataParaRelatorio());
	}


	@Test
	public void formataParaRelatorioComOutraAtividade() {
	    CheckIn c = new CheckIn("CHK3", LocalDateTime.now().format(
	    		DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), "Musculação", 45, "MODERADA", 500, 2, u1, d1);

	    String dataFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	    String esperado = dataFormatada + " " + d1.getTitulo() + " #CHK3 Musculação 45min MODERADA";

	    assertEquals(esperado, c.formataParaRelatorio());
	}
	
	// curtirCheckin
	@Test
	public void curtirCheckinUsuarioNaoParticipa() {
	    Usuario u3 = new Usuario("mari", "MAr@gmail.com", "Ola! não sou monitora de P2", "senha123", "USR2");
	    String resultado = c1.curtirCheckin(u3);
	    assertEquals("Deve participar do desafio para curtir check in dele.", resultado);
	}

	@Test
	public void curtirCheckinUsuarioJaCurtiu() {
	    u1.ingressaNoDesafio(d1);
	    String resultado1 = c1.curtirCheckin(u1);
	    String resultado2 = c1.curtirCheckin(u1);

	    assertEquals("Like adicionado", resultado1);
	    assertEquals("Um usuário só pode curtir uma vez um check-in.", resultado2);
	}

	@Test
	public void curtirCheckinPrimeiraVezValido() {
	    u1.ingressaNoDesafio(d1);
	    String resultado = c1.curtirCheckin(u1);

	    assertEquals("Like adicionado", resultado);
	    assertTrue(c1.getListaCurtidas().contains(u1));
	}
	
	@Test
	public void curtidaNaoPermitidaSeUsuarioNaoParticipa() {
	    Usuario maria = new Usuario("Maria", "maria@gmail.com", "Bio da Maria", "senha123", "U3"); // não ingressa no desafio

	    String resultado = c1.curtirCheckin(maria);

	    assertEquals("Deve participar do desafio para curtir check in dele.", resultado);
	    assertEquals(0, c1.getCurtidas());
	    assertFalse(c1.getListaCurtidas().contains(maria));
	}

	@Test
	public void usuarioNaoPodeCurtirDuasVezes() {
	    u1.ingressaNoDesafio(d1);

	    String primeiraVez = c1.curtirCheckin(u1);
	    String segundaVez = c1.curtirCheckin(u1);

	    assertEquals("Like adicionado", primeiraVez);
	    assertEquals("Um usuário só pode curtir uma vez um check-in.", segundaVez);
	    assertEquals(1, c1.getCurtidas()); 
	    assertTrue(c1.getListaCurtidas().contains(u1));
	}

	@Test
	public void curtidaValidaDeParticipante() {
	    u2.ingressaNoDesafio(d1);

	    String resultado = c1.curtirCheckin(u2);

	    assertEquals("Like adicionado", resultado);
	    assertEquals(1, c1.getCurtidas());
	    assertTrue(c1.getListaCurtidas().contains(u2));
	}

	@Test
	public void multiplosUsuariosCurtindo() {
	    u1.ingressaNoDesafio(d1);
	    u2.ingressaNoDesafio(d1);

	    String r1 = c1.curtirCheckin(u1);
	    String r2 = c1.curtirCheckin(u2);

	    assertEquals("Like adicionado", r1);
	    assertEquals("Like adicionado", r2);
	    assertEquals(2, c1.getCurtidas());
	    assertTrue(c1.getListaCurtidas().contains(u1));
	    assertTrue(c1.getListaCurtidas().contains(u2));
	}

	
	// adicionaComentario
	@Test
	public void comentarioNaoPermitidoSeUsuarioNaoParticipa() {
	    Usuario maria = new Usuario("Maria", "maria@gmail.com", "Bio da Maria", "senha123", "U3"); // não ingressa no desafio

	    String resultado = c1.adicionaComentario(maria, "Muito bom!");

	    assertEquals("Deve participar do desafio para comentar check in dele.", resultado);
	    assertTrue(c1.getComentarios().isEmpty());
	}

	@Test
	public void comentarioNaoPodeSerVazio() {
	    u1.ingressaNoDesafio(d1);

	    String resultado = c1.adicionaComentario(u1, "   "); // apenas espaços

	    assertEquals("Comentário não pode ser vazio.", resultado);
	    assertTrue(c1.getComentarios().isEmpty());
	}

	@Test
	public void comentarioValidoDeParticipante() {
	    u1.ingressaNoDesafio(d1);

	    String resultado = c1.adicionaComentario(u1, "Boa corrida!");

	    assertTrue(resultado.contains("Boa corrida!"));
	    assertEquals(1, c1.getComentarios().size());
	    assertTrue(c1.getComentarios().get(0).contains(u1.getID()));
	}

	@Test
	public void variosComentariosDeUsuarios() {
	    u1.ingressaNoDesafio(d1);
	    u2.ingressaNoDesafio(d1);

	    String r1 = c1.adicionaComentario(u1, "Bom treino!");
	    String r2 = c1.adicionaComentario(u2, "Parabéns!");

	    assertTrue(r1.contains("Bom treino!"));
	    assertTrue(r2.contains("Parabéns!"));
	    assertEquals(2, c1.getComentarios().size());
	    assertTrue(c1.getComentarios().get(0).contains(u1.getID()));
	    assertTrue(c1.getComentarios().get(1).contains(u2.getID()));
	}
	
	
	// formataComentarios
	@Test
	void formatoSemComentarios() {
	    String esperado = c1.toString() + "\nComentários:\n";
	    assertEquals(esperado, c1.formataComentarios());
	}

	@Test
	void formatoUmComentario() {
	    c1.getComentarios().add(" - USR1 Jota Muito bom treino!");
	    
	    String esperado = c1.toString() + "\nComentários:\n - USR1 Jota Muito bom treino!";
	    assertEquals(esperado, c1.formataComentarios());
	}

	@Test
	void formatoVariosComentarios() {
	    c1.getComentarios().add(" - USR1 Jota Primeira mensagem");
	    c1.getComentarios().add(" - USR2 Eliane Comentário extra");
	    
	    String esperado = c1.toString() + "\nComentários:\n"
	            + " - USR1 Jota Primeira mensagem\n - USR2 Eliane Comentário extra";
	    
	    assertEquals(esperado, c1.formataComentarios());
	}

	@Test
	void formatoMantemOrdem() {
	    c1.getComentarios().add(" - USR1 Jota Primeiro");
	    c1.getComentarios().add(" - USR2 Eliane Segundo");
	    c1.getComentarios().add(" - USR1 Jota Terceiro");
	    
	    String resultado = c1.formataComentarios();
	    assertTrue(resultado.contains("Primeiro\n - USR2 Eliane Segundo\n - USR1 Jota Terceiro"));
	}


	// toString
	@Test
	void checaFormatoToString() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String dataFormatada = c1.getDataHora().format(formatter);

	    String esperado = String.format("[%s] %s — [%s] %s — %s — %dmin — INTENSIDADE: %s — %d kcal\nlikes: %s",
	            c1.getID(),
	            dataFormatada,
	            c1.getUsuario().getID(),
	            c1.getUsuario().getNome(),
	            c1.getAtividade(),
	            c1.getDuracaoMin(),
	            c1.getIntensidade().toUpperCase(),
	            c1.getCalorias(),
	            c1.getListaCurtidas().size());

	    assertEquals(esperado, c1.toString());
	}

	@Test
	void toStringComCurtidas() {
	    u2.ingressaNoDesafio(d1);
	    c1.curtirCheckin(u2);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String dataFormatada = c1.getDataHora().format(formatter);

	    String esperado = String.format("[%s] %s — [%s] %s — %s — %dmin — INTENSIDADE: %s — %d kcal\nlikes: %s",
	            c1.getID(),
	            dataFormatada,
	            c1.getUsuario().getID(),
	            c1.getUsuario().getNome(),
	            c1.getAtividade(),
	            c1.getDuracaoMin(),
	            c1.getIntensidade().toUpperCase(),
	            c1.getCalorias(),
	            c1.getListaCurtidas().size());

	    assertEquals(esperado, c1.toString());
	}

	@Test
	void toStringSemCurtidas() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String dataFormatada = c1.getDataHora().format(formatter);

	    assertTrue(c1.toString().startsWith("[" + c1.getID() + "] " + dataFormatada));
	}

}
