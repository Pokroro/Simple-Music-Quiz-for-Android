package com.example.sllbt;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final static String TAG_MAIN = "Main";

    private ArrayList<String> chanson; // La chanson sélectionnée

    MediaPlayer mediaPlayer; // Le truc qui sert à jouer des médias


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ArrayList<String>> listeChansons = StringToList(textToString(R.raw.sllbt), ";"); // Le CSV sous forme de liste de listes
        // On pourrait faire de la POO et créer une liste de "chansons"
        chanson = listeChansons.get(1);

        mediaPlayer = new MediaPlayer();

        // On veut lire des fichiers audios
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(chanson.get(1)); // Fourniture de l'adresse de la chanson à jouer au lecteur
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            Log.e(TAG_MAIN,"Impossible d'accéder à la chanson");
        }

    }

    // Lancement musique
    public void musicplay(View v)
    {
        mediaPlayer.start();
        Log.d(TAG_MAIN,"Lancement de la musique" + chanson.get(0) + "située à l'adresse" + chanson.get(1));
    }

    // Pause musique
    public void musicpause(View v)
    {
        mediaPlayer.pause();
        Log.d(TAG_MAIN,"Pause");
    }

    // Fin musique
    public void musicstop(View v)
    {
        mediaPlayer.stop();
        Log.d(TAG_MAIN,"Stop");
    }


    // Renvoie le contenu d'un document texte à l'adresse path sous forme de String
    public String textToString(int path) {
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(path);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch (Exception e) {
            Log.e(TAG_MAIN,"Impossible d'ouvrir la ressource");
            return ("");
        }
    }

    // Renvoie une liste de paires de String basée sur un CSV sous forme de String
    public ArrayList<ArrayList<String>> StringToList(String chaine, String delimiteur) {
        ArrayList<ArrayList<String>> liste = new ArrayList<>();
        String[] t_lignes = chaine.split("\n");
        for (String s_ligne : t_lignes) {
            String[] t_ligne = s_ligne.split(delimiteur);
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(t_ligne));
            liste.add(temp);
        }
        return liste;
    }
}
