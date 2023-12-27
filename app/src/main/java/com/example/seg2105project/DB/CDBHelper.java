/**
 * The EDBHelper class is a helper class for managing a SQLite database for events, including methods
 * for creating tables, adding events, retrieving event names, starting events, getting the number of
 * events, and deleting events.
 */
package com.example.seg2105project.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.seg2105project.ClubOwner.ClubInfo;
import com.example.seg2105project.Shared.Event;

import java.util.ArrayList;

public class CDBHelper extends SQLiteOpenHelper{

    // The line `private static final String DATABASE_NAME = "event.db";` is declaring a constant
    // variable `DATABASE_NAME` of type `String` with the value "event.db". This constant is used as the
    // name of the SQLite database file.
    private static final String DATABASE_NAME = "clubInfo.db";
    // The line `private static final String TABLE_NAME = "events";` is declaring a constant variable
    // `TABLE_NAME` of type `String` with the value "events". This constant is used as the table name
    // for the SQLite database table.
    private static final String TABLE_NAME = "clubInfo";
    // The line `private static final String COL_ID = "id";` is declaring a constant variable `COL_ID`
    // of type `String` with the value "id". This constant is used as the column name for the "id"
    // field in the SQLite database table.
    private static final String COL_ID = "id";
    // The line `private static final String COL_EVENTNAME = "eventname";` is declaring a constant
    // variable `COL_EVENTNAME` of type `String` with the value "eventname". This constant is used as
    // the column name for the "eventname" field in the SQLite database table.
    private static final String COL_CLUB_NAME = "clubname";
    // The line `private static final String COL_DESCRIPTION = "description";` is declaring a constant
    // variable `COL_DESCRIPTION` of type `String` with the value "description". This constant is used
    // as the column name for the "description" field in the SQLite database table.
    private static final String COL_INSTAGRAM = "instagram";
    // The line `private static final String COL_LEVEL = "level";` is declaring a constant variable
    // `COL_LEVEL` of type `String` with the value "level". This constant is used as the column name
    // for the "level" field in the SQLite database table.
    private static final String COL_CONTACT = "contact";
    private static final String COL_PHONE_NUMBER = "phonenumber";
    // The line `private static final String COL_EQUIPEMENT = "equipements";` is declaring a constant
    // variable `COL_EQUIPEMENT` of type `String` with the value "equipements". This constant is used
    // as the column name for the "equipements" field in the SQLite database table.

    // The `EDBHelper` constructor is used to initialize the `EDBHelper` class. It calls the constructor
    // of the `SQLiteOpenHelper` class, passing in the `context`, `DATABASE_NAME`, `null`, and `2` as
    // arguments.
    private static final String COL_RATING = "rating";
    private static final String COL_FEEDBACK = "feedback";

    public CDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase edatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CLUB_NAME + " TEXT, " +
                COL_INSTAGRAM + " TEXT, " +
                COL_CONTACT + " TEXT, " +
                COL_PHONE_NUMBER + " TEXT, " +
                COL_RATING + " TEXT, " +
                COL_FEEDBACK + " TEXT)";

        edatabase.execSQL(createTable);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addClubInfo(String clubName, String instagram, String contact, String phoneNumber, float rating, String feedback) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CLUB_NAME, clubName);
        contentValues.put(COL_INSTAGRAM, instagram);
        contentValues.put(COL_CONTACT, contact);
        contentValues.put(COL_PHONE_NUMBER, phoneNumber);
        contentValues.put(COL_RATING, rating);
        contentValues.put(COL_FEEDBACK, feedback);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        return result != -1; // Returns true if data is inserted successfully
    }
    public boolean clubFound(String clubName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_CLUB_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{clubName});

        boolean found = cursor.moveToFirst();
        cursor.close();
        db.close();

        return found;
    }




    public ArrayList<ClubInfo> getAllInfo() {
        ArrayList<ClubInfo> clubInfoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                int clubNameIndex = cursor.getColumnIndex(COL_CLUB_NAME);
                int instagramIndex = cursor.getColumnIndex(COL_INSTAGRAM);
                int contactIndex = cursor.getColumnIndex(COL_CONTACT);
                int phoneNumberIndex = cursor.getColumnIndex(COL_PHONE_NUMBER);
                int ratingIndex = cursor.getColumnIndex(COL_RATING);
                int feedbackIndex = cursor.getColumnIndex(COL_FEEDBACK);

                while (cursor.moveToNext()) {
                    String name = getColumnValue(cursor, clubNameIndex);
                    String instagram = getColumnValue(cursor, instagramIndex);
                    String contact = getColumnValue(cursor, contactIndex);
                    String phoneNumber = getColumnValue(cursor, phoneNumberIndex);
                    float rating = cursor.getFloat(ratingIndex);
                    String feedback = getColumnValue(cursor, feedbackIndex);

                    ClubInfo clubInfo = new ClubInfo(name, instagram, contact, phoneNumber);
                    clubInfo.addRating(rating, feedback);

                    clubInfoList.add(clubInfo);
                }
            } finally {
                cursor.close();
            }
        } else {
            // Handle the case where the cursor is null
        }

        db.close();

        return clubInfoList;
    }

    private String getColumnValue(Cursor cursor, int columnIndex) {
        return (columnIndex != -1) ? cursor.getString(columnIndex) : null;
    }


    public String getInstagram(String clubName) {

        SQLiteDatabase db = this.getReadableDatabase();
        String instagram = null;

        String[] columns = {COL_CLUB_NAME};
        String selection = COL_INSTAGRAM + "=?";
        String[] selectionArgs = {clubName};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_CLUB_NAME);
            if (columnIndex != -1) {
                instagram = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return instagram;
    }

    public String getContact(String clubName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String contact = null;

        String[] columns = {COL_CLUB_NAME};
        String selection = COL_CONTACT + "=?";
        String[] selectionArgs = {clubName};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_CLUB_NAME);
            if (columnIndex != -1) {
                contact = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return contact;
    }

    public String getPhoneNumber(String clubName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String phoneNumber = null;

        String[] columns = {COL_CLUB_NAME};
        String selection = COL_PHONE_NUMBER + "=?";
        String[] selectionArgs = {clubName};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_CLUB_NAME);
            if (columnIndex != -1) {
                phoneNumber = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return phoneNumber;
    }

    public ArrayList<ClubInfo> getAllFeedback() {
        ArrayList<ClubInfo> clubInfoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                int clubNameIndex = cursor.getColumnIndex(COL_CLUB_NAME);
                int instagramIndex = cursor.getColumnIndex(COL_INSTAGRAM);
                int contactIndex = cursor.getColumnIndex(COL_CONTACT);
                int phoneNumberIndex = cursor.getColumnIndex(COL_PHONE_NUMBER);
                int ratingIndex = cursor.getColumnIndex(COL_RATING);
                int feedbackIndex = cursor.getColumnIndex(COL_FEEDBACK);

                while (cursor.moveToNext()) {
                    String name = getColumnValue(cursor, clubNameIndex);
                    String instagram = getColumnValue(cursor, instagramIndex);
                    String contact = getColumnValue(cursor, contactIndex);
                    String phoneNumber = getColumnValue(cursor, phoneNumberIndex);
                    float rating = cursor.getFloat(ratingIndex);
                    String feedback = getColumnValue(cursor, feedbackIndex);

                    ClubInfo clubInfo = new ClubInfo(name, instagram, contact, phoneNumber);
                    clubInfo.addRating(rating, feedback);

                    clubInfoList.add(clubInfo);
                }
            } finally {
                cursor.close();
            }
        } else {
            return null;
        }

        db.close();

        return clubInfoList;
    }

}
