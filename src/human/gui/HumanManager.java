/**
 * 
 */
package human.gui;

import human.HumanInterface;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import alarmClock.AlarmClockInterface;

import complementary.MiamMiam;

/**
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class HumanManager extends Thread {

	private HumanInterface human;
	private HumanView HumanView;

	/**
	 * Constructor using alarm clock
	 * 
	 * @param human
	 *            {@link AlarmClockInterface} - The alarme clock to manage
	 */
	public HumanManager(HumanInterface human) {
		super();
		this.human = human;
		this.HumanView = new HumanView(human);
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					if (this.human.isAsleep()) {
						Date sleepingDate = this.human.getSleepingDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(sleepingDate);
						if (MiamMiam.TESTING) {
							cal.add(Calendar.SECOND,
									HumanInterface.TEST_mySelfWakeUpMinimum);
						} else {
							cal.add(Calendar.MINUTE,
									HumanInterface.mySelfWakeUpMinimum);
						}
						if (!cal.getTime().before(
								Calendar.getInstance().getTime())) {
							this.HumanView.setSelfWakeUp(false);
						} else {
							this.HumanView.setSelfWakeUp(true);
						}
					} else if (this.human.isAwake()) {
						Date wakeUpDate = this.human.getWakeUpDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(wakeUpDate);
						if (MiamMiam.TESTING) {
							cal.add(Calendar.SECOND,
									HumanInterface.TEST_armMinimum);
						} else {
							cal.add(Calendar.MINUTE, HumanInterface.armMinimum);
						}
						if (!cal.getTime().before(
								Calendar.getInstance().getTime())) {
							this.HumanView.setArm(false);
						} else {
							this.HumanView.setArm(true);
						}
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

	public void update(Date date) {
		this.HumanView.update(date);
	}

	public HumanView getView() {
		return this.HumanView;
	}

	@Override
	public void interrupt() {
		super.interrupt();
	}
}
