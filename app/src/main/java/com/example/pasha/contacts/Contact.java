package com.example.pasha.contacts;


public class Contact implements Comparable<Contact> {

    private int contactId;
    private String name;
    private String surname;
    private String tel;
    private String other;

    public Contact(String name, String surname, String tel) {
        this.name = name;
        this.tel = tel;
        this.surname = surname;
    }

    public Contact(String name, String surname, String tel, String other) {
        this.name = name;
        this.tel = tel;
        this.surname = surname;
        this.other = other;
    }

    public Contact(int contactId, String name, String surname, String tel, String other) {
        this.contactId = contactId;
        this.name = name;
        this.surname = surname;
        this.tel = tel;
        this.other = other;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int compareTo(Contact o) {
        if (surname.compareToIgnoreCase(o.surname)==0){
            return name.compareToIgnoreCase(o.name);
        }
        return surname.compareToIgnoreCase(o.surname);
    }
}
