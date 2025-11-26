package Classes;

import java.util.ArrayList;

public class Frequencia implements TipoDePontuacao {
	
	public Frequencia() {}
	
	@Override
	public int calculaPontuacao(ArrayList<CheckIn> listaCheckIns) {
		return listaCheckIns.size();
	}
	
	
	@Override
	public String toString() {
		return "FREQUENCIA";
	}
}
