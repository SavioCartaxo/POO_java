package br.com.app;

import java.util.*;
public abstract class Evento {
    protected String titulo;
    protected String descricao;
    protected String data;
    protected int maxParticipantes;
    protected List<String> listaParticipantes;

    public Evento(String titulo, String descricao, String data, int maxParticipantes) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.maxParticipantes = maxParticipantes;
        this.listaParticipantes = new ArrayList<>();
    }

    public boolean inscreveNoEvento(String email) {
        if (this.listaParticipantes.size() >= this.maxParticipantes) {return false;}
        
        listaParticipantes.add(email);
        return true;
    }
    
    public abstract int Pontuacao();
    public abstract String toString();
}
