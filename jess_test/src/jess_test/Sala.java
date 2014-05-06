package jess_test;

public class Sala {
	/*sensores*/
	int temperatura;
	int luz;
	int humidade;
	
	
	/* dispositivos */
	Boolean aquecedor;
	Boolean janela;
	Boolean ac;
	Boolean maqCafe;
	Boolean porta;
	Boolean movimento;
	Boolean alarme;
	Boolean inundacao;
	Boolean persiana;
	Boolean forno;
	Boolean lampada;
	
	
	
	
	
	public Boolean getAc() {
		return ac;
	}

	public void setAc(Boolean ac) {
		this.ac = ac;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	public boolean getAquecedor() {
		return aquecedor;
	}

	public void setAquecedor(boolean aquecedor) {
		this.aquecedor = aquecedor;
	}

	public boolean getJanela() {
		return janela;
	}

	public void setJanela(boolean janela) {
		this.janela = janela;
	}

	
}
