/**
 * 
 */
package messages;

import java.rmi.RemoteException;

import human.HumanInterface;
import alarmClock.AlarmClockInterface;

/**
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class DisarmMessage extends MessageTransmitter {

	public DisarmMessage(HumanInterface human, AlarmClockInterface alarm,
			String messageAction) {
		super(human, alarm, messageAction);
	}

	@Override
	public void loopAction() {
		try {
			if (this.alarm.isArmed()) {
				this.alarm.disarme();
			} else if (this.alarm.isRinging()) {
				this.alarm.autoDisarme();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean condition() {
		return true;
	}

}
