package Classes;

import java.util.ArrayList;

public class Distancia implements TipoDePontuacao {
	
	public Distancia() {}
	
	@Override
	public int calculaPontuacao(ArrayList<CheckIn> listaCheckIns) {
		int pontuacao = 0;
		
		for (CheckIn c: listaCheckIns) {
			pontuacao += c.getDistancia();
		}
		
		return pontuacao;
	}
	
	@Override
	public String toString() {
		return "DISTANCIA";
	}

}