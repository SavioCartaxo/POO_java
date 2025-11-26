package scr.SimuladoUm;

public class Workshop extends Evento {
    private int duracao;

    public Workshop(String titulo, String descricao, String data, int maxParticipantes, int duracao){
        super(titulo,descricao, data, maxParticipantes);
        this.duracao = duracao;
    }

    @Override
    public int calculaPontuacao(){
        return this.duracao;
    }

    @Override
    public String toString(){
        String out = "";

        out += "Titulo: " + this.titulo + '\n';
        out += "Descrição: " + this.descricao + '\n';
        out += "Data: " + this.data + '\n';
        out += "Numero maximo de participantes:" + this.maxParticipantes + '\n';
        out += "Duração" + this.duracao + " Horas";

        return out;
    }
}
