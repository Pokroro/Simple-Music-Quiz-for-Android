package com.example.sllbt;

import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final static String TAG_MAIN = "debug_main";

    private final static int DELAI = 2000;

    private ArrayList<Song> listeChansons;
    private Song chanson; // La chanson sélectionnée à un moment donné
    private Button bPlay;
    private AutoCompleteTextView twReponse;
    private TextView tTitre;
    private TextView tResultat;

    MediaPlayer mediaPlayer; // Le truc qui sert à jouer des médias


    @Override
    protected void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

        // Le CSV sous forme de liste de chansons
        listeChansons = csvToList(R.raw.songs, ";");

        // Création des boutons
        bPlay = findViewById(R.id.buttonPlay);
        bPlay.setTag(0);
        bPlay.setText(getString(R.string.commencer));
        bPlay.setOnClickListener(this::playGame);
        Button bSubmit = findViewById(R.id.buttonSubmit);
        bSubmit.setOnClickListener(this::verifResultat);
        Button bChange = findViewById(R.id.buttonChange);
        bChange.setOnClickListener(this::changeSong);
        Log.d(TAG_MAIN,"Boutons créés");

        // Création du formulaire de réponse
        String[] listeTitres = new String[listeChansons.size()];
        for (int i = 0; i < listeChansons.size(); i++) { // Création d'une liste de titres
            listeTitres[i] = listeChansons.get(i).title;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listeTitres);
        twReponse = findViewById(R.id.autocompleteReponse);
        twReponse.setAdapter(adapter);
        Log.d(TAG_MAIN,"Zone de texte créée");

        tTitre = findViewById(R.id.texteTitre);
        tResultat = findViewById(R.id.texteResultat);
        Log.d(TAG_MAIN,"TextView créées");

        // Création du lecteur de médias
        mediaPlayer = new MediaPlayer();
        // On veut lire des fichiers audios
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.d(TAG_MAIN, "MediaPlayer configuré");
        // La chanson sélectionnée
        chanson = listeChansons.get(genAleatoire(listeChansons.size()-1));
        insertSong();

    }
    
    // Fonction du bouton principal
    private void playGame(View v) {
        mediaPlayer.seekTo(0);
        bPlay.setText(getString(R.string.reecouter));
        musicPlayDelay(v, 2000);
    }

    // Joue une musique pendant delai secondes
    public void musicPlayDelay(View v, int delai) {
        musicPlay(v);
        Handler handler = new Handler();
        handler.postDelayed(() -> musicPause(v), delai);
    }

    // Lancement musique
    public void musicPlay(View v) {
        mediaPlayer.start();
        Log.d(TAG_MAIN, "Play");
    }

    // Pause musique
    public void musicPause(View v) {
        mediaPlayer.pause();
        Log.d(TAG_MAIN,"Pause");
    }


    // Fin musique
    public void musicStop(View v) {
        mediaPlayer.stop();
        Log.d(TAG_MAIN,"Stop");
    }

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
            for (int i = 0; i < t_lignes.length; i++) {
                String[] t_ligne = t_lignes[i].split(delimiteur);
                liste.add(new Song(t_ligne[0], t_ligne[1], i));

            } return liste;

            } catch (Exception e) {
                Log.e(TAG_MAIN, "Impossible d'ouvrir la ressource");
                return (null);
            }
        }

    // Met à jour l'UI selon que l'utilisateur gagne ou perde
    private void verifResultat(View v) {
        fermerClavier();
        String reponse = twReponse.getText().toString();
        Log.d(TAG_MAIN,"Réponse entrée : '" + reponse + "'");
        if (!reponse.equals("")) {
            tTitre.setText(getString(R.string.display_result, chanson.title));
            if (reponse.equalsIgnoreCase(chanson.title)) {
                Log.d(TAG_MAIN, "Victoire !");
                tResultat.setText(getString(R.string.bravo));
                musicPlay(v);
            } else {
                Log.d(TAG_MAIN, "Echec.");
                tResultat.setText(getString(R.string.perdu));
            }
        } else {
            Toast.makeText(this, "Veuillez entrer une réponse", Toast.LENGTH_SHORT).show();
        }

    }

    // Ferme le clavier s'il est ouvert
    private void fermerClavier() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d(TAG_MAIN,"Erreur dans fermerClavier()");
        }
    }

    // Génère un entier aléatoire compris entre 0 et max
    private int genAleatoire(int max) {
        return new Random().nextInt(max + 1);
    }

    // "Insère" une chanson dans le lecteur
    private void insertSong() {
        try {
            mediaPlayer.setDataSource(chanson.url); // Fourniture de l'adresse de la chanson à jouer au lecteur
            mediaPlayer.prepare();
            Log.d(TAG_MAIN,"Chargement de la chanson '" + chanson.title + "' située à l'adresse " + chanson.url);

        } catch (IOException e) {
            Log.e(TAG_MAIN,"Impossible d'accéder à la chanson'" + chanson.title + "' située à l'adresse " + chanson.url);
        }
    }

    // Change la chanson "insérée" dans le lecteur
    private void changeSong(View v) {
        musicStop(v);
        bPlay.setText(getString(R.string.reecouter));
        twReponse.setText("");
        tTitre.setText("");
        tResultat.setText("");
        mediaPlayer.reset();
        int temp = genAleatoire(listeChansons.size()-1);
        while (temp == chanson.indice) {
            temp = genAleatoire(listeChansons.size()-1);
        }
        chanson = listeChansons.get(temp);
        insertSong();
        Log.d(TAG_MAIN,"Changement de chanson");
        musicPlayDelay(v, DELAI);
    }

}