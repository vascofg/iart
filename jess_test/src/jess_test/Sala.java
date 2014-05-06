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
	
	
	
	
	
	public int getLuz() {
		return luz;
	}

	public void setLuz(int luz) {
		this.luz = luz;
	}

	public int getHumidade() {
		return humidade;
	}

	public void setHumidade(int humidade) {
		this.humidade = humidade;
	}

	public Boolean getMaqCafe() {
		return maqCafe;
	}

	public void setMaqCafe(Boolean maqCafe) {
		this.maqCafe = maqCafe;
	}

	public Boolean getPorta() {
		return porta;
	}

	public void setPorta(Boolean porta) {
		this.porta = porta;
	}

	public Boolean getMovimento() {
		return movimento;
	}

	public void setMovimento(Boolean movimento) {
		this.movimento = movimento;
	}

	public Boolean getAlarme() {
		return alarme;
	}

	public void setAlarme(Boolean alarme) {
		this.alarme = alarme;
	}

	public Boolean getInundacao() {
		return inundacao;
	}

	public void setInundacao(Boolean inundacao) {
		this.inundacao = inundacao;
	}

	public Boolean getPersiana() {
		return persiana;
	}

	public void setPersiana(Boolean persiana) {
		this.persiana = persiana;
	}

	public Boolean getForno() {
		return forno;
	}

	public void setForno(Boolean forno) {
		this.forno = forno;
	}

	public Boolean getLampada() {
		return lampada;
	}

	public void setLampada(Boolean lampada) {
		this.lampada = lampada;
	}

	public void setAquecedor(Boolean aquecedor) {
		this.aquecedor = aquecedor;
	}

	public void setJanela(Boolean janela) {
		this.janela = janela;
	}

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
