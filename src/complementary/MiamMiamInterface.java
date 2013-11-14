package complementary;import java.rmi.RemoteException;


public interface MiamMiamInterface {

	public abstract boolean inDisarming();

	public abstract boolean inArming();

	public abstract void addToken();

	/**
	 * @return the isMiamMiaming
	 * @throws RemoteException
	 */
	public abstract boolean isMiamMiaming();

	/**
	 * @return the isDisarming
	 * @throws RemoteException
	 */
	public abstract boolean isDisarming();

	/**
	 * @return the isArming
	 * @throws RemoteException
	 */
	public abstract boolean isArming();

}