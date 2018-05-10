package classes_pkg;

public class Processo {
	private int prioridade ; 
	private float tempoExec,tempoChegada;
	private String status;
	private int numProcesso;
	private float tempoRestante;
	
	public int getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(int numProcesso) {
		this.numProcesso = numProcesso;
	}

	public Processo(float tempoChegada,float tempoExec, int prioridade, int numProcesso ) {
		this.tempoChegada = tempoChegada;
		this.tempoExec = tempoExec;
		status = "Parado";
		this.numProcesso = numProcesso;
		this.prioridade = prioridade;
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public float getTempoExec() {
		return tempoExec;
	}

	public void setTempoExec(float tempoExec) {
		this.tempoExec = tempoExec;
	}

	public float getTempoChegada() {
		return tempoChegada;
	}

	public void setTempoChegada(float tempoChegada) {
		this.tempoChegada = tempoChegada;
	}
	
	public void diminuiTempoExec(float i){
		this.tempoExec-=i;
		if(tempoExec <  0) {
			status = "Finalizado";
		}
	}
}
