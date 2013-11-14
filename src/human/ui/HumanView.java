package human.ui;

import human.Human;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HumanView extends JFrame implements ActionListener {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -2823987526485659657L;
	private Human human;
	private int frameWidth = 200;
	private int frameHeight = 185;
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	private JLabel label=new JLabel("Alarm Clock: null");
	private JButton goToSleep = new JButton("Go to sleep");
	private JButton arme = new JButton("Arm alarm clock");
	private JButton disarme = new JButton("Disarm alarm clock");
	private JButton gotNightmares = new JButton("Got nightmares");

	public HumanView(Human human) {

		super();
		this.human = human;

		this.setSize(this.frameWidth, this.frameHeight);
		this.setMinimumSize(new Dimension(this.frameWidth, this.frameHeight));
		this.setMaximumSize(new Dimension(this.frameWidth, this.frameHeight));
		this.setTitle("Client");
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		/*
		 * To define the close operation
		 */
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		final HumanView hv = this;

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (hv.askQuit()) {
					hv.close();
				}
			}
		});

		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout(4, 1));

		this.label.setPreferredSize(new Dimension(190, 25));
		this.arme.setPreferredSize(new Dimension(190, 25));
		this.disarme.setPreferredSize(new Dimension(190, 25));
		this.goToSleep.setPreferredSize(new Dimension(190, 25));
		this.gotNightmares.setPreferredSize(new Dimension(190, 25));

		this.arme.addActionListener(this);
		this.disarme.addActionListener(this);
		this.gotNightmares.addActionListener(this);
		this.goToSleep.addActionListener(this);

		panel.add(this.label);
		panel.add(this.arme);
		panel.add(this.disarme);
		panel.add(this.goToSleep);
		panel.add(this.gotNightmares);

		this.update((Date)null);
		
		this.getContentPane().add(panel, BorderLayout.CENTER);

		this.setVisible(true);
	}
	
	public void update(Date date) {
		this.arme.setEnabled(this.human.isAwake());
		this.disarme.setEnabled(this.human.isSleepy());
		this.gotNightmares.setEnabled(this.human.isAsleep());
		this.goToSleep.setEnabled(this.human.isSleepy());
		if(date!=null) {
			this.label.setText("Alarm Clock: "+this.format.format(date));
		}
		this.repaint();
	}

	protected void close() {
		this.dispose();
		System.exit(0);
	}

	protected boolean askQuit() {
		return JOptionPane.showConfirmDialog(this,
				"Do you want to kill yourself?", "Quit? :(",
				JOptionPane.YES_NO_OPTION) == 0;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(this.arme)) {
			this.human.arme();
		}
		else if(event.getSource().equals(this.disarme)) {
			this.human.disarme();
		}
		else if(event.getSource().equals(this.goToSleep)) {
			this.human.goToSleep();
		}
		else if(event.getSource().equals(this.gotNightmares)) {
			this.human.gotNightmares();
		}
	}

}
