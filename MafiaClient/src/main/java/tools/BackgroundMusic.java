package tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import ui.FrameHandler;

public class BackgroundMusic {
	
	static Clip clip;
	
	public BackgroundMusic() {
		FrameHandler.setBackgroundMusic(this);
	}
	public static void playBgm(String file) {
		try {
			AudioInputStream ais =
					AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.loop(clip.LOOP_CONTINUOUSLY);
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void playSoundEffect(String file) {
		try {
			AudioInputStream ais =
					AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stopSound() {
		clip.stop();
		clip.close();
	}
	
	

}
