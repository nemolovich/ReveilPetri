package complementary;

import java.rmi.RemoteException;


/**
 * The complementary class managing the only token of global network to allow
 * only one cycle
 * <br/>
 * <br/>
 * <b>Remarque:</b> Singleton instance
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class MiamMiam implements MiamMiamInterface {

	/**
	 * Set this variable to true to reduce the waiting times
	 * this is useful to test the program
	 */
	public static boolean TESTING = true;

	private static MiamMiam instance = null;
	private boolean isMiamMiaming = true;
	private boolean isDisarming = false;
	private boolean isArming = false;

	private MiamMiam() throws RemoteException {
		super();
	}

	/**
	 * Returns the singleton instance of the {@link MiamMiam complementary class}
	 * @return {@link MiamMiam} - The complementary class instance
	 */
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

	@Override
	public boolean inDisarming() {
		if (!this.isMiamMiaming) {
			System.err.println("[ERROR] You can not disarm right now!");
			return false;
		}
		this.isMiamMiaming = false;
		this.isDisarming = true;
		return true;
	}

	@Override
	public boolean inArming() {
		if (!this.isMiamMiaming) {
			if(!this.isArming()) {
				System.err.println("[ERROR] You can not arm right now!");
			}
			return this.isArming;
		}
		this.isMiamMiaming = false;
		this.isArming = true;
		return true;
	}

	@Override
	public void addToken() {
		if (this.isMiamMiaming) {
			System.err.println("[ERROR] There is already a token!");
			return;
		}
		this.isMiamMiaming = true;
	}

	@Override
	public boolean isMiamMiaming() {
		return this.isMiamMiaming;
	}

	@Override
	public boolean isDisarming() {
		return this.isDisarming;
	}

	@Override
	public boolean isArming() {
		return this.isArming;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.isArming ? 1231 : 1237);
		result = prime * result + (this.isDisarming ? 1231 : 1237);
		result = prime * result + (this.isMiamMiaming ? 1231 : 1237);
		return result;
	}

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

	@Override
	public String toString() {
		return "MiamMiam:\n\tisMiamMiaming=" + this.isMiamMiaming
				+ ",\n\tisDisarming=" + this.isDisarming + ",\n\tisArming="
				+ this.isArming;
	}
}
