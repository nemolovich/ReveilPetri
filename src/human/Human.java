package human;

import human.gui.HumanView;
import human.utils.SoundPlayer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import alarmClock.AlarmClockInterface;

/**
 * The human user item
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class Human extends UnicastRemoteObject implements HumanInterface {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -425569064779440156L;
	private boolean awake = true;
	private boolean sleepy = false;
	private boolean asleep = false;
	private Date startDate;
	private Date sleepingDate;

	private AlarmClockInterface alarmClock;
	private HumanView view;
	private SoundPlayer soundPlayer;
	private static int mySelfWakeUpMinimum=30;
	private static int armMinimum=10;

	/**
	 * The human user constructor using the used remote alarm clock
	 * 
	 * @param alarmClock
	 *            {@link AlarmClockInterface} - The remote alarm clock
	 * @throws RemoteException
	 */
	public Human(AlarmClockInterface alarmClock) throws RemoteException {
		super();
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.view = new HumanView(this);
		this.alarmClock = alarmClock;
		try {
			alarmClock.setHuman(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.startDate = Calendar.getInstance().getTime();
	}

	@Override
	public void goToSleep() {
		System.out.println("[REQUEST] goToSleep() in process");
		if (!this.sleepy) {
			System.err.println("Your are not sleepy yet!");
			System.out.println("[REQUEST] goToSleep() --> [FAIL]");
			return;
		}
		this.sleepingDate=Calendar.getInstance().getTime();
		this.sleepy = false;
		this.asleep = true;
		if (this.soundPlayer != null) {
			this.soundPlayer.interrupt();
			this.soundPlayer = null;
		}
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] goToSleep() --> [OK]");
		System.out.println("Going to sleep...");
	}

	@Override
	public void gotNightmares() {
		System.out.println("[REQUEST] gotNightmares() in process");
		if (!this.asleep) {
			System.err.println("You are not asleep yet!");
			System.out.println("[REQUEST] gotNightmares() --> [FAIL]");
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.startDate);
		cal.add(Calendar.SECOND, Human.mySelfWakeUpMinimum);
		if (cal.getTime().before(Calendar.getInstance().getTime())) {
			System.err.println("You can not wake up yet!");
			System.out.println("[REQUEST] gotNightmares() --> [FAIL]");
			return;
		}
		this.asleep = false;
		this.sleepy = true;
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] gotNightmares() --> [OK]");
		System.out.println("These boring nightmares...");
	}

	@Override
	public void arme(Date date) {
		System.out.println("[REQUEST] arme() in process");
		if (!this.awake) {
			System.err.println("You are not awake yet!");
			System.out.println("[REQUEST] arme() --> [FAIL]");
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.startDate);
		cal.add(Calendar.SECOND, Human.armMinimum);
		if(!cal.getTime().before(Calendar.getInstance().getTime())) {
			System.err.println("You can not arm the alarm clock yet!");
			System.out.println("[REQUEST] arme() --> [FAIL]");
			return;
		}
		try {
			if (this.alarmClock.inArming()) {
				this.alarmClock.arme(date);
			} else {
				System.err.println("Can not arm alarm clock!");
				System.out.println("[REQUEST] arme() --> [FAIL]");
				return;
			}
		} catch (RemoteException e) {
			System.out.println("[REQUEST] arme() --> [FAIL]");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.view,
					"The connection with alarm clock has been lost\n"
							+ "Program will exit now", "Connection lost x)",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		this.awake = false;
		this.asleep = true;
		this.view.update(date);
		System.out.println("[REQUEST] arme() --> [OK]");
		System.out.println("Alarm clock armed");
	}

	@Override
	public void disarme() {
		System.out.println("[REQUEST] disarme() in process");
		if (!this.sleepy) {
			System.err.println("You are not sleepy yet!");
			System.out.println("[REQUEST] disarme() --> [FAIL]");
			return;
		}
		try {
			if (this.alarmClock.inDisarming()) {
				if (this.alarmClock.isArmed()) {
					this.alarmClock.disarme();
				} else if (this.alarmClock.isRinging()) {
					this.alarmClock.autoDisarme();
				}
			} else {
				System.err.println("Can not disarme alarm clock!");
				System.out.println("[REQUEST] disarme() --> [FAIL]");
				return;
			}
		} catch (RemoteException e) {
			System.out.println("[REQUEST] disarme() --> [FAIL]");
			e.printStackTrace();
		}
		this.sleepy = false;
		this.awake = true;
		if (this.soundPlayer != null) {
			this.soundPlayer.interrupt();
			this.soundPlayer = null;
		}
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] disarme() --> [OK]");
		System.out.println("It's time to wake up...");
	}

	@Override
	public void gotRing() {
		System.out.println("[REQUEST] gotRing() in process");
		if (!this.asleep) {
			System.err.println("You are not asleep yet!");
			System.out.println("[REQUEST] gotRing() --> [FAIL]");
			return;
		}
		this.asleep = false;
		this.sleepy = true;
		this.playSound("sound/ring.wav");
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] gotRing() --> [OK]");
		System.out.println("Ding ding ding ding, ding ding ding ding...");
	}

	public synchronized void playSound(final String soundPath) {
		this.soundPlayer = new SoundPlayer("sound/ring.wav");
		this.soundPlayer.start();
	}
	
	public Date getStartDate() throws RemoteException {
		return this.startDate;
	}

	public Date getRingDate() throws RemoteException {
		return this.alarmClock.getRingDate();
	}

	public boolean isAwake() {
		return this.awake;
	}

	public void setAwake(boolean awake) {
		this.awake = awake;
	}

	public boolean isSleepy() {
		return this.sleepy;
	}

	public void setSleepy(boolean sleepy) {
		this.sleepy = sleepy;
	}

	public boolean isAsleep() {
		return this.asleep;
	}

	public void setAsleep(boolean asleep) {
		this.asleep = asleep;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.asleep ? 1231 : 1237);
		result = prime * result + (this.awake ? 1231 : 1237);
		result = prime * result + (this.sleepy ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Human other = (Human) obj;
		if (this.asleep != other.asleep)
			return false;
		if (this.awake != other.awake)
			return false;
		if (this.sleepy != other.sleepy)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Human:\n\tawake=" + this.awake + ",\n\tsleepy=" + this.sleepy
				+ ",\n\tasleep=" + this.asleep;
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	// Human human=new Human();
	// System.out.println("You are now ready!");
	// System.out.println(human.toString());
	// @SuppressWarnings("resource")
	// Scanner sc=new Scanner(System.in);
	// String command="";
	// do {
	// System.out.println("Type your command: ");
	// command=sc.nextLine();
	// if(command.equalsIgnoreCase("goToSleep")) {
	// human.goToSleep();
	// }
	// else if(command.equalsIgnoreCase("arme")) {
	// human.arme();
	// }
	// else if(command.equalsIgnoreCase("disarme")) {
	// human.disarme();
	// }
	// else if(command.equalsIgnoreCase("gotNightmares")) {
	// human.gotNightmares();
	// }
	// else if(command.equalsIgnoreCase("gotRing")) {
	// human.gotRing();
	// }
	// else if(!command.equalsIgnoreCase("selfkill")) {
	// System.err.println("Unknown command \""+command+"\"");
	// }
	// System.out.println(human.toString());
	// } while(!command.equalsIgnoreCase("selfkill"));
	// System.err.println("R.I.P.");
	// }

}
