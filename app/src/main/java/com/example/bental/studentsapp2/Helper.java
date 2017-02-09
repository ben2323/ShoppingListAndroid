package com.example.bental.studentsapp2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.ImageView;

import com.example.bental.studentsapp2.model.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ben on 2/3/2017.
 */

public class Helper {


    @Nullable
    public static void setImageFromStorage(String pictureName, Context context, final ImageView imageView) {
        if (pictureName.isEmpty()) {
            //no picture
            //todo add a 'no image' image
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.common_full_open_on_phone));
            return;
        }
        File f = getFile(pictureName, context);
        try {
            if (f != null) {
                imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(f)));
            } else {

                //file doesn't exist on local device, will fetch from server

                Model.instance().downloadPicture(createFile(pictureName, context), new Model.DownloadFileListener() {
                    @Override
                    public void onComplete(File file) {
                        try {
                            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
                        } catch (FileNotFoundException e) {
                            imageView.setImageBitmap(null);
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (FileNotFoundException e) {
            Log.d("loading image error: ", e.getMessage());
            imageView.setImageBitmap(null);
        }
    }

    public static File getFile(String fileName, Context context) {
        String localFiledir = getLocalDir(context);
        File file = new File(localFiledir + fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static Uri getUriFromFile(File file, Context context) {
        return FileProvider.getUriForFile(context,
                "com.example.bental.studentsapp2.fileprovider",
                file);
    }

    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getLocalDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/pics/";
    }

    public static void createDir(String path) {
        File newDir = new File(path);
        if (!newDir.exists()) {
            newDir.mkdirs();
        }
    }

    public static void copyFile(String sourcePath, Context context) {
        File sourceFile = new File(Uri.parse(sourcePath).getPath());

        String destinationPath = getLocalDir(context) + sourceFile.getName();
        File destination = new File(destinationPath);
        try {
            FileUtils.copyFile(sourceFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createFile(String fileName, Context context) {
        String path = getLocalDir(context);
        File newfile = new File(path + fileName);
        try {
            createDir(path);
            newfile.createNewFile();
            return newfile;
        } catch (IOException e) {
            return null;
        }
    }

    public static SpinnerDialog showLoader(FragmentManager fm) {
        SpinnerDialog myInstance = new SpinnerDialog();
        myInstance.show(fm, "some_tag");
        return myInstance;
    }

    public static String capturePicture(String pictureName, Context context, Fragment f, int pictureMode) {
        //take from camera
        if (pictureMode == 1) {
            String dirPath = getLocalDir(context);
            String file = dirPath + pictureName + "_" + SystemClock.currentThreadTimeMillis() + ".jpg";
            File newfile = new File(file);
            try {
                newfile.createNewFile();
            } catch (IOException e) {
            }
            Uri outputFileUri = getUriFromFile(newfile, context);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            f.startActivityForResult(cameraIntent, 1);
            return newfile.getName();

            //take from gallery
        } else {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            f.startActivityForResult(pickPhoto, 2);
            return "";
        }


    }
}
