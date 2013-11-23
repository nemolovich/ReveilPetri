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

}