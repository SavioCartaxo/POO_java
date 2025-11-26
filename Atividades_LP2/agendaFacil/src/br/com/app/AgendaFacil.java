package br.com.app;

import java.util.*;

public class AgendaFacil {
    public List<Estudante> listaEstutantes;

    public AgendaFacil(){
        this.listaEstutantes = new ArrayList<>();
    }

    public boolean cadastraEstudante(String nome, String email){
        for (int i = 0; i < listaEstutantes.size(); i++) {
            if (listaEstutantes.get(i).ComparaEmail(email)){
                return false;
            }
        }
        this.listaEstutantes.add(new Estudante(email, nome));
        return true;
    }
}
