package scr.SimuladoUm;

public class User {
    private String nome;
    private int quantidadeDeEventos;
    private int pontos;

    public User(String nome){
        this.nome = nome;
        this.quantidadeDeEventos = 0;
        this.pontos = 0;
    }

    public String getNome(){
        return this.nome;
    }

    public int getPontos(){
        return this.pontos;
    }

    @Override
    public String toString(){
        String out = "";

        out += "Nome: " + this.nome + ' ' ;
        out  += "quantidade de eventos que participa: " + this.quantidadeDeEventos + ' ';
        out += "Pontuação total: " + this.pontos;
        
        return out;
    }
}
