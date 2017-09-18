package com.example.pasha.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "contactDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG", "--- onCreate database ---");
        // create table with fields
        db.execSQL("create table contacts ("
                + "id integer primary key autoincrement," + "name text,"
                + "surname text," + "phone text," + "other text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  db.execSQL("drop table if exists mytable");

        //onCreate(db);

    }
}
