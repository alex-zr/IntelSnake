package jon.com.ua;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 11/16/13
 */
public class PlayBackground extends Thread {
    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                File soundFile = new File("smb-overworld.wav");

                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

                Clip clip = AudioSystem.getClip();

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
