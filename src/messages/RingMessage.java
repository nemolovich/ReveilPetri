/**
 * 
 */
package messages;

import human.HumanInterface;

import java.rmi.RemoteException;

import alarmClock.AlarmClockInterface;

/**
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class RingMessage extends MessageTransmitter {

	public RingMessage(HumanInterface human, AlarmClockInterface alarm,
			String messageAction) {
		super(human, alarm, messageAction);
	}

	@Override
	public void loopAction() {
		try {
			this.human.gotRing();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean condition() {
		try {
			return this.alarm.isRinging();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

}
