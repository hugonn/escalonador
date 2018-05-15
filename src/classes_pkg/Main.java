package classes_pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

	/*
	 *  1 - compara o tempo atual com o tempo da lista de processos. Caso tenha chegado um processo, joga no processador -- ok
	 *    
	 *    
	 *    - caso ja tenha um processo rodando compara a fatia de tempo, ve se o processo ta dentro dos quantum estimado, caso esteja:
	 *    	- o processo finalizou?
	 *    		- se sim, olhar na lista de espera e pegar o processo com maior prioridade. Jogar ele no processador. Se nao tem lista de espera,  verifica se naquele instante de
	 *    			tempo chegou algum processo. 
	 *    			- se sim, coloca o processo no processador
	 *    			- se nao, espera o prox processo chegar           -- Essa verificação é feita no passo 1
	 *      
	 *      	- se nao, Verifico na lista de processos se chegou algum processo de maior prioridade (nao preciso verificar na lista de espera, pois se o proc ta na lista de espera
	 *      	quer dizer que o mesmo tem a mesma prioriodade ou menor do que está no processador, ja que processos de maior prioridade entram "direto" no processador". 
	 *      	sem falar que o processo  ta dentro da fatia de tempo dele... se for da mesma prioridade, tem que esperar a fatia de tempo do processo que esta sendo executado
	 *          terminar.
	 *    
	 *   - caso o quantum tenha chegado no limite:
	 *   	- verificar se na lista de espera possui um processo de mesma prioridade do que esta sendo executado.
	 *   		- se tiver, pegar o processo que esta sendo executado, jogar na lista de espera e executar o processo da lista de espera com mesma prioridade
	 *   	- caso nao tenha na lista de espera, verificar se naquele instante de tempo chegou algum processo 
	 *   		- se sim e for de prioridade maior, joga o processo que esta no processador para a lista de espera e executa o novo processo que chegou.
	 *   		- se sim e for prioridade menor, jogar na lista de espera e continua execução do processo que ja esta na cpu
	 *          - se nao, verificar se o processo que esta no proc foi finalizado
	 *          	- se sim, tirar o processo da cpu e esperar o proximo
	 *          	- se nao, continuar executando o mesmo processo 
	 */

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String coord = "C:\\Users\\Pai e Mae\\Desktop\\arc.txt";
        try {
        	int i = 0, count=0;

        	float tChegada,tExec,tempoAtual =0;
        	
			int priori,countProcesso=0, indicNumProc =1;
			
			boolean processosFinalizados = false;
			
			boolean controleContexto = false;
			
			int indexMaiorPriori;
			
			ArrayList<Processo> lstProcEspera = new ArrayList<Processo>();
			
			String grafico="";
			
			String[] textoSeparado;
			
	        File arq = new File(coord);
	        
	        FileReader fi = new FileReader(arq);  
	        
	        BufferedReader leitor = new BufferedReader(fi);
	        
	        String linha = leitor.readLine();
	        
	        Processo lstProcesso[];
	        
	        String num = "";
	        
	        if(linha.length() > 1) {
	        	for(int j =0; j<linha.length();j++) 
	        		num+=linha.charAt(j);
	        	
	        	lstProcesso = new Processo[Integer.parseInt(num)];
	        	
	        }else
	        	lstProcesso = new Processo[Character.getNumericValue(linha.charAt(0))];
	        

			ArrayList<Processo> lstProcConcluido = new ArrayList<Processo>(); 
			
			linha = leitor.readLine();
			
			Escalonador agendador;
			
			 if(linha.length() > 1) {
				 
		         num = "";
		         
		         for(int j =0; j<linha.length();j++) 
		        	 
		        	num+=linha.charAt(j);
		        	
		         agendador = new Escalonador(Float.parseFloat(num));
		         
		     }else
		         agendador = new Escalonador(Character.getNumericValue(linha.charAt(0)));
	       
			Processador processador = new Processador();
			
			linha = leitor.readLine();
			
			while(linha != null) {
				
				textoSeparado = linha.split(" ");
				
				i = linha.indexOf(" ");
				
				tChegada = Float.parseFloat(textoSeparado[0]);
				
				tExec = Float.parseFloat(textoSeparado[1]);
				
				priori = Integer.parseInt(textoSeparado[2]);  //processos estao em uma lista de processos "circular"
				
				Processo pro;
				
				if(textoSeparado.length > 3) {
					String IO="";
					
					for(int k=3; k<textoSeparado.length; k++) {
						
						IO+=textoSeparado[k]+" ";
						
					}
					
					pro = new Processo(tChegada,tExec,priori,indicNumProc, IO);
					
					lstProcesso[countProcesso] = pro;
					
				}else {
					
					pro = new Processo(tChegada,tExec,priori,indicNumProc);
					
					lstProcesso[countProcesso] = pro;
				}
				
				countProcesso++;
				
				indicNumProc++;
				
				linha = leitor.readLine();
					
			}
			
			while(processosFinalizados != true) { // Enquanto existem processos na lista de processos
				/*
				 *  1 - compara o tempo atual com o tempo da lista de processos. Caso tenha chegado um processo, joga no processador -- ok
				 *    
				 *    
				 *    - caso ja tenha um processo rodando compara a fatia de tempo, ve se o processo ta dentro dos quantum estimado, caso esteja:
				 *    	- o processo finalizou?
				 *    		- se sim, olhar na lista de espera e pegar o processo com maior prioridade. Jogar ele no processador. Se nao tem lista de espera,  verifica se naquele instante de
				 *    			tempo chegou algum processo. 
				 *    			- se sim, coloca o processo no processador
				 *    			- se nao, espera o prox processo chegar           -- Essa verificação é feita no passo 1
				 *    */
				
 				System.out.println("TempoAtual: " + tempoAtual + " - ProcessoNoProc: " +(processador.procEmExecucao() == null ? "null" :processador.procEmExecucao().getNumProcesso() ) +
					" - TempoNoProcessador: "+ processador.getTempoNoProc() +" - "+ (processador.procEmExecucao() == null ? "null" : "Tempo Restante: " + processador.procEmExecucao().getTempoExec() )+
						
					(lstProcesso == null ? " Fim dos Processos na Lista " : " Proximo Processo a ser exec: "+lstProcesso[0].getNumProcesso()) + " - Lista de espera: "+lstProcEspera.size() + " - Tempo Exec processo 1: " + ( lstProcEspera.isEmpty()  ? "0" : lstProcEspera.get(0).getTempoExec() ));
					

				if(processador.procEmExecucao() == null) {
					
					if(lstProcEspera.size() == 1) {
						
						processador.executaProcesso(lstProcEspera.get(0));
						
						lstProcEspera.remove(0);
						
						controleContexto = true;
						
					}else if(lstProcEspera.size() > 1) {
						
						 indexMaiorPriori = agendador.maiorPrioridade(lstProcEspera);
						 
						 processador.executaProcesso(lstProcEspera.get(indexMaiorPriori));
						 
						 lstProcEspera.remove(indexMaiorPriori);
						 
						 controleContexto = true;
						  
					}else {
						
						if(lstProcesso != null) {
							 
							 if(lstProcesso.length == 1) {
								 
								 if(tempoAtual >= lstProcesso[0].getTempoChegada()) {
									 
									 lstProcesso[0].setStatus("Executando");
									 
									 processador.executaProcesso(lstProcesso[0]);			 //Coloca o processo no processador, remove da lista de processos 
									 
									 lstProcesso = null;
									 
									 controleContexto = true;
								
									 break;
								 }
							 } else {
								 
								 for(count=0; count<lstProcesso.length-1;count++) {
									 
									 if(tempoAtual >= lstProcesso[count].getTempoChegada()) {
										 
										 lstProcesso[count].setStatus("Executando");
										 
										 processador.executaProcesso(lstProcesso[count]);			 //Coloca o processo no processador, remove da lista de processos 
										 
										 lstProcesso = agendador.removeProcessoLista(lstProcesso);
										 
										 controleContexto = true;
										 
										 break;
									 }
								 }
							 }
						 }
					}
					
				 }else {
					 
					 if(processador.getTempoNoProc() < agendador.getFatiaTempo()) {
						 // processo ta dentro da fatia de tempo
						
						 if(processador.procEmExecucao().getStatus() == "Finalizado") {
							 
							 
							 if(!lstProcConcluido.contains(processador.procEmExecucao()))
								 lstProcConcluido.add(processador.procEmExecucao());
							 
							 processador.executaProcesso(null);							// Procurar na lista de espera se tem algo, se nao tiver procurar na lista de processo se algum processo chegou
							 
							 controleContexto = true;	// Vai mudar o contexto
							 
							 if(lstProcEspera.size() > 0) {
								 
								 indexMaiorPriori = agendador.maiorPrioridade(lstProcEspera);
								 
								 processador.executaProcesso(lstProcEspera.get(indexMaiorPriori));
								 
								 lstProcEspera.remove(indexMaiorPriori);
								 
							 }else {
								 // nao tem nada na espera, verificar se o primeiro processo da lista de processo chegou
								 if(lstProcesso != null) {
									 
									 if(lstProcesso[0].getTempoChegada() <= tempoAtual) {
										 
										 lstProcesso[0].setStatus("Executando");
										 
										 processador.executaProcesso(lstProcesso[0]);	
										 
										 lstProcesso = agendador.removeProcessoLista(lstProcesso);
										 
										 break;
										 
									 } 
								 }
							 }	 
						 }else {
							 // processo nao finalizou ainda
							 
	/*				- se nao, Verifico na lista de processos se chegou algum processo de maior prioridade (nao preciso verificar na lista de espera, pois se o proc ta na lista de espera
					 *      	quer dizer que o mesmo tem a mesma prioriodade ou menor do que está no processador, ja que processos de maior prioridade entram "direto" no processador". 
					 *      	sem falar que o processo  ta dentro da fatia de tempo dele... se for da mesma prioridade, tem que esperar a fatia de tempo do processo que esta sendo executado
							    terminar.
	*/							 
							if(lstProcesso != null) {
								
								if(lstProcesso.length == 1) {
									
									if(lstProcesso[0].getTempoChegada() <= tempoAtual) {
										 
										 if(lstProcesso[0].getPrioridade() < processador.procEmExecucao().getPrioridade()) {
											 // coloca o processo que ta sendo executado na lista de espera e o novo processo no processador
											 
											 controleContexto = true;										
											 
											 processador.procEmExecucao().setStatus("Em Espera"); 
											 
											 lstProcesso[0].setStatus("Executando");
											 
											 lstProcEspera.add(processador.procEmExecucao());
											 
											 processador.executaProcesso(lstProcesso[0]);
											 	 
											 lstProcesso = null;
											 
										 }else {
											 // Caso o processo tenha chegado mas seja de mesma prioridade ou menor
											 
											 lstProcesso[0].setStatus("Em Espera");
											
											 lstProcEspera.add(lstProcesso[0]);
											 
											 lstProcesso = null;
											 
										 }
										 
									 }
									
								}
								 
								else if(lstProcesso.length > 1) {
									 
									 for(count = 0; count< lstProcesso.length-1; count++) {
										 
										 if(lstProcesso[count].getTempoChegada() <= tempoAtual) {
											 
											 if(lstProcesso[count].getPrioridade() < processador.procEmExecucao().getPrioridade()) {
												 // coloca o processo que ta sendo executado na lista de espera e o novo processo no processador
												 
												 controleContexto = true;
												 
												 processador.procEmExecucao().setStatus("Em Espera");
												 
												 processador.setTempoNoProc();
												 
												 lstProcesso[count].setStatus("Executando");
												 
												 lstProcEspera.add(processador.procEmExecucao());
												 
												 processador.executaProcesso(lstProcesso[count]);
												 
												 lstProcesso = agendador.removeProcessoLista(lstProcesso);
												 
												 break;
												 
											 }else {
												 // Caso o processo tenha chegado mas seja de mesma prioridade ou menor
												 
												 lstProcesso[count].setStatus("Em Espera");
												 
												 lstProcEspera.add(lstProcesso[count]);
				
												 lstProcesso = agendador.removeProcessoListaEspera(lstProcesso);

												 break;
											 }
											 
										 }
					
									 }
									 
								 }
								
							}
							 
						 }
						 
						 
					 }else {
						 
						 controleContexto = true;
						 
						 /* se chegou ao limite do timeslice 
						  * 
						  * - caso o quantum tenha chegado no limite:
						 *   	- verificar se na lista de espera possui um processo de mesma prioridade do que esta sendo executado.
						 *   		- se tiver, pegar o processo que esta sendo executado, jogar na lista de espera e executar o processo da lista de espera com mesma prioridade
						 *   	- caso nao tenha na lista de espera, verificar se naquele instante de tempo chegou algum processo 
						 *   		- se sim e for de prioridade maior, joga o processo que esta no processador para a lista de espera e executa o novo processo que chegou.
						 *   		- se sim e for prioridade menor, jogar na lista de espera e continua execução do processo que ja esta na cpu
						 *          - se nao, verificar se o processo que esta no proc foi finalizado
						 *          	- se sim, tirar o processo da cpu e esperar o proximo
						 *          	- se nao, continuar executando o mesmo processo 
						  * */
						 
						 if(processador.procEmExecucao().getStatus() == "Finalizado") {
							 
							 lstProcConcluido.add(processador.procEmExecucao());
							 
							 
							 if(lstProcEspera.size() == 1) {
							 
								 processador.executaProcesso(lstProcEspera.get(0));
								 		 
								 lstProcEspera.remove(0);
							 } 
							 
							 else if(lstProcEspera.size() > 1) {
								 
								 indexMaiorPriori = agendador.maiorPrioridade(lstProcEspera);
								 
								 processador.executaProcesso(lstProcEspera.get(indexMaiorPriori));
								 
								 lstProcEspera.remove(indexMaiorPriori);
								 
							 }
							 
							 controleContexto = true;	// Vai mudar o contexto
						 
						 }else {
							 
							 if(lstProcEspera.size() == 1) {
								 
								 if(lstProcEspera.get(0).getPrioridade() == processador.procEmExecucao().getPrioridade()) {
									 
									 processador.procEmExecucao().setStatus("Em Espera");
									 
									 lstProcEspera.get(0).setStatus("Executando");
									 
									 lstProcEspera.add(processador.procEmExecucao());
									 
									 processador.executaProcesso(lstProcEspera.get(0));
									 
									 lstProcEspera.remove(lstProcEspera.get(0));
									 
								 }
							 
							 } else if(lstProcEspera.size() > 1) {
								
								 for(count =0; count < lstProcEspera.size(); count++) {
									 
									 if(lstProcEspera.get(count).getPrioridade() == processador.procEmExecucao().getPrioridade()) {
										 
										 processador.procEmExecucao().setStatus("Em Espera");
										 
										 lstProcEspera.get(count).setStatus("Executando");
										 
										 lstProcEspera.add(processador.procEmExecucao());
										 
										 processador.executaProcesso(lstProcEspera.get(count));
										 
										 lstProcEspera.remove(lstProcEspera.get(count));
										 
										 break;
									 }
									 
								 }
								 
							 } 
							 
						 }
						  
					 }
					 	 
				 }	
				
				tempoAtual++;
				
				if(lstProcEspera!=null)
					agendador.adicionaTempoEspera(lstProcEspera);
				
				if(processador.procEmExecucao() != null) {
					 
					if(processador.getTempoNoProc() == agendador.getFatiaTempo() || controleContexto == true  ) {
						
						if(grafico.charAt(grafico.length()-1) == '-')
							
							grafico+=processador.procEmExecucao().getNumProcesso();
						
						else if(grafico.charAt(grafico.length()-1) != 'C')
							
							grafico+= "C";
						
						
						tempoAtual++;
						
						processador.zeraFatia();
						
						controleContexto = false;
					}
					else{

						processador.setTempoNoProc();
						
						if(processador.procEmExecucao().getStatus() != "Finalizado" || processador.procEmExecucao().getTempoExec() == 0.0)
							
							grafico+= processador.procEmExecucao().getNumProcesso();
					}
					
				}else 
					grafico+="-";
						
				if(lstProcesso == null) {
					
					if(lstProcEspera.isEmpty()) {
						
						if(processador.procEmExecucao() == null) {
							
							processosFinalizados = true;
							
							System.out.println("CPU:" + grafico);
						
							System.out.println(agendador.getTempoEsperaMedio(lstProcConcluido));
							
						}
					}
				}				
			}
			
        }catch(Exception ex){
        	
        	System.out.println(ex);
        }
        
     }
	

}
