package human.utils;

import java.io.FileInputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * A thread object to play a sound (the alarm clock ring)
 * 
 * @author Florian FAGNIEZ, Brian GOHIER, Noémie RULLIER
 *
 */
public class SoundPlayer extends Thread {

	private String soundPath;
	private AudioStream as;

	/**
	 * Constructor using the sound path
	 * @param soundPath {@link String} - The sound path
	 */
	public SoundPlayer(String soundPath) {
		super();
		this.soundPath = soundPath;
	}

	@Override
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
		AudioPlayer.player.stop(this.as);
		super.interrupt();
	}
}
