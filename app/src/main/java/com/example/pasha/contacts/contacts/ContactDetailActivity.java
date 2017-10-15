package com.example.pasha.contacts.contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pasha.contacts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailActivity extends AppCompatActivity {
    @BindView(R.id.nameDetailTV) TextView textViewName;
    @BindView(R.id.TelDetailTV) TextView textViewTel;
    @BindView(R.id.otherDetailTV) TextView textViewOther;

    private AlertDialog.Builder ad;
    private String name;
    private String surname;
    private int contactID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);

        name = getIntent().getStringExtra("name");
        surname = getIntent().getStringExtra("surname");
        textViewName.setText(surname + " " + name);
        textViewTel.setText(getIntent().getStringExtra("tel"));
        textViewOther.setText(getIntent().getStringExtra("other"));
        contactID = getIntent().getIntExtra("ID", -1);
        Log.d("TAG", "(Detail Activity)contactID " + contactID);

        textViewTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textViewTel.getText().toString()));
                if (ActivityCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Delete:
                String title = "Delete this contact";
                String message = "Are you sure?";
                String button1String = "NO";
                String button2String = "YES";
                ad = new AlertDialog.Builder(ContactDetailActivity.this);
                ad.setTitle(title);
                ad.setMessage(message);
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent();
                        intent.putExtra("ID", contactID);
                        Toast.makeText(ContactDetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        setResult(ContactsActivity.RESULT_DELETE_CONTACT, intent);
                        finish();
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                    }
                });
                ad.show();
                break;

            case R.id.action_Edit:
                Intent intent_two = new Intent(ContactDetailActivity.this, SetContactActivity.class);
                intent_two.putExtra("name", name);
                intent_two.putExtra("surname", surname);
                intent_two.putExtra("tel", textViewTel.getText().toString());
                intent_two.putExtra("other", textViewOther.getText().toString());
                intent_two.putExtra("ID", contactID);
                startActivityForResult(intent_two, 3);
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
        if (resultCode == ContactsActivity.RESULT_SET_CONTACT) {
            name = data.getStringExtra("newName");
            surname = data.getStringExtra("newSurname");
            textViewName.setText(surname + " " + name);
            String tel = data.getStringExtra("newPhone");
            textViewTel.setText(tel);
            String other = data.getStringExtra("newOther");
            textViewOther.setText(other);
            Intent intent = new Intent();
            intent.putExtra("newName", name);
            intent.putExtra("newSurname", surname);
            intent.putExtra("newPhone", tel);
            intent.putExtra("newOther", other);
            intent.putExtra("ID", contactID);
            setResult(ContactsActivity.RESULT_EDIT_CONTACT, intent);
        }
    }
}
