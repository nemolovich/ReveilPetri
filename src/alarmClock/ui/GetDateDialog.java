package alarmClock.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * A JDialog to get a {@link String} from a {@link JTextField}
 * 
 * @author Grumpy Group
 * 
 */
public class GetDateDialog extends JDialog implements ActionListener {

	/**
	 * ID
	 */
	private static final long serialVersionUID = 5452317256991837012L;
	private Date value = null;
	private JTextField hourField = new JTextField();
	private JTextField minuteField = new JTextField();
	private JButton validateButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	/**
	 * A JDialog to get a {@link java.util.Date Date} value from a {@link JTextField}
	 * @param owner {@link JFrame} - The owner frame
	 */
	public GetDateDialog(JFrame owner) {
		super(owner, "Please select a date", true);

		this.setSize(350, 280);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel textPanel = new JPanel();
		String message="Select the ringing date";
		textPanel.setMinimumSize(new Dimension(350, 60));
		JLabel text = new JLabel("<html>" + message.replaceAll("\n", "<br/>")
				+ "</html>", SwingConstants.CENTER);
		textPanel.add(text);

		// Field
		JPanel entryPanel = new JPanel();
		entryPanel.setPreferredSize(new Dimension(320, 45));
		JLabel nomLabel = new JLabel("Date:");
		this.hourField.setPreferredSize(new Dimension(80, 25));
		this.minuteField.setPreferredSize(new Dimension(80, 25));
		entryPanel.add(nomLabel);
		entryPanel.add(this.hourField);
		entryPanel.add(this.minuteField);

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

	public Date getValue() {
		return this.value;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(this.cancelButton)) {
			this.value = null;
			this.dispose();
		} else if (event.getSource().equals(this.validateButton)) {
			int hour=Integer.valueOf(this.hourField.getText());
			int minute=Integer.valueOf(this.minuteField.getText());
			
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.HOUR, hour);
			cal.set(Calendar.MINUTE, minute);
			
			this.value = cal.getTime();
			this.dispose();
		}
	}

}
