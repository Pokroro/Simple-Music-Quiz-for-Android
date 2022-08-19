package com.example.sllbt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class CustomDialog extends Dialog {

    private final static String TAG_CS = "debug_custom_dialog";

    private EditText etTitre;
    private EditText etURL;

    interface DialogListener {
        void songEntered(String titre, String url);
    }

    public Context context;

    private final CustomDialog.DialogListener dialogListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_dialog);
        Log.d(TAG_CS,"dialogue créé");

        etTitre = findViewById(R.id.editTextTitre);
        etURL = findViewById(R.id.editTextURL);
        Button bAnnuler  = findViewById(R.id.button_cancel);
        bAnnuler.setOnClickListener(this::bAnnulerClick);
        Button bOK = findViewById(R.id.button_ok);
        bOK.setOnClickListener(this::bOKClick);

    }

    // Constructeur
    public CustomDialog(Context context,
                        CustomDialog.DialogListener dialogTitleListener) {
        super(context);
        this.context = context;
        this.dialogListener = dialogTitleListener;

    }

    // Ferme le dialogue
    private void bAnnulerClick(View v)  {
        this.dismiss();
    }

    // WIP
    private void bOKClick(View v) {
        String s_titre = etTitre.getText().toString();
        String s_URL = etURL.getText().toString();

        if(s_titre.isEmpty())  {
            Toast.makeText(this.context, "Veuillez entrer qqc", Toast.LENGTH_LONG).show();
            return;
        } else {
            Log.d(TAG_CS, "Ajout de la chanson '" + s_titre + "' située à l'adresse " + s_URL);
            // TODO : Effectivement ajouter la chanson à la liste
            this.dismiss();
        }

        // Envoie les informations entrées à HomeActivity
        dialogListener.songEntered(s_titre, s_URL);

    }
}