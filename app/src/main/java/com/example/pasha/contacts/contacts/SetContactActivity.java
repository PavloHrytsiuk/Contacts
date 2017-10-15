package com.example.pasha.contacts.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pasha.contacts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetContactActivity extends AppCompatActivity {

    @BindView(R.id.setNameSetActivity) EditText etName;
    @BindView(R.id.setSurnameSetActivity) EditText etSurname;
    @BindView(R.id.setPhoneSetActivity) EditText etPhone;
    @BindView(R.id.setOtherSetActivity) EditText etOther;

    private int contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_contact);
        ButterKnife.bind(this);

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
                setResult(ContactsActivity.RESULT_SET_CONTACT, intent);
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
