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
    List<Contact> listContactsCleanCopy;
    Context context;

    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listContacts = objects;
        this.listContactsCleanCopy = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        //Contact item = getItem(position);
        Contact item = listContacts.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.item_Text);
        if (item.getSurname().isEmpty()) {
            textView.setText(item.getName());
        } else {
            textView.setText(item.getSurname() + "  " + item.getName());
        }


        //TextView textView_Tel = (TextView) convertView.findViewById(R.id.item_Tel);
        //textView_Tel.setText(item.getTel());
        return convertView;
    }



    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listContacts = new ArrayList<Contact>();
       // listContacts.clear();
        if (charText.length() == 0) {
            listContacts.addAll(listContactsCleanCopy);
        } else {
            for (Contact item : listContactsCleanCopy) {
                if (item.getSurname().toLowerCase(Locale.getDefault()).contains(charText)| item.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listContacts.add(item);
                }            }
        }
       // notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listContacts.size();
    }

}
