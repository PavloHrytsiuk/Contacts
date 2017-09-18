package com.example.pasha.contacts;

import java.util.List;

/**
 * Created by Pasha on 29.11.2016.
 */

public interface ContactDAO
{
    // Добавление контакта - возвращает ID добавленного контакта
    public void addContact(Contact contact);
    // Редактирование контакта
    public void updateContact(Contact contact, int position);
    // Удаление контакта по его ID
    public void deleteContact(int  position);
    // Получение контакта
    public Contact getContact(int position);
    // Получение списка контактов
    //public List<Contact> findContacts();

}