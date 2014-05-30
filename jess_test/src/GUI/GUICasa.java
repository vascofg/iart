package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import jess_test.Main;
import jess_test.Sala;

public class GUICasa {

	private static JFrame frame;
	private static OptionsPanel options;
	private static Border selectedBorder = new LineBorder(Color.blue);
	static ArrayList<CellPanel> components = new ArrayList<CellPanel>();
	private static Dimension cellDimension = new Dimension(100, 100);

	private static int selectedIndex = -1;

	private static class OptionsPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private static Border border = new MatteBorder(0, 1, 0, 0, Color.black);
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
			this.setBorder(border);
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
			GUI.addComponent(this, nome, layout, c, 0, 0);

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
			GUI.addComponent(this, tipo, layout, c, 0, 1);

			inundacao = new JCheckBox("Sensor inundação");
			inundacao.setEnabled(false);
			c.gridwidth = 1;
			GUI.addComponent(this, inundacao, layout, c, 0, 2);

			aquecedor = new JCheckBox("Aquecedor");
			aquecedor.setEnabled(false);
			GUI.addComponent(this, aquecedor, layout, c, 1, 2);

			janela = new JCheckBox("Janela");
			janela.setEnabled(false);
			janela.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if (arg0.getStateChange() == ItemEvent.SELECTED)
						persiana.setEnabled(true);
					else
						persiana.setEnabled(false);
				}
			});
			GUI.addComponent(this, janela, layout, c, 0, 3);

			ac = new JCheckBox("Ar condicionado");
			ac.setEnabled(false);
			GUI.addComponent(this, ac, layout, c, 1, 3);

			maqCafe = new JCheckBox("Máquina de café");
			maqCafe.setEnabled(false);
			GUI.addComponent(this, maqCafe, layout, c, 0, 4);

			persiana = new JCheckBox("Persiana");
			persiana.setEnabled(false);
			GUI.addComponent(this, persiana, layout, c, 1, 4);

			forno = new JCheckBox("Forno");
			forno.setEnabled(false);
			GUI.addComponent(this, forno, layout, c, 0, 5);

			lampada = new JCheckBox("Lâmpada");
			lampada.setEnabled(false);
			GUI.addComponent(this, lampada, layout, c, 1, 5);

			guardar = new JButton("Guardar", null);
			guardar.setEnabled(false);
			guardar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ok.setEnabled(true);
					Sala s;
					if (Main.casa.salas[selectedIndex] != null) // edit
						s = Main.casa.salas[selectedIndex];
					else { // create
						s = new Sala(Sala.WC);
						s.setTemperatura((int) Math.round(Math.random() * 11) + 10);// 10-20
						Main.casa.salas[selectedIndex] = s;
					}

					s.setNome(nome.getText());
					s.setTipo(tipo.getSelectedIndex());
					s.setInundacao(inundacao.isSelected() ? false : null);
					s.setAquecedor(aquecedor.isSelected() ? false : null);
					s.setJanela(janela.isSelected() ? false : null);
					s.setAc(ac.isSelected() ? false : null);
					s.setMaqCafe(maqCafe.isSelected() ? false : null);
					s.setPersiana((persiana.isSelected() && persiana
							.isEnabled()) ? false : null);
					s.setForno(forno.isSelected() ? false : null);
					s.setLampada(lampada.isSelected() ? false : null);
					apagar.setEnabled(true);
				}
			});
			GUI.addComponent(this, guardar, layout, c, 0, 6);

			apagar = new JButton("Apagar", null);
			apagar.setEnabled(false);
			apagar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Main.casa.salas[selectedIndex] != null) {
						Main.casa.salas[selectedIndex] = null;
						if (Main.casa.getNumSalas() == 0)
							ok.setEnabled(false);
						resetAllInputs();
					}
				}
			});
			GUI.addComponent(this, apagar, layout, c, 1, 6);

			c.gridwidth = 2;
			ok = new JButton("OK", null);
			ok.setEnabled(false);
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
					Main.houseReady();
				}
			});
			GUI.addComponent(this, ok, layout, c, 0, 7);
		}

		private void enableAllInputs() {
			nome.setEnabled(true);
			tipo.setEnabled(true);
			inundacao.setEnabled(true);
			aquecedor.setEnabled(true);
			janela.setEnabled(true);
			ac.setEnabled(true);
			maqCafe.setEnabled(true);
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
			persiana.setEnabled(false);
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

		public void mousePressed(MouseEvent e) {
			options.loadCell(Integer.parseInt(e.getComponent().getName()));
		};
	};

	public static void init() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				GUI.addComponent(contentPanel, temp, layout, c, j, i);
			}
		}
		options = new OptionsPanel();
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = GridBagConstraints.REMAINDER;
		GUI.addComponent(contentPanel, options, layout, c, 4, 0);
		options.getRootPane().setDefaultButton(options.guardar); // set enter
																	// action
		frame.pack();
		frame.setLocationRelativeTo(null); // center window
		frame.setVisible(true);
	}
}
