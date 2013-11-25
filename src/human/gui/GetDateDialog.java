package human.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * A {@link JDialog} to get a {@link Date} from two {@link JTextField}
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noemie RULLIER
 * 
 */
public class GetDateDialog extends JDialog implements ActionListener,
		KeyListener {

	/**
	 * ID
	 */
	private static final long serialVersionUID = 5452317256991837012L;
	private Date value = null;
	private JButton hourUp = new JButton();
	private JButton hourDown = new JButton();
	private JButton minuteUp = new JButton();
	private JButton minuteDown = new JButton();
	private JTextField hourField = new JTextField("00");
	private JTextField minuteField = new JTextField("00");
	private JButton validateButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	/**
	 * A JDialog to get a {@link java.util.Date Date} value from the
	 * {@link JTextField JTextFields}
	 * 
	 * @param owner
	 *            {@link JFrame} - The owner frame
	 */
	public GetDateDialog(JFrame owner) {
		super(owner, "Select a time", true);

		int frameWidth = 300;
		int frameHeight = 350;

		this.setSize(frameWidth, frameHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Infos
		JPanel textPanel = new JPanel();
		String message = "Please select the ringing time";
		textPanel.setMinimumSize(new Dimension(frameWidth, 60));
		JLabel text = new JLabel("<html>" + message.replaceAll("\n", "<br/>")
				+ "</html>", SwingConstants.CENTER);
		textPanel.add(text);

		// Label
		JPanel entryPanel = new JPanel();
		entryPanel.setPreferredSize(new Dimension(frameWidth - 100, 130));
		entryPanel.setBorder(BorderFactory.createTitledBorder("Time (HH:mm):"));

		// Field
		int fieldWidth = 100;
		int fieldHeight = 28;
		int buttonSize = 20;
		int fieldLabelSize = 4;
		int labelSep = fieldWidth
				- (fieldWidth - buttonSize + 2 * fieldLabelSize);
		Dimension buttonDimension = new Dimension(buttonSize, buttonSize);

		ImageIcon plusIcon = new ImageIcon("img/plus.png");
		ImageIcon minusIcon = new ImageIcon("img/minus.png");

		JPanel pickerPanel = new JPanel();
		pickerPanel.setLayout(new GridLayout(3, 1));

		JPanel upPanel = new JPanel();
		upPanel.setLayout(new FlowLayout());
		upPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		upPanel.setPreferredSize(new Dimension(fieldWidth - 20,
				fieldHeight - 20));
		this.hourUp.setPreferredSize(buttonDimension);
		this.hourUp.setToolTipText("Add an hour");
		this.hourUp.setIcon(plusIcon);
		this.hourUp.addActionListener(this);
		this.minuteUp.setPreferredSize(buttonDimension);
		this.minuteUp.setToolTipText("Add a minute");
		this.minuteUp.setIcon(plusIcon);
		this.minuteUp.addActionListener(this);
		upPanel.add(this.hourUp);
		JLabel upLabel = new JLabel();
		upLabel.setPreferredSize(new Dimension(labelSep, fieldHeight - 20));
		upPanel.add(upLabel);
		upPanel.add(this.minuteUp);

		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new FlowLayout());
		fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		fieldPanel.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
		this.hourField.setPreferredSize(new Dimension(30, fieldHeight - 5));
		this.hourField.addActionListener(this);
		this.hourField.addKeyListener(this);
		this.minuteField.setPreferredSize(new Dimension(30, fieldHeight - 5));
		this.minuteField.addActionListener(this);
		this.minuteField.addKeyListener(this);
		fieldPanel.add(this.hourField);
		JLabel fieldLabel = new JLabel(":");
		fieldLabel.setPreferredSize(new Dimension(fieldLabelSize,
				fieldHeight - 5));
		fieldPanel.add(fieldLabel);
		fieldPanel.add(this.minuteField);

		JPanel downPanel = new JPanel();
		downPanel.setLayout(new FlowLayout());
		downPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		downPanel.setPreferredSize(new Dimension(fieldWidth - 20,
				fieldHeight - 20));
		this.hourDown.setPreferredSize(new Dimension(buttonDimension));
		this.hourDown.setToolTipText("Remove an hour");
		this.hourDown.setIcon(minusIcon);
		this.hourDown.addActionListener(this);
		this.minuteDown.setPreferredSize(new Dimension(buttonDimension));
		this.minuteDown.setToolTipText("Remove a minute");
		this.minuteDown.setIcon(minusIcon);
		this.minuteDown.addActionListener(this);
		downPanel.add(this.hourDown);
		JLabel downLabel = new JLabel();
		downLabel.setPreferredSize(new Dimension(labelSep, fieldHeight - 20));
		downPanel.add(downLabel);
		downPanel.add(this.minuteDown);
		pickerPanel.add(upPanel);
		pickerPanel.add(fieldPanel);
		pickerPanel.add(downPanel);

		entryPanel.add(pickerPanel);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		this.hourField.setText(String.format("%02d",
				cal.get(Calendar.HOUR_OF_DAY)));
		this.minuteField
				.setText(String.format("%02d", cal.get(Calendar.MINUTE)));

		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.setPreferredSize(new Dimension(200, 45));
		this.validateButton.setMnemonic(KeyEvent.VK_O);
		this.cancelButton.setMnemonic(KeyEvent.VK_C);
		buttonsPanel.add(this.cancelButton);
		buttonsPanel.add(this.validateButton);
		this.validateButton.addActionListener(this);
		this.validateButton.requestFocus();
		this.cancelButton.addActionListener(this);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 10));

		this.getRootPane().setDefaultButton(this.validateButton);

		this.getContentPane().add(textPanel, BorderLayout.NORTH);
		this.getContentPane().add(entryPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
	}

	/**
	 * Returns the date value of the compute date from fields
	 * 
	 * @return {@link Date} - The date in fields
	 */
	public Date getValue() {
		return this.value;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int hour = Integer.valueOf(this.hourField.getText());
		int minute = Integer.valueOf(this.minuteField.getText());
		Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		if (event.getSource().equals(this.hourUp)
				|| event.getSource().equals(this.minuteUp)
				|| event.getSource().equals(this.hourDown)
				|| event.getSource().equals(this.minuteDown)) {
			if (event.getSource().equals(this.hourUp)) {
				cal.add(Calendar.HOUR_OF_DAY, 1);
			} else if (event.getSource().equals(this.minuteUp)) {
				cal.add(Calendar.MINUTE, 1);
			} else if (event.getSource().equals(this.hourDown)) {
				cal.add(Calendar.HOUR_OF_DAY, -1);
			} else if (event.getSource().equals(this.minuteDown)) {
				cal.add(Calendar.MINUTE, -1);
			}
			this.hourField.setText(String.format("%02d",
					cal.get(Calendar.HOUR_OF_DAY)));
			this.minuteField.setText(String.format("%02d",
					cal.get(Calendar.MINUTE)));
		} else if (event.getSource().equals(this.cancelButton)) {
			this.value = null;
			this.dispose();
		} else if (event.getSource().equals(this.validateButton)
				|| event.getSource().equals(this.hourField)
				|| event.getSource().equals(this.minuteField)) {

			if (currentDate.after(cal.getTime())) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			cal.set(Calendar.SECOND, 0);

			this.value = cal.getTime();
			this.dispose();
		}
	}

	/**
	 * Check if a {@link String text value} is a digit and is correctly bounded
	 * 
	 * @param value
	 *            {@link String} - The value to check
	 * @param stringlength
	 *            {@link Integer int} - The max string length of the value
	 * @param min
	 *            {@link Integer int} - The minimum integer value
	 * @param max
	 *            {@link Integer int} - The maximum integer value
	 * @return {@link String} - The correct string format
	 */
	private static String checkDigit(String value, int stringlength, int min,
			int max) {
		String result = value;
		if (value.length() > stringlength) {
			result = value.substring(0, stringlength);
		}
		if (value.isEmpty()) {
			return "";
		}
		if (Integer.parseInt(result) > max) {
			result = "" + max;
		} else if (Integer.parseInt(result) < min) {
			result = "" + min;
		}
		return result;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (!Character.isDigit(event.getKeyChar())) {
			return;
		}
		if (event.getSource().equals(this.hourField)) {
			String hour = this.hourField.getText();
			this.hourField.setText(GetDateDialog.checkDigit(hour, 2, 0, 23));
		} else if (event.getSource().equals(this.minuteField)) {
			String minute = this.minuteField.getText();
			this.minuteField
					.setText(GetDateDialog.checkDigit(minute, 2, 0, 59));
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// Useless
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// Useless
	}

}
