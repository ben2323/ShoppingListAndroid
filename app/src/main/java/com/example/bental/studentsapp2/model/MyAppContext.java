package com.example.bental.studentsapp2.model;

import android.app.Application;
import android.content.Context;

/**
 * Created by ben on 2/8/2017.
 */

public class MyAppContext extends Application {

    private static Context context;
    public void onCreate() {
        super.onCreate();
        MyAppContext.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return MyAppContext.context;
    }
}

