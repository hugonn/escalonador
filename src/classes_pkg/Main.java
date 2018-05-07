package classes_pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String coord = "C:\\Users\\Pai e Mae\\Desktop\\arc.txt";
        try {
        	int i = 0, count=0;

        	float tChegada,tExec,tempoAtual =0;
			int priori,countProcesso=0;
			boolean processosFinalizados = false;
			boolean controleContexto = false;
			
			ArrayList<Processo> lstProcEspera = new ArrayList<Processo>();
			
			String strAux="";
			String strAux2="";
			String grafico="";
			
	        File arq = new File(coord);
	        FileReader fi = new FileReader(arq);  
	        BufferedReader leitor = new BufferedReader(fi);
	        String linha = leitor.readLine();
	        
			Processo lstProcesso[] = new Processo[Character.getNumericValue(linha.charAt(0))];
			linha = leitor.readLine();
	       
			Processador processador = new Processador();
			Escalonador agendador = new Escalonador(Character.getNumericValue(linha.charAt(0))); //Só para nao colocar o nome da classe de novo. Coloquei agendador
			
			linha = leitor.readLine();
			
			while(linha != null) {
				
				tChegada = 0;
				tExec = 0;
				priori = 0;
				
				i = linha.indexOf(" ");
				tChegada = Float.parseFloat(linha.substring(0, i));
				strAux = linha.substring(i+1,linha.length());
				
				i = strAux.indexOf(" ");
				tExec = Float.parseFloat(strAux.substring(0, i));
				strAux2 = strAux.substring(i+1,strAux.length());
				
				priori = Integer.parseInt(strAux2.substring(0, strAux2.length()));  //processos estao em uma lista de processos "circular"
				
				Processo pro = new Processo(tChegada,tExec,priori,countProcesso+1);
				lstProcesso[countProcesso] = pro;
				
				countProcesso++;
				linha = leitor.readLine();

			}
			
			while(processosFinalizados != true) { // Enquanto existem processos na lista de processos
				
			
				if(processador.procEmExecucao()==null) {
					// processador nao tem nenhum processo ainda
					
				
					if(tempoAtual != 0){
						for(count=0;count<lstProcesso.length; count++) {
							
							if(lstProcesso[count].getTempoChegada() == tempoAtual) {		//Implementação para o primeiro processo na lista
								processador.executaProcesso(lstProcesso[count]);
								
								
								lstProcesso[count].setStatus("Executando");
								grafico +=  String.valueOf(processador.procEmExecucao().getNumProcesso());		// Verificar a lista de processo, já que nenhum processo esta no processador. Nao tem pq verificar a lista de espera
								
							}
						}
						
						if( processador.procEmExecucao() == null) 
							grafico += '-';
					}
					
				}else { // processador ta executando um processo
					
					if(processador.getTempoNoProc() != agendador.getFatiaTempo()) {
						
						if(processador.procEmExecucao().getStatus() == "Finalizado") {
							// Pesquisar o processo na lista de espera ou na lista de processos
						}else {
							
							processador.setTempoNoProc();
							
							for(count = 0; count < lstProcesso.length ; count++) {
										
								if((lstProcesso[count].getStatus() != "Executando" || lstProcesso[count].getStatus() != "Finalizado") && lstProcesso[count].getTempoChegada() <= tempoAtual) { // Processo chegou, verificar se ele tem a prioridade maior que o que esta sendo exec  
											
									if(processador.procEmExecucao().getPrioridade() > lstProcesso[count].getPrioridade()) { // se tiver, troca o contexto, atualiza o tempo de execução do processo e troca o seu status. Lista de Espera é só para processos de mesma prioridade
										grafico+="C";
										controleContexto = true;
														
										processador.procEmExecucao().diminuiTempoExec(processador.getTempoNoProc());
										processador.procEmExecucao().setStatus("Parado");
				//										lstProcEspera.add(processador.procEmExecucao());
														
										processador.executaProcesso(lstProcesso[count]);
										processador.procEmExecucao().setStatus("Executando");
														
//										grafico += processador.procEmExecucao().getNumProcesso();
												
									}else if(processador.procEmExecucao().getPrioridade() <= lstProcesso[count].getPrioridade()) {
										lstProcEspera.add(lstProcesso[count]);
										
										grafico+=processador.procEmExecucao().getNumProcesso();			// Não esquecer de diminuir tempo de execução cada vez que o processo fica no processador
										processador.procEmExecucao().diminuiTempoExec(1);
										
									}
		
								}
										
							}
						}
						
						
					}else {
						System.out.println(processador.getTempoNoProc());
						if(processador.procEmExecucao().getStatus() == "Finalizado") {
							//Troco de processo direto. Pesquisar em uma das listas
						}
						
						if(lstProcEspera.size()>0) {
							for(count=0;count < lstProcEspera.size() ; count++) {
								if(lstProcEspera.get(count).getPrioridade() == processador.procEmExecucao().getPrioridade()) {
									
								}
							}
						}
						
					}
					
					
					if(tempoAtual == 12) {
						processosFinalizados = true;
					}
					
					
					// processador possui algum processo
					
					
				}
				
//				System.out.println(tempoAtual + " - " + processador.getTempoNoProc() +"-" + grafico);
				
				if(controleContexto ) {
					tempoAtual += 2;
					controleContexto = false;
				}
				else {
					tempoAtual++;
					
				}
				
			}
			
			for(int c = 0; c<lstProcesso.length; c++) {
				if(lstProcesso[c].getStatus() != "Finalizado") {
					break;
				}else {
					processosFinalizados = true;
				}
			}
			System.out.println(tempoAtual + "-" + processador.procEmExecucao().getStatus() + " - " + processador.getTempoNoProc() +"- "+processador.procEmExecucao().getTempoExec() +" - " + grafico);
        }catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
        
     }
	

}
