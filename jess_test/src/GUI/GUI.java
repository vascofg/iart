package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GUI.CellPanel;
import jess_test.Main;
import jess_test.Sala;
import jess_test.World;

public class GUI {
	private static JFrame frame;
	private static StatePanel statePanel;
	private static SettingsPanel settingsPanel;
	static CellPanel[] components = new CellPanel[9];
	private static Border selectedBorder = new LineBorder(Color.RED);

	private static Dimension cellDimension = new Dimension(150, 150);
	private static int selectedIndex = -1;

	private static MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			statePanel.loadCell(Integer.parseInt(e.getComponent().getName()));
		};
	};

	public static void init() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout layout = new GridBagLayout();
		Container contentPanel = frame.getContentPane();
		CellPanel temp = null;
		JLabel tmpLabel = null;
		frame.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		// Populating Arraylist object.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp = new CellPanel();
				temp.setLayout(new BorderLayout());
				int index = i * 3 + j;
				temp.setName(Integer.toString(index));
				tmpLabel = new JLabel();
				if (Main.casa.salas[index] != null) {
					temp.tipo = Main.casa.salas[index].getTipo();
					tmpLabel.setText(Main.casa.salas[index].getNome());
					tmpLabel.setMinimumSize(new Dimension(cellDimension.width,
							0));
					tmpLabel.setHorizontalAlignment(SwingConstants.CENTER);
					temp.add(tmpLabel, BorderLayout.PAGE_END);
					temp.setPreferredSize(cellDimension);
					temp.addMouseListener(mouseAdapter);
					GUI.addComponent(contentPanel, temp, layout, c, j, i);
					components[index] = temp;
				}

			}
		}
		statePanel = new StatePanel();
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 3;
		GUI.addComponent(contentPanel, statePanel, layout, c, 4, 0);
		c.gridwidth = GridBagConstraints.REMAINDER;
		settingsPanel = new SettingsPanel();
		GUI.addComponent(contentPanel, settingsPanel, layout, c, 0, 3);
		frame.pack();
		frame.setLocationRelativeTo(null); // center window
		frame.setVisible(true);
	}

	static void addComponent(Container container, JComponent component,
			GridBagLayout layout, GridBagConstraints constraints, int gridX,
			int gridY) {
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		layout.setConstraints(component, constraints);
		container.add(component);
	}

	public static void updateState() {
		statePanel.loadCell(selectedIndex);
	}

	public static void updateWorld() {
		settingsPanel.updateWorld();
	}

	private static class SettingsPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private JLabel temperaturaMundo;
		private JLabel luzMundo;
		private JLabel humidadeMundo;
		private JLabel horas;
		private JSpinner tempIdeal;
		private JSpinner luzIdeal;
		private JSpinner humidadeIdeal;

		private static Border border = new MatteBorder(1, 0, 0, 0, Color.black);

		// TODO: sliders

		public SettingsPanel() {
			this.setBorder(border);
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			this.setLayout(layout);

			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.NORTH;
			c.weightx = 1;
			c.weighty = 1;

			int y = 0;

			c.insets = new Insets(10, 0, 0, 0);

			horas = new JLabel("Horas");
			horas.setHorizontalAlignment(SwingConstants.CENTER);
			horas.setFont(horas.getFont().deriveFont(16.0f));
			GUI.addComponent(this, horas, layout, c, 0, y);

			temperaturaMundo = new JLabel("Temperatura");
			temperaturaMundo.setHorizontalAlignment(SwingConstants.CENTER);
			temperaturaMundo.setFont(temperaturaMundo.getFont().deriveFont(
					16.0f));
			GUI.addComponent(this, temperaturaMundo, layout, c, 1, y);

			luzMundo = new JLabel("Luminosidade");
			luzMundo.setHorizontalAlignment(SwingConstants.CENTER);
			luzMundo.setFont(luzMundo.getFont().deriveFont(16.0f));
			GUI.addComponent(this, luzMundo, layout, c, 2, y);

			humidadeMundo = new JLabel("Humidade");
			humidadeMundo.setHorizontalAlignment(SwingConstants.CENTER);
			humidadeMundo.setFont(humidadeMundo.getFont().deriveFont(16.0f));
			GUI.addComponent(this, humidadeMundo, layout, c, 3, y++);

			c.insets = new Insets(15, 12, 5, 12);

			JLabel tmp = new JLabel("Condições ideais: ");
			tmp.setHorizontalAlignment(SwingConstants.CENTER);
			GUI.addComponent(this, tmp, layout, c, 0, y);

			tempIdeal = new JSpinner(new SpinnerNumberModel(24, 16, 32, 1));
			World.tempIdeal = 24;
			GUI.addComponent(this, tempIdeal, layout, c, 1, y);
			tempIdeal.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					World.tempIdeal = (int) ((JSpinner) arg0.getSource())
							.getValue();
				}
			});

			luzIdeal = new JSpinner(
					new SpinnerNumberModel(7000, 0, 20000, 1000));
			World.luzIdeal = 7000;
			GUI.addComponent(this, luzIdeal, layout, c, 2, y);
			luzIdeal.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					World.luzIdeal = (int) ((JSpinner) arg0.getSource())
							.getValue();
				}
			});

			humidadeIdeal = new JSpinner(new SpinnerNumberModel(50, 30, 70, 5));
			World.humidadeIdeal = 50;
			GUI.addComponent(this, humidadeIdeal, layout, c, 3, y);
			humidadeIdeal.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					World.humidadeIdeal = (int) ((JSpinner) arg0
							.getSource()).getValue();
				}
			});
		}

		public void updateWorld() {
			temperaturaMundo.setText(String.format("%.1f",
					Main.mundo.getTemperatura())
					+ " ºC");
			luzMundo.setText(String.format("%.1f", Main.mundo.getLuminosidade())
					+ " lm");
			humidadeMundo.setText(Main.mundo.getHumidade() + "%");
			int horasVal = Main.mundo.getHoras();
			horas.setText((horasVal < 10) ? ("0" + horasVal + ":00")
					: (horasVal + ":00"));
		}
	}

	private static class StatePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private static Border border = new MatteBorder(0, 1, 0, 0, Color.black);
		private JLabel temperatura;
		private JLabel luz;
		private Semaphore porta;
		private Semaphore inundacao;
		private Semaphore movimento;
		private Semaphore alarme;
		private Semaphore incendio;
		private Semaphore aquecedor;
		private Semaphore janela;
		private Semaphore ac;
		private Semaphore maqCafe;
		private Semaphore persiana;
		private Semaphore forno;
		private Semaphore lampada;

		public StatePanel() {
			this.setBorder(border);

			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			this.setLayout(layout);

			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.NORTH;
			c.weightx = 1;
			c.weighty = 1;

			int y = 0;

			temperatura = new JLabel("Temperatura");
			temperatura.setHorizontalAlignment(SwingConstants.CENTER);
			temperatura.setFont(temperatura.getFont().deriveFont(16.0f));
			GUI.addComponent(this, temperatura, layout, c, 0, y);

			luz = new JLabel("Luminosidade");
			luz.setHorizontalAlignment(SwingConstants.CENTER);
			luz.setFont(luz.getFont().deriveFont(16.0f));
			GUI.addComponent(this, luz, layout, c, 1, y++);

			movimento = new Semaphore("Sensor movimento");
			movimento.setEnabled(false);
			GUI.addComponent(this, movimento, layout, c, 0, y);

			alarme = new Semaphore("Alarme");
			alarme.setEnabled(false);
			GUI.addComponent(this, alarme, layout, c, 1, y++);

			incendio = new Semaphore("Sensor incêndio");
			incendio.setEnabled(false);
			GUI.addComponent(this, incendio, layout, c, 0, y);

			inundacao = new Semaphore("Sensor inundação");
			inundacao.setEnabled(false);
			GUI.addComponent(this, inundacao, layout, c, 1, y++);

			porta = new Semaphore("Porta");
			porta.setEnabled(false);
			GUI.addComponent(this, porta, layout, c, 0, y);

			aquecedor = new Semaphore("Aquecedor");
			aquecedor.setEnabled(false);
			GUI.addComponent(this, aquecedor, layout, c, 1, y++);

			janela = new Semaphore("Janela");
			janela.setEnabled(false);
			GUI.addComponent(this, janela, layout, c, 0, y);

			ac = new Semaphore("Ar condicionado");
			ac.setEnabled(false);
			GUI.addComponent(this, ac, layout, c, 1, y++);

			maqCafe = new Semaphore("Máquina de café");
			maqCafe.setEnabled(false);
			GUI.addComponent(this, maqCafe, layout, c, 0, y);

			persiana = new Semaphore("Persiana");
			persiana.setEnabled(false);
			GUI.addComponent(this, persiana, layout, c, 1, y++);

			forno = new Semaphore("Forno");
			forno.setEnabled(false);
			GUI.addComponent(this, forno, layout, c, 0, y);

			lampada = new Semaphore("Lâmpada");
			lampada.setEnabled(false);
			GUI.addComponent(this, lampada, layout, c, 1, y++);
		}

		public void loadCell(int index) {
			try {
				if (selectedIndex >= 0 && index != selectedIndex) {
					components[selectedIndex].setBorder(null);
				}
				components[index].setBorder(selectedBorder);
				Sala s = Main.casa.salas[index];
				temperatura.setText(String.format("%.1f", s.getTemperatura()) + " ºC");
				luz.setText(s.getLuz() + " lm");

				if (s.getMovimento() == null)
					movimento.setEnabled(false);
				else {
					movimento.setEnabled(true);
					movimento.setSelected(s.getMovimento());
				}

				if (s.getAlarme() == null)
					alarme.setEnabled(false);
				else {
					alarme.setEnabled(true);
					alarme.setSelected(s.getAlarme());
				}

				if (s.getIncendio() == null)
					incendio.setEnabled(false);
				else {
					incendio.setEnabled(true);
					incendio.setSelected(s.getIncendio());
				}

				if (s.getInundacao() == null)
					inundacao.setEnabled(false);
				else {
					inundacao.setEnabled(true);
					inundacao.setSelected(s.getInundacao());
				}

				if (s.getPorta() == null)
					porta.setEnabled(false);
				else {
					porta.setEnabled(true);
					porta.setSelected(s.getPorta());
				}

				if (s.getAquecedor() == null)
					aquecedor.setEnabled(false);
				else {
					aquecedor.setEnabled(true);
					aquecedor.setSelected(s.getAquecedor());
				}

				if (s.getJanela() == null)
					janela.setEnabled(false);
				else {
					janela.setEnabled(true);
					janela.setSelected(s.getJanela());
				}

				if (s.getAc() == null)
					ac.setEnabled(false);
				else {
					ac.setEnabled(true);
					ac.setSelected(s.getAc());
				}

				if (s.getMaqCafe() == null)
					maqCafe.setEnabled(false);
				else {
					maqCafe.setEnabled(true);
					maqCafe.setSelected(s.getMaqCafe());
				}

				if (s.getPersiana() == null)
					persiana.setEnabled(false);
				else {
					persiana.setEnabled(true);
					persiana.setSelected(s.getPersiana());
				}

				if (s.getForno() == null)
					forno.setEnabled(false);
				else {
					forno.setEnabled(true);
					forno.setSelected(s.getForno());
				}

				if (s.getLampada() == null)
					lampada.setEnabled(false);
				else {
					lampada.setEnabled(true);
					lampada.setSelected(s.getLampada());
				}
				selectedIndex = index;
			} catch (IndexOutOfBoundsException | NullPointerException e) { // item
																			// does
																			// not
																			// exist

			}
		}

		private static class Semaphore extends JCheckBox {
			private static final long serialVersionUID = 1L;

			private static ImageIcon selected = new ImageIcon(
					"img/selected.png");
			private static ImageIcon notSelected = new ImageIcon(
					"img/notSelected.png");
			private static ImageIcon notSet = new ImageIcon("img/notSet.png");

			public Semaphore(String name) {
				super(name);
				this.setSelectedIcon(selected);
				this.setIcon(notSelected);
				this.setDisabledIcon(notSet);
				this.setDisabledSelectedIcon(notSet);
			}

			// make check box "read-only" (ignore mouse and key events)
			protected void processKeyEvent(KeyEvent e) {
			}

			protected void processMouseEvent(MouseEvent e) {
			}
		}
	}
}
