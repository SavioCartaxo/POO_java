package Classes;

import java.util.ArrayList;

public interface TipoDePontuacao {
	int calculaPontuacao(ArrayList<CheckIn> listaCheckIns);
	String toString();
}