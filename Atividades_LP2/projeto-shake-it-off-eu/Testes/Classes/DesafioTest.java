package Classes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DesafioTest {
    private Usuario u1;
    private Usuario u2;
    private Desafio d1;

    @BeforeEach
    public void setup() {
        u1 = new Usuario("Jota", "Jota@gmail.com", "Ola! sou monitor de P2", "senha123", "USR1");
        u2 = new Usuario("Eliane", "Eliane@gmail.com", "Bio teste", "senha123", "USR2");
        
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 12, 1);
        d1 = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS2", u1);
    }


    // Construtor
    @Test
    public void testConstrutorValido() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 10, 10);
        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS1", u1);
        assertNotNull(d);
    }

    @Test
    public void testConstrutorFimPosteriorAoInicio() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 10);
        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS2", u1);
        assertNotNull(d);
    }

    @Test
    public void testInicioIgualFimLancaExcecao() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 1);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "Descricao", inicio, fim, 5, "DISTANCIA", "DS3", u1);
        });
        assertEquals("A data de fim deve ser posterior à data de início", thrown.getMessage());
    }
    
    @Test
    public void testConstrutorInicioPosteriorAoFim() {
        LocalDate inicio = LocalDate.of(2025, 1, 10);
        LocalDate fim = LocalDate.of(2025, 1, 1);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "Descricao", inicio, fim, 5, "TEMPO", "DS3", u1);
        });
        assertEquals("A data de fim deve ser posterior à data de início", thrown.getMessage());
    }

    @Test
    public void testTituloVazio() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS1", u1);
        });
        assertEquals("Por favor, informe o título do desafio", thrown.getMessage());
    }

    @Test
    public void testDescricaoVazia() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "   ", inicio, fim, 5, "FREQUENCIA", "DS1", u1);
        });
        assertEquals("Por favor, informe a descrição do desafio", thrown.getMessage());
    }

    @Test
    public void testEstrategiaVazia() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "Descricao", inicio, fim, 5, "  ", "DS1", u1);
        });
        assertEquals("Por favor, informe a estratégia de pontuação", thrown.getMessage());
    }

    @Test
    public void testIDVazio() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "", u1);
        });
        assertEquals("Por favor, informe o ID do desafio", thrown.getMessage());
    }

    @Test
    public void testCriadorNulo() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS1", null);
        });
        assertEquals("O criador do desafio não pode ser nulo", thrown.getMessage());
    }

    @Test
    public void testEstrategiaFrequenciaMaiuscula() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5,
                "FREQUENCIA", "DS1", u1);
        assertNotNull(d);
    }

    @Test
    public void testEstrategiaDistanciaMinuscula() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5,
                "distancia", "DS2", u1);
        assertNotNull(d);
    }

    @Test
    public void testEstrategiaTempoComMistura() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5,
                "TeMpO", "DS3", u1);
        assertNotNull(d);
    }

    @Test
    public void testEstrategiaInvalida() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "Descricao", inicio, fim, 5,
                    "ALEATORIA", "DS4", u1);
        });

        assertEquals("Estrategia de pontuação deve ser uma dessas 3: FREQUENCIA, DISTANCIA ou TEMPO",
                thrown.getMessage());
    }

    @Test
    public void testMetaDiariaNegativa() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Desafio("Titulo", "Descricao", inicio, fim, -1, "FREQUENCIA", "DS1", u1);
        });

        assertEquals("A meta diaria nao pode ser negativa", thrown.getMessage());
    }
    
    @Test
    public void testMetaDiariaZero() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 0, "FREQUENCIA", "DS2", u1);
        assertNotNull(d);
    }

    @Test
    public void testMetaDiariaPositiva() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 10, "FREQUENCIA", "DS3", u1);
        assertNotNull(d);
    }


    // desafioEmAndamento
    @Test
    public void testDesafioTerminandoHoje() {
        LocalDate hoje = LocalDate.now();
        Desafio d = new Desafio("Titulo", "Descricao", hoje.minusDays(5), hoje, 5, "FREQUENCIA", "DS1", u1);
        assertTrue(d.desafioEmAndamento());
    }

    @Test
    public void testDesafioComecandoHoje() {
        LocalDate hoje = LocalDate.now();
        Desafio d = new Desafio("Titulo", "Descricao", hoje, hoje.plusDays(5), 5, "FREQUENCIA", "DS2", u1);
        assertTrue(d.desafioEmAndamento());
    }

    @Test
    public void testDesafioNoMeioDoPeriodo() {
        LocalDate hoje = LocalDate.now();
        Desafio d = new Desafio("Titulo", "Descricao", hoje.minusDays(2), hoje.plusDays(2), 5, "FREQUENCIA", "DS3", u1);
        assertTrue(d.desafioEmAndamento());
    }

    @Test
    public void testDesafioAntesDoInicio() {
        LocalDate hoje = LocalDate.now();
        Desafio d = new Desafio("Titulo", "Descricao", hoje.plusDays(1), hoje.plusDays(5), 5, "FREQUENCIA", "DS4", u1);
        assertFalse(d.desafioEmAndamento());
    }

    @Test
    public void testDesafioDepoisDoFim() {
        LocalDate hoje = LocalDate.now();
        Desafio d = new Desafio("Titulo", "Descricao", hoje.minusDays(5), hoje.minusDays(1), 5, "FREQUENCIA", "DS5", u1);
        assertFalse(d.desafioEmAndamento());
    }

    
    // ingressaNoDesafio
    @Test
    public void testUsuarioNaoPodeEntrarQuandoDesafioNaoEstaEmAndamento() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 5); // fim antes do presente para garantir falha
        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS1", u1);

        String resultado = d.ingressaNoDesafio(u2);

        assertEquals("Não foi possível entrar no desafio.", resultado);
        assertFalse(d.getListaUsuarios().contains(u2));
    }

    @Test
    public void testUsuarioNaoPodeEntrarSeJaParticipa() {
        d1.ingressaNoDesafio(u2);
        String resultado = d1.ingressaNoDesafio(u2);

        assertEquals("Não foi possível entrar no desafio.", resultado);
    }

    @Test
    public void testUsuarioNovoEntraComSucesso() {
    	String resultado = d1.ingressaNoDesafio(u2);

        assertEquals("Você está participando!", resultado);
        assertTrue(d1.getListaUsuarios().contains(u2));
    }

    
    // temUsuario
    @Test
    public void testTemUsuarioQuandoUsuarioEstaNoDesafio() {
        d1.ingressaNoDesafio(u2);
        assertTrue(d1.temUsuario(u2));
    }

    @Test
    public void testTemUsuarioQuandoUsuarioNaoEstaNoDesafio() {
        assertFalse(d1.temUsuario(u2));
    }

    @Test
    public void testTemUsuarioQuandoListaUsuariosEstaVazia() {
        assertFalse(d1.temUsuario(u2));
    }

    @Test
    public void testTemUsuarioComUsuarioNulo() {
        assertFalse(d1.temUsuario(null));
    }

    
    // AdicionaCheckIn
    @Test
    public void testAdicionaCheckIn() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        CheckIn checkIn = new CheckIn("CHK1", dataHora, "Corrida", 30, "LEVE", 300, 5, u2, d1);

        d1.AdicionaCheckIn(checkIn);

        assertTrue(d1.getListaCheckin().contains(checkIn), "Deveria conter o CheckIn adicionado");
        assertEquals(1, d1.getListaCheckin().size());
    }

    @Test
    public void testAdicionaCheckInNulo() {
        d1.AdicionaCheckIn(null);
        assertTrue(d1.getListaCheckin().contains(null));
    }

    
    // encerra
    @Test
    public void testEncerraRetornaTrue() {
        assertTrue(d1.encerra(), "Deveria retornar true quando o desafio está em andamento");
    }

    @Test
    public void testEncerraRetornaFalse() {
        LocalDate inicio = LocalDate.now().minusDays(10);
        LocalDate fim = LocalDate.now().minusDays(5);
        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DSX", u1);

        assertFalse(d.encerra(), "Deveria retornar false quando o desafio já acabou");
    }

    
    
    // listaCheckins
    @Test
    public void testListaCheckinsVazia() {
        assertEquals("Ainda não houve registro de check-in.", d1.listaCheckins());
    }

    @Test
    public void testListaCheckinsComUmCheckIn() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        CheckIn checkIn = new CheckIn("CHK1", dataHora, "Corrida", 30, "LEVE", 300, 5, u2, d1);
        d1.AdicionaCheckIn(checkIn);

        String esperado = checkIn.toString();
        assertEquals(esperado, d1.listaCheckins());
    }

    @Test
    public void testListaCheckinsComVariosCheckInsOrdem() throws InterruptedException {
        String dataHora1 = LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String dataHora2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        CheckIn c1 = new CheckIn("CHK1", dataHora2, "Corrida", 20, "MODERADA", 200, 4, u2, d1);
        CheckIn c2 = new CheckIn("CHK2", dataHora1, "Musculação", 40, "LEVE", 400, 6, u2, d1);

        d1.AdicionaCheckIn(c1);
        d1.AdicionaCheckIn(c2);

        String esperado = c2.toString() + "\n" + c1.toString();
        assertEquals(esperado, d1.listaCheckins());
    }


    // calculaRanking
    @Test
    public void testCalculaRankingRankingFinalFalseEnquantoEmAndamento() {
        d1.calculaRanking();
        assertFalse(d1.isRankingFinal());
    }

    @Test
    public void testCalculaRankingRankingFinalTrueQuandoFimPassado() {
        // Ajusta datas para simular desafio finalizado
        d1.setInicio(LocalDate.now().minusDays(10));
        d1.setFim(LocalDate.now().minusDays(1));

        d1.calculaRanking();
        assertTrue(d1.isRankingFinal());
    }

    @Test
    public void testCalculaRankingRetornaFalseSeJaFinalizado() {
        // Ajusta datas para simular desafio finalizado
        d1.setInicio(LocalDate.now().minusDays(10));
        d1.setFim(LocalDate.now().minusDays(1));

        d1.calculaRanking();
        boolean segundaChamada = d1.calculaRanking();

        assertFalse(segundaChamada);
    }

    
    // desafioTerminou
    @Test
    public void testDesafioTerminouQuandoDataFimPassada() {
        LocalDate inicio = LocalDate.now().minusDays(10);
        LocalDate fim = LocalDate.now().minusDays(1);

        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS1", u1);
        assertTrue(d.desafioTerminou(), "Desafio deve estar terminado quando fim já passou");
    }

    @Test
    public void testDesafioTerminouQuandoDataFimHoje() {
        LocalDate inicio = LocalDate.now().minusDays(5);
        LocalDate fim = LocalDate.now();

        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS2", u1);
        assertFalse(d.desafioTerminou());
    }

    @Test
    public void testDesafioTerminouQuandoDataFimFutura() {
        assertFalse(d1.desafioTerminou());
    }

    
    // toString
    @Test
    public void testToStringDesafioEmAndamento() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 12, 1);
        Desafio d1 = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS2", u1);

        String esperado = "[" + d1.getID() + "]" +
                "Titulo" +
                "(" + inicio + " a " + fim + ")" +
                ", criado por: " + u1.getNome() + "\n" +
                "Descricao" + "\n" +
                "Meta diaria mínima: 5min; " +
                "Pontuação por: " + d1.getEstrategiaPontuacao().toString();

        assertEquals(esperado, d1.toString());
    }

    @Test
    public void testToStringDesafioFinalizado() {
        LocalDate inicio = LocalDate.now().minusDays(10);
        LocalDate fim = LocalDate.now().minusDays(1);
        Desafio d = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "DS3", u1);

        String esperadoPrefixo = "[FINALIZADO]";
        String resultado = d.toString();

        assertTrue(resultado.startsWith(esperadoPrefixo), "Deve indicar [FINALIZADO] no início");
        assertTrue(resultado.contains("Titulo"));
        assertTrue(resultado.contains("Descricao"));
    }

    @Test
    public void testToStringFormatoCompleto() {
        LocalDate inicio = LocalDate.of(2025, 6, 1);
        LocalDate fim = LocalDate.of(2025, 9, 1);
        Desafio d = new Desafio("Maratona", "Corrida longa", inicio, fim, 10, "TEMPO", "DS4", u1);

        String resultado = d.toString();

        assertTrue(resultado.contains("Maratona"));
        assertTrue(resultado.contains("Corrida longa"));
        assertTrue(resultado.contains("Meta diaria mínima: 10min"));
        assertTrue(resultado.contains("Pontuação por: " + d.getEstrategiaPontuacao().toString()));
    }

    
    // equals
    @Test
    public void testEqualsMesmaReferencia() {
        assertTrue(d1.equals(d1));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(d1.equals(null));
    }

    @Test
    public void testEqualsClasseDiferente() {
        assertFalse(d1.equals(new Object()));
    }

    @Test
    public void testEqualsMesmoID() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 12, 1);

        Desafio outroDesafio = new Desafio("OutroTitulo", "OutraDescricao", inicio, fim, 5, "FREQUENCIA", d1.getID(), u1);
        assertTrue(d1.equals(outroDesafio));
    }

    @Test
    public void testEqualsIDsDiferentes() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 12, 1);

        Desafio outroDesafio = new Desafio("Titulo", "Descricao", inicio, fim, 5, "FREQUENCIA", "ID_DIFERENTE", u1);
        assertFalse(d1.equals(outroDesafio));
    }

}
