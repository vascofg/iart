package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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
	private static final int BLINKING_RATE = 500; // alarm blink rate

	private static Dimension cellDimension = new Dimension(150, 150);
	private static int selectedIndex = -1;

	private static MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			int cell = Integer.parseInt(e.getComponent().getName());
			if (SwingUtilities.isLeftMouseButton(e))
				statePanel.loadCell(cell);
			if (SwingUtilities.isRightMouseButton(e)) {
				Main.casa.salas[cell].setMovimento(!Main.casa.salas[cell]
						.getMovimento());
				if (selectedIndex == cell)
					statePanel.loadCell(cell); // reload
			}
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
		Timer timer = new Timer(BLINKING_RATE, new TimerListener(components));
		timer.setInitialDelay(0);
		timer.start();
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
		private JSpinner forno;
		private JSpinner maqCafe;

		private static Border border = new MatteBorder(1, 0, 0, 0, Color.black);

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

			c.insets = new Insets(10, 5, 5, 0);

			GUI.addComponent(this, new JLabel("Horas", SwingConstants.CENTER),
					layout, c, 0, y);
			GUI.addComponent(this, new JLabel("Temperatura",
					SwingConstants.CENTER), layout, c, 1, y);
			GUI.addComponent(this, new JLabel("Luminosidade",
					SwingConstants.CENTER), layout, c, 2, y);
			GUI.addComponent(this,
					new JLabel("Humidade", SwingConstants.CENTER), layout, c,
					3, y++);

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

			JLabel tmp = new JLabel("<html>Condições<br>ideais:</html>");
			tmp.setHorizontalAlignment(SwingConstants.CENTER);
			GUI.addComponent(this, tmp, layout, c, 0, y);

			JSpinner tempIdeal = new JSpinner(new SpinnerNumberModel(24, 16,
					32, 1));
			((JSpinner.DefaultEditor) tempIdeal.getEditor()).getTextField()
					.setEditable(false);
			World.tempIdeal = 24;
			GUI.addComponent(this, tempIdeal, layout, c, 1, y);
			tempIdeal.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					World.tempIdeal = (int) ((JSpinner) arg0.getSource())
							.getValue();
				}
			});

			JCheckBox luz = new JCheckBox("Luz");
			World.luzIdeal = 0;
			GUI.addComponent(this, luz, layout, c, 2, y);
			luz.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED)
						World.luzIdeal = 5000;
					else
						World.luzIdeal = 0;
				}
			});

			JCheckBox poupanca = new JCheckBox(
					"<html>Poupança<br>de energia</html>");
			World.poupanca = false;
			GUI.addComponent(this, poupanca, layout, c, 3, y++);
			poupanca.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED)
						World.poupanca = true;
					else
						World.poupanca = false;
				}
			});

			GUI.addComponent(this, new JLabel("Forno", SwingConstants.CENTER),
					layout, c, 0, y);
			SpinnerModel sdm = new SpinnerDateModel(new Date(0), null, null,
					Calendar.HOUR_OF_DAY);
			forno = new JSpinner(sdm);
			forno.setEnabled(false);
			JSpinner.DateEditor de = new JSpinner.DateEditor(forno, "HH:mm");
			forno.setEditor(de);
			de.getTextField().setEditable(false);
			JCheckBox fornoOn = new JCheckBox();
			JPanel temp = new JPanel();
			temp.add(forno);
			temp.add(fornoOn);
			fornoOn.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						World.forno = true;
						forno.setEnabled(true);
					} else {
						World.forno = false;
						forno.setEnabled(false);
					}
				}
			});
			World.horaForno = 1;
			GUI.addComponent(this, temp, layout, c, 1, y);
			forno.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					World.horaForno = ((Date) ((JSpinner) arg0.getSource())
							.getValue()).getHours();
				}
			});

			GUI.addComponent(this, new JLabel("Maq. Café",
					SwingConstants.CENTER), layout, c, 2, y);
			sdm = new SpinnerDateModel(new Date(0), null, null,
					Calendar.HOUR_OF_DAY);
			maqCafe = new JSpinner(sdm);
			maqCafe.setEnabled(false);
			de = new JSpinner.DateEditor(maqCafe, "HH:mm");
			maqCafe.setEditor(de);
			de.getTextField().setEditable(false);
			JCheckBox maqCafeOn = new JCheckBox();
			temp = new JPanel();
			temp.add(maqCafe);
			temp.add(maqCafeOn);
			maqCafeOn.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						World.maqCafe = true;
						maqCafe.setEnabled(true);
					} else {
						World.maqCafe = false;
						maqCafe.setEnabled(false);
					}
				}
			});
			World.horaMaqCafe = 1;
			GUI.addComponent(this, temp, layout, c, 3, y);
			maqCafe.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					World.horaMaqCafe = ((Date) ((JSpinner) arg0.getSource())
							.getValue()).getHours();
				}
			});

			c.gridheight = 3;
			JButton emergencia = new JButton("Simular Emergência", null);
			emergencia.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ArrayList<Sala> temp = new ArrayList<>();
					for (int i = 0; i < Main.casa.salas.length; i++)
						if (Main.casa.salas[i] != null)
							temp.add(Main.casa.salas[i]);
					Sala s = temp.get((int) (Math.random() * temp.size()));
					new Thread(new SimuladorEmergencia(s)).start();
				}
			});
			GUI.addComponent(this, emergencia, layout, c, 4, 0);
		}

		private class SimuladorEmergencia implements Runnable {
			private Sala s;

			public SimuladorEmergencia(Sala s) {
				this.s = s;
			}

			@Override
			public void run() {
				int emergencia;
				if (s.getInundacao() != null)
					emergencia = (int) (Math.random() * 3);
				else
					emergencia = (int) (Math.random() * 2);
				try {
					switch (emergencia) {
					case 0:
						s.setAlarme(true);
						Thread.sleep(10000);
						s.setAlarme(false);
						break;
					case 1:
						s.setIncendio(true);
						Thread.sleep(10000);
						s.setIncendio(false);
						break;
					case 2:
						s.setInundacao(true);
						Thread.sleep(10000);
						s.setInundacao(false);
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void updateWorld() {
			temperaturaMundo.setText(String.format("%.1f",
					Main.mundo.getTemperatura())
					+ " ºC");
			luzMundo.setText(String.format("%.1f", Main.mundo.getLuminosidade())
					+ " lm");
			humidadeMundo.setText(Main.mundo.getHumidade() + "%");
			if (Main.mundo.getHumidade() >= 85) // chuva
				humidadeMundo.setForeground(Color.blue);
			else
				humidadeMundo.setForeground(Color.darkGray);
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

			movimento = new Semaphore("Movimento");
			movimento.setEnabled(false);
			GUI.addComponent(this, movimento, layout, c, 0, y);

			alarme = new Semaphore("Alarme");
			alarme.setEnabled(false);
			GUI.addComponent(this, alarme, layout, c, 1, y++);

			incendio = new Semaphore("Incêndio");
			incendio.setEnabled(false);
			GUI.addComponent(this, incendio, layout, c, 0, y);

			inundacao = new Semaphore("Inundação");
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
				temperatura.setText(String.format("%.1f", s.getTemperatura())
						+ " ºC");
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

	private static class TimerListener implements ActionListener {
		private CellPanel[] cells;

		public TimerListener(CellPanel[] cells) {
			this.cells = cells;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < Main.casa.salas.length; i++) {
				Sala s = Main.casa.salas[i];
				if (s != null && s.emergencia()) {
					if (cells[i].getBorder() == null)
						cells[i].setBorder(selectedBorder);
					else
						cells[i].setBorder(null);
				}

			}
		}
	}
}
