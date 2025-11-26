package br.edu.ufcg.computacao.p2lp2.coisa;

/**
 * Essa classe serve para verificar se um aluno bateu sua meta de estudos (em horas) de uma certa matéria.
 * 
 * A meta de estudos de um aluno de certa disciplina é o dobro das horas de aula que esse aluno tem de aulas.
 * Por exemplo, uma matéria X que tenha 15 horas de aula necessita de 30 horas de estudo para bater a meta de estudos.
 * 
 * @author Sávio Cartaxo
*/
public class RegistroTempoOnline {
    
    private String nomeDisciplina;

    /**
     * Guarda um inteiro que representa o tempo online de estudo esperado de um aluno para que este bata sua meta.
     * A meta é o dobro de horas que a disciplina tem de aula. Por exemplo, se ele assiste 15 horas de aula, precisa
     * estudar 30 horas para bater a meta.
     */
    private int tempoOnlineEsperado;

    /**
     * Armazena quantas horas o aluno já estudou até o momento.
     */
    private int tempoOnlineAtual;

    /**
     * Constrói o tempo de estudo de uma disciplina.
     * O tempo de estudo esperado é setado para 120 horas.
     * 
     * @param nomeDisciplina que representa o nome da disciplina que será armazenada.
     */
    public RegistroTempoOnline(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
        this.tempoOnlineEsperado = 120;
        this.tempoOnlineAtual = 0;
    }

    /**
     * Constrói o tempo de estudo de uma disciplina.
     * 
     * @param nomeDisciplina que representa o nome da disciplina que será armazenada.
     * @param tempoOnlineEsperado tempo de estudo esperado da matéria (2x o tempo de aula da matéria).
     */
    public RegistroTempoOnline(String nomeDisciplina, int tempoOnlineEsperado) {
        this.nomeDisciplina = nomeDisciplina;
        this.tempoOnlineEsperado = tempoOnlineEsperado;
        this.tempoOnlineAtual = 0;
    }

    public void adicionaTempoOnline(int tempoAdicionado) {
        this.tempoOnlineAtual += tempoAdicionado;
    }

    /**
     * Verifica se o aluno atingiu a meta de tempo online da disciplina.
     *
     * @return true se o tempo online atual for maior ou igual ao tempo esperado; 
     *         false caso contrário.
     */
    public boolean atingiuMetaTempoOnline() {
        return this.tempoOnlineAtual >= this.tempoOnlineEsperado;
    }

    /**
     * Retorna uma representação em forma de string dos dados da disciplina,
     * incluindo o nome da disciplina, o tempo online atual e o tempo online esperado.
     *
     * @return uma string no formato "nomeDisciplina tempoOnlineAtual/tempoOnlineEsperado"
     */
    public String toString() {
        return this.nomeDisciplina + ' ' + this.tempoOnlineAtual + '/' + this.tempoOnlineEsperado;
    }
}
