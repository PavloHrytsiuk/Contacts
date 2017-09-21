package com.example.pasha.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


public class Database {
    private SQLiteDatabase database;
    private Context context;
    private Cursor cursor = null;
    private DBHelper dbHelper; //= new DBHelper(context);

    Database(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }


    public void firstConnectionToDatebase() {

        // connection to the base
        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }

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

        cursor.close();
        dbHelper.close();
    }

    public ArrayList<Contact> getListFromDatabase() {

        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }

        try {
            cursor = database.query("contacts", null, null, null, null, null, null);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }

        //read for SQL
        ArrayList<Contact> list;
        //listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();

        //contactsListChange = contactsList;  ///&^&&
        //cursor = database.query("contacts", null, null, null, null, null, null);

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

    private void addContactToDatabase(Contact contact) {
        //dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        // database.delete("contacts", null, null);
        ContentValues contentValues = new ContentValues();
           /* if (contact.getContactId() != 0) {
                contentValues.put("id", contact.getContactId());
            }*/
        contentValues.put("name", contact.getName());
        contentValues.put("surname", contact.getSurname());
        contentValues.put("phone", contact.getTel());
        contentValues.put("other", contact.getOther());
        Log.d("TAG", "NEW contact = " + database.insert("contacts", null, contentValues));

        contentValues.clear();

        dbHelper.close();
    }

    public void readDatabaseToLog() {
        database = dbHelper.getWritableDatabase();
        try {
            cursor = database.query("contacts", null, null, null, null, null, null);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int surnameIndex = cursor.getColumnIndex("surname");
            int phoneIndex = cursor.getColumnIndex("phone");
            int otherIndex = cursor.getColumnIndex("other");
            do {
                Log.d("TAG", "☺ ID = " + cursor.getInt(idIndex) +
                        ", Name = " + cursor.getString(nameIndex) +
                        ", Surname = " + cursor.getString(surnameIndex) +
                        ", Phone = " + cursor.getString(phoneIndex) +
                        ", Other = " + cursor.getString(otherIndex));
               // list.add(new Contact(cursor.getInt(idIndex), cursor.getString(nameIndex), cursor.getString(surnameIndex), cursor.getString(phoneIndex), cursor.getString(otherIndex)));
            } while (cursor.moveToNext());
        } else Log.d("TAG", "0 rows");
        cursor.close();
        dbHelper.close();
    }

}