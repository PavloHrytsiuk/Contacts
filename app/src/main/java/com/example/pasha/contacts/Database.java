package com.example.pasha.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Database {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;
    private Cursor cursor = null;

    public void connectionDatebase(Context context) {
        this.context = context;

        dbHelper = new DBHelper(context);
        // connection to the base
        database = dbHelper.getWritableDatabase();

        //cursor = null;
        try {
            cursor = database.query("contacts", null, null, null, null, null, null);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
        Log.d("TAG", "count " + String.valueOf(cursor.getCount()));
        if (cursor.getCount() == 0) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "Name");
            contentValues.put("surname", "Surname");
            contentValues.put("phone", "+38*********");
            contentValues.put("other", "***");
            Log.d("TAG", "NEW id = " + database.insert("contacts", null, contentValues));
        }
    }

    public List<Contact> getListFromDatabase() {

        //read for SQL
        List<Contact> list;
        //listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();

        //contactsListChange = contactsList;  ///&^&&


        cursor = database.query("contacts", null, null, null, null, null, null);

        //////read
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int surnameIndex = cursor.getColumnIndex("surname");
            int phoneIndex = cursor.getColumnIndex("phone");
            int otherIndex = cursor.getColumnIndex("other");
            do {
                Log.d("TAG", "ID = " + cursor.getInt(idIndex) +
                        ", Name = " + cursor.getString(nameIndex) +
                        ", Surname = " + cursor.getString(surnameIndex) +
                        ", Phone = " + cursor.getString(phoneIndex) +
                        ", Other = " + cursor.getString(otherIndex));
                list.add(new Contact(cursor.getInt(idIndex), cursor.getString(nameIndex), cursor.getString(surnameIndex), cursor.getString(phoneIndex), cursor.getString(otherIndex)));
            } while (cursor.moveToNext());
        } else Log.d("TAG", "0 rows");
        Collections.sort(list);
        //***set id how position!!!
        cursor.close();
        dbHelper.close();
        //sortIdList();
        return list;
    }

}
