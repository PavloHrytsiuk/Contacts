package com.example.pasha.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SetContactActivity extends AppCompatActivity {

    EditText etName;
    EditText etSurname;
    EditText etPhone;
    EditText etOther;
    final int SET_CONTACT = 1;
    int contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_contact);

        etName = (EditText) findViewById(R.id.setName_setActivity);
        etSurname = (EditText) findViewById(R.id.setSurname_setActivity);
        etPhone = (EditText) findViewById(R.id.setPhone_setActivity);
        etOther = (EditText) findViewById(R.id.setOther_setActivity);
        etName.setText(getIntent().getStringExtra("name"), null);
        etSurname.setText(getIntent().getStringExtra("surname"), null);
        etPhone.setText(getIntent().getStringExtra("tel"), null);
        etOther.setText(getIntent().getStringExtra("other"), null);
        contactID = getIntent().getIntExtra("ID", -1); /////


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_set_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_OK:
                if (etPhone.getText().toString().isEmpty()) {
                    Toast.makeText(SetContactActivity.this, "Please set mobile phone!", Toast.LENGTH_LONG).show();
                    break;
                }
                if (etName.getText().toString().isEmpty() && etSurname.getText().toString().isEmpty()) {
                    Toast.makeText(SetContactActivity.this, "Please set name or surname!", Toast.LENGTH_LONG).show();
                    break;
                }
                Intent intent = new Intent();
                //Contact newContact = new Contact(etName.getText().toString(), etSurname.getText().toString(), etPhone.getText().toString());
                intent.putExtra("newName", etName.getText().toString());
                intent.putExtra("newSurname", etSurname.getText().toString());
                intent.putExtra("newPhone", etPhone.getText().toString());
                intent.putExtra("newOther", etOther.getText().toString());
                intent.putExtra("ID", contactID);
//                if (contactID == -1) {
//                    int size = getIntent().getIntExtra("size", 0);
//                    intent.putExtra("ID", size + 1000); /////////***************
//                } else intent.putExtra("ID", contactID);

                Toast.makeText(SetContactActivity.this, "Save", Toast.LENGTH_SHORT).show();
                setResult(SET_CONTACT, intent);
                finish();
                break;
            default:
                setResult(RESULT_CANCELED, null);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
