package alarmClock.gui;

import java.rmi.RemoteException;
import java.util.Date;

import alarmClock.AlarmClockInterface;

public class ClockManager extends Thread {

	private AlarmClockInterface alarmClock;
	private AlarmClockView clockView;

	/**
	 * @param clockView
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
					this.clockView.update(currentDate, this.alarmClock.isArmed(),
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
}
