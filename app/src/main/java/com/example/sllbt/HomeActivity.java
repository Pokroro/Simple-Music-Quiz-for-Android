package com.example.sllbt;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class HomeActivity extends AppCompatActivity {

    private final static String TAG_HOME = "debug_home";

    private TextView tAddSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button bLaunch = findViewById(R.id.buttonLaunch);
        bLaunch.setOnClickListener(this::gameLaunch);

        tAddSong = findViewById(R.id.texteAddSongHome);

        FloatingActionButton fabOpenDialog = findViewById(R.id.floatingActionButton);
        fabOpenDialog.setOnClickListener(this::openDialog);

    }

    private void gameLaunch(View v) {
        // Lance le jeu
        Log.d(TAG_HOME,"Lancement du jeu");
        startActivity(new Intent(this, MainActivity.class));

    }

    private void openDialog(View v) {
        CustomDialog.DialogListener dialogListener = (ds_titre, ds_URL) -> {
            Log.d(TAG_HOME, "Réception de "+ "(" + ds_titre + "," + ds_URL + ")");
            tAddSong.setText(getString(R.string.add_song_home, ds_titre, ds_URL));
        };
        final CustomDialog dialog = new CustomDialog(this, dialogListener);

        dialog.show();
    }

}