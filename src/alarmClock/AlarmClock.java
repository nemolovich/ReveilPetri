package alarmClock;

import human.HumanInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

import javax.swing.UIManager;

import alarmClock.gui.ClockManager;

import complementary.MiamMiam;

public class AlarmClock extends UnicastRemoteObject implements
		AlarmClockInterface {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -4805647396138407720L;
	private boolean disarmed = true;
	private boolean armed = false;
	private boolean ringing = false;
	private HumanInterface human;
	private MiamMiam miamMiam;
	private ClockManager clockManager;
	private Date ringDate;

	public AlarmClock() throws RemoteException {
		super();
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.miamMiam=MiamMiam.getInstance();
		this.clockManager = new ClockManager(this);
		this.clockManager.start();
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		this.ringDate=cal.getTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see alarmClock.AlarmClockInterface#arme()
	 */
	@Override
	public void arme(Date date) throws RemoteException {
		System.out.println("Arming alarmclock...");
		if (!this.disarmed) {
			System.err.println("The alarm clock is not disarmed yet!");
			return;
		}
		this.ringDate=date;
		this.miamMiam.addToken();
		this.disarmed = false;
		this.armed = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see alarmClock.AlarmClockInterface#ring()
	 */
	@Override
	public void ring() throws RemoteException {
		System.out.println("Ringing alarmclock...");
		if (!this.armed && !this.ringing) {
			System.err.println("The alarm clock is not armed yet!");
			return;
		}
		try {
			this.human.gotRing();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.armed = false;
		this.ringing = true;
	}

	/*
	 * (non-Javadoc)
	 * @see alarmClock.AlarmClockInterface#autoDisarme()
	 */
	@Override
	public void autoDisarme() throws RemoteException {
		System.out.println("Auto-disarming alarmclock...");
		if (!this.ringing) {
			System.err.println("The alarm clock is not ringing at the moment!");
			return;
		}
		this.miamMiam.addToken();
		this.ringing = false;
		this.disarmed = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see alarmClock.AlarmClockInterface#disarme()
	 */
	@Override
	public void disarme() throws RemoteException {
		System.out.println("Disarming alarmclock...");
		if (!this.armed) {
			System.err.println("The alarm clock is not armed yet!");
			return;
		}
		this.miamMiam.addToken();
		this.armed = false;
		this.disarmed = true;
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
		this.human = human;
	}

	@Override
	public Date getCurrentDate() throws RemoteException {
		return Calendar.getInstance().getTime();
	}

	@Override
	public void stop() throws RemoteException {
		this.clockManager.interrupt();
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

	public boolean isDisarmed() {
		return this.disarmed;
	}

	public void setDisarmed(boolean disarmed) {
		this.disarmed = disarmed;
	}

	public void setArmed(boolean armed) {
		this.armed = armed;
	}

	public void setRinging(boolean ringing) {
		this.ringing = ringing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.armed ? 1231 : 1237);
		result = prime * result + (this.disarmed ? 1231 : 1237);
		result = prime * result + (this.ringing ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
