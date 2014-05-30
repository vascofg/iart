package jess_test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sala {

	public static final int WC = 0;
	public static final int COZINHA = 1;
	public static final int QUARTO = 2;
	public static final int SALA = 3;
	public static final int SALAJANTAR = 4;

	public static final String[] TipoText = { "Casa de Banho", "Cozinha",
			"Quarto", "Sala", "Sala de Jantar" };

	public static final BufferedImage[] TipoImgs = new BufferedImage[5];

	static {
		try {
			TipoImgs[0] = ImageIO.read(new File("img/casadebanho.png"));
			TipoImgs[1] = ImageIO.read(new File("img/cozinha.png"));
			TipoImgs[2] = ImageIO.read(new File("img/quarto.png"));
			TipoImgs[3] = ImageIO.read(new File("img/sala.png"));
			TipoImgs[4] = ImageIO.read(new File("img/salaJantar.png"));
		} catch (IOException e) {
		}
	}

	private String nome;

	private int tipo;

	/* sensores */
	public float temperatura;
	public int luz;
	public boolean movimento;
	private Boolean alarme;
	private Boolean inundacao;
	private Boolean incendio;

	/* dispositivos */

	private Boolean aquecedor;
	private Boolean janela;
	private Boolean ac;
	private Boolean maqCafe;
	private Boolean persiana;
	private Boolean forno;
	private Boolean lampada;
	private Boolean antesLampada;
	private Boolean antesPersiana;

	public static class Builder {
		private int tipo;

		private String nome = "";
		private float temperatura = 0;
		private int luz = 0;
		private boolean movimento = false;
		private Boolean alarme = false;
		private Boolean inundacao = null;
		private Boolean incendio = false;
		private Boolean aquecedor = null;
		private Boolean janela = null;
		private Boolean ac = null;
		private Boolean maqCafe = null;
		private Boolean persiana = null;
		private Boolean forno = null;
		private Boolean lampada = null;

		public Builder(int tipo) {
			this.tipo = tipo;
		}

		public Builder nome(String val) {
			nome = val;
			return this;
		}

		public Builder temperatura(float val) {
			temperatura = val;
			return this;
		}

		public Builder luz(int val) {
			luz = val;
			return this;
		}

		public Builder inundacao() {
			inundacao = false;
			return this;
		}

		public Builder aquecedor() {
			aquecedor = false;
			return this;
		}

		public Builder janela(boolean persiana) {
			janela = false;
			if (persiana) // se receber true, existe persiana
				this.persiana = false;
			return this;
		}

		public Builder ac() {
			ac = false;
			return this;
		}

		public Builder maqCafe() {
			maqCafe = false;
			return this;
		}

		public Builder forno() {
			forno = false;
			return this;
		}

		public Builder lampada() {
			lampada = false;
			return this;
		}

		public Sala build() {
			return new Sala(this);
		}

	}

	public Sala(Builder builder) {
		nome = builder.nome;
		tipo = builder.tipo;
		temperatura = builder.temperatura;
		luz = builder.luz;
		movimento = builder.movimento;
		alarme = builder.alarme;
		inundacao = builder.inundacao;
		incendio = builder.incendio;
		aquecedor = builder.aquecedor;
		janela = builder.janela;
		ac = builder.ac;
		maqCafe = builder.maqCafe;
		persiana = builder.persiana;
		forno = builder.forno;
		lampada = builder.lampada;
	}

	public Sala(String nome, int tipo, float temperatura, int luz,
			Boolean inundacao, Boolean aquecedor, Boolean janela, Boolean ac,
			Boolean maqCafe, Boolean persiana, Boolean forno, Boolean lampada) {
		this.nome = nome;
		this.tipo = tipo;
		this.temperatura = temperatura;
		this.luz = luz;
		this.inundacao = inundacao;
		this.aquecedor = aquecedor;
		this.janela = janela;
		this.ac = ac;
		this.maqCafe = maqCafe;
		this.persiana = persiana;
		this.forno = forno;
		this.lampada = lampada;
	}

	public Sala(int tipo) {
		this.tipo = tipo;
		this.temperatura = 0;
		this.luz = 0;
		this.movimento = false;
		this.alarme = false;
		this.incendio = false;
		this.antesLampada = false;
		this.antesPersiana = false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(float temperatura) {
		this.temperatura = temperatura;
	}

	public int getLuz() {
		return luz;
	}

	public void setLuz(int luz) {
		this.luz = luz;
	}

	public boolean getMovimento() {
		return movimento;
	}

	public void setMovimento(boolean movimento) {
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

	public Boolean getIncendio() {
		return incendio;
	}

	public void setIncendio(Boolean incendio) {
		this.incendio = incendio;
	}

	public Boolean getAquecedor() {
		return aquecedor;
	}

	public void setAquecedor(Boolean aquecedor) {
		this.aquecedor = aquecedor;
	}

	public Boolean getJanela() {
		return janela;
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

	public Boolean getMaqCafe() {
		return maqCafe;
	}

	public void setMaqCafe(Boolean maqCafe) {
		this.maqCafe = maqCafe;
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

	public Boolean getAntesLampada() {
		return antesLampada;
	}

	public void setAntesLampada(Boolean antesLampada) {
		this.antesLampada = antesLampada;
	}

	public Boolean getAntesPersiana() {
		return antesPersiana;
	}

	public void setAntesPersiana(Boolean antesPersiana) {
		this.antesPersiana = antesPersiana;
	}

	public boolean emergencia() {
		if (Boolean.TRUE.equals(alarme) || Boolean.TRUE.equals(inundacao)
				|| Boolean.TRUE.equals(incendio))
			return true;
		return false;
	}
}
