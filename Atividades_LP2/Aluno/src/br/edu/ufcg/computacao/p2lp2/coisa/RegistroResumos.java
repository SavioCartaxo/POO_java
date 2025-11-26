package br.edu.ufcg.computacao.p2lp2.coisa;

/**
 * Classe que gerencia um registro limitado de resumos de estudos.
 * Permite armazenar até um número máximo de resumos, garantindo que não haja duplicidade de temas.
 * Quando o limite é atingido, os resumos mais antigos são substituídos de forma circular.
 * 
 * Cada resumo é uma String no formato "tema: conteúdo".
 * 
 * @author Sávio Cartaxo
 */
public class RegistroResumos {
    
    /**
     * Array que armazena os resumos no formato "tema: conteúdo".
     */
    private String[] listaResumos;
    
    /**
     * Número máximo de resumos que podem ser armazenados.
     */
    private int numeroResumosMax;
    
    /**
     * Índice do próximo local para inserção de resumo (modo circular).
     */
    private int ponteiro;
    
    /**
     * Quantidade atual de resumos armazenados.
     */
    private int quantidadeResumosAtuais;
    
    /**
     * Constrói um registro de resumos com capacidade máxima definida.
     * 
     * @param numeroDeResumos capacidade máxima de resumos armazenados.
     */
    RegistroResumos(int numeroDeResumos) {
        this.numeroResumosMax = numeroDeResumos;
        this.listaResumos = new String[numeroDeResumos];
        this.ponteiro = 0;
        this.quantidadeResumosAtuais = 0; 
    }
    
    /**
     * Adiciona um resumo com o tema e conteúdo especificados.
     * Se já existir um resumo com o mesmo tema, não adiciona outro.
     * Caso o limite máximo seja atingido, substitui o resumo mais antigo.
     * 
     * @param tema tema do resumo (não pode haver duplicatas).
     * @param conteudo conteúdo do resumo.
     */
    public void adiciona(String tema, String conteudo) {
        boolean jaTaNaLista = false;
        
        for (int i = 0; i < this.quantidadeResumosAtuais; i++) {
            if (this.listaResumos[i].split(" ")[0].equals(tema + ':')) {
                jaTaNaLista = true;
                break;
            }
        }
        
        if (!jaTaNaLista) {
            String resumo = tema + ": " + conteudo;
            
            this.listaResumos[this.ponteiro] = resumo;
            this.ponteiro++;
            
            if (this.quantidadeResumosAtuais != this.numeroResumosMax) {
                this.quantidadeResumosAtuais = this.ponteiro;
            }
            
            if (this.ponteiro == this.numeroResumosMax) {
                this.ponteiro = 0;
            }
        }
    }
    
    /**
     * Retorna um array com todos os resumos armazenados.
     * 
     * @return array de resumos armazenados (com posições nulas se não estiverem preenchidas).
     */
    public String[] pegaResumos() {
        String[] out = new String[this.numeroResumosMax];
        
        for (int i = 0; i < this.numeroResumosMax; i++) {
            out[i] = this.listaResumos[i];
        }
        
        return out;
    }
    
    /**
     * Retorna uma string formatada com a quantidade de resumos cadastrados e seus temas.
     * 
     * @return String contendo número de resumos e os temas separados por " | ".
     */
    public String imprimeResumos() {
        String out = "";
        
        out += "- " + this.quantidadeResumosAtuais;
        out += " resumo(s) cadastrado(s)\n";
        out += "- ";
        
        for (int i = 0; i < this.quantidadeResumosAtuais; i++) {
            String temaEResumoSeparado = this.listaResumos[i].split(":")[0];
            out += temaEResumoSeparado;

            if (i != this.quantidadeResumosAtuais - 1) {
                out += " | ";
            }
        }
        
        return out;
    }
    
    /**
     * Retorna a quantidade atual de resumos armazenados.
     * 
     * @return número de resumos armazenados.
     */
    public int conta() {
        return this.quantidadeResumosAtuais;
    }
    
    /**
     * Verifica se existe um resumo com o tema especificado.
     * 
     * @param tema tema a ser verificado.
     * @return true se o resumo com o tema existir, false caso contrário.
     */
    public boolean temResumo(String tema) {
        for (int i = 0; i < this.quantidadeResumosAtuais; i++) {
            if (this.listaResumos[i].split(" ")[0].equals(tema + ':')) {
                return true;
            }
        }
        
        return false;
    }
}
