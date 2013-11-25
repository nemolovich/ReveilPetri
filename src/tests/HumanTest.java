package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import human.Human;
import human.HumanInterface;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import complementary.MiamMiam;

/**
 * The test class for the {@link Human Human class}
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noemie RULLIER
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HumanTest {

	/**
	 * Set this constant to <code>true</code> to specify you are using JUnit for
	 * tests, this will disable the Graphic User Interfaces for alarm clock
	 */
	public static boolean HUMAN_IN_TEST = false;

	private Human human;
	private MiamMiam miamMiam = MiamMiam.getInstance();

	@Before
	public void init() {
		HumanTest.HUMAN_IN_TEST = true;
		MiamMiam.TESTING = true;
		try {
			this.human = new Human(null);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public static void start() {
		System.out.println("Start the tests for Human");
	}

	@AfterClass
	public static void end() {
		System.out.println("End the tests for Human");
	}

	@Before
	public void before() {
		System.out.println("\n----------------------------------------");
	}

	@After
	public void after() {
		System.out.println("\n----------------------------------------");
	}

	/**
	 * Test the {@link Human#Human(alarmClock.AlarmClockInterface)} constructor
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void contructorTest() throws RemoteException {
		this.expected("Constructor", true, false, false);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#arm(java.util.Date)} method with specifics
	 * conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>After</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testArmInAsleep() throws RemoteException {
		this.expected("Arm", false, true, false, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("wakeUpDate", cal.getTime());
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.arm(null);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#arm(java.util.Date)} method with specifics
	 * conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>true</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>After</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testArmInSleepy() throws RemoteException {
		this.expected("Arm", false, false, true, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("wakeUpDate", cal.getTime());
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.arm(null);
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#arm(java.util.Date)} method with specifics
	 * conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>After</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testArmInWakeUpAfterTime() throws RemoteException {
		this.expected("Arm", false, true, false, true, false, false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("wakeUpDate", cal.getTime());
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.arm(null);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#arm(java.util.Date)} method with specifics
	 * conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>Before</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testArmInWakeUpBeforeTime() throws RemoteException {
		this.expected("Arm", true, false, false, true, false, false);
		Calendar cal = Calendar.getInstance();
		this.setHumanField("wakeUpDate", cal.getTime());
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.arm(null);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#disarm()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testDisarmInAsleep() throws RemoteException {
		this.expected("Disarm", false, true, false, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.disarm();
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#disarm()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>true</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testDisarmInSleepy() throws RemoteException {
		this.expected("Disarm", true, false, false, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.disarm();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#disarm()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testDisarmInWakeUp() throws RemoteException {
		this.expected("Disarm", true, false, false, true, false, false);
		this.setHumanField("awake", true);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", false);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.disarm();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#goToSleep()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGoToSleepInAsleep() throws RemoteException {
		this.expected("GoToSleep", false, true, false, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.goToSleep();
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#goToSleep()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>true</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGoToSleepInSleepy() throws RemoteException {
		this.expected("GoToSleep", false, true, false, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.goToSleep();
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#goToSleep()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGoToSleepInWakeUp() throws RemoteException {
		this.expected("GoToSleep", true, false, false, true, false, false);
		this.setHumanField("awake", true);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", false);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.goToSleep();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#gotNightmares()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>After</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGotNightmaresInAsleepAfterTime() throws RemoteException {
		this.expected("GotNightmares", false, false, true, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("sleepingDate", cal.getTime());
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.gotNightmares();
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#gotNightmares()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>Before</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGotNightmaresInAsleepBeforeTime() throws RemoteException {
		this.expected("GotNightmares", false, true, false, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		Calendar cal = Calendar.getInstance();
		this.setHumanField("sleepingDate", cal.getTime());
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.gotNightmares();
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#gotNightmares()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>true</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>After</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGotNightmaresInSleepy() throws RemoteException {
		this.expected("GotNightmares", false, false, true, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("sleepingDate", cal.getTime());
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.gotNightmares();
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#gotNightmares()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>After</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGotNightmaresInWakeUp() throws RemoteException {
		this.expected("GotNightmares", true, false, false, true, false, false);
		this.setHumanField("awake", true);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("sleepingDate", cal.getTime());
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.gotNightmares();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#gotRing()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>true</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGotRingInAsleep() throws RemoteException {
		this.expected("GotRing", false, false, true, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.gotRing();
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#gotRing()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>true</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGotRingInSleepy() throws RemoteException {
		this.expected("GotRing", false, false, true, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.gotRing();
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.state(this.human);
	}

	/**
	 * Test the {@link Human#gotRing()} method with specifics conditions:<br/>
	 * 
	 * <pre>
	 * 	&bull; {@link Human#isAwake()}: <code>true</code><br/>
	 * 	&bull; {@link Human#isAsleep()}: <code>false</code><br/>
	 * 	&bull; {@link Human#isSleepy()}: <code>false</code><br/>
	 * 	&bull; {@link Human#getWakeUpDate()}: <code>X</code><br/>
	 * 	&bull; {@link Human#getSleepingDate()}: <code>X</code><br/>
	 * </pre>
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void testGotRingInWakeUp() throws RemoteException {
		this.expected("GotRing", true, false, false, true, false, false);
		this.setHumanField("awake", true);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", false);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.gotRing();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	public void setHumanField(String fieldName, Object value) {
		Field f;
		try {
			f = Human.class.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(this.human, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setMiamMiamField(String fieldName, Object value) {
		Field f;
		try {
			f = MiamMiam.class.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(this.miamMiam, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void expected(String method, boolean awake, boolean asleep,
			boolean sleepy) {
		System.out.println("New test for \"" + method + "\":\n"
				+ "Values expected:");
		this.display(awake, asleep, sleepy);
	}

	private void expected(String method, boolean awake, boolean asleep,
			boolean sleepy, boolean wasAwake, boolean wasAsleep,
			boolean wasSleepy) {
		this.expected(method, awake, asleep, sleepy);
		System.out.println("From initial values:");
		this.display(wasAwake, wasAsleep, wasSleepy);
	}

	public void display(boolean awake, boolean asleep, boolean sleepy) {
		System.out.println("\tAwake:\t\t" + awake + "\n" + "\tAsleep:\t\t"
				+ asleep + "\n" + "\tSleepy:\t\t" + sleepy);
	}

	public void state(HumanInterface human) throws RemoteException {
		System.out.println("\nState:");
		this.display(human.isAwake(), human.isAsleep(), human.isSleepy());
	}
}
