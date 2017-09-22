package com.example.pasha.contacts;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    private List<Contact> listContacts;
    private List<Contact> listContactsCleanCopy;

    public RVAdapter(Context context, List<Contact> listContacts) {
        this.listContacts = listContacts;
        this.listContactsCleanCopy = listContacts;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView textView;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            textView = (TextView) itemView.findViewById(R.id.item_Text);
        }
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Contact item = listContacts.get(position);
        if (item.getSurname().isEmpty()) {
            holder.textView.setText(item.getName());
        } else {
            holder.textView.setText(item.getSurname() + "  " + item.getName());
        }
    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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

    public List<Contact> getListContacts() {
        return listContacts;
    }

}