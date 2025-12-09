package gui; // หรือ logic ตามที่คุณสะดวก

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private static AudioClip clickClip;

    private static MediaPlayer cityBgm;

    static {
        try {
            clickClip = new AudioClip(
                    SoundManager.class.getResource("/sounds/click.mp3").toExternalForm()
            );
        } catch (Exception e) {
            System.out.println("Cannot load click.mp3");
        }


        try {
            Media cityMedia = new Media(
                    SoundManager.class.getResource("/sounds/bgm_city.mp3").toExternalForm()
            );
            cityBgm = new MediaPlayer(cityMedia);
            cityBgm.setCycleCount(MediaPlayer.INDEFINITE); // loop
            cityBgm.setVolume(0.4); // เบาหน่อย
        } catch (Exception e) {
            System.out.println("Cannot load bgm_city.mp3");
        }
    }

    // ===== SFX =====
    public static void playClick() {
        if (clickClip != null) clickClip.play();
    }


    // ===== BGM เมือง =====
    public static void playCityBgm() {
        if (cityBgm != null) {
            cityBgm.stop();
            cityBgm.play();
        }
    }

    public static void stopCityBgm() {
        if (cityBgm != null) {
            cityBgm.stop();
        }
    }
}
