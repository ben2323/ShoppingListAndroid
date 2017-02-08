package com.example.bental.studentsapp2;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by ben on 2/8/2017.
 */

public class SpinnerDialog extends DialogFragment {

    public SpinnerDialog() {
        // use empty constructors. If something is needed use onCreate's
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        ProgressDialog _dialog = new ProgressDialog(getActivity());
        this.setStyle(STYLE_NO_TITLE, getTheme()); // You can use styles or inflate a view
        _dialog.setMessage("Loading.."); // set your messages if not inflated from XML
        _dialog.setCancelable(false);

        return _dialog;
    }
}
