package jess_test;

import GUI.GUI;
import GUI.GUICasa;
import jess.*;

public class Main {
	public static Casa casa;
	public static Rete r;
	private static Environment environmentThread;
	public static World mundo;

	public static void main(String[] args) throws JessException {
		casa = new Casa();
		r = new Rete();
		r.batch("rules.clp");
		environmentThread = new Environment();
		
		GUICasa.init();
		mundo = new World();
		mundo.init();
		
	}

	public static void houseReady() {
		GUI.init();
		environmentThread.start();
	}
}
