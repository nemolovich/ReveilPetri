package human;

import human.gui.HumanManager;
import human.utils.SoundPlayer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import tests.HumanTest;

import messages.ArmMessage;
import messages.DisarmMessage;
import messages.MessageTransmitter;
import alarmClock.AlarmClockInterface;

import complementary.MiamMiam;

/**
 * The human user item
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noemie RULLIER
 * 
 */
public class Human extends UnicastRemoteObject implements HumanInterface {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -425569064779440156L;

	/* ********************************* *
	 * HUMAN STATES *
	 */
	private boolean awake = true;
	private boolean sleepy = false;
	private boolean asleep = false;

	private Date wakeUpDate;
	// private Date ringingDate;
	private Date sleepingDate;

	private MessageTransmitter disarmeMessage;
	private MessageTransmitter armMessage;

	private AlarmClockInterface alarmClock;
	private SoundPlayer soundPlayer;
	private HumanManager manager;

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
		this.alarmClock = alarmClock;
		this.wakeUpDate = Calendar.getInstance().getTime();
		if (!HumanTest.HUMAN_IN_TEST) {
			this.manager = new HumanManager(this);
			this.manager.start();
		}
		if (alarmClock != null) {
			try {
				alarmClock.setHuman(this);
				this.armMessage = new ArmMessage(this, alarmClock, "arm");
				this.armMessage.start();
				this.disarmeMessage = new DisarmMessage(this, alarmClock,
						"disarm");
				this.disarmeMessage.start();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			if (!HumanTest.HUMAN_IN_TEST) {
				System.err.println("Alarm clock is null!");
				System.exit(0);
			}
		}
	}

	@Override
	public void goToSleep() {
		System.out.println("[REQUEST] goToSleep() in process");
		if (!this.sleepy) {
			if (this.asleep) {
				System.err.println("[ERROR] Your are not in Inception: "
						+ "you can not sleep in your sleep!");
			} else {
				System.err.println("[ERROR] Your are not sleepy yet!");
			}
			System.out.println("[REQUEST] goToSleep() --> [FAIL]");
			return;
		}
		this.sleepingDate = Calendar.getInstance().getTime();
		this.sleepy = false;
		this.asleep = true;
		if (this.soundPlayer != null) {
			this.soundPlayer.interrupt();
			this.soundPlayer = null;
		}
		try {
			if (this.manager != null) {
				this.manager.update(this.alarmClock.getRingDate());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] goToSleep() --> [OK]");
		System.out.println("[INFO] Going to sleep...");
	}

	@Override
	public void gotNightmares() {
		System.out.println("[REQUEST] gotNightmares() in process");
		if (!this.asleep) {
			System.err.println("[ERROR] You are not asleep yet!");
			System.out.println("[REQUEST] gotNightmares() --> [FAIL]");
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.sleepingDate);
		if (MiamMiam.TESTING) {
			cal.add(Calendar.SECOND, HumanInterface.TEST_mySelfWakeUpMinimum);
		} else {
			cal.add(Calendar.MINUTE, HumanInterface.mySelfWakeUpMinimum);
		}
		if (!cal.getTime().before(Calendar.getInstance().getTime())) {
			System.err.println("[ERROR] You can not wake up yet!");
			System.out.println("[REQUEST] gotNightmares() --> [FAIL]");
			return;
		}
		this.asleep = false;
		this.sleepy = true;
		try {
			if (this.manager != null) {
				this.manager.update(this.alarmClock.getRingDate());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] gotNightmares() --> [OK]");
		System.out.println("[INFO] These boring nightmares...");
	}

	@Override
	public void arm(Date date) {
		System.out.println("[REQUEST] arme() in process");
		if (!this.awake) {
			System.err.println("[ERROR] You are not awake yet!");
			System.out.println("[REQUEST] arme() --> [FAIL]");
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.wakeUpDate);
		if (MiamMiam.TESTING) {
			cal.add(Calendar.SECOND, HumanInterface.TEST_armMinimum);
		} else {
			cal.add(Calendar.MILLISECOND, HumanInterface.armMinimum);
		}
		if (!cal.getTime().before(Calendar.getInstance().getTime())) {
			System.err.println("[ERROR] You can not arm the alarm clock yet!");
			System.out.println("[REQUEST] arme() --> [FAIL]");
			return;
		}
		try {
			if (!HumanTest.HUMAN_IN_TEST) {
				if (this.alarmClock.inArming()) {
					((ArmMessage) this.armMessage).setDate(date);
					this.armMessage.setInTransmition();
					// this.alarmClock.arme(date);
				} else {
					System.err.println("[ERROR] Can not arm alarm clock!");
					System.out.println("[REQUEST] arme() --> [FAIL]");
					return;
				}
			}
		} catch (RemoteException e) {
			System.out.println("[REQUEST] arme() --> [FAIL]");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.manager.getView(),
					"The connection with alarm clock has been lost\n"
							+ "Program will exit now", "Connection lost x)",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		this.sleepingDate = Calendar.getInstance().getTime();
		this.awake = false;
		this.asleep = true;
		if (this.manager != null) {
			this.manager.update(date);
		}
		System.out.println("[REQUEST] arme() --> [OK]");
		System.out.println("[INFO] Alarm clock armed");
	}

	@Override
	public void disarm() {
		System.out.println("[REQUEST] disarme() in process");
		if (!this.sleepy) {
			System.err.println("[ERROR] You are not sleepy yet!");
			System.out.println("[REQUEST] disarme() --> [FAIL]");
			return;
		}
		try {
			if (!HumanTest.HUMAN_IN_TEST) {
				if (this.alarmClock.inDisarming()) {
					this.disarmeMessage.setInTransmition();
					// if (this.alarmClock.isArmed()) {
					// this.alarmClock.disarme();
					// } else if (this.alarmClock.isRinging()) {
					// this.alarmClock.autoDisarme();
					// }
				} else {
					System.err.println("[ERROR] Can not disarme alarm clock!");
					System.out.println("[REQUEST] disarme() --> [FAIL]");
					return;
				}
			}
		} catch (RemoteException e) {
			System.out.println("[REQUEST] disarme() --> [FAIL]");
			e.printStackTrace();
		}
		this.wakeUpDate = Calendar.getInstance().getTime();
		this.sleepy = false;
		this.awake = true;
		if (this.soundPlayer != null) {
			this.soundPlayer.interrupt();
			this.soundPlayer = null;
		}
		try {
			if (this.manager != null) {
				this.manager.update(this.alarmClock.getRingDate());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] disarme() --> [OK]");
		System.out.println("[INFO] It's time to wake up...");
	}

	@Override
	public void gotRing() {
		System.out.println("[REQUEST] gotRing() in process");
		if (!this.asleep) {
			System.err.println("[ERROR] You are not asleep yet!");
			System.out.println("[REQUEST] gotRing() --> [FAIL]");
			return;
		}
		this.asleep = false;
		this.sleepy = true;
		if (!HumanTest.HUMAN_IN_TEST) {
			this.playSound("sound/ring.wav");
		}
		try {
			if (this.manager != null) {
				this.manager.update(this.alarmClock.getRingDate());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("[REQUEST] gotRing() --> [OK]");
		System.out.println("[INFO] Ding ding ding ding, "
				+ "ding ding ding ding...");
	}

	public synchronized void playSound(final String soundPath) {
		this.soundPlayer = new SoundPlayer("sound/ring.wav");
		this.soundPlayer.start();
	}

	@Override
	public void kill() {
		this.armMessage.interrupt();
		this.disarmeMessage.interrupt();
		this.manager.interrupt();
		System.err.println("R.I.P.");
		System.exit(0);
	}

	@Override
	public Date getWakeUpDate() throws RemoteException {
		return this.wakeUpDate;
	}

	@Override
	public Date getSleepingDate() throws RemoteException {
		return this.sleepingDate;
	}

	@Override
	public boolean isAwake() throws RemoteException {
		return this.awake;
	}

	@Override
	public boolean isSleepy() throws RemoteException {
		return this.sleepy;
	}

	@Override
	public boolean isAsleep() throws RemoteException {
		return this.asleep;
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
