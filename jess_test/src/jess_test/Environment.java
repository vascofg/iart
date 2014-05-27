package jess_test;

import jess.JessException;
import GUI.GUI;

public class Environment extends Thread {
	private boolean go = true;

	@Override
	public synchronized void start() {
		for (Sala s : Main.casa.salas)
			if (s != null)
				try {
					Main.r.add(s);
				} catch (JessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		super.start();
	}

	@Override
	public void run() {
		while (go) {
			try {
				for (Sala s : Main.casa.salas) {
					if (s != null) {
						if (Boolean.TRUE.equals(s.getAquecedor())) {
							// System.out.println("aquecedor ligado");
							s.setTemperatura(s.getTemperatura() + 1);

						}
						if (Boolean.TRUE.equals(s.getJanela())) {
							// System.out.println("janela aberta");
							s.setTemperatura(s.getTemperatura() - 1);
						}
						Main.r.updateObject(s);
					}
				}
				Main.r.run();
				GUI.updateState();
				Thread.sleep(100);
			} catch (JessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void interrupt() {
		this.go = false;
		super.interrupt();
	}
}
