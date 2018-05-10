package classes_pkg;

import java.util.ArrayList;

public class Escalonador {
	private float fatiaTempo;
	
	public Escalonador(float fatiaTempo) {
		this.fatiaTempo = fatiaTempo;
	}
	
	public float getFatiaTempo() {
		return fatiaTempo;
	}
	
	public Processo[] removeProcessoLista(Processo[] lst) {
		int contadorProcesso = 0;
		if(lst.length > 1) {
			Processo[] lstAux = new Processo[lst.length-1];
			
			for(int count = 0; count<lst.length;count++) {
				if(lst[count].getStatus() != "Executando") {
					lstAux[contadorProcesso] = lst[count];
					contadorProcesso++;
				}
			}
			
			return lstAux;
		}
		else {
			Processo[] lstAux = new Processo[1];
			lstAux[0] = lst[0];
			return lstAux;
		}
	}
	
	public Processo[] removeProcessoListaEspera(Processo[] lst) {
		
		int contadorProcesso = 0;
		if(lst.length > 1) {
			Processo[] lstAux = new Processo[lst.length-1];
			
			for(int count = 0; count<lst.length;count++) {
				if(lst[count].getStatus() != "Em Espera") {
					lstAux[contadorProcesso] = lst[count];
					contadorProcesso++;
				}
			}
			
			return lstAux;
		}
		else {
			Processo[] lstAux = new Processo[1];
			lstAux[0] = lst[0];
			return lstAux;
		}
		
		
	}
	
	public int maiorPrioridade(ArrayList<Processo> lst) {
		int i, indexPriori = 0;
		
		if(lst.size() == 1) {
			return 0;
		
		} else {
			for(i = 0; i<lst.size(); i++) {
				if(lst.get(indexPriori).getPrioridade() > lst.get(i).getPrioridade()) {
					indexPriori = i;
				}
				
			}
		}
		
		return indexPriori;
		
		
	}
}
