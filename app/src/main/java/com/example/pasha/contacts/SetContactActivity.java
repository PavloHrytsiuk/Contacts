package com.example.pasha.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SetContactActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etSurname;
    private EditText etPhone;
    private EditText etOther;
    private int contactID;
    final int SET_CONTACT = 1;

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
                intent.putExtra("newName", etName.getText().toString());
                intent.putExtra("newSurname", etSurname.getText().toString());
                intent.putExtra("newPhone", etPhone.getText().toString());
                intent.putExtra("newOther", etOther.getText().toString());
                intent.putExtra("ID", contactID);
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
