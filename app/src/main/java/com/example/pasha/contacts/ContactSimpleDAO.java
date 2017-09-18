package com.example.pasha.contacts;

/**
 * Created by Pasha on 29.11.2016.
 */

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactSimpleDAO implements ContactDAO
{

    private final List<Contact> contacts = new ArrayList<Contact>();

    @Override
    public void addContact(Contact contact) {

       // Long id = generateContactId();
        //contact.setContactId(id);
        contacts.add(contact);
       // return id;
    }

    @Override
    public void updateContact(Contact contact, int position) {
        Contact oldContact = getContact(position);
        if(oldContact != null) {
            oldContact.setName(contact.getName());
            oldContact.setSurname(contact.getSurname());
            oldContact.setTel(contact.getTel());
            //oldContact.setEmail(contact.getEmail());
        }
    }

    @Override
    public void deleteContact(int  position) {
        contacts.remove(position);
//        for(Iterator<Contact> it = contacts.iterator(); it.hasNext();) {
//            Contact cnt = it.next();
//            if(cnt.getContactId().equals(contactId)) {
//                it.remove();
//            }
//        }
    }

    @Override
    public Contact getContact(int position) {
        return contacts.get(position);
    }

//    @Override
//    public List<Contact> findContacts() {
//        return contacts;
//    }

//    private Long generateContactId() {
//        Long contactId = Math.round(Math.random() * 1000 + System.currentTimeMillis());
//        while(getContact(contactId) != null) {
//            contactId = Math.round(Math.random() * 1000 + System.currentTimeMillis());
//        }
//        return contactId;
//    }
}