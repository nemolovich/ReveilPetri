package human.main;


import human.Human;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import alarmClock.AlarmClockInterface;

/**
 * The human launcher
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 *
 */
public class HumanLauncher {

	public static void main(String[] args) {

		AlarmClockInterface alarmClock = null;
		int port = 1095;
		String url = null;
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			url = "//" + InetAddress.getLocalHost().getHostName() + ":" + port
					+ "/AlarmClock";
			alarmClock = (AlarmClockInterface) Naming.lookup(url);
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Can not connect to server",
					"Ooops :o", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (NotBoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

//		Human human = null;
		try {
//			human = 
					new Human(alarmClock);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Congrats! You were born!");
//		System.out.println(human.toString());
//		@SuppressWarnings("resource")
//		Scanner sc = new Scanner(System.in);
//		String command = "";
//		do {
//			System.out.println("Type your command: ");
//			command = sc.nextLine();
//			if (command.equalsIgnoreCase("goToSleep")) {
//				human.goToSleep();
//			} else if (command.equalsIgnoreCase("arme")) {
//				human.arme();
//			} else if (command.equalsIgnoreCase("disarme")) {
//				human.disarme();
//			} else if (command.equalsIgnoreCase("gotNightmares")) {
//				human.gotNightmares();
//			} else if (command.equalsIgnoreCase("gotRing")) {
//				human.gotRing();
//			} else if (!command.equalsIgnoreCase("selfkill")) {
//				System.err.println("Unknown command \"" + command + "\"");
//			}
//			System.out.println(human.toString());
//			System.out.println(alarmClock.toString());
//		} while (!command.equalsIgnoreCase("selfkill"));
//		System.err.println("R.I.P.");
	}
}
