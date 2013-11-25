package human;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * The {@link Human human item} iterface
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public interface HumanInterface extends Remote {

	/* ********************************* *
	 *      HUMAN TIMES CONSTRAINTS      *
	 * ********************************* */
	public static final int wakeUpTime = 480;
	public static final int TEST_mySelfWakeUpMinimum = 10;
	public static final int TEST_armMinimum = 15;
	public static final int mySelfWakeUpMinimum = 60;
	public static final int armMinimum = 840;

	/**
	 * Human action to go to sleep
	 * 
	 * @throws RemoteException
	 */
	public abstract void goToSleep() throws RemoteException;

	/**
	 * Human action to wake up by him-self
	 * 
	 * @throws RemoteException
	 */
	public abstract void gotNightmares() throws RemoteException;

	/**
	 * Human action to arm the alarm clock from a ringing date
	 * 
	 * @param date
	 *            {@link Date} - The date on which the user want to wake up
	 * @throws RemoteException
	 */
	public abstract void arme(Date date) throws RemoteException;

	/**
	 * Human action to disarm the alarm clock
	 * @throws RemoteException
	 */
	public abstract void disarme() throws RemoteException;

	/**
	 * Remote action to got the alarm clock ring
	 * @throws RemoteException
	 */
	public abstract void gotRing() throws RemoteException;


	public abstract boolean isAwake() throws RemoteException;

	public abstract boolean isSleepy() throws RemoteException;

	public abstract boolean isAsleep() throws RemoteException;

	public abstract Date getWakeUpDate() throws RemoteException;

	public abstract Date getSleepingDate() throws RemoteException;

	/**
	 * Kill The human instance
	 */
	public abstract void kill() throws RemoteException;

}