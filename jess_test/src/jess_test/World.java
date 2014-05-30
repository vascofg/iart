package jess_test;

import java.util.Random;

public class World {
	static public int tempIdeal, luzIdeal, humidadeIdeal;
	static public double temperatura;
	static public double luminosidade;
	static public boolean poupanca;
	static public int humidade;
	private int horas;
	public int[] hum = { 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100 };
	int arrN;
	
	public static Integer getTempIdeal() {
		return tempIdeal;
	}

	public static Integer getLuzIdeal() {
		return luzIdeal;
	}

	public static Integer getHumidadeIdeal() {
		return humidadeIdeal;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public double getLuminosidade() {
		return luminosidade;
	}

	public void setLuminosidade(double luminosidade) {
		this.luminosidade = luminosidade;
	}

	public int getHumidade() {
		return humidade;
	}

	public void setHumidade(int humidade) {
		this.humidade = humidade;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public void init() {
		setTemperatura(13);
		setLuminosidade(0);
		setHumidade(5);
		setHoras(0);
		setPoupanca(false);
		
	}

	public void iteration() {
		int tempH = getHoras();
		if (getHoras() >= 23) {
			setHoras(0);
			Random rand = new Random();
			arrN = rand.nextInt(10);
		} else {
			setHoras(++tempH);

		}
		setTemperatura(calcTemp(tempH));
		setLuminosidade(calcLuminosidade(tempH));
		setHumidade(calcHumidade(arrN));
	}

	double calcTemp(int hours) {
		Random rand = new Random();
		double temp;
		temp = Math.sin(hours / (24 / Math.PI)) * (30 + rand.nextInt(10) - 5);
		return temp;
	}

	double calcLuminosidade(int hours) {
		Random rand = new Random();
		double lumen;
		lumen = Math.sin(hours / (24 / Math.PI))
				* (4500 + rand.nextInt(1000) - 200);
		if (lumen < 0)
			lumen = 0;
		return lumen;
	}

	int calcHumidade(int c) {

		Random rand = new Random();

		int n = rand.nextInt(11) - 5;
		return hum[c] + n;
	}

	public static boolean isPoupanca() {
		return poupanca;
	}

	public static void setPoupanca(boolean poupanca) {
		World.poupanca = poupanca;
	}
	
	
}
