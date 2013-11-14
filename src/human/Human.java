package human;import human.ui.HumanView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import alarmClock.AlarmClockInterface;




public class Human extends UnicastRemoteObject implements HumanInterface {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -425569064779440156L;
	private boolean awake = true;
	private boolean sleepy = false;
	private boolean asleep = false;

	private AlarmClockInterface alarmClock;
	private HumanView view;

	/**
	 * @param alarmClock
	 * @throws RemoteException
	 */
	public Human(AlarmClockInterface alarmClock) throws RemoteException {
		super();
		this.view=new HumanView(this);
		this.alarmClock = alarmClock;
		try {
			alarmClock.setHuman(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see human.HumanInterface#goToSleep()
	 */
	@Override
	public void goToSleep() {
		System.out.println("Going to sleep...");
		if (!this.sleepy) {
			System.err.println("Your are not sleepy yet!");
			return;
		}
		this.sleepy = false;
		this.asleep = true;
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see human.HumanInterface#gotNightmares()
	 */
	@Override
	public void gotNightmares() {
		System.out.println("These boring nightmares...");
		if (!this.asleep) {
			System.err.println("You are not asleep yet!");
			return;
		}
		this.asleep = false;
		this.sleepy = true;
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see human.HumanInterface#arme()
	 */
	@Override
	public void arme() {
		System.out.println("Arming alarm clock...");
		if (!this.awake) {
			System.err.println("You are not awake yet!");
			return;
		}
		try {
			if(this.alarmClock.inArming()) {
				this.alarmClock.arme();
			}
			else {
				System.err.println("Can not arm alarm clock!");
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.awake = false;
		this.asleep = true;
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see human.HumanInterface#disarme()
	 */
	@Override
	public void disarme() {
		System.out.println("It's time to wake up...");
		if (!this.sleepy) {
			System.err.println("You are not sleepy yet!");
			return;
		}
		try {
			if(this.alarmClock.inDisarming()) {
				if(this.alarmClock.isArmed()) {
					this.alarmClock.disarme();
				}
				else if(this.alarmClock.isRinging()) {
					this.alarmClock.autoDisarme();
				}
			}
			else {
				System.err.println("Can not disarme alarm clock!");
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.sleepy = false;
		this.awake = true;
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see human.HumanInterface#gotRing()
	 */
	@Override
	public void gotRing() {
		System.out.println("Ding ding ding ding, ding ding ding ding...");
		if (!this.asleep) {
			System.err.println("You are not asleep yet!");
			return;
		}
		this.asleep = false;
		this.sleepy = true;
		try {
			this.view.update(this.alarmClock.getRingDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.asleep ? 1231 : 1237);
		result = prime * result + (this.awake ? 1231 : 1237);
		result = prime * result + (this.sleepy ? 1231 : 1237);
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
		Human other = (Human) obj;
		if (this.asleep != other.asleep)
			return false;
		if (this.awake != other.awake)
			return false;
		if (this.sleepy != other.sleepy)
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
