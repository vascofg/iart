package jess_test;

import java.util.ArrayList;
import java.util.Random;

public class World {
	private double temperatura;
	private double Luminosidade;
	private double humidade;
	private int hours;
	public int[] hum = { 50, 55, 60, 65, 70, 75, 80, 85, 90, 100 };
	int arrN;
	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public double getLuminosidade() {
		return Luminosidade;
	}

	public void setLuminosidade(double luminosidade) {
		Luminosidade = luminosidade;
	}

	public double getHumidade() {
		return humidade;
	}

	public void setHumidade(double humidade) {
		this.humidade = humidade;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public void init() {
		setTemperatura(13);
		setLuminosidade(0);
		setHumidade(5);
		setHours(0);
	}

	public void iteration() {
		int tempH = getHours();
		if (getHours() >= 23){
			setHours(0);
			Random rand = new Random();
			arrN = rand.nextInt(10) ;
		}
		else {
			setHours(++tempH);

		}
		setTemperatura(calcTemp(tempH));
		setLuminosidade(calcLuminosidade(tempH));
		setHumidade( calcHumidade(arrN));
	}

	double calcTemp(int hours) {
		Random rand = new Random();
		double temp;
		temp = Math.sin(hours / (24 / Math.PI)) * (30 + rand.nextInt(10)-5);
		return temp;
	}

	double calcLuminosidade(int hours) {
		Random rand = new Random();
		double lumen;
		lumen = Math.sin(hours / (24 / Math.PI)) * (7500+ rand.nextInt(1000)-500) ;
		if(lumen<0)
			lumen=0;
		return lumen;
	}

	double calcHumidade(int c) {

		Random rand = new Random();

		int n = rand.nextInt(11)-5 ;
		return hum[c]+n;
	}
}
