package alarmClock.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import alarmClock.AlarmClockInterface;

public class AlarmClockView extends JFrame {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -8800312832914435709L;
	private int frameWidth = 250;
	private int frameHeight = 140;
	private JLabel dateLabel = new JLabel();
	private JLabel ringLabel = new JLabel();
	private JLabel ringLabel_d = new JLabel();
	private JPanel datePanel = new JPanel();
	private boolean armed = false;
	private boolean ringing = false;
	private AlarmClockInterface alarmClock;
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	private JPanel bottomPanel;
	private JPanel armedPanel;
	private JLabel ringingPanel;
	private ImageIcon armedImage;
	private ImageIcon ringingImage;
	private ImageIcon armedImage_d;
	private ImageIcon ringingImage_d;
	private JLabel ringingPanel_d;
	private JPanel armedPanel_d;

	public AlarmClockView(AlarmClockInterface alarmClock) {

		super();
		this.alarmClock = alarmClock;

		this.setSize(this.frameWidth, this.frameHeight);
		this.setMinimumSize(new Dimension(this.frameWidth, this.frameHeight));
		this.setTitle("Alarm Clock");
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		/*
		 * To define the close operation
		 */
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		final AlarmClockView acv = this;

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (acv.askQuit()) {
					acv.close();
				}
			}
		});

		this.datePanel.setBackground(Color.decode("#000000"));
		this.datePanel.setForeground(Color.decode("#FF0000"));

		Font myFont = new Font("DigiTalk-mono", Font.PLAIN, 50);
		try {
			myFont = Font.createFont(Font.TRUETYPE_FONT, new File(
					"font/DS-DIGI.TTF"));
		} catch (FontFormatException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		myFont = myFont.deriveFont(Font.PLAIN, 70);
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		ge.registerFont(myFont);

		this.dateLabel.setFont(myFont);
		this.dateLabel.setText("00:00:00");
		this.dateLabel.setForeground(Color.decode("#FF0000"));
		this.dateLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		this.dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.dateLabel.setPreferredSize(new Dimension(250, 70));

		Font myLittleFont = myFont.deriveFont(Font.BOLD, 18);
		this.ringLabel.setFont(myLittleFont);
		this.ringLabel.setText("00:00:00");
		this.ringLabel.setForeground(Color.decode("#FF0000"));
		this.ringLabel.setVerticalAlignment(SwingConstants.TOP);
		this.ringLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.ringLabel.setPreferredSize(new Dimension(70, 46));

		this.ringLabel_d.setFont(myLittleFont);
		this.ringLabel_d.setText("00:00:00");
		this.ringLabel_d.setForeground(Color.decode("#5E5E5E"));
		this.ringLabel_d.setVerticalAlignment(SwingConstants.TOP);
		this.ringLabel_d.setHorizontalAlignment(SwingConstants.RIGHT);
		this.ringLabel_d.setPreferredSize(new Dimension(70, 46));

		this.bottomPanel = new JPanel();
		this.bottomPanel.setLayout(new FlowLayout());
		this.bottomPanel.setBackground(Color.decode("#000000"));
		this.bottomPanel.setBorder(BorderFactory.createLineBorder(Color
				.decode("#FF0000")));
		this.bottomPanel.setPreferredSize(new Dimension(250, 46));

		this.ringingImage = AlarmClockView.getImageIcon("img/ringing.png", 15,
				15);
		this.armedImage = AlarmClockView.getImageIcon("img/ac.png", 0, 0);
		this.ringingImage_d = AlarmClockView.getImageIcon("img/ringing_d.png",
				15, 15);
		this.armedImage_d = AlarmClockView.getImageIcon("img/ac_d.png", 0, 0);

		this.armedPanel = new JPanel();
		this.armedPanel.setPreferredSize(new Dimension(150, 26));
		this.armedPanel.setBackground(Color.decode("#000000"));
		this.armedPanel.add(new JLabel(this.armedImage));
		this.armedPanel.add(this.ringLabel);
		this.ringingPanel = new JLabel(this.ringingImage);
		this.ringingPanel.setPreferredSize(new Dimension(30, 26));
		this.armedPanel_d = new JPanel();
		this.armedPanel_d.setPreferredSize(new Dimension(150, 26));
		this.armedPanel_d.setBackground(Color.decode("#000000"));
		this.armedPanel_d.add(new JLabel(this.armedImage_d));
		this.armedPanel_d.add(this.ringLabel_d);
		this.ringingPanel_d = new JLabel(this.ringingImage_d);
		this.ringingPanel_d.setPreferredSize(new Dimension(30, 26));

		this.datePanel.add(this.dateLabel);
		this.bottomPanel.add(this.ringingPanel, BorderLayout.WEST);
		this.bottomPanel.add(this.armedPanel, BorderLayout.EAST);

		this.getContentPane().add(this.datePanel, BorderLayout.CENTER);
		this.getContentPane().add(this.bottomPanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
		try {
			this.update(alarmClock.getCurrentDate(), alarmClock.isArmed(),
					alarmClock.isRinging());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	private static ImageIcon getImageIcon(String path, int x, int y) {
		ImageIcon imageIcon = new ImageIcon(path);
		Image img = imageIcon.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, x, y, 20, 20, null);
		imageIcon = new ImageIcon(bi);
		return imageIcon;
	}

	public void close() {
		try {
			this.alarmClock.stop();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.dispose();
	}
	
	boolean askQuit() {
		return JOptionPane.showConfirmDialog(this,
				"Do you want to throw this alarm clock?", "Quit? :(",
				JOptionPane.YES_NO_OPTION) == 0;
	}
	
	public void setRingDate(Date date) {
		this.ringLabel.setText(this.format.format(date));
	}

	public void update(Date date, boolean armed, boolean ringing) {
		this.repaint();
		this.dateLabel.setText(this.format.format(date));
		this.armed = armed;
		this.ringing = ringing;
		this.bottomPanel.removeAll();
		if (this.ringing) {
			this.bottomPanel.add(this.ringingPanel, BorderLayout.WEST);
		} else {
			this.bottomPanel.add(this.ringingPanel_d, BorderLayout.WEST);
		}
		if (this.armed || this.ringing) {
			this.bottomPanel.add(this.armedPanel, BorderLayout.EAST);
		} else {
			this.bottomPanel.add(this.armedPanel_d, BorderLayout.EAST);
		}
	}
}
