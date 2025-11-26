package br.com.app;

public class Palestra extends Evento{
    
    public Palestra(String titulo, String descricao, String data, int maxParticipantes){
        super(titulo, descricao, data, maxParticipantes);
    }

    public int Pontuacao(){
        return 2;
    }

    public String toString(){
        return "";
    }

}
