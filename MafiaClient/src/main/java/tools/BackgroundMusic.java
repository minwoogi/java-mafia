package tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import ui.FrameHandler;

public class BackgroundMusic {
	
	static Clip clip;
	
	public BackgroundMusic(String file) {
		FrameHandler.setBackgroundMusic(this);
		playBgm(file);
	}
	public static void playBgm(String file) {
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
	
	public static void stopBgm() {
		clip.stop();
		clip.close();
	}
	
	

}
