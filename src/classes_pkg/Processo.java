package classes_pkg;

public class Processo {
	private int prioridade ; 
	private float tempoExec,tempoChegada;
	private String status;
	private int numProcesso;
	private float tempoRestante;
	private float[] IO;
	private float tempoEspera;
	private float tempoResposta;
	
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
		
		this.tempoEspera = 0;
		
		this.tempoResposta = -1;
		
	}
	public Processo(float tempoChegada,float tempoExec, int prioridade, int numProcesso, String Io ) {
		
		this(tempoChegada, tempoExec, prioridade, numProcesso);
		
		String[] textoSeparado = Io.split(" ");
		
		this.IO = new float[textoSeparado.length];
		
		for(int i = 0; i< textoSeparado.length; i++) {
			
			this.IO[i] = Float.parseFloat(textoSeparado[i]);
			
		}
		
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
		
		if(tempoExec ==  0) {
			
			status = "Finalizado";
			
		}
	}
	
	public void adicionaTempEspera() {
		
		tempoEspera++;
		
	}
	
	public float getTempoEspera() {
		
		return this.tempoEspera;
	}
	
	public void setTempoResposta(float tr) {
		this.tempoResposta = tr;
	}
	
	public float getTempoResposta() {
		
		return tempoResposta;
	}
}
