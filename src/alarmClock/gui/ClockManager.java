package alarmClock.gui;

import java.rmi.RemoteException;
import java.util.Date;

import alarmClock.AlarmClockInterface;

/**
 * The alarm clock manager: synchronize the {@link AlarmClockView alarm clock
 * view} with the {@link AlarmClockInterface alarm clock item}
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class ClockManager extends Thread {

	private AlarmClockInterface alarmClock;
	private AlarmClockView clockView;

	/**
	 * Constructor using alarm clock
	 * 
	 * @param alarmClock
	 *            {@link AlarmClockInterface} - The alarme clock to manage
	 */
	public ClockManager(AlarmClockInterface alarmClock) {
		super();
		this.alarmClock = alarmClock;
		this.clockView = new AlarmClockView(alarmClock);
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Date currentDate = this.alarmClock.getCurrentDate();
					this.clockView.update(currentDate,
							this.alarmClock.isArmed(),
							this.alarmClock.isRinging());
					if (this.alarmClock.isArmed()) {
						Date ringDate = this.alarmClock.getRingDate();
						this.clockView.setRingDate(ringDate);
						if (currentDate.after(ringDate)
								|| currentDate.equals(ringDate)) {
							this.alarmClock.ring();
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

	@Override
	public void interrupt() {
		System.err
				.println("The alarm clock does not have battery anymo.......");
		super.interrupt();
	}
}
