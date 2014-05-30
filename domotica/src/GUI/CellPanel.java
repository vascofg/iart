package GUI;

import java.awt.Graphics;

import javax.swing.JPanel;

import jess_test.Sala;

class CellPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	int tipo = -1;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (tipo >= 0)
			g.drawImage(Sala.TipoImgs[tipo], 0, 0, this.getWidth(),
					this.getHeight(), null);
	}
}