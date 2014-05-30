package jess_test;

public class Casa {
	public Sala[] salas;
	public Boolean portaRua;

	public Casa() {
		salas = new Sala[9];
		portaRua = false;
	}

	public int getNumSalas() {
		int count = 0;
		for (Sala s : salas)
			if (s != null)
				count++;
		return count;
	}
}
