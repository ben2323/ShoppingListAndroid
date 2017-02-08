package com.example.bental.studentsapp2.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import static android.R.attr.version;

/**
 * Created by ben on 2/7/2017.
 */

public class DatabaseHelper  extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
        super(context, "datamodel.db", null, 200000000);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE users (userId  TEXT,  firstName  TEXT, lastName  TEXT, email TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE groups (groupId  TEXT,  groupName  TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE shoppingItems (itemId  TEXT,  name  TEXT, Quantity  FLOAT," +
                " addedDate TEXT,addedByUserId TEXT, imageUrl TEXT, groupId TEXT )");
        sqLiteDatabase.execSQL("CREATE TABLE userGroups (groupId  TEXT,  groupName  TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
/*        sqLiteDatabase.execSQL("DROP TABLE users");
        sqLiteDatabase.execSQL("DROP TABLE groups");
        sqLiteDatabase.execSQL("DROP TABLE shoppingItems");
        sqLiteDatabase.execSQL("DROP TABLE userGroups");*/
        onCreate(sqLiteDatabase);
    }
}
