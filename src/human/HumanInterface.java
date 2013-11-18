package human;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface HumanInterface extends Remote {

	public abstract void goToSleep() throws RemoteException;

	public abstract void gotNightmares() throws RemoteException;

	public abstract void arme(Date date) throws RemoteException;

	public abstract void disarme() throws RemoteException;

	public abstract void gotRing() throws RemoteException;

}