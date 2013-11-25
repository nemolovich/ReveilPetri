package messages;

import human.HumanInterface;
import alarmClock.AlarmClockInterface;

import complementary.MiamMiam;

/**
 * The message manager
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noemie RULLIER
 * 
 */
public abstract class MessageTransmitter extends Thread {

	protected HumanInterface human;
	protected AlarmClockInterface alarm;
	protected boolean inTransmition = false;
	private String messageAction;

	/**
	 * Maximum time to transfert the message in secondes
	 */
	protected static final int TEST_maxMessageTransfertTime = 5; // This will
																	// use +3
	protected static final int maxMessageTransfertTime = 600; // In secondes

	/**
	 * @param human
	 *            {@link HumanInterface} - The human
	 * @param alarm
	 *            {@link AlarmClockInterface} - The alarm clock
	 */
	public MessageTransmitter(HumanInterface human, AlarmClockInterface alarm,
			String messageAction) {
		super();
		this.human = human;
		this.alarm = alarm;
		this.messageAction = messageAction;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				if (this.condition() && this.inTransmition) {
					int randomTime;
					if (MiamMiam.TESTING) {
						randomTime = 3 + (int) (Math.random() * (MessageTransmitter.TEST_maxMessageTransfertTime + 1));
					} else {
						randomTime = (int) (Math.random() * (MessageTransmitter.maxMessageTransfertTime + 1));
					}
					System.out.println("[INFO] The message to \""
							+ this.messageAction + "\" tranfert will take "
							+ randomTime + " seconde"
							+ (randomTime > 1 ? "s" : ""));
					if (randomTime > 0) {
						try {
							Thread.sleep(randomTime * 1000);
						} catch (InterruptedException e) {
							// Can not wait
						}
					}
					this.loopAction();
					this.inTransmition = false;
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

	public abstract void loopAction();

	public abstract boolean condition();

}
