package classes_pkg;

public class Processador {
	
	private Processo proc;
	
	private float tempoNoProc;
	
	public Processador() {
		
		proc = null;
		
		tempoNoProc = 0;
	}
	
	public void executaProcesso(Processo p) {
		
		proc = p;				 // coloca o processo a ser executado no processador
		
		tempoNoProc = 1;
	}
	
	public void zeraFatia() {
		
		tempoNoProc = 0;		// Zera a fatia de tempo do processador q
	}
	
	public Processo procEmExecucao() {
		
		return proc;
	}
	
	public void setTempoNoProc() {
		
		this.tempoNoProc += 1;
		
		proc.diminuiTempoExec(1);
	}
	
	public float getTempoNoProc(){
		
		return tempoNoProc;
	}

}
