package Classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

class UsuarioTest {
	
	private Usuario u1;
    private Usuario u2;
    private Usuario u3;

    @BeforeEach
    void prepara() {
        // Criando usuários válidos para reutilização
    	u1 = new Usuario("Jota", "Jota@gmail.com", "Ola! sou monitor de P2", "senha123", "USR1");
    	u2 = new Usuario("Eliane", "Eliane@gmail.com", "Ola! sou professora de P2", "elinda1", "USR2");
    	u3 = new Usuario("Sávio", "email@email.com", "Bio legal", "123456789012", "USR1");
    }
    
    // Testes de validação de campos obrigatórios
    @Test
    void criacaoUsuarios() {
        assertEquals("Jota", u1.getNome());
        assertEquals("Jota@gmail.com", u1.getEmail());
        assertEquals("senha123", u1.getSenha());
        assertEquals("Ola! sou monitor de P2", u1.getBio());
        assertEquals("USR1", u1.getID());

        assertEquals("Eliane", u2.getNome());
        assertEquals("Eliane@gmail.com", u2.getEmail());
        assertEquals("elinda1", u2.getSenha());
        assertEquals("Ola! sou professora de P2", u2.getBio());
        assertEquals("USR2", u2.getID());
    }

    @Test
    void deveLancarExcecaoQuandoNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("", "email@email.com", "Bio legal", "123456", "USR1"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario(null, "email@email.com", "Bio legal", "123456", "USR1"));
    }

    @Test
    void deveLancarExcecaoQuandoEmailInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", "", "Bio legal", "123456", "USR1"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", null, "Bio legal", "123456", "USR1"));
    }

    @Test
    void deveLancarExcecaoQuandoSenhaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", "email@email.com", "Bio legal", "", "USR1")); // senha menor que 6
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", "email@email.com", "Bio legal", "123", "USR1")); // senha menor que 6
    }

    @Test
    void deveLancarExcecaoQuandoBioInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", "email@email.com", "", "123456", "USR1"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", "email@email.com", null, "123456", "USR1"));
    }

    @Test
    void deveLancarExcecaoQuandoIDInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", "email@email.com", "Bio legal", "123456", ""));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Sávio", "email@email.com", "Bio legal", "123456", null));
    }

    // Testes de tamanho de senha
    @Test
    void senhaCom1CaractereDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Usuario("Sávio", "email@email.com", "Bio legal", "1", "USR1"));
    }

    @Test
    void senhaCom5CaracteresDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Usuario("Sávio", "email@email.com", "Bio legal", "12345", "USR1"));
    }

    @Test
    void senhaCom6CaracteresDeveCriarUsuario() {
        Usuario u = new Usuario("Sávio", "email@email.com", "Bio legal", "123456", "USR3");
        assertEquals("123456", u.getSenha());
    }

    @Test
    void senhaCom12CaracteresDeveCriarUsuario() {
        Usuario u = new Usuario("Sávio", "email@email.com", "Bio legal", "123456789012", "USR4");
        assertEquals("123456789012", u.getSenha());
    }
    
    @Test
    void emailComEspacosDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Usuario("Sávio", "    ", "Bio legal", "123456", "USR1"));
    }

    @Test
    void senhaComEspacosNaoDeveLancarExcecao() {
        Usuario u = new Usuario("Sávio", "email@email.com", "Bio legal", "      ", "USR1");
        assertEquals("      ", u.getSenha());
    }

    @Test
    void bioComEspacosDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Usuario("Sávio", "email@email.com", "   ", "123456", "USR1"));
    }
    
  
    // ingressaNoDesafio
    @Test
    void checaListaDeDesafiosQuandoNaoTemDesafioAdicionado() {
        assertTrue(u1.getListaDesafios().isEmpty());
    }

    @Test
    void checaListaDeDesafiosQuandoTemUmDesafioAdicionado() {
        u1.ingressaNoDesafio(new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1));
        assertEquals(1, u1.getListaDesafios().size());
    }

    @Test
    void testaAdicionarDoisDesafiosIguais() {
        Desafio desafio = new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1);
        u1.ingressaNoDesafio(desafio);
        u1.ingressaNoDesafio(desafio);
        assertEquals(1, u1.getListaDesafios().size());
    }

    @Test
    void checaListaDeDesafiosQuandoTemDoisDesafiosDiferentesAdicionados() {
        u1.ingressaNoDesafio(new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1));
        u1.ingressaNoDesafio(new Desafio("Desafio Novo", "Correr 10km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF2", u1));
        assertEquals(2, u1.getListaDesafios().size());
    }
    
    
    // adicionaCheckIn
    @Test
    void checaListaDeCheckInQuandoNaoTemCheckInAdicionado() {
        assertTrue(u1.getListaCheckIn().isEmpty());
    }

    @Test
    void checaListaDeCheckInQuandoTemUmCheckInAdicionado() {
        CheckIn c = new CheckIn("CHK1", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), "Corrida", 30, "MODERADA", 200, 5, u1, new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1));
        u1.adicionaCheckIn(c);
        assertEquals(1, u1.getListaCheckIn().size());
    }

    @Test
    void checaListaDeCheckInQuandoTemDoisCheckInAdicionados() {
        CheckIn c1 = new CheckIn("CHK1", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), "Corrida", 30, "MODERADA", 200, 5, u1, new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1));
        CheckIn c2 = new CheckIn("CHK2", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), "Caminhada", 20, "LEVE", 100, 2, u1, new Desafio("Desafio Caminhada", "Caminhar 2km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 15, "FREQUENCIA", "DSF2", u1));
        u1.adicionaCheckIn(c1);
        u1.adicionaCheckIn(c2);
        assertEquals(2, u1.getListaCheckIn().size());
    }
    
    
    // listarCheckins
    @Test
    void quandoListaDesafiosVazia() {
        assertEquals("Usuário não participa de nenhum desafio.", u2.listarCheckins());
    }

    @Test
    void quandoListaCheckInsVazia() {
        Desafio desafio = new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u2);
        u2.ingressaNoDesafio(desafio);

        assertEquals("Ainda não houve registro de check-in.", u2.listarCheckins());
    }

    @Test
    void listaCheckinsQuandoTemSoUmCheckIn() {
        Desafio desafio = new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u2);
        u2.ingressaNoDesafio(desafio);
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        CheckIn checkIn = new CheckIn("CHK1", dataHora, "Corrida", 30, "LEVE", 300, 5, u2, desafio);
        u2.adicionaCheckIn(checkIn);
        String esperado = "[CHK1] " + dataHora + " — [" + u2.getID() + "] " + u2.getNome() + " — Corrida — 30min — INTENSIDADE: LEVE — 300 kcal\nlikes: 0\nDesafio: " + desafio.getTitulo();
        assertEquals(esperado, u2.listarCheckins());
    }
    
    
    // participaDoDesafio
    @Test
    void usuarioNaoParticipaDeNenhumDesafio() {
        Desafio desafio = new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1);
        assertFalse(u1.participaDoDesafio(desafio));
    }

    @Test
    void usuarioParticipaDeUmDesafio() {
        Desafio desafio = new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1);
        u1.ingressaNoDesafio(desafio);
        assertTrue(u1.participaDoDesafio(desafio));
    }

    @Test
    void usuarioPerguntaPorDesafioDiferente() {
        Desafio desafio1 = new Desafio("Desafio Corrida", "Correr 5km por dia", LocalDate.now(), LocalDate.now().plusDays(7), 30, "FREQUENCIA", "DSF1", u1);
        Desafio desafio2 = new Desafio("Desafio Caminhada", "Caminhar 2km", LocalDate.now(), LocalDate.now().plusDays(7), 15, "FREQUENCIA", "DSF2", u1);
        u1.ingressaNoDesafio(desafio1);
        assertFalse(u1.participaDoDesafio(desafio2));
    }

    
    // toString
    @Test
    public void toStringUsuario1() {
        String esperado = "[USR1] Jota (Jota@gmail.com) Ola! sou monitor de P2";
        assertEquals(esperado, u1.toString());
    }

    @Test
    public void toStringUsuario2() {
        String esperado = "[USR2] Eliane (Eliane@gmail.com) Ola! sou professora de P2";
        assertEquals(esperado, u2.toString());
    }

    @Test
    public void toStringUsuario3() {
        String esperado = "[USR1] Sávio (email@email.com) Bio legal";
        assertEquals(esperado, u3.toString());
    }
   
    
    // equals
    @Test
    public void equalsMesmoEmail() {
        Usuario outro = new Usuario("Outro Nome", "Jota@gmail.com", "outrasenha", "Bio qualquer", "USR99");
        assertEquals(u1, outro);
    }

    @Test
    public void equalsEmailsDiferentes() {
        // u1 (email: Jota@gmail.com) ≠ u2 (email: Eliane@gmail.com)
        assertNotEquals(u1, u2);
    }

    @Test
    public void equalsComNull() {
        assertNotEquals(u1, null);
    }
    
}