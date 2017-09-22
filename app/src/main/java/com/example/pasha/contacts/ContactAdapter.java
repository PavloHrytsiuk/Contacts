package com.example.pasha.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ContactAdapter extends ArrayAdapter<Contact> {
    List<Contact> listContacts;
    private List<Contact> listContactsCleanCopy;
    private Context context;

    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listContacts = objects;
        this.listContactsCleanCopy = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Contact item = listContacts.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.item_Text);
        if (item.getSurname().isEmpty()) {
            textView.setText(item.getName());
        } else {
            textView.setText(item.getSurname() + "  " + item.getName());
        }
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listContacts = new ArrayList<>();
        if (charText.length() == 0) {
            listContacts.addAll(listContactsCleanCopy);
        } else {
            for (Contact item : listContactsCleanCopy) {
                if (item.getSurname().toLowerCase(Locale.getDefault()).contains(charText) | item.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listContacts.add(item);
                }
            }
        }
    }

    @Override
    public int getCount() {
        return listContacts.size();
    }
}
