package br.edu.ufcg.computacao.p2lp2.coisa;

/**
 * Representa o estado de descanso de um aluno, determinado pela média de horas de lazer por semana.
 * Se a média semanal de descanso for inferior a 26 horas, o aluno é considerado "cansado".
 * Caso contrário, é considerado "descansado".
 * 
 * @author Sávio Cartaxo
 */
public class Descanso {

    /**
     * Total de horas de descanso registradas ao longo das semanas.
     */
    private int horasDeDescanso;

    /**
     * Número de semanas consideradas para o cálculo da média de descanso.
     */
    private int numeroDeSemanas;

    /**
     * Inicializa o estado de descanso com 0 horas e 1 semana.
     */
    public Descanso() {
        this.horasDeDescanso = 0;
        this.numeroDeSemanas = 1;
    }

    /**
     * Define o total de horas de descanso registradas.
     *
     * @param horasDeDescanso Quantidade de horas de descanso acumuladas.
     */
    public void defineHorasDescanso(int horasDeDescanso) {
        this.horasDeDescanso = horasDeDescanso;
    }

    /**
     * Define o número de semanas consideradas para o cálculo do descanso.
     *
     * @param numeroDeSemanas Número de semanas.
     */
    public void defineNumeroSemanas(int numeroDeSemanas) {
        this.numeroDeSemanas = numeroDeSemanas;
    }

    /**
     * Retorna o estado geral de descanso do aluno.
     *
     * @return "cansado" se a média semanal de descanso for inferior a 26 horas,
     *         "descansado" caso contrário.
     */
    public String getStatusGeral() {
        if (this.horasDeDescanso / numeroDeSemanas < 26) {
            return "cansado";
        } else {
            return "descansado";
        }
    }
}
