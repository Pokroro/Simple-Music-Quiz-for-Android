package com.example.sllbt.ux;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Random;

public class Utils {

    private final static String TAG_SNIPPETS = "debug_snippets";

    // Ferme le clavier s'il est ouvert
    public void fermerClavier(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            Log.d(TAG_SNIPPETS,"Erreur dans fermerClavier()");
        }
    }

    // Génère un entier aléatoire compris entre 0 et max
    public int genAleatoire(int max) {
        return new Random().nextInt(max + 1);
    }

    // Renvoie si l'application a accès à Internet ou non
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            // On essaie de ping Google
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            boolean temp = (ipProcess.waitFor() == 0);
            if (temp) {
                Log.d(TAG_SNIPPETS, "Accès Internet : OK");
            } else {
                Log.e(TAG_SNIPPETS, "Accès Internet : NON");
            }
            return temp;
        } catch (Exception e) {
            Log.e(TAG_SNIPPETS, "Erreur dans isOnline");
            return false;
        }
    }

}
