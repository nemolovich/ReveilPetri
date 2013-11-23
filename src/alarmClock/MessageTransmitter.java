package alarmClock;

import human.HumanInterface;

import java.rmi.RemoteException;

/**
 * The message manager
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class MessageTransmitter extends Thread {

	private HumanInterface human;
	private AlarmClockInterface alarm;
	private boolean inTransmition=false;

	/**
	 * @return
	 */
	public static MessageTransmitter getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param human
	 *            {@link HumanInterface} - The human
	 * @param alarm
	 *            {@link AlarmClockInterface} - The alarm clock
	 */
	public MessageTransmitter(HumanInterface human, AlarmClockInterface alarm) {
		super();
		this.human = human;
		this.alarm = alarm;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					if(this.alarm.isRinging() && this.inTransmition) {
						Thread.sleep(5000);
						this.human.gotRing();
						this.inTransmition=false;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			// Killed
		}
	}
	
	/**
	 * @param inTransmition the inTransmition to set
	 */
	public void setInTransmition() {
		this.inTransmition = true;
	}

}
