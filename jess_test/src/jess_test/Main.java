package jess_test;

import GUI.GUI;
import GUI.GUICasa;
import jess.*;

public class Main {
	public static Casa casa;
	static Rete r;
	private static Environment environmentThread;

	public static void main(String[] args) throws JessException {
		casa = new Casa();
		r = new Rete();
		r.batch("rules.clp");
		environmentThread = new Environment();
		
		GUICasa.init();
		World mundo= new World();
		mundo.init();
		while(true){
			try {
				Thread.sleep(1000);
				mundo.iteration();
				System.out.println("horas :"+(float)mundo.getHours());
				System.out.println("temp :"+(float)mundo.getTemperatura());
				System.out.println("lux :"+(float)mundo.getLuminosidade());
				if(mundo.getHumidade()>90)
					System.out.println("chuva forte");
				
				System.out.println("humidade :" + mundo.getHumidade()+"\n");
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void houseReady() {
		GUI.init();
		environmentThread.start();
	}
}
