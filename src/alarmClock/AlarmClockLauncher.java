package alarmClock;

import java.net.BindException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;

/**
 * The alarm clock launcher
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 *
 */
public class AlarmClockLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int port = 1095;
		AlarmClock alarmClock = null;
		String url = null;
		try {
			LocateRegistry.createRegistry(port);
			alarmClock = new AlarmClock();
			url = "//" + InetAddress.getLocalHost().getHostName() + ":" + port
					+ "/AlarmClock";
			Naming.rebind(url, alarmClock);
		} catch (ExportException e) {
			if (e.getCause() instanceof BindException) {
				System.err
						.println("The alarm clock can not be started on this port: "
								+ port);
			}
			e.printStackTrace();
			System.exit(0);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("[ Alarm clock is set as remote  ] : " + url);
		System.out.println("Alarm clock is ready!");
//		System.out.println(alarmClock.toString());
//		Scanner sc = new Scanner(System.in);
//		String command = "";
//		try {
//			do {
//				System.out.println("Type your command: ");
//				command = sc.nextLine();
//				if (command.equalsIgnoreCase("arme")) {
//						alarmClock.arme();
//				} else if (command.equalsIgnoreCase("autodisarme")) {
//					alarmClock.autoDisarme();
//				} else if (command.equalsIgnoreCase("disarme")) {
//					alarmClock.disarme();
//				} else if (command.equalsIgnoreCase("ring")) {
//					alarmClock.ring();
//				} else if (!command.equalsIgnoreCase("noBattery")) {
//					System.err.println("Unknown command \"" + command + "\"");
//				}
//				System.out.println(alarmClock.toString());
//			} while (!command.equalsIgnoreCase("noBattery"));
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		System.err
//				.println("The alarm clock does not have battery anymo.......");
	}

}
