package jon.com.ua;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 11/16/13
 */
public class PlaySound extends Thread {
    private static File soundFile = new File("smb_vine.wav");
    private static AudioInputStream ais;
    public static Clip clip;

    public PlaySound() {
        try {
            ais = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

            //Thread.sleep(clip.getMicrosecondLength() / 1000);
//            clip.stop();
//            clip.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
