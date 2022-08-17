package com.example.sllbt;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddDialogFragment extends DialogFragment {

    public static final String ARG_TITLE = "YesNoDialog.Title";
    public static final String ARG_MESSAGE = "YesNoDialog.Message";

    private AddDialogFragmentListener listener;

    public interface AddDialogFragmentListener {
        /**
         *
         * @param resultCode - Activity.RESULT_OK, Activity.RESULT_CANCEL
         */
        void onYesNoResultDialog(int resultCode, @Nullable Intent data);
    }

    public void setOnYesNoDialogFragmentListener(AddDialogFragmentListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert args != null;
        String title = args.getString(ARG_TITLE);
        String message = args.getString(ARG_MESSAGE);

        return new AlertDialog.Builder(requireActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> buttonOkClick())
                .setNegativeButton("No", (dialog, which) -> buttonNoClick())
                .create();
    }

    private void buttonOkClick() {

        Bundle bundle = new Bundle();
        bundle.putString("key1", "Value 1");
        bundle.putString("key2", "Value 2");
        Intent data  = new Intent().putExtras(bundle);

        this.listener.onYesNoResultDialog(Activity.RESULT_OK, data);

    }

    private void buttonNoClick() {
        int resultCode = Activity.RESULT_CANCELED;
        Intent data  = null;

        this.listener.onYesNoResultDialog(resultCode, data);


    }

}