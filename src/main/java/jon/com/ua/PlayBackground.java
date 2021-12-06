package jon.com.ua;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 11/16/13
 */
public class PlayBackground extends Thread {
    private static File soundFile = new File("smb-overworld.wav");
    private static AudioInputStream ais;
    public static Clip clip;

    static {
        try {
            ais = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                clip.open(ais);

                clip.setFramePosition(0);
                clip.start();

                Thread.sleep(clip.getMicrosecondLength() / 1000);
                clip.stop();
                clip.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
