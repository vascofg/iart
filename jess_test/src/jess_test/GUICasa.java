package jess_test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class GUICasa {

	private static JFrame frame;
	private static OptionsPanel options;
	private static Border optionsBorder = new MatteBorder(0, 1, 0, 0,
			Color.black);
	private static Border selectedBorder = new LineBorder(Color.blue);
	static ArrayList<CellPanel> components = new ArrayList<CellPanel>();
	private static Dimension cellDimension = new Dimension(100, 100);
	private static Dimension optionsPanelDimension = new Dimension(250, 0);

	private static int selectedIndex = -1;

	private static class CellPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		int tipo = -1;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (tipo >= 0)
				g.drawImage(Sala.TipoImgs[tipo], 0, 0, null);
		}
	}

	private static class OptionsPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private JTextField nome;
		private JComboBox<String> tipo;
		private JCheckBox inundacao;
		private JCheckBox aquecedor;
		private JCheckBox janela;
		private JCheckBox ac;
		private JCheckBox maqCafe;
		private JCheckBox persiana;
		private JCheckBox forno;
		private JCheckBox lampada;
		private JButton guardar;
		private JButton apagar;
		private JButton ok;

		public OptionsPanel() {
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			this.setLayout(layout);

			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.NORTH;
			c.weightx = 1;
			c.weighty = 1;

			c.gridwidth = 2;
			nome = new JTextField("Nome");
			nome.setHorizontalAlignment(SwingConstants.CENTER);
			nome.setEnabled(false);
			nome.setFont(nome.getFont().deriveFont(32.0f));
			addComponent(this, nome, layout, c, 0, 0);

			tipo = new JComboBox<String>(Sala.TipoText);
			tipo.setEnabled(false);
			tipo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					components.get(selectedIndex).tipo = tipo
							.getSelectedIndex();
					components.get(selectedIndex).repaint();
				}
			});
			addComponent(this, tipo, layout, c, 0, 1);

			inundacao = new JCheckBox("Sensor inundação");
			inundacao.setEnabled(false);
			c.gridwidth = 1;
			addComponent(this, inundacao, layout, c, 0, 2);

			aquecedor = new JCheckBox("Aquecedor");
			aquecedor.setEnabled(false);
			addComponent(this, aquecedor, layout, c, 1, 2);

			janela = new JCheckBox("Janela");
			janela.setEnabled(false);
			addComponent(this, janela, layout, c, 0, 3);

			ac = new JCheckBox("Ar condicionado");
			ac.setEnabled(false);
			addComponent(this, ac, layout, c, 1, 3);

			maqCafe = new JCheckBox("Máquina de café");
			maqCafe.setEnabled(false);
			addComponent(this, maqCafe, layout, c, 0, 4);

			persiana = new JCheckBox("Persiana");
			persiana.setEnabled(false);
			addComponent(this, persiana, layout, c, 1, 4);

			forno = new JCheckBox("Forno");
			forno.setEnabled(false);
			addComponent(this, forno, layout, c, 0, 5);

			lampada = new JCheckBox("Lâmpada");
			lampada.setEnabled(false);
			addComponent(this, lampada, layout, c, 1, 5);

			guardar = new JButton("Guardar", null);
			guardar.setEnabled(false);
			guardar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Sala s;
					if (Main.casa.salas[selectedIndex]!=null) //edit
						s = Main.casa.salas[selectedIndex];
					else { //create
						s = new Sala(Sala.WC);
						Main.casa.salas[selectedIndex] = s;
					}

					s.setNome(nome.getText());
					s.setTipo(tipo.getSelectedIndex());
					s.setInundacao(inundacao.isSelected() ? false : null);
					s.setAquecedor(aquecedor.isSelected() ? false : null);
					s.setJanela(janela.isSelected() ? false : null);
					s.setAc(ac.isSelected() ? false : null);
					s.setMaqCafe(maqCafe.isSelected() ? false : null);
					s.setPersiana(persiana.isSelected() ? false : null);
					s.setForno(forno.isSelected() ? false : null);
					s.setLampada(lampada.isSelected() ? false : null);
					apagar.setEnabled(true);
				}
			});
			addComponent(this, guardar, layout, c, 0, 6);

			apagar = new JButton("Apagar", null);
			apagar.setEnabled(false);
			apagar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Main.casa.salas[selectedIndex] != null) {
						Main.casa.salas[selectedIndex] = null;
						resetAllInputs();
					}
				}
			});
			addComponent(this, apagar, layout, c, 1, 6);
		}

		private void enableAllInputs() {
			nome.setEnabled(true);
			tipo.setEnabled(true);
			inundacao.setEnabled(true);
			aquecedor.setEnabled(true);
			janela.setEnabled(true);
			ac.setEnabled(true);
			maqCafe.setEnabled(true);
			persiana.setEnabled(true);
			forno.setEnabled(true);
			lampada.setEnabled(true);
			guardar.setEnabled(true);
		}

		private void resetAllInputs() {
			nome.setText("");
			tipo.setSelectedIndex(0);
			inundacao.setSelected(false);
			aquecedor.setSelected(false);
			janela.setSelected(false);
			ac.setSelected(false);
			maqCafe.setSelected(false);
			persiana.setSelected(false);
			forno.setSelected(false);
			lampada.setSelected(false);
			apagar.setEnabled(false);
		}

		public void loadCell(int index) {
			enableAllInputs();
			// clear previous border and image
			if (selectedIndex >= 0 && index != selectedIndex) {
				components.get(selectedIndex).setBorder(null);
				if (Main.casa.salas[selectedIndex] == null)
					components.get(selectedIndex).tipo = -1;
			}
			selectedIndex = index;
			try {
				Sala s = Main.casa.salas[selectedIndex];
				nome.setText(s.getNome());
				tipo.setSelectedIndex(s.getTipo());
				inundacao.setSelected(s.getInundacao() != null);
				aquecedor.setSelected(s.getAquecedor() != null);
				janela.setSelected(s.getJanela() != null);
				ac.setSelected(s.getAc() != null);
				maqCafe.setSelected(s.getMaqCafe() != null);
				persiana.setSelected(s.getPersiana() != null);
				forno.setSelected(s.getForno() != null);
				lampada.setSelected(s.getLampada() != null);
				apagar.setEnabled(true);
			} catch (IndexOutOfBoundsException | NullPointerException e) { // item
																			// does
																			// not
																			// exist
				resetAllInputs();
				apagar.setEnabled(false);
			}
		}
	}

	private static MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			((JPanel) e.getComponent()).setBorder(selectedBorder);
		}

		public void mouseExited(MouseEvent e) {
			if (Integer.parseInt(e.getComponent().getName()) != selectedIndex)
				((JPanel) e.getComponent()).setBorder(null);
		}

		public void mouseClicked(MouseEvent e) {
			options.loadCell(Integer.parseInt(e.getComponent().getName()));
		};
	};

	public static void init() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main.casa = new Casa();
		GridBagLayout layout = new GridBagLayout();
		Container contentPanel = frame.getContentPane();
		CellPanel temp = null;
		frame.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		// Populating Arraylist object.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp = new CellPanel();
				int index = i * 3 + j;
				temp.setName(Integer.toString(index));
				// temp.setBorder(notSelectedBorder);
				temp.setPreferredSize(cellDimension);
				temp.addMouseListener(mouseAdapter);
				GUICasa.components.add(index, temp);
				addComponent(contentPanel, temp, layout, c, j, i);
			}
		}
		options = new OptionsPanel();
		options.setBorder(optionsBorder);
		options.setPreferredSize(optionsPanelDimension);
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = GridBagConstraints.REMAINDER;
		addComponent(contentPanel, options, layout, c, 4, 0);
		options.getRootPane().setDefaultButton(options.guardar); // set enter
																	// action
		frame.pack();
		frame.setLocationRelativeTo(null); //center window
		frame.setVisible(true);
	}

	private static void addComponent(Container container, JComponent component,
			GridBagLayout layout, GridBagConstraints constraints, int gridX,
			int gridY) {
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		layout.setConstraints(component, constraints);
		container.add(component);

	}

	public static void main(String[] args) {
		GUICasa.init();
	}
}
