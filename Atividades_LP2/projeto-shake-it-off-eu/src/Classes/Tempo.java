package Classes;

import java.util.ArrayList;

public class Tempo implements TipoDePontuacao {
	
	public Tempo() {}
	
	@Override
	public int calculaPontuacao(ArrayList<CheckIn> listaCheckIns) {
		int pontuacao = 0;
		double fator;
		double duracao;
		String intensidade;
		
		for (CheckIn c: listaCheckIns) {
			duracao = c.getDuracao();
			intensidade = c.getIntensidade();

		    if ("ALTA".equals(intensidade)) {
		        fator = 1.2;
		    } else if ("MODERADA".equals(intensidade)) {
		        fator = 1.1;
		    } else {
		        fator = 1.0;
		    }
			
			pontuacao += duracao * fator;
		}
		
		return pontuacao;
	}
	
	
	@Override
	public String toString() {
		return "TEMPO";
	}

}
