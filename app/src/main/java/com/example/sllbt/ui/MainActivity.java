package com.example.sllbt.ui;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.example.sllbt.R;
import com.example.sllbt.ux.CsvManager;
import com.example.sllbt.ux.Utils;
import com.example.sllbt.ux.Song;
import com.example.sllbt.ux.SongManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // TODO : Find a way to load Songs in advance to reduce loading time
    // Perhaps using 2 concurrents mediaPlayer
    // Also try not to store data (Songs list) in this Activity

    // TODO : Make an asynchronous Internet Check

    private final static String TAG_MAIN = "debug_main";

    private final static int DELAI = 2000; // Ne sera plus constant dans les versions futures

    private final static Utils tool = new Utils();

    private int flag=0; // Vérifie si le jeu a déjà commencé ou non

    private ArrayList<Song> listeChansons;
    private Button bPlay;
    private Button bChange;
    private AutoCompleteTextView twReponse;
    private TextView tTitre;
    private TextView tResultat;

    SongManager mediaPlayer; // Le truc qui sert à jouer des médias

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Le CSV sous forme de liste de chansons
        CsvManager csvManager = new CsvManager(getResources());
        listeChansons = csvManager.csvToList(R.raw.songs, ";");

        // Création des boutons
        bPlay = findViewById(R.id.buttonPlay);
        bPlay.setTag(0);
        bPlay.setText(getString(R.string.commencer));
        bPlay.setOnClickListener(this::playGame);
        Button bSubmit = findViewById(R.id.buttonSubmit);
        bSubmit.setOnClickListener(this::verifResultat);
        bChange = findViewById(R.id.buttonChange);
        bChange.setOnClickListener(this::changeSong);
        bChange.setVisibility(View.INVISIBLE);
        Log.d(TAG_MAIN,"Boutons créés");

        // Création du formulaire de réponse
        String[] listeTitres = new String[listeChansons.size()];
        for (int i = 0; i < listeChansons.size(); i++) { // Création d'une liste de titres
            listeTitres[i] = listeChansons.get(i).getTitle();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listeTitres);
        twReponse = findViewById(R.id.autocompleteReponse);
        twReponse.setAdapter(adapter);
        Log.d(TAG_MAIN,"Zone de texte créée");

        tTitre = findViewById(R.id.texteTitre);
        tResultat = findViewById(R.id.texteResultat);
        Log.d(TAG_MAIN,"TextView créées");

        // Création du lecteur de médias
        mediaPlayer = new SongManager(listeChansons.get(tool.genAleatoire(listeChansons.size()-1)));
        // On veut lire des fichiers audios
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.d(TAG_MAIN, "MediaPlayer configuré");

    }
    
    // Fonction du bouton principal
    private void playGame(View v) {
        if (tool.isOnline()) {
            if (NotLancementCheck(v)) {
                mediaPlayer.seekTo(0);
                mediaPlayer.musicPlayDelay(DELAI);
            }
        } else {
            openInternetDialog();
        }
    }

    // Met à jour l'UI selon que l'utilisateur gagne ou perde
    private void verifResultat(View v) {
        tool.fermerClavier(this);
        String reponse = twReponse.getText().toString();
        Log.d(TAG_MAIN,"Réponse entrée : '" + reponse + "'");
        if (!reponse.equals("")) {
            tTitre.setText(HtmlCompat.fromHtml(getString(R.string.display_result, mediaPlayer.getChanson().getTitle()), HtmlCompat.FROM_HTML_MODE_COMPACT));
            if (reponse.equalsIgnoreCase(mediaPlayer.getChanson().getTitle())) {
                Log.d(TAG_MAIN, "Victoire !");
                tResultat.setText(getString(R.string.bravo));
                mediaPlayer.musicPlay();
            } else {
                Log.d(TAG_MAIN, "Echec.");
                tResultat.setText(getString(R.string.perdu));
            }
        } else {
            Toast.makeText(this, "Veuillez entrer une réponse", Toast.LENGTH_SHORT).show();
        }

    }

    // Change la chanson "insérée" dans le lecteur
    private void changeSong(View v) {
        tool.fermerClavier(this);
        mediaPlayer.musicStop();
        UIclean();

        if (tool.isOnline()) {
            mediaPlayer.reset();
            int temp = tool.genAleatoire(listeChansons.size() - 1);
            while (temp == mediaPlayer.getChanson().getIndice()) {
                temp = tool.genAleatoire(listeChansons.size() - 1);
            }
            mediaPlayer.setChanson(listeChansons.get(temp));
            mediaPlayer.insertSong();
            Log.d(TAG_MAIN, "Changement de chanson");

            mediaPlayer.musicPlayDelay(DELAI);

        } else {
            openInternetDialog();
        }
    }

    private boolean NotLancementCheck(View v) {
        if (flag == 0) {
            flag = 1;
            changeSong(v);
            bPlay.setText(getString(R.string.reecouter));
            bChange.setVisibility(View.VISIBLE);
            Log.d(TAG_MAIN,"Lancement du jeu");
            return false;
        } else {
            return true;
        }
    }

    private void openInternetDialog() {
        final InternetDialog dialog = new InternetDialog(this);
        dialog.show();
    }

    private void UIclean() {
        twReponse.setText("");
        tTitre.setText("");
        tResultat.setText("");
    }

}