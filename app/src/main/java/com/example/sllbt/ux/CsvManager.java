package com.example.sllbt.ux;

import android.content.res.Resources;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

public class CsvManager {

    private final static String TAG_CSVM = "debug_csv_manager";

    private final Resources res;

    public CsvManager(Resources res) {
        this.res = res;
    }

    // Prend un CSV de chansons à l'adresse path, renvoie une ArrayList de chansons
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ArrayList<Song> csvToList(int path, String delimiteur) {
        try {
            InputStream in_s = this.res.openRawResource(path);
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
            Log.e(TAG_CSVM, "Impossible d'ouvrir la ressource");
            return (null);
        }
    }

}
