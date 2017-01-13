package com.example.bental.studentsapp2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ben on 12/14/2016.
 */

public class DialogCreator {

    public static AlertDialog createDialog(Context context, String msg,String title, final Runnable callback) {
       return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               callback.run();
            }
        }).show();

    }
}
