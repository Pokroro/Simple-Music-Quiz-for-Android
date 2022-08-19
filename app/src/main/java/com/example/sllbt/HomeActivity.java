package com.example.sllbt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    private final static String TAG_HOME = "debug_home";

    private TextView tAddTitre;
    private TextView tAddURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button bLaunch = findViewById(R.id.buttonLaunch);
        bLaunch.setOnClickListener(this::gameLaunch);

        tAddTitre = findViewById(R.id.texteAddTitre);
        tAddURL = findViewById(R.id.texteAddURL);

        FloatingActionButton fabOpenDialog = findViewById(R.id.floatingActionButton);
        fabOpenDialog.setOnClickListener(this::openDialog);

    }

    private void gameLaunch(View v) {
        // Lance le jeu
        Log.d(TAG_HOME,"Lancement du jeu");
        startActivity(new Intent(this, MainActivity.class));

    }

    private void openDialog(View v) {
        CustomDialog.DialogTitleListener dialogTitleListener = ds_title -> tAddTitre.setText(getString(R.string.add_title, ds_title));
        CustomDialog.DialogURLListener dialogURLListener = ds_url -> tAddURL.setText(getString(R.string.add_url, ds_url));

        final CustomDialog dialog = new CustomDialog(this, dialogTitleListener, dialogURLListener);

        dialog.show();
    }

}