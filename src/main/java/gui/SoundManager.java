package gui; // หรือ logic ตามที่คุณสะดวก

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private static AudioClip clickClip;

    static {
        try {
            clickClip = new AudioClip(
                    SoundManager.class.getResource("/sounds/click.mp3").toExternalForm()
            );
        } catch (Exception e) {
            System.out.println("Cannot load click.mp3");
        }


        try {
            AudioClip cityMedia = new AudioClip(
                    SoundManager.class.getResource("/sounds/bgm_city.mp3").toExternalForm()
            );
            cityMedia.setCycleCount(AudioClip.INDEFINITE); // loop
            cityMedia.setVolume(0.4);
        } catch (Exception e) {
            System.out.println("Cannot load bgm_city.mp3");
        }
    }

    // ===== SFX =====
    public static void playClick() {
        if (clickClip != null) clickClip.play();
    }

}
