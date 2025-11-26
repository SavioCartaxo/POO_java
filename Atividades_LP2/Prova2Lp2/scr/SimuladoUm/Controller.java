package scr.SimuladoUm;

import java.util.*;
public class Controller {
    private Map<String, User> listaUser;
    private Map<Integer, Evento> listaEventos;

    public Controller(){
        this.listaUser = new HashMap<>();
        this.listaEventos = new HashMap<>();
    }

    public boolean cadastrarEstudante(String nome, String email) {
        if (listaUser.containsKey(email)){
            return false;
        }
        listaUser.put(email, new User(nome));
        return true;
    }

    public String exibirEstudante(String email) {
        List<User> usuarios = new ArrayList<>(listaUser.values());

        usuarios.sort(Comparator.comparingInt(User::getPontos).reversed());

        String out = "";
        for (User usuario : usuarios) {
            out += usuario.getNome() + '\n';
        }

        return out;
    }

    public int cadastrarPalestra(String titulo, String descricao, String data, int maxParticipantes){
        Palestra palestra = new Palestra(titulo,descricao, data, maxParticipantes);
        listaEventos.put(listaEventos.size() + 1, palestra);

        return palestra.calculaPontuacao();
    }

    public int cadastrarWorkshop(String titulo, String descricao, String data, int maxParticipantes, int duracao){
        Workshop workshop = new Workshop(titulo,descricao, data, maxParticipantes, duracao);
        listaEventos.put(listaEventos.size() + 1, workshop);

        return workshop.calculaPontuacao();
    }

    public int cadastrarOficinaAprendizagem(String titulo, String descricao, String data, int maxParticipantes, int duracao, boolean certificacao){
        OficinaAprendizagem oficinaAprendizagem = new OficinaAprendizagem(titulo,descricao, data, maxParticipantes, duracao, certificacao);
        listaEventos.put(listaEventos.size() + 1, oficinaAprendizagem);

        return oficinaAprendizagem.calculaPontuacao();
    }

    public boolean inscreverParticipanteEmEvento(String emailParticipante, int idEvento){
        return this.listaEventos.get(idEvento).inscreverParticipanteEmEvento(emailParticipante);
    }

    public String exibirDetalhesEvento(int idEvento){
        return this.listaEventos.get(idEvento).toString();
    }
    
}