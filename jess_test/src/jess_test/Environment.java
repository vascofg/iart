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
			e.printStackTrace();
		}
		super.start();
	}

	@Override
	public void run() {
		int i = 0;
		while (go) {
			try {
				World.consumoEnergetico = 0;
				Main.r.run();
				if (i-- == 0) {
					Main.mundo.iteration();
					Main.r.updateObject(Main.mundo);
					GUI.settingsPanel.updateWorld();
					i = worldIterations;
				}
				GUI.updateState();
				for (Sala s : Main.casa.salas) {
					if (s != null) {
						if (Boolean.TRUE.equals(s.getAquecedor())) {
							World.consumoEnergetico += 1550;
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
							World.consumoEnergetico += 1350;
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
									if (s.getAntesLampada() != s.getLampada()) {
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
							World.consumoEnergetico += 40;
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
						if (s.getForno() != null) {
							if (World.forno) {
								if (World.horas == World.horaForno)
									s.setForno(true);
								else
									s.setForno(false);
							} else
								s.setForno(false);
						}
						if (s.getMaqCafe() != null) {
							if (World.maqCafe) {
								if (World.horas == World.horaMaqCafe)
									s.setMaqCafe(true);
								else
									s.setMaqCafe(false);
							} else
								s.setMaqCafe(false);
						}
						if (Boolean.TRUE.equals(s.getForno()))
							World.consumoEnergetico += 1500;

						if (Boolean.TRUE.equals(s.getMaqCafe()))
							World.consumoEnergetico += 600;
						Main.r.updateObject(s);
					}
				}
				GUI.settingsPanel.updatePowerConsumption();
				Thread.sleep(1000);
			} catch (JessException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
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
