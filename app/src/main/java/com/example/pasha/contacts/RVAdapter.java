package com.example.pasha.contacts;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    private List<Contact> contacts;
    private List<Contact> listContactsCleanCopy;
    private ContactsCallbacks callbacks;

    public RVAdapter(List<Contact> contacts, ContactsCallbacks callbacks) {
        this.contacts = contacts;
        this.listContactsCleanCopy = contacts;
        this.callbacks = callbacks;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v, callbacks);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Contact item = contacts.get(position);
        if (item.getSurname().isEmpty()) {
            holder.textView.setText(item.getName());
        } else {
            holder.textView.setText(item.getSurname() + "  " + item.getName());
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        contacts = new ArrayList<>();
        if (charText.length() == 0) {
            contacts.addAll(listContactsCleanCopy);
        } else {
            for (Contact item : listContactsCleanCopy) {
                if (item.getSurname().toLowerCase(Locale.getDefault()).contains(charText) |
                        item.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    contacts.add(item);
                }
            }
        }
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv) CardView cardView;
        @BindView(R.id.item_Text) TextView textView;

        PersonViewHolder(View itemView, final ContactsCallbacks callbacks) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.onClick(getAdapterPosition());
                }
            });
        }
    }
}