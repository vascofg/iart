package jess_test;

import jess.*;

public class Main {
	static Casa casa;
	static Rete r;

	public static void main(String[] args) throws JessException {
		casa = new Casa();
		r = new Rete();
		r.batch("rules.clp");
		/**/
		GUICasa.init();
		// run();
	}

	public static void run() throws JessException {
		Sala s = casa.salas[0];

		while (true) {
			try {
				Thread.sleep(100);
				if (Boolean.TRUE.equals(s.getAquecedor())) {
					// System.out.println("aquecedor ligado");
					s.setTemperatura(s.getTemperatura() + 1);

				}
				if (Boolean.TRUE.equals(s.getJanela())) {
					// System.out.println("janela aberta");
					s.setTemperatura(s.getTemperatura() - 1);
				}

				r.run();
			} catch (JessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(s.getTemperatura());
			r.updateObject(s);
		}
	}
}
