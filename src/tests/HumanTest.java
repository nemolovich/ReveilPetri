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
import org.junit.Test;

import complementary.MiamMiam;

/**
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
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

	@Test
	public void testArmeInWakeUpBeforeTime() throws RemoteException {
		this.expected("Arme", true, false, false, true, false, false);
		Calendar cal = Calendar.getInstance();
		this.setHumanField("wakeUpDate", cal.getTime());
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.arme(null);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	@Test
	public void testArmeInWakeUpAfterTime() throws RemoteException {
		this.expected("Arme", false, true, false, true, false, false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("wakeUpDate", cal.getTime());
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.arme(null);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	@Test
	public void testArmeInAsleep() throws RemoteException {
		this.expected("Arme", false, true, false, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("wakeUpDate", cal.getTime());
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.arme(null);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	@Test
	public void testArmeInSleepy() throws RemoteException {
		this.expected("Arme", false, false, true, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		this.setHumanField("wakeUpDate", cal.getTime());
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.arme(null);
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.state(this.human);
	}

	@Test
	public void testDisarmeInWakeUp() throws RemoteException {
		this.expected("Disarme", true, false, false, true, false, false);
		this.setHumanField("awake", true);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", false);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.disarme();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	@Test
	public void testDisarmeInAsleep() throws RemoteException {
		this.expected("Disarme", false, true, false, false, true, false);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", true);
		this.setHumanField("sleepy", false);
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.disarme();
		assertFalse(this.human.isAwake());
		assertTrue(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}

	@Test
	public void testDisarmeInSleepy() throws RemoteException {
		this.expected("Disarme", true, false, false, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.disarme();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}
	
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
	
	@Test
	public void testGoToSleepyInSleepy() throws RemoteException {
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
	
	@Test
	public void testGotNightmaresInWakeUp() throws RemoteException {
		this.expected("GotNightmares", true, false, false, true, false, false);
		this.setHumanField("awake", true);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", false);
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.human.gotNightmares();
		assertTrue(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertFalse(this.human.isSleepy());
		this.state(this.human);
	}
	
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
	
	@Test
	public void testGotNightmaresInSleepy() throws RemoteException {
		this.expected("GotNightmares", false, false, true, false, false, true);
		this.setHumanField("awake", false);
		this.setHumanField("asleep", false);
		this.setHumanField("sleepy", true);
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.human.gotNightmares();
		assertFalse(this.human.isAwake());
		assertFalse(this.human.isAsleep());
		assertTrue(this.human.isSleepy());
		this.state(this.human);
	}

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
