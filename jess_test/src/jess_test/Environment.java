package jess_test;

import jess.JessException;
import GUI.GUI;

public class Environment extends Thread {
	private boolean go = true;
	private static int worldIterations = 2;

	@Override
	public synchronized void start() {
		try {
			for (Sala s : Main.casa.salas)
				if (s != null)
					Main.r.add(s);
			Main.r.add(Main.mundo);
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.start();
	}

	@Override
	public void run() {
		int i = 0;
		while (go) {
			try {
				for (Sala s : Main.casa.salas) {

					if (s != null) {
						if (Boolean.TRUE.equals(s.getAquecedor())) {
							// System.out.println(s.getTemperatura()+"    "+Main.mundo.getTemperatura());
							s.setTemperatura(s.getTemperatura() + 1);

						}
						if (Boolean.TRUE.equals(s.getJanela())) {
							// System.out.println(s.getTemperatura()+"    "+Main.mundo.getTemperatura());
							if (Main.mundo.getTemperatura() != s
									.getTemperatura()) {

								s.setTemperatura((float) (0.1 * (Main.mundo
										.getTemperatura() - s.getTemperatura()) + s
										.getTemperatura()));
							}

						} else {
							s.setTemperatura((float) (0.05 * (Main.mundo
									.getTemperatura() - s.getTemperatura()) + s
									.getTemperatura()));
						}

						if (Boolean.TRUE.equals(s.getAc())) {
							s.setTemperatura(s.getTemperatura() - 1);
						}

						if (Boolean.TRUE.equals(s.getPersiana())) {
							if (Boolean.TRUE.equals(s.getLampada())) {
								if (s.getAntesLampada() != s.getLampada()) {
									s.setAntesLampada(true);
									s.setLuz((int) (0.6 * Main.mundo
											.getLuminosidade() + 1200));
								}
							} else {
								if (Boolean.FALSE.equals(s.getLampada())) {
									if (s.getAntesLampada() != s.getLampada()) {
										s.setAntesLampada(false);
										s.setLuz((int) (0.6 * Main.mundo
												.getLuminosidade()));
										if (s.getLuz() < 0)
											s.setLuz(0);
									}

								}
							}
						}

						if (Boolean.FALSE.equals(s.getPersiana())) {
							if (Boolean.TRUE.equals(s.getLampada())) {
								if (s.getAntesLampada() != s.getLampada()) {
									s.setAntesLampada(true);
									s.setLuz((int) (0.1 * Main.mundo
											.getLuminosidade() + 1200));
								}
							} else {
								if (Boolean.FALSE.equals(s.getLampada())) {
									if (s.getAntesLampada()!= s.getLampada()) {
										s.setAntesLampada(false);
										s.setLuz((int) (0.1 * Main.mundo
												.getLuminosidade()));
										if (s.getLuz() < 0)
											s.setLuz(0);
									}
								}

							}
						}

						if (Boolean.TRUE.equals(s.getLampada())) {
							if (s.getAntesLampada() ^ s.getLampada()) {
								s.setAntesLampada(true);
								s.setLuz(s.getLuz() + 1200);
							}
						}
						if (Boolean.FALSE.equals(s.getLampada())) {
							if (s.getAntesLampada() ^ s.getLampada()) {
								s.setAntesLampada(false);
								s.setLuz(s.getLuz() - 1200);
								if (s.getLuz() < 0)
									s.setLuz(0);
							}
						}

						Main.r.updateObject(s);
					}
				}
				if (i-- == 0) {
					Main.mundo.iteration();
					Main.r.updateObject(Main.mundo);
					GUI.updateWorld();
					i = worldIterations;
				}
				Main.r.run();
				GUI.updateState();
				Thread.sleep(1000);
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
