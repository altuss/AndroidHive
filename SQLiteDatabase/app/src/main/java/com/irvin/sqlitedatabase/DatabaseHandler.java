package com.irvin.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by Hp on 3/18/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // db version
    private static final int DATABASE_VERSION = 1;

    // db name
    private static final String DATABASE_NAME = "contactsManager";

    // db table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTO INCREMENT, " + KEY_NAME + " TEXT, " + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // create db again
        onCreate(db);
    }

    ///////////// CRUD OPERATIONS /////////////////

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_name());
        values.put(KEY_PH_NO, contact.get_phoneNumber());

        // insert into db
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] arguments = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_CONTACTS, null, KEY_ID + "=?", arguments, null, null, null, null);
        if (cursor != null)
            cursor.moveToNext();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
                cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
        cursor.close();
        db.close();
        return contact;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactsList = new ArrayList<Contact>();
        SQLiteDatabase db = this.getWritableDatabase();

        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_name(cursor.getString(1));
                contact.set_phoneNumber(cursor.getString(2));
                contactsList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactsList;
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();

        // return count
        return cursor.getCount();
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(contact.get_id())};

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_name());
        values.put(KEY_PH_NO, contact.get_phoneNumber());

        db.update(TABLE_CONTACTS, values, KEY_ID + " =?", args);
        db.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(contact.get_id())};
        db.delete(TABLE_CONTACTS, KEY_ID + " =?", args);
        db.close();
    }
}
