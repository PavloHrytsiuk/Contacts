package com.example.pasha.contacts;

/**
 * Created by Pasha on 29.11.2016.
 */


/**
 * Фабрика для создания экземпляра ContactDAO
 */
public class ContactDAOFactory
{
    public static ContactDAO getContactDAO() {
        return new ContactSimpleDAO();
    }
}