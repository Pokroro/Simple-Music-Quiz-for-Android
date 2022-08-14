package com.example.sllbt;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String TAG_MAIN = "Main";

    MediaPlayer mediaPlayer; // Le truc qui sert à jouer des médias


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Le CSV sous forme de liste de chansons
        ArrayList<Song> listeChansons = csvToList(R.raw.sllbt, ";");

        // Création des boutons
        Button bPause = findViewById(R.id.buttonPause);
        bPause.setOnClickListener(this::musicPause);
        Button bPlay = findViewById(R.id.buttonPlay);
        bPlay.setOnClickListener(this::musicPlay);
        // Button bSubmit = findViewById(R.id.buttonSubmit);
        // bPlay.setOnClickListener(this::verifResultat);
        Log.d(TAG_MAIN,"Boutons créés");

        // Création du formulaire de réponse
        String[] listeTitres = new String[listeChansons.size()];
        for (int i = 0; i < listeChansons.size(); i++) { // Création d'une liste de titres
            listeTitres[i] = listeChansons.get(i).title;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listeTitres);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocompleteReponse);
        textView.setAdapter(adapter);
        Log.d(TAG_MAIN,"Zone de texte créée");

        TextView tTitre = (TextView) findViewById(R.id.texteTitre);
        tTitre.setText("Titre");
        TextView tResultat = (TextView) findViewById(R.id.texteResultat);
        tResultat.setText("Résultat");
        Log.d(TAG_MAIN,"TextView créées");

        // Création du lecteur de médias
        mediaPlayer = new MediaPlayer();
        // On veut lire des fichiers audios
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // La chanson sélectionnée
        Song chanson = listeChansons.get(2);

        try {
            mediaPlayer.setDataSource(chanson.url); // Fourniture de l'adresse de la chanson à jouer au lecteur
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.pause();
            Log.d(TAG_MAIN,"Lancement de la chanson '" + chanson.title + "' située à l'adresse " + chanson.url);
            // TODO : Déplacer ça vers musicPlay

        } catch (IOException e) {
            Log.e(TAG_MAIN,"Impossible d'accéder à la chanson'" + chanson.title + "' située à l'adresse " + chanson.url);
        }
    }

    // Lancement musique
    public void musicPlay(View v) {
        mediaPlayer.start();
        Log.d(TAG_MAIN, "Start");
    }

    // Pause musique
    public void musicPause(View v) {
        mediaPlayer.pause();
        Log.d(TAG_MAIN,"Pause");
    }

    /*
    // Fin musique
    public void musicStop(View v)
    {
        mediaPlayer.stop();
        Log.d(TAG_MAIN,"Stop");
    }

     */

    // Prend un CSV de chansons à l'adresse path, renvoie une ArrayList de chansons
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ArrayList<Song> csvToList(int path, String delimiteur) {
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(path);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            String chaine = new String(b);
            // Cette chaine contient la totalité du document texte

            ArrayList<Song> liste = new ArrayList<>();
            String[] t_lignes = chaine.split("\n");
            for (String s_ligne : t_lignes) {
                String[] t_ligne = s_ligne.split(delimiteur);
                liste.add(new Song(t_ligne[0], t_ligne[1]));

            } return liste;

            } catch (Exception e) {
                Log.e(TAG_MAIN, "Impossible d'ouvrir la ressource");
                return (null);
            }
        }
/*
    private void verifResultat() {

    }

 */

    }