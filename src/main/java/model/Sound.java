package model;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sound {
    private MediaPlayer mediaPlayer;
    private int soundNumber;

    private static final URL[] soundtracks = new URL[7];

    static {
        soundtracks[0] = Sound.class.getResource("/media/password-infinity-123276.mp3");
        soundtracks[1] = Sound.class.getResource("/media/simple-piano-melody-9834.mp3");
        soundtracks[2] = Sound.class.getResource("/media/electronic-future-beats-117997.mp3");
        soundtracks[3] = Sound.class.getResource("/media/bubble-sound-43207.mp3");
        soundtracks[4] = Sound.class.getResource("/media/track1.mp3");
    }

    public Sound() {

    }

    public Sound(int i) {
        setFile(i);
    }

    public void setFile(int i) {
        Media media = new Media(soundtracks[i].toString());
        mediaPlayer = new MediaPlayer(media);
        soundNumber = i;
    }

    public void play() {
        mediaPlayer.play();
    }

    public void loop() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public int getSoundNumber() {
        return soundNumber;
    }
}
