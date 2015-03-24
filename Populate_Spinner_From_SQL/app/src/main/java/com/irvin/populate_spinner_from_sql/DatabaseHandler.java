package com.irvin.populate_spinner_from_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 3/24/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // db version
    private static final int DATABASE_VERSION = 1;

    // db name
    private static final String DATABASE_NAME = "spinnerExample";

    // db table name
    private static final String TABLE_LABELS = "labels";

    // Contacts table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LABELS_TABLE = "CREATE TABLE " + TABLE_LABELS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTO INCREMENT," + KEY_NAME + "TEXT)";
        db.execSQL(CREATE_LABELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LABELS);

        // create db again
        onCreate(db);
    }

    /**
     * Inserting new lable into lables table
     * */
    public void insertLabel(String label) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, label);

        db.insert(TABLE_LABELS, null, values);
        db.close();
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
     public List<String> getAllLabels() {
         List<String> labels = new ArrayList<String>();
         SQLiteDatabase db = this.getReadableDatabase();

         String selectAllQuery = "SELECT * FROM " + TABLE_LABELS;

         Cursor cursor = db.rawQuery(selectAllQuery, null);

         if (cursor.moveToFirst()) {
             do {
                 labels.add(cursor.getString(1));
             } while (cursor.moveToNext());
         }
         cursor.close();
         db.close();
         return labels;
     }
}
