package alarmClock;

import human.HumanInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

import javax.swing.UIManager;

import tests.AlarmClockTest;

import messages.MessageTransmitter;
import messages.RingMessage;
import alarmClock.gui.ClockManager;

import complementary.MiamMiam;

/**
 * 
 * The alarm clock item
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noemie RULLIER
 * 
 */
public class AlarmClock extends UnicastRemoteObject implements
		AlarmClockInterface {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -4805647396138407720L;

	/* ********************************* *
	 * HUMAN STATES *
	 */
	private boolean disarmed = true;
	private boolean armed = false;
	private boolean ringing = false;

	private MiamMiam miamMiam;
	private ClockManager clockManager;
	private Date ringDate;
	private MessageTransmitter ringTransmitter;

	public AlarmClock() throws RemoteException {
		super();
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.miamMiam = MiamMiam.getInstance();
		if (!AlarmClockTest.ALARM_CLOCK_IN_TEST) {
			this.clockManager = new ClockManager(this);
			this.clockManager.start();
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		this.ringDate = cal.getTime();
	}

	@Override
	public void arm(Date date) throws RemoteException {
		System.out.println("[REQUEST] arme() in process");
		if (!this.disarmed) {
			System.err.println("[ERROR] The alarm clock is not disarmed yet!");
			System.out.println("[REQUEST] arme() --> [FAIL]");
			return;
		}
		this.ringDate = date;
		this.miamMiam.addToken();
		this.disarmed = false;
		this.armed = true;
		System.out.println("[REQUEST] arme() --> [OK]");
		System.out.println("[INFO] Alarm clock armed");
	}

	@Override
	public void ring() throws RemoteException {
		System.out.println("[REQUEST] ring() in process");
		if (!this.armed && !this.ringing) {
			System.err.println("[ERROR] The alarm clock is not armed yet!");
			System.out.println("[REQUEST] ring() --> [FAIL]");
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.ringDate);
		if (MiamMiam.TESTING) {
			cal.add(Calendar.SECOND, AlarmClockInterface.TEST_ringAgainTime);
			System.out.println("[INFO] Alarm will ring again in "
					+ AlarmClockInterface.TEST_ringAgainTime + " secondes if "
					+ "it still armed");
		} else {
			cal.add(Calendar.MINUTE, AlarmClockInterface.ringAgainTime);
			System.out.println("[INFO] Alarm will ring again in "
					+ AlarmClockInterface.ringAgainTime + " minutes if "
					+ "it still armed");
		}
		this.ringDate = cal.getTime();
		this.armed = false;
		this.ringing = true;
		if (this.ringTransmitter != null) {
			this.ringTransmitter.setInTransmition();
		}
		System.out.println("[REQUEST] ring() --> [OK]");
		System.out.println("[INFO] Alarm clock is now ringing");
	}

	@Override
	public void autoDisarm() throws RemoteException {
		System.out.println("[REQUEST] autoDisarme() in process");
		if (!this.ringing) {
			System.err
					.println("[ERROR] The alarm clock is not ringing at the moment!");
			System.out.println("[REQUEST] autoDisarme() --> [FAIL]");
			return;
		}
		this.miamMiam.addToken();
		this.ringing = false;
		this.disarmed = true;
		System.out.println("[REQUEST] autoDisarme() --> [OK]");
		System.out.println("[INFO] Alarm clock auto-disarmed");
	}

	@Override
	public void disarm() throws RemoteException {
		System.out.println("[REQUEST] disarme() in process");
		if (!this.armed) {
			System.err.println("[ERROR] The alarm clock is not armed yet!");
			System.out.println("[REQUEST] disarme() --> [FAIL]");
			return;
		}
		this.miamMiam.addToken();
		this.armed = false;
		this.disarmed = true;
		System.out.println("[REQUEST] disarme() --> [OK]");
		System.out.println("[INFO] Alarm clock disarmed");
	}

	@Override
	public Date getRingDate() throws RemoteException {
		return this.ringDate;
	}

	@Override
	public boolean isArmed() throws RemoteException {
		return this.armed;
	}

	@Override
	public boolean isRinging() throws RemoteException {
		return this.ringing;
	}

	@Override
	public void setHuman(HumanInterface human) throws RemoteException {
		if (this.ringTransmitter != null) {
			this.ringTransmitter.interrupt();
			this.ringTransmitter = null;
		}
		this.ringTransmitter = new RingMessage(human, this, "ring");
		this.ringTransmitter.start();
	}

	@Override
	public Date getCurrentDate() throws RemoteException {
		return Calendar.getInstance().getTime();
	}

	@Override
	public void stop() throws RemoteException {
		this.clockManager.interrupt();
		if (this.ringTransmitter != null) {
			this.ringTransmitter.interrupt();
		}
		System.err.println("The alarm clock does not "
				+ "have battery anymo.......");
		System.exit(0);
	}

	@Override
	public boolean inArming() throws RemoteException {
		return this.miamMiam.inArming();
	}

	@Override
	public boolean inDisarming() throws RemoteException {
		return this.miamMiam.inDisarming();
	}

	@Override
	public boolean isDisarmed() throws RemoteException {
		return this.disarmed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.armed ? 1231 : 1237);
		result = prime * result + (this.disarmed ? 1231 : 1237);
		result = prime * result + (this.ringing ? 1231 : 1237);
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
		AlarmClock other = (AlarmClock) obj;
		if (this.armed != other.armed)
			return false;
		if (this.disarmed != other.disarmed)
			return false;
		if (this.ringing != other.ringing)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlarmClock:\n\tdisarmed=" + this.disarmed + ",\n\tarmed="
				+ this.armed + ",\n\tringing=" + this.ringing;
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	// AlarmClock alarmClock = new AlarmClock();
	// System.out.println("Alarm clock is ready!");
	// System.out.println(alarmClock.toString());
	// @SuppressWarnings("resource")
	// Scanner sc=new Scanner(System.in);
	// String command="";
	// do {
	// System.out.println("Type your command: ");
	// command=sc.nextLine();
	// if(command.equalsIgnoreCase("arme")) {
	// alarmClock.arme();
	// }
	// else if(command.equalsIgnoreCase("autodisarme")) {
	// alarmClock.autoDisarme();
	// }
	// else if(command.equalsIgnoreCase("disarme")) {
	// alarmClock.disarme();
	// }
	// else if(command.equalsIgnoreCase("ring")) {
	// alarmClock.ring();
	// }
	// else if(!command.equalsIgnoreCase("noBattery")) {
	// System.err.println("Unknown command \""+command+"\"");
	// }
	// System.out.println(alarmClock.toString());
	// } while(!command.equalsIgnoreCase("noBattery"));
	// System.err.println("The alarm clock does not have battery anymo.......");
	// }

}
