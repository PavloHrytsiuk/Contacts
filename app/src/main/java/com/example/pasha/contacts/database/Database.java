package com.example.pasha.contacts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pasha.contacts.contacts.Contact;

import java.util.ArrayList;
import java.util.Collections;

public class Database {
    private SQLiteDatabase database;
    private Cursor cursor = null;
    private DBHelper dbHelper;

    public Database(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Contact> getListFromDatabase() {
        try {
            database = dbHelper.getWritableDatabase();
            cursor = database.query("contacts", null, null, null, null, null, null);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
        ArrayList<Contact> list;
        list = new ArrayList<>();
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "Name");
            contentValues.put("surname", "Surname");
            contentValues.put("phone", "+38*********");
            contentValues.put("other", "***");
            Log.d("TAG", "NEW id = " + database.insert("contacts", null, contentValues));
        }
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int surnameIndex = cursor.getColumnIndex("surname");
            int phoneIndex = cursor.getColumnIndex("phone");
            int otherIndex = cursor.getColumnIndex("other");
            do {
                list.add(new Contact(cursor.getInt(idIndex), cursor.getString(nameIndex), cursor.getString(surnameIndex), cursor.getString(phoneIndex), cursor.getString(otherIndex)));
            } while (cursor.moveToNext());
        } else Log.d("TAG", "0 rows");
        Collections.sort(list);
        cursor.close();
        dbHelper.close();
        return list;
    }

    public void addContactToDatabase(Contact contact) {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("surname", contact.getSurname());
        contentValues.put("phone", contact.getTel());
        contentValues.put("other", contact.getOther());
        Log.d("TAG", "NEW contact = " + database.insert("contacts", null, contentValues));
        contentValues.clear();
        dbHelper.close();
    }

    public void readDatabaseToLog() {
        try {
            database = dbHelper.getWritableDatabase();
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
            } while (cursor.moveToNext());
        } else Log.d("TAG", "0 rows");
        cursor.close();
        dbHelper.close();
    }

    public void deleteContactFromDatabase(int id) {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
        String where = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        try {
            database.delete("contacts", where, whereArgs);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
        dbHelper.close();
    }

    public void updateContactInDatabase(Contact contact) {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
        String where = "id = ?";
        String[] whereArgs = {String.valueOf(contact.getContactID())};
        Log.d("Tag", "ContactID_update: " + String.valueOf(contact.getContactID()));
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("surname", contact.getSurname());
        contentValues.put("phone", contact.getTel());
        contentValues.put("other", contact.getOther());
        try {
            database.update("contacts", contentValues, where, whereArgs);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
        contentValues.clear();
        dbHelper.close();
    }

}
