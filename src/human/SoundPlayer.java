package human;

import java.io.FileInputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundPlayer extends Thread {

	private String soundPath;
	private AudioStream as;

	/**
	 * @param soundPath
	 */
	public SoundPlayer(String soundPath) {
		super();
		this.soundPath = soundPath;
	}

	public void run() {
		try {
			FileInputStream inputStream = new FileInputStream(this.soundPath);
			this.as = new AudioStream(inputStream);
			AudioPlayer.player.start(this.as);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void interrupt() {
		System.err.println("Interrupt");
		AudioPlayer.player.stop(this.as);
		super.interrupt();
	}
}
