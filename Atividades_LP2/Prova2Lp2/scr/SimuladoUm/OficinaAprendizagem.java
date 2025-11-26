package scr.SimuladoUm;

public class OficinaAprendizagem extends Evento {
    private int duracao;
    private boolean certificado;

    public OficinaAprendizagem(String titulo, String descricao, String data, int maxParticipantes, int duracao, boolean certificado){
        super(titulo,descricao, data, maxParticipantes);
        this.duracao = duracao;
        this.certificado = certificado;
    }
    @Override
    public int calculaPontuacao(){
        int s = this.duracao;
        if (this.certificado) {s += 10;}
        
        return s;
    }

    @Override
    public String toString(){
        String out = "";

        out += "Titulo: " + this.titulo + '\n';
        out += "Descrição: " + this.descricao + '\n';
        out += "Data: " + this.data + '\n';
        out += "Numero maximo de participantes:" + this.maxParticipantes + '\n';
        out += "Duração" + this.duracao + " Horas" + '\n';
        out += "Emite certificado: ";

        if (this.certificado) {
            out += "Sim";
        } else {
            out += "Não";
        }

        return out;
    }
}
