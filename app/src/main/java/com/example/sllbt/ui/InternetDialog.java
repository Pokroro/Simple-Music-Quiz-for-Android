package com.example.sllbt.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sllbt.R;

class InternetDialog extends Dialog {

    private final static String TAG_ID = "debug_internet_dialog";

    private final Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); // TODO : check types
        setContentView(R.layout.dialog_internet);

        Button bAnnuler  = findViewById(R.id.button_cancel);
        bAnnuler.setOnClickListener(this::bAnnulerClick);
        Button bInternetSettings = findViewById(R.id.button_internet_settings);
        bInternetSettings.setOnClickListener(this::bISClick);

        Log.d(TAG_ID,"dialogue créé");

    }

    // Constructeur
    public InternetDialog(Context context) {
        super(context);
        this.context = context;
    }

    // Ferme le dialogue
    private void bAnnulerClick(View v)  {
        this.dismiss();
    }

    // Ouvre les paramètres Internet du téléphone
    private void bISClick(View v) {
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }


}
