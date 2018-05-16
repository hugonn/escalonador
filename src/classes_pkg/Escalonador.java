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
																 // Remove o processo da lista de processos e retorna a lista de processos que "ainda não chegaram"
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
	
	public Processo[] removeProcessoListaEspera(Processo[] lst){
		
		int contadorProcesso = 0;
		
		if(lst.length > 1) {
			
			Processo[] lstAux = new Processo[lst.length-1];
			
			for(int count = 0; count<lst.length;count++) {
				
				if(lst[count].getStatus() != "Em Espera") {
																		// Remove o processo da lista de processo que esteja "Em Espera". Para nao duplicar processos.
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
		
		if(lst.size() == 1){
			
			return 0;
		
		}else{
			
			for(i = 0; i<lst.size(); i++){
																									//retorna o index do processo que contem a maior prioridade da lista de espera
				if(lst.get(indexPriori).getPrioridade() > lst.get(i).getPrioridade()) {
					
					indexPriori = i;
				}	
			}
		}
		
		return indexPriori;
	}
	
	public void adicionaTempoEspera(ArrayList<Processo> lst) {
		
		for(Processo p: lst) {
			
			p.adicionaTempEspera();
		
		}
	}
	
	public String getTempoRespostaMedio(ArrayList<Processo> lst) {
		
		String TRM = "Tempo de Resposta Médio: ";
		
		float somaTempos = 0;
		
		float tempoMedResposta = 0;
		
		for(int i =0;i<lst.size(); i++) {
			
			if(i == lst.size()-1) 
				
				TRM += "("+"P"+lst.get(i).getNumProcesso() +": "+ lst.get(i).getTempoResposta()+")" +" = ";
			else
				
				TRM += "("+ "P"+lst.get(i).getNumProcesso() +": "+lst.get(i).getTempoResposta()+")" + " + ";
			
			somaTempos+=lst.get(i).getTempoResposta();
		}	
			
		TRM += somaTempos +"/"+ lst.size()+ " = ";
		
		tempoMedResposta = somaTempos/lst.size();
			
		TRM+= tempoMedResposta + " u.t ";
		
		return TRM;
	}
	
	public String getTempoEsperaMedio(ArrayList<Processo> lst) {
		
		String TEM = "Tempo de Espera Médio: ";
		
		float somaTempos = 0;
		
		float tempoMedEspera = 0;
		
		for(int i =0;i<lst.size(); i++) {
			
			if(i == lst.size()-1) 
				
				TEM += "("+"P"+lst.get(i).getNumProcesso() +": "+ lst.get(i).getTempoEspera()+")" +" = ";
			else
				
				TEM += "("+ "P"+lst.get(i).getNumProcesso() +": "+lst.get(i).getTempoEspera()+")" + " + ";
			
			somaTempos+=lst.get(i).getTempoEspera();
		}	
			
		TEM += somaTempos +"/"+ lst.size()+ " = ";
		
		tempoMedEspera = somaTempos/lst.size();
			
		TEM+= tempoMedEspera + " u.t ";
		
		return TEM;
		
	}
}
