package com.example.sllbt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class HomeActivity extends AppCompatActivity implements AddDialogFragment.AddDialogFragmentListener {

    private final static String TAG_HOME = "debug_home";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button bLaunch = findViewById(R.id.buttonLaunch);
        bLaunch.setOnClickListener(this::gameLaunch);

        Button buttonShow = this.findViewById(R.id.button_show);
        this.textView = this.findViewById(R.id.textView);

        buttonShow.setOnClickListener(v -> buttonShowClicked());
    }

    // User click on "Show Dialog" button.
    private void buttonShowClicked()  {
        this.textView.setText("---");

        // Create YesNoDialogFragment
        DialogFragment dialogFragment = new AddDialogFragment();

        // Arguments:
        Bundle args = new Bundle();
        args.putString(AddDialogFragment.ARG_TITLE, "Confirmation");
        args.putString(AddDialogFragment.ARG_MESSAGE, "Do you like this example?");
        dialogFragment.setArguments(args);

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Show:
        dialogFragment.show(fragmentManager, "Dialog");
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof AddDialogFragment) {
            AddDialogFragment yesNoDialogFragment = (AddDialogFragment) fragment;
            yesNoDialogFragment.setOnYesNoDialogFragmentListener(this);
        }
    }

    // Implement method of YesNoDialogFragment.YesNoDialogFragmentListener
    @Override
    public void onYesNoResultDialog(int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            // ...
            this.textView.setText("You select YES");
        } else if(resultCode == Activity.RESULT_CANCELED) {
            this.textView.setText("You select NO");
        } else {
            this.textView.setText("You don't select");
        }
    }

    private void gameLaunch(View v) {
        // Lance le jeu
        Log.d(TAG_HOME,"Lancement du jeu");
        startActivity(new Intent(this, MainActivity.class));

    }


}