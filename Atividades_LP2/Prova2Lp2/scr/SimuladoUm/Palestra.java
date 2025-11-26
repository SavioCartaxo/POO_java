package scr.SimuladoUm;

public class Palestra extends Evento {
    
    public Palestra(String titulo, String descricao, String data, int maxParticipantes){
        super(titulo,descricao, data, maxParticipantes);
    }

    @Override
    public int calculaPontuacao(){
        return 2;
    }

    @Override
    public String toString(){
        String out = "";

        out += "Titulo: " + this.titulo + '\n';
        out += "Descrição: " + this.descricao + '\n';
        out += "Data: " + this.data + '\n';
        out += "Numero maximo de participantes:" + this.maxParticipantes;

        return out;
    }
}
