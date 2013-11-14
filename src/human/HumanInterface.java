package human;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HumanInterface extends Remote {

	public abstract void goToSleep() throws RemoteException;

	public abstract void gotNightmares() throws RemoteException;

	public abstract void arme() throws RemoteException;

	public abstract void disarme() throws RemoteException;

	public abstract void gotRing() throws RemoteException;

}