package br.edu.ufcg.computacao.p2lp2.coisa;

import java.util.Arrays;

/**
 * Representa uma disciplina com controle de horas de estudo, notas e status de aprovação.
 * Útil para o gerenciamento acadêmico do desempenho do aluno em determinada matéria.
 * 
 * Por padrão, a disciplina possui 4 notas. A média é calculada como a média aritmética dessas 4 notas.
 * Caso alguma nota não tenha sido cadastrada, é considerada como zero.
 * 
 * @author Sávio Cartaxo
 */
public class Disciplina {

    private String nomeDisciplina;
    private int horasDeEstudo;
    private double[] notas;
    private double media;

    /**
     * Construtor da disciplina. Inicializa:
     * - Nome da disciplina;
     * - Horas de estudo em 0;
     * - Vetor de 4 notas, todas iniciando com valor 0;
     * - Média em 0.
     * 
     * @param nomeDisciplina Nome da disciplina.
     */
    public Disciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
        this.horasDeEstudo = 0;
        this.notas = new double[4];
        this.media = 0;
    }

    /**
     * Acrescenta horas de estudo ao total acumulado.
     *
     * @param horas Quantidade de horas a serem somadas.
     */
    public void cadastraHoras(int horas) {
        this.horasDeEstudo += horas;
    }

    /**
     * Registra ou substitui a nota de uma das quatro avaliações.
     *
     * @param nota Número da prova (1 a 4).
     * @param valorNota Valor obtido na prova (0 a 10).
     */
    public void cadastraNota(int nota, double valorNota) {
        this.notas[nota - 1] = valorNota;
        media = (notas[0] + notas[1] + notas[2] + notas[3]) / 4;
    }

    /**
     * Verifica se o aluno está aprovado na disciplina.
     *
     * @return true se a média for maior ou igual a 7.0; false caso contrário.
     */
    public boolean aprovado() {
        return media >= 7;
    }

    /**
     * Retorna a representação textual da disciplina.
     * Inclui: nome, horas de estudo, média (com uma casa decimal) e lista de notas.
     *
     * @return String no formato "nomeDisciplina horas media [notas]".
     */
    public String toString() {
        return nomeDisciplina + " " + horasDeEstudo + " " + String.format("%.1f", media) + " " + Arrays.toString(notas);
    }
}
