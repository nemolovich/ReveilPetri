package complementary;

import java.rmi.RemoteException;

public class MiamMiam implements MiamMiamInterface {

	private static MiamMiam instance = null;
	private boolean isMiamMiaming = true;
	private boolean isDisarming = false;
	private boolean isArming = false;


	private MiamMiam() throws RemoteException {
		super();
	}

	public static MiamMiam getInstance() {
		if (MiamMiam.instance == null) {
			try {
				MiamMiam.instance = new MiamMiam();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return MiamMiam.instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see complementary.MiamMiamInterface#inDisarming()
	 */
	@Override
	public boolean inDisarming() {
		if (!this.isMiamMiaming) {
			System.err.println("You can not disarming right now!");
			return false;
		}
		this.isMiamMiaming = false;
		this.isDisarming = true;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see complementary.MiamMiamInterface#inArming()
	 */
	@Override
	public boolean inArming() {
		if (!this.isMiamMiaming) {
			System.err.println("You can not disarming right now!");
			return false;
		}
		this.isMiamMiaming = false;
		this.isArming = true;
		return true;
	}

	// public void getToken() {
	// if(!this.isMiamMiaming) {
	// System.err.println("No available token!");
	// return;
	// }
	// this.isMiamMiaming=false;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see complementary.MiamMiamInterface#addToken()
	 */
	@Override
	public void addToken() {
		if (this.isMiamMiaming) {
			System.err.println("There is already a token!");
			return;
		}
		this.isMiamMiaming = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see complementary.MiamMiamInterface#isMiamMiaming()
	 */
	@Override
	public boolean isMiamMiaming() {
		return this.isMiamMiaming;
	}

	/**
	 * @param isMiamMiaming
	 *            the isMiamMiaming to set
	 */
	public void setMiamMiaming(boolean isMiamMiaming) {
		this.isMiamMiaming = isMiamMiaming;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see complementary.MiamMiamInterface#isDisarming()
	 */
	@Override
	public boolean isDisarming() {
		return this.isDisarming;
	}

	/**
	 * @param isDisarming
	 *            the isDisarming to set
	 */
	public void setDisarming(boolean isDisarming) {
		this.isDisarming = isDisarming;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see complementary.MiamMiamInterface#isArming()
	 */
	@Override
	public boolean isArming() {
		return this.isArming;
	}

	/**
	 * @param isArming
	 *            the isArming to set
	 */
	public void setArming(boolean isArming) {
		this.isArming = isArming;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.isArming ? 1231 : 1237);
		result = prime * result + (this.isDisarming ? 1231 : 1237);
		result = prime * result + (this.isMiamMiaming ? 1231 : 1237);
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MiamMiam other = (MiamMiam) obj;
		if (this.isArming != other.isArming)
			return false;
		if (this.isDisarming != other.isDisarming)
			return false;
		if (this.isMiamMiaming != other.isMiamMiaming)
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
		return "MiamMiam:\n\tisMiamMiaming=" + this.isMiamMiaming
				+ ",\n\tisDisarming=" + this.isDisarming + ",\n\tisArming="
				+ this.isArming;
	}
}
