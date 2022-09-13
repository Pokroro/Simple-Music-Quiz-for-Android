package com.example.sllbt.ux;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

public class SongManager extends MediaPlayer {

    private final static String TAG_SM = "debug_song_manager";

    private Song chanson;

    public SongManager(Song chanson) {
        this.chanson = chanson;
    }

    public Song getChanson() {
        return chanson;
    }

    public void setChanson(Song chanson) {
        this.chanson = chanson;
    }

    // Lancement musique
    public void musicPlay() {
        this.start();
        Log.d(TAG_SM, "Play");
    }

    // Pause musique
    public void musicPause() {
        this.pause();
        Log.d(TAG_SM,"Pause");
    }

    // Fin musique
    public void musicStop() {
        this.stop();
        Log.d(TAG_SM,"Stop");
    }

    // Joue une musique pendant delai secondes
    public void musicPlayDelay(int delai) {
        musicPlay();
        Handler handler = new Handler();
        handler.postDelayed(this::musicPause, delai);
    }

    // "Insère" une chanson dans le lecteur
    public void insertSong() {
        try {
            this.setDataSource(chanson.getUrl()); // Fourniture de l'adresse de la chanson à jouer au lecteur
            this.prepare();
            Log.d(TAG_SM,"Chargement de la chanson '" + chanson.getTitle() + "' située à l'adresse " + chanson.getUrl());

        } catch (IOException e) {
            Log.e(TAG_SM,"Impossible d'accéder à la chanson'" + chanson.getTitle() + "' située à l'adresse " + chanson.getUrl());
        }
    }

}
