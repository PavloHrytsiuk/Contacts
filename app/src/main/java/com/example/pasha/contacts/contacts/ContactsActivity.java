package com.example.pasha.contacts.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.pasha.contacts.database.Database;
import com.example.pasha.contacts.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity implements ContactsCallbacks {

    @BindView(R.id.recycleView) RecyclerView recyclerView;
    @BindView(R.id.editSearch) EditText searchEdText;

    static final int RESULT_SET_CONTACT = 1;
    static final int RESULT_DELETE_CONTACT = 2;
    static final int RESULT_EDIT_CONTACT = 3;

    private ArrayList<Contact> contacts;
    private String searchText;
    private Database databaseClass = new Database(this);
    private ContactAdapter contactAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_main);
        ButterKnife.bind(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        contacts = databaseClass.getListFromDatabase();
        databaseClass.readDatabaseToLog();
        sortIdList();
        contactAdapter = new ContactAdapter(contacts, this);
        recyclerView.setAdapter(contactAdapter);

        searchEdText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = searchEdText.getText().toString().toLowerCase(Locale.getDefault());
                contactAdapter.filter(searchText);
                contactAdapter.notifyDataSetChanged();
                Log.d("TAG", "******");
                for (Contact x : contactAdapter.getContacts()) {
                    Log.d("TAG", "Adapter after " + x.getSurname());
                }
                Log.d("TAG", "***contacts***");
                for (Contact x : contacts) {
                    Log.d("TAG", "contacts  " + x.getSurname());
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
                Intent intent = new Intent(ContactsActivity.this, SetContactActivity.class);
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
        int positionID = data.getIntExtra("ID", -1);
        if (resultCode == RESULT_SET_CONTACT) {
            Contact newContact = new Contact(data.getStringExtra("newName"), data.getStringExtra("newSurname"), data.getStringExtra("newPhone"), data.getStringExtra("newOther"));
            contacts.add(newContact);
            Collections.sort(contacts);
            sortIdList();
            if (searchText != null) {
                contactAdapter.filter(searchText);
            }
            contactAdapter.notifyDataSetChanged();
            databaseClass.addContactToDatabase(newContact);
            databaseClass.readDatabaseToLog();
        }
        if (resultCode == RESULT_DELETE_CONTACT) {
            if (positionID != -1) {
                Log.d("TAG", "♦♦♦ getContactID - " + contacts.get(positionID).getContactID());
                databaseClass.deleteContactFromDatabase(contacts.get(positionID).getContactID());
                databaseClass.readDatabaseToLog();
                contacts.remove(positionID);
                sortIdList();
                if (searchText != null) {
                    contactAdapter.filter(searchText);
                }
                contactAdapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_EDIT_CONTACT) {
            Log.d("TAG", "(RESULT_EDIT_CONTACT)positionID " + positionID);
            int contactIdForNewContact = contacts.get(positionID).getContactID();
            Log.d("TAG", "contactIdForNewContact:" + contactIdForNewContact);
            if (positionID != -1) {
                Contact newContact = new Contact(contactIdForNewContact, data.getStringExtra("newName"), data.getStringExtra("newSurname"), data.getStringExtra("newPhone"), data.getStringExtra("newOther"));
                Log.d("TAG", "NewContact_Name: " + newContact.getName());
                Log.d("TAG", "NewContact_Surname: " + newContact.getSurname());
                Log.d("TAG", "NewContact_Tel:" + newContact.getTel());
                Log.d("TAG", "NewContact_Other:" + newContact.getOther());
                Log.d("TAG", "NewContact_positionID: " + String.valueOf(newContact.getPositionID()));
                databaseClass.updateContactInDatabase(newContact);
                databaseClass.readDatabaseToLog();
                contacts.set(positionID, newContact);
                Collections.sort(contacts);
                sortIdList();
                if (searchText != null) {
                    contactAdapter.filter(searchText);
                }
                contactAdapter.notifyDataSetChanged();
            }
        }
    }

    private void sortIdList() {
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).setPositionID(i);
        }
        for (int i = 0; i < contacts.size(); i++) {
            Log.d("TAG", "new PositionID =" + contacts.get(i).getPositionID() + " " + contacts.get(i).getSurname() + " " + contacts.get(i).getName());
        }
    }

    @Override
    public void onClick(int position) {
        List<Contact> listContacts = contactAdapter.getContacts();
        Intent intent = new Intent(ContactsActivity.this, ContactDetailActivity.class);
        intent.putExtra("name", listContacts.get(position).getName());
        intent.putExtra("surname", listContacts.get(position).getSurname());
        intent.putExtra("tel", listContacts.get(position).getTel());
        intent.putExtra("other", listContacts.get(position).getOther());
        intent.putExtra("size", contacts.size());
        intent.putExtra("ID", listContacts.get(position).getPositionID());
        startActivityForResult(intent, 1);
    }
}
