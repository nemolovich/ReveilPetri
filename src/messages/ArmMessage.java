/**
 * 
 */
package messages;

import human.HumanInterface;

import java.rmi.RemoteException;
import java.util.Date;

import alarmClock.AlarmClockInterface;

/**
 * The Arm message manager
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noemie RULLIER
 * 
 */
public class ArmMessage extends MessageTransmitter {

	private Date date;

	public ArmMessage(HumanInterface human, AlarmClockInterface alarm,
			String messageAction) {
		super(human, alarm, messageAction);
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	@Override
	public void loopAction() {
		try {
			this.alarm.arm(this.date);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean condition() {
		return true;
	}

}
