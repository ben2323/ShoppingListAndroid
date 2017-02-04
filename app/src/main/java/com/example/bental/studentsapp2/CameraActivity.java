package com.example.bental.studentsapp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends Activity {
String picturePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        String pictureName = getIntent().getExtras().getString("pictureName");
        final String dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/pics/";
        File newdir = new File(dir);
        if (!newdir.exists()) {
            newdir.mkdirs();
        }
        String file = dir + pictureName + "_" + SystemClock.currentThreadTimeMillis() + ".jpg";
        File newfile = new File(file);

        try {
            newfile.createNewFile();
        } catch (IOException e) {
            int ff = 6;
        }

        Uri outputFileUri = FileProvider.getUriForFile(this,
                "com.example.android.fileprovider",
                newfile);
        picturePath = newfile.toString();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
           // Helper.setImageFromStorage(picturePath,this);
            Log.d("CameraDemo", "Pic saved");
            onBackPressed();
        }
    }

}
