package br.com.app;

public class Estudante {
    private String email;
    private String nome;
    private int eventosParticipados;
    private int pontos;

    public Estudante(String email, String nome){
        this.nome = nome;
        this.email = email;
        this.pontos = 0;
        this.eventosParticipados = 0;
    }

    public boolean ComparaEmail(String email) {
        if (this.email.equals(email)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String out = "";
        
        out += this.nome + " - Eventos Participados: ";
        out += this.eventosParticipados + " - Pontos Feitos: ";
        out += this.pontos;

        return out;
    }

}
