package com.example.pasha.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ContactsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Contact> contactsList;
    // ArrayList<Contact> contactsListChange;


    ContactAdapter contactAdapter;
    final int SET_CONTACT = 1;
    final int RESULT_DELETE = 2;
    final int RESULT_EDIT_CONTACT = 3;

    DBHelper dbHelper;
    SQLiteDatabase database;
    boolean dbChange = false;
    EditText searchEdText;
    private String searchText;
    Database databaseClass = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_main);

        listView = (ListView) findViewById(R.id.listView);
        //contactsList = new ArrayList<>();
        contactsList = databaseClass.getListFromDatabase();
        databaseClass.readDatabaseToLog();
        sortIdList();


//        contactsList.add(new Contact("Palvo", "Hrytsiuk", "+380953275624"));
//        contactsList.add(new Contact("Oleksandr", "Hrytsiuk", "+380953085331"));
//        contactsList.add(new Contact("Anna", "Hrytsiuk", "+380505542403"));
//        contactsList.add(new Contact("Zhanna", "Hrytsiuk", "+80952580785", "Mather"));
//        contactsList.add(new Contact("Maria", "Kononyuk", "+380662444516"));
//        contactsList.add(new Contact("Pasha", "Zan", "380666083972"));
//        contactsList.add(new Contact("Vadim", "Rubik", "380953085451"));
//        contactsList.add(new Contact("Jaroslav", "Medyantsev", "380953085231"));
//        contactsList.add(new Contact("Sergiy", "Pavlyuk", "380673020331"));
//        contactsList.add(new Contact("Andriy", "Gavruluyk", "380673250331"));


        contactAdapter = new ContactAdapter(this, R.layout.contact_item, contactsList);
        listView.setAdapter(contactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsActivity.this, ContactDetailActivity.class);
                intent.putExtra("name", contactAdapter.listContacts.get(position).getName());
                intent.putExtra("surname", contactAdapter.listContacts.get(position).getSurname());
                intent.putExtra("tel", contactAdapter.listContacts.get(position).getTel());
                intent.putExtra("other", contactAdapter.listContacts.get(position).getOther());
//                int contactPosition = -1;
//                for (int i = 0; i < contactsList.size(); i++) {
//                    if (contactsList.get(i).getContactId() == contactAdapter.listContacts.get(position).getContactId()) {
//                        contactPosition = i;
//                    }
//                }
                intent.putExtra("size", contactsList.size());
                //  intent.putExtra("position", contactPosition);
                intent.putExtra("ID", contactAdapter.listContacts.get(position).getPositionID());
                startActivityForResult(intent, 1);
            }
        });
        searchEdText = (EditText) findViewById(R.id.editSearch);
        searchEdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // contactAdapter.getFilter().filter(s);
                // contactAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = searchEdText.getText().toString().toLowerCase(Locale.getDefault());
                contactAdapter.filter(searchText);
//                contactsList.clear();
                //contactsList = (ArrayList<Contact>) contactAdapter.listContacts;
//                for (Contact cont : contactAdapter.listContacts) {
//                    contactsList.add(cont);
//                }
                contactAdapter.notifyDataSetChanged();
                //  contactAdapter = new ContactAdapter(ContactsActivity.this, R.layout.contact_item, contactsList);

                Log.d("TAG", "******");
                for (Contact x : contactAdapter.listContacts) {
                    Log.d("TAG", "Adapter after " + x.getSurname());
                }
                Log.d("TAG", "***contactsList***");
                for (Contact x : contactsList) {
                    Log.d("TAG", "contactsList  " + x.getSurname());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_Add:
                //Toast.makeText(ContactsActivity.this, "Add", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ContactsActivity.this, SetContactActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        int position = data.getIntExtra("position", -1); ///change later
        int positionID = data.getIntExtra("ID", -1);

        if (resultCode == SET_CONTACT) {

            Contact newContact = new Contact(data.getStringExtra("newName"), data.getStringExtra("newSurname"), data.getStringExtra("newPhone"), data.getStringExtra("newOther"));
            contactsList.add(newContact);
            Collections.sort(contactsList);
            sortIdList();
            if (searchText != null) {
                contactAdapter.filter(searchText);
            }
            contactAdapter.notifyDataSetChanged();
            ///*******
            //reloadSqlDB(contactsList);
            databaseClass.addContactToDatabase(newContact);
            databaseClass.readDatabaseToLog();
            //dbChange = true;


        }
        if (resultCode == RESULT_DELETE) {
//            int position = data.getIntExtra("position", -1);

            if (positionID != -1) {
                Log.d("TAG", "♦♦♦ getContactID - " + contactsList.get(positionID).getContactID());
                databaseClass.deleteContactFromDatabase(contactsList.get(positionID).getContactID());
                databaseClass.readDatabaseToLog();
                contactsList.remove(positionID);
                sortIdList();
                if (searchText != null) {
                    contactAdapter.filter(searchText);
                }
                contactAdapter.notifyDataSetChanged();
                //reloadSqlDB(contactsList);

               // dbChange = true;
            }

        }
        if (resultCode == RESULT_EDIT_CONTACT) {
//            int position = data.getIntExtra("position", -1); ///change later
            Log.d("TAG", "(RESULT_EDIT_CONTACT)positionID " + positionID);

            if (positionID != -1) {
                Contact newContact = new Contact( data.getStringExtra("newName"), data.getStringExtra("newSurname"), data.getStringExtra("newPhone"), data.getStringExtra("newOther"));
                Log.d("TAG", "NewContact_Name" + newContact.getName());
                Log.d("TAG", "NewContact_Surname" + newContact.getSurname());
                Log.d("TAG", "NewContact_Tel" + newContact.getTel());
                Log.d("TAG", "NewContact_Other" + newContact.getOther());
                Log.d("TAG", "NewContact_positionID" + String.valueOf(newContact.getPositionID()));


                contactsList.set(positionID, newContact);
                Collections.sort(contactsList);
                sortIdList();
                if (searchText != null) {
                    contactAdapter.filter(searchText);
                }
                contactAdapter.notifyDataSetChanged();
                //reloadSqlDB(contactsList);
                dbChange = true;
            }

        }
    }

    private void reloadSqlDB(List<Contact> list) {
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        database.delete("contacts", null, null);
        ContentValues contentValues = new ContentValues();
        for (Contact contact : list) {
            if (contact.getPositionID() != 0) {
                contentValues.put("id", contact.getPositionID());
            }
            contentValues.put("name", contact.getName());
            contentValues.put("surname", contact.getSurname());
            contentValues.put("phone", contact.getTel());
            contentValues.put("other", contact.getOther());
            Log.d("TAG", "NEW id = " + database.insert("contacts", null, contentValues));
            contentValues.clear();
        }
        dbHelper.close();
    }


    @Override
    protected void onDestroy() {
        if (dbChange) {
            reloadSqlDB(contactsList);
            Log.d("TAG", "DB change!");
        }
        super.onDestroy();
    }

    private void sortIdList() {
        for (int i = 0; i < contactsList.size(); i++) {
            contactsList.get(i).setPositionID(i);
        }
        for (int i = 0; i < contactsList.size(); i++) {
            Log.d("TAG", "new PositionID =" + contactsList.get(i).getPositionID() + " " + contactsList.get(i).getSurname() + " " + contactsList.get(i).getName());
        }
    }
}
