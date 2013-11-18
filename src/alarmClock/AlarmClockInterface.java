package alarmClock;

import human.HumanInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * The alarm clock item interface
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 *
 */
public interface AlarmClockInterface extends Remote {

	/**
	 * Arm the alarm clock at the given date
	 * 
	 * @param date
	 *            {@link Date} - The date to set as ringing date
	 * @throws RemoteException
	 */
	public abstract void arme(Date date) throws RemoteException;

	/**
	 * Make the alarm clock ringing
	 * 
	 * @throws RemoteException
	 */
	public abstract void ring() throws RemoteException;

	/**
	 * Disarm the alarm clock automatically (that means when the alarm clock is
	 * ringing)
	 * 
	 * @throws RemoteException
	 */
	public abstract void autoDisarme() throws RemoteException;

	/**
	 * Disarm alarm clock manually
	 * 
	 * @throws RemoteException
	 */
	public abstract void disarme() throws RemoteException;

	/**
	 * Say if the alarm clock is armed
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the alarm clock is
	 *         armed, <code>false</code> otherwise
	 * @throws RemoteException
	 */
	public abstract boolean isArmed() throws RemoteException;

	/**
	 * Say if the alarm clock is ringing
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the alarm clock is
	 *         ringing, <code>false</code> otherwise
	 * @throws RemoteException
	 */
	public abstract boolean isRinging() throws RemoteException;

	/**
	 * Return the current date from the alarm clock
	 * 
	 * @return {@link Date} - The current date
	 * @throws RemoteException
	 */
	public abstract Date getCurrentDate() throws RemoteException;

	/**
	 * Stop the alarm clock
	 * 
	 * @throws RemoteException
	 */
	public abstract void stop() throws RemoteException;

	/**
	 * Set the human user of this alarm clock
	 * 
	 * @param human
	 *            {@link HumanInterface} - The user
	 * @throws RemoteException
	 */
	public abstract void setHuman(HumanInterface human) throws RemoteException;

	/**
	 * Say if the alarm clock is currently in arming process
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the alarm clock is
	 *         in arming process, <code>false</code> otherwise
	 * @throws RemoteException
	 */
	public abstract boolean inArming() throws RemoteException;

	/**
	 * Say if the alarm clock is currently in disarming process
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the alarm clock is
	 *         in disarming process, <code>false</code> otherwise
	 * @throws RemoteException
	 */
	public abstract boolean inDisarming() throws RemoteException;

	/**
	 * Returns the date on which it should be ring
	 * 
	 * @return {@link Date} - The ringing date
	 * @throws RemoteException
	 */
	public abstract Date getRingDate() throws RemoteException;

}