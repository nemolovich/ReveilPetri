package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import human.HumanInterface;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import alarmClock.AlarmClock;
import alarmClock.AlarmClockInterface;

import complementary.MiamMiam;

/**
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 * 
 */
public class AlarmClockTest {

	/**
	 * Set this constant to <code>true</code> to specify you are using JUnit for
	 * tests, this will disable the Graphic User Interfaces for alarm clock
	 */
	public static boolean ALARM_CLOCK_IN_TEST = false;

	private AlarmClock alarm;
	private MiamMiam miamMiam = MiamMiam.getInstance();

	@Before
	public void init() {
		AlarmClockTest.ALARM_CLOCK_IN_TEST = true;
		MiamMiam.TESTING = true;
		try {
			this.alarm = new AlarmClock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public static void start() {
		System.out.println("Start the tests for AlarmClock");
	}

	@AfterClass
	public static void end() {
		System.out.println("End the tests for AlarmClock");
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
	public void testContructor() throws RemoteException {
		this.expected("Constructor", false, true, false);
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testArmInArme() throws RemoteException {
		this.expected("Arme", true, false, false, true, false, false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, HumanInterface.wakeUpTime);
		this.setAlarmField("armed", true);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", false);
		assertTrue(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.arme(cal.getTime());
		assertTrue(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testArmInDisarme() throws RemoteException {
		this.expected("Arme", true, false, false, false, true, false);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, HumanInterface.wakeUpTime);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", true);
		this.setAlarmField("ringing", false);
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.arme(cal.getTime());
		assertTrue(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testArmInRinging() throws RemoteException {
		this.expected("Arme", false, false, true, false, false, true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, HumanInterface.wakeUpTime);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", true);
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.arme(cal.getTime());
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testDisarmInArme() throws RemoteException {
		this.expected("Disarme", false, true, false, true, false, false);
		this.setAlarmField("armed", true);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", false);
		assertTrue(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.disarme();
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testDisarmInDisarme() throws RemoteException {
		this.expected("Disarme", false, true, false, false, true, false);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", true);
		this.setAlarmField("ringing", false);
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.disarme();
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testDisarmInRinging() throws RemoteException {
		this.expected("Disarme", false, false, true, false, false, true);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", true);
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.disarme();
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testAutodisarmInArme() throws RemoteException {
		this.expected("Autodisarme", true, false, false, true, false, false);
		this.setAlarmField("armed", true);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", false);
		assertTrue(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.autoDisarme();
		assertTrue(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testAutodisarmInDisarme() throws RemoteException {
		this.expected("Autodisarme", false, true, false, false, true, false);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", true);
		this.setAlarmField("ringing", false);
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.autoDisarme();
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testAutodisarmInRinging() throws RemoteException {
		this.expected("Autodisarme", false, true, false, false, false, true);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", true);
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.autoDisarme();
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testRingInArme() throws RemoteException {
		this.expected("Ring", false, false, true, true, false, false);
		this.setAlarmField("armed", true);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", false);
		assertTrue(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.ring();
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testRingInDisarme() throws RemoteException {
		this.expected("Ring", false, true, false, false, true, false);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", true);
		this.setAlarmField("ringing", false);
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.ring();
		assertFalse(this.alarm.isArmed());
		assertTrue(this.alarm.isDisarmed());
		assertFalse(this.alarm.isRinging());
		this.state(this.alarm);
	}

	@Test
	public void testRingInRinging() throws RemoteException {
		this.expected("Ring", false, false, true, false, false, true);
		this.setAlarmField("armed", false);
		this.setAlarmField("disarmed", false);
		this.setAlarmField("ringing", true);
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.setMiamMiamField("isMiamMiaming", false);
		this.alarm.ring();
		assertFalse(this.alarm.isArmed());
		assertFalse(this.alarm.isDisarmed());
		assertTrue(this.alarm.isRinging());
		this.state(this.alarm);
	}

	public void setAlarmField(String fieldName, Object value) {
		Field f;
		try {
			f = AlarmClock.class.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(this.alarm, value);
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

	private void expected(String method, boolean armed, boolean disarmed,
			boolean ringing) {
		System.out.println("New test for \"" + method + "\":\n"
				+ "Values expected:");
		this.display(armed, disarmed, ringing);
	}

	private void expected(String method, boolean armed, boolean disarmed,
			boolean ringing, boolean wasArmed, boolean wasDisarmed,
			boolean wasRinging) {
		this.expected(method, armed, disarmed, ringing);
		System.out.println("From initial values:");
		this.display(wasArmed, wasDisarmed, wasRinging);
	}

	public void display(boolean armed, boolean disarmed, boolean ringing) {
		System.out.println("\tArmed:\t\t" + armed + "\n" + "\tDisarmed:\t"
				+ disarmed + "\n" + "\tRinging:\t" + ringing);
	}

	public void state(AlarmClockInterface alarm) throws RemoteException {
		System.out.println("\nState:");
		this.display(alarm.isArmed(), alarm.isDisarmed(), alarm.isRinging());
	}
}
