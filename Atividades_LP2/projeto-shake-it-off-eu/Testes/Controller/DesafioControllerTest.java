package Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.Desafio;
import Classes.Usuario;

class DesafioControllerTest {

	private DesafioController dc;
	private UsuarioController uc;
    private String idUsuario;
    
    @BeforeEach
    void inicia() {
        uc = new UsuarioController();
        dc = new DesafioController();
        
        idUsuario = uc.adicionaUsuario("Jota", "jota@gmail.com", "Bio legal", "senha123");
        
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(10);
        dc.criarDesafio(idUsuario, "senha123", "Corrida", "Descrição teste",
                inicio, fim, 30, "FREQUENCIA", uc);
    }


    @Test
    public void criaDesafioComSucesso() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(10);

        String idDesafio = dc.criarDesafio(idUsuario, "senha123", "Corrida", "Descrição teste",
                inicio, fim, 30, "FREQUENCIA", uc);

        assertNotNull(idDesafio);
        assertTrue(idDesafio.startsWith("DSF"));
        assertTrue(dc.getListaDesafios().containsKey(idDesafio));
    }

    @Test
    public void criarDesafioFalhaSenhaIncorreta() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dc.criarDesafio(idUsuario, "senhaErrada", "Corrida", "Descrição teste",
                    inicio, fim, 30, "FREQUENCIA", uc);
        });

        assertEquals("Usuario nao autenticado", exception.getMessage());
    }

    @Test
    public void criarDesafioUsuarioParticipaAutomaticamente() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(10);
        String idDesafio = dc.criarDesafio(idUsuario, "senha123", "Corrida", "Descrição teste",
                inicio, fim, 30, "FREQUENCIA", uc);
        Desafio desafio = dc.getListaDesafios().get(idDesafio);
        Usuario usuario = uc.getUsuario(idUsuario);
        assertTrue(usuario.participaDoDesafio(desafio));
    }


    @Test
    void ingressoValidoNoDesafio() {
        String resultado = dc.IngressarNoDesafio(idUsuario, "DSF1", "senha123", uc);
        assertEquals("Você está participando!", resultado);
        assertTrue(uc.getUsuario(idUsuario).participaDoDesafio(dc.getDesafio("DSF1")));
    }


    @Test
    void listarDesafiosComParticipantes() {
        dc.IngressarNoDesafio(idUsuario, "DSF1", "senha123", uc);
        String lista = dc.listaDesafios(idUsuario, "senha123", uc);
        assertNotNull(lista);
        assertTrue(lista.contains("Corrida"));
        assertTrue(lista.contains("Participantes: 1"));
    }

    @Test
    void ingressoNoDesafioComSenhaIncorreta() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dc.IngressarNoDesafio(idUsuario, "DSF1", "senhaErrada", uc);
        });
        assertEquals("Usuario nao autenticado", exception.getMessage());
    }

    @Test
    void listarDesafiosComSenhaIncorreta() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dc.listaDesafios(idUsuario, "senhaErrada", uc);
        });
        assertEquals("Usuario nao autenticado", exception.getMessage());
    }

    @Test
    void listarTodosDesafiosDeveRetornarDesafioExistente() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(10);
        String esperado = "[DSF1]Corrida(" + inicio + " a " + fim + "), criado por: Jota\n" +
                          "Descrição teste\n" +
                          "Meta diaria mínima: 30min; Pontuação por: FREQUENCIA\n" +
                          "Participantes: 0";
        String resultado = dc.listaDesafios(idUsuario, "senha123", uc);
        assertEquals(esperado, resultado);
    }


    @Test
    void listarDesafiosComSenhaIncorretaDeveLancarExcecao() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dc.listaDesafios(idUsuario, "senhaErrada", uc);
        });
        assertEquals("Usuario nao autenticado", exception.getMessage());
    }

    @Test
    void listarDesafiosAtivosDeveRetornarSomenteDesafiosEmAndamento() {
        dc.IngressarNoDesafio(idUsuario, "DSF1", "senha123", uc);
        String resultado = dc.listarDesafiosAtivos(idUsuario, "senha123", uc);
        
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(10);
        
        String esperado = "[DSF1]Corrida(" + inicio + " a " + fim + "), criado por: Jota\n" +
                          "Descrição teste\n" +
                          "Meta diaria mínima: 30min; Pontuação por: FREQUENCIA\n" +
                          "Participantes: 1";
        assertEquals(esperado, resultado);
    }


    @Test
    void listarDesafiosAtivosComSenhaErradaDeveLancarExcecao() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dc.listarDesafiosAtivos(idUsuario, "senhaErrada", uc);
        });
        assertEquals("Usuario nao autenticado", exception.getMessage());
    }

    @Test
    void finalizarDesafioComUsuarioNaoCriadorDeveFalhar() {
        String idDesafio = "DSF1";
        String idOutroUsuario = uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio Maria", "senha321");
        dc.IngressarNoDesafio(idOutroUsuario, idDesafio, "senha321", uc);
        String resultado = dc.finalizarDesafio(idOutroUsuario, "senha321", idDesafio, uc);
        assertEquals("Apenas o criador pode finalizar o desafio.", resultado);
        assertFalse(dc.getDesafio(idDesafio).isRankingFinal());
    }

    @Test
    void finalizarDesafioForaDoPeriodoDeveFalhar() {
        String idDesafio = "DSF1";
        dc.getDesafio(idDesafio).setFim(LocalDate.now().minusDays(1));
        String resultado = dc.finalizarDesafio(idUsuario, "senha123", idDesafio, uc);
        assertEquals("Não é possível finalizar o desafio nesse momento.", resultado);
        assertFalse(dc.getDesafio(idDesafio).isRankingFinal());
    }

    @Test
    void calcularRankingDesafioComParticipanteEIniciado() {
        String idDesafio = "DSF1";
        dc.IngressarNoDesafio(idUsuario, idDesafio, "senha123", uc);
        String resultado = dc.calcularRankingDesafio(idUsuario, "senha123", idDesafio, uc);
        assertEquals("Ranking calculado.", resultado);
        assertNotNull(dc.getDesafio(idDesafio).getRanking());
        assertFalse(dc.getDesafio(idDesafio).getRanking().isEmpty());
    }

    @Test
    void calcularRankingDesafioSemParticipacaoDeUsuario() {
        String idDesafio = "DSF1";
        String idOutroUsuario = uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio Maria", "senha321");
        String resultado = dc.calcularRankingDesafio(idOutroUsuario, "senha321", idDesafio, uc);
        assertEquals("Deve participar do desafio para calcular ranking.", resultado);
    }

    @Test
    void calcularRankingDesafioAntesDoInicio() {
        String idDesafio = "DSF1";
        dc.IngressarNoDesafio(idUsuario, idDesafio, "senha123", uc);
        dc.getDesafio(idDesafio).setInicio(LocalDate.now().plusDays(1));
        String resultado = dc.calcularRankingDesafio(idUsuario, "senha123", idDesafio, uc);
        assertEquals("O desafio ainda não começou.", resultado);
    }

    @Test
    void visualizarRankingDesafioUsuarioNaoParticipa() {
        String idDesafio = "DSF1";
        String outroUsuarioId = uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio", "senha456");
        String resultado = dc.visualizarRankingDesafio(outroUsuarioId, "senha456", idDesafio, 3, uc);
        assertEquals("Deve participar do desafio para calcular ranking.", resultado);
    }

    @Test
    void visualizarRankingDesafioTopNMenorQueTotal() {
        String idDesafio = "DSF1";
        dc.IngressarNoDesafio(idUsuario, idDesafio, "senha123", uc);
        String outroId = uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio", "senha456");
        dc.IngressarNoDesafio(outroId, idDesafio, "senha456", uc);
        String ranking = dc.visualizarRankingDesafio(idUsuario, "senha123", idDesafio, 1, uc);
        assertNotNull(ranking);
        assertTrue(ranking.contains("Corrida"));
        assertTrue(ranking.contains("#1"));
    }

    @Test
    void visualizarRankingDesafioTopNMaiorQueTotal() {
        String idDesafio = "DSF1";
        dc.IngressarNoDesafio(idUsuario, idDesafio, "senha123", uc);
        String ranking = dc.visualizarRankingDesafio(idUsuario, "senha123", idDesafio, 10, uc);
        assertNotNull(ranking);
        assertTrue(ranking.contains("Corrida"));
    }

    @Test
    void visualizarRankingDesafioRetornaRankingFormatado() {
        String idDesafio = "DSF1";
        dc.IngressarNoDesafio(idUsuario, idDesafio, "senha123", uc);
        String ranking = dc.visualizarRankingDesafio(idUsuario, "senha123", idDesafio, 5, uc);
        assertTrue(ranking.startsWith("Corrida"));
    }

    @Test
    void autenticaDesafioComDesafioExistenteNaoLancaExcecao() {
        assertDoesNotThrow(() -> dc.autenticaDesafio("DSF1"));
    }

    @Test
    void autenticaDesafioComDesafioInexistenteLancaExcecao() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dc.autenticaDesafio("DSF999");
        });
        assertEquals("Falha de autenticação Desafio", exception.getMessage());
    }

    @Test
    void temUsuarioRetornaTrueSeUsuarioParticipa() {
        Desafio desafio = dc.getDesafio("DSF1");
        Usuario usuario = uc.getUsuario(idUsuario);
        dc.IngressarNoDesafio(idUsuario, "DSF1", "senha123", uc);
        assertTrue(dc.temUsuario(desafio, usuario));
    }

    @Test
    void temUsuarioRetornaFalseSeUsuarioNaoParticipa() {
        Desafio desafio = dc.getDesafio("DSF1");
        String outroUsuarioId = uc.adicionaUsuario("Maria", "maria@gmail.com", "Bio", "senha456");
        Usuario outroUsuario = uc.getUsuario(outroUsuarioId);
        assertFalse(dc.temUsuario(desafio, outroUsuario));
    }
}