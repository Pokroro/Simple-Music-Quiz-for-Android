package com.example.sllbt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private final static String TAG_HOME = "debug_home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button bLaunch = findViewById(R.id.buttonLaunch);
        bLaunch.setOnClickListener(this::gameLaunch);
    }

    private void gameLaunch(View v) {
        // Lance le jeu
        Log.d(TAG_HOME,"Lancement du jeu");
        startActivity(new Intent(this, MainActivity.class));

    }

}