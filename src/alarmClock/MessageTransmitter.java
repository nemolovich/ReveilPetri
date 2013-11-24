package alarmClock;

import human.HumanInterface;

import java.rmi.RemoteException;

import complementary.MiamMiam;

/**
 * The message manager
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class MessageTransmitter extends Thread {

	private HumanInterface human;
	private AlarmClockInterface alarm;
	private boolean inTransmition = false;

	/**
	 * Maximum time to transfert the message in secondes
	 */
	private static final int TEST_maxMessageTransfertTime = 5;
	private static final int maxMessageTransfertTime = 10;

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

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					if (this.alarm.isRinging() && this.inTransmition) {
						int randomTime;
						if (MiamMiam.TESTING) {
							randomTime = 3 + (int) (Math.random() * MessageTransmitter.TEST_maxMessageTransfertTime);
							System.out
							.println("[INFO] The message tranfert will take "
									+ randomTime + " seconde"+(randomTime>1?"s":""));
						} else {
							randomTime = (int) (Math.random() * MessageTransmitter.maxMessageTransfertTime);
							System.out
							.println("[INFO] The message tranfert will take "
									+ randomTime + " minute"+(randomTime>1?"s":""));
						}
						if (randomTime > 0) {
							Thread.sleep(randomTime);
						}
						this.human.gotRing();
						this.inTransmition = false;
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

	public void setInTransmition() {
		this.inTransmition = true;
	}

}
