package scr.SimuladoUm;

import java.util.*;

public abstract class Evento {
    protected String titulo;
    protected String descricao;
    protected String data;
    protected int maxParticipantes;
    private List<String> listaParticipantes;

    public Evento(String titulo, String descricao, String data, int maxParticipantes) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.maxParticipantes = maxParticipantes;
        this.listaParticipantes = new ArrayList<>();
    }

    boolean inscreverParticipanteEmEvento(String emailParticipante){
        // Se participante ja ingressou no Evento
        if (this.listaParticipantes.contains(emailParticipante)){
            return false;
        }

        // Se Desafio ja esta cheio
        if (this.listaParticipantes.size() >= this.maxParticipantes) {
            return false;
        }

        this.listaParticipantes.add(emailParticipante);
        return true;
    }

    abstract int calculaPontuacao();
    
}
