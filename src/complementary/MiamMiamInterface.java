package complementary;



/**
 * The {@link MiamMiam complementary class} interface
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public interface MiamMiamInterface {
	
	/**
	 * To reduce the waiting times
	 */
	public static final boolean TESTING=true;

	/**
	 * Say if the alarm clock is in disarming state
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the alarm clock is
	 *         in disarming mode, <code>false</code> otherwise
	 */
	public abstract boolean inDisarming();

	/**
	 * Say if the alarm clock is in arming state
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the alarm clock is
	 *         in arming mode, <code>false</code> otherwise
	 */
	public abstract boolean inArming();

	/**
	 * Add back the token to reset the cycle
	 */
	public abstract void addToken();

	/**
	 * Say if the token is available
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the token is
	 *         available, <code>false</code> otherwise
	 */
	public abstract boolean isMiamMiaming();

	/**
	 * Say if the alarm clock is in disarming mode
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the token is
	 *         in disarming state, <code>false</code> otherwise
	 */
	public abstract boolean isDisarming();

	/**
	 * Say if the alarm clock is in arming mode
	 * 
	 * @return {@link Boolean boolean} - <code>true</code> if the token is
	 *         in arming state, <code>false</code> otherwise
	 */
	public abstract boolean isArming();

}