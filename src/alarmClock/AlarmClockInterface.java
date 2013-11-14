package alarmClock;

import human.HumanInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface AlarmClockInterface extends Remote {

	public abstract void arme() throws RemoteException;

	public abstract void ring() throws RemoteException;

	public abstract void autoDisarme() throws RemoteException;

	public abstract void disarme() throws RemoteException;
	
	public abstract boolean isArmed() throws RemoteException;

	public abstract boolean isRinging() throws RemoteException;

	public abstract Date getCurrentDate() throws RemoteException;

	public abstract void stop() throws RemoteException;

	public abstract void setHuman(HumanInterface human) throws RemoteException;

	public abstract boolean inArming() throws RemoteException;

	public abstract boolean inDisarming() throws RemoteException;

	public abstract Date getRingDate() throws RemoteException;

}