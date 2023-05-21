package model;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    MediaPlayer mediaPlayer;

    private static final URL[] soundtracks = new URL[4];

    static {
        soundtracks[0] = Sound.class.getResource("/media/jacques_brel_-_ne_me_quitte_pas.mp3");
        soundtracks[1] = Sound.class.getResource("/media/track1.mp3");
        soundtracks[2] = Sound.class.getResource("/media/bubble-sound-43207.mp3");

    }
    public Sound(){

    }
    public Sound(int i) {
        setFile(i);
    }

    public void setFile(int i) {
        Media media = new Media(soundtracks[i].toString());
        mediaPlayer = new MediaPlayer(media);

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

}
