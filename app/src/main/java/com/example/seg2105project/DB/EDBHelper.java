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

import com.example.seg2105project.Shared.Event;

import java.util.ArrayList;

public class EDBHelper extends SQLiteOpenHelper{

    // The line `private static final String DATABASE_NAME = "event.db";` is declaring a constant
    // variable `DATABASE_NAME` of type `String` with the value "event.db". This constant is used as the
    // name of the SQLite database file.
    private static final String DATABASE_NAME = "adminEvents.db";
    // The line `private static final String TABLE_NAME = "events";` is declaring a constant variable
    // `TABLE_NAME` of type `String` with the value "events". This constant is used as the table name
    // for the SQLite database table.
    private static final String TABLE_NAME = "events";
    // The line `private static final String COL_ID = "id";` is declaring a constant variable `COL_ID`
    // of type `String` with the value "id". This constant is used as the column name for the "id"
    // field in the SQLite database table.
    private static final String COL_ID = "id";
    // The line `private static final String COL_EVENTNAME = "eventname";` is declaring a constant
    // variable `COL_EVENTNAME` of type `String` with the value "eventname". This constant is used as
    // the column name for the "eventname" field in the SQLite database table.
    private static final String COL_EVENTNAME = "eventname";
    // The line `private static final String COL_NAME = "name";` is declaring a constant
    // variable `COL_NAME` of type `String` with the value "name". This constant is used
    // as the column name for the "description" field in the SQLite database table.
    private static final String COL_NAME = "name";
    // The line `private static final String COL_AGEREQ = "age";` is declaring a constant variable
    // `COL_AGEREQ` of type `String` with the value "age". This constant is used as the column name for
    // the "age" field in the SQLite database table.
    private static final String COL_AGEREQ = "age";
    // The line `private static final String COL_LEVEL = "level";` is declaring a constant variable
    // `COL_LEVEL` of type `String` with the value "level". This constant is used as the column name
    // for the "level" field in the SQLite database table.
    private static final String COL_LEVEL = "level";
    private static final String COL_PACE = "pace";
    // The line `private static final String COL_EQUIPEMENT = "equipements";` is declaring a constant
    // variable `COL_EQUIPEMENT` of type `String` with the value "equipements". This constant is used
    // as the column name for the "equipements" field in the SQLite database table.
    private static final String COL_CLUB_NAME = "clubname";
    // The line `private static final String COL_OWNER_ID = "ownerid";` is declaring a constant
    // variable `COL_OWNER_ID` of type `String` with the value "ownerid". This constant is used as
    // the column name for the "ownerid" field in the SQLite database table.

    private static final String COL_PARTICIPANTS = "participants";
    private static final String COL_DATE = "date";


   // The `EDBHelper` constructor is used to initialize the `EDBHelper` class. It calls the constructor
   // of the `SQLiteOpenHelper` class, passing in the `context`, `DATABASE_NAME`, `null`, and `2` as
   // arguments.
    public EDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    /**
     * This function creates a table in a SQLite database with specified column names and data types.
     * 
     * @param edatabase The parameter "edatabase" is an instance of the SQLiteDatabase class. It
     * represents the database on which the table is being created.
     */
    @Override
    public void onCreate(SQLiteDatabase edatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EVENTNAME + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_AGEREQ + " TEXT, " +
                COL_LEVEL + " TEXT, " +
                COL_PACE + " TEXT, " +
                COL_CLUB_NAME + " TEXT, " +
                COL_PARTICIPANTS + " TEXT, " +
                COL_DATE + " TEXT)";

        edatabase.execSQL(createTable);
    }

    /**
     * The function drops a table if it exists and then creates a new table.
     * 
     * @param db The "db" parameter is an instance of the SQLiteDatabase class. It represents the
     * database that is being upgraded.
     * @param oldVersion The old version of the database schema.
     * @param newVersion The new version of the database schema.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /**
     * The function adds an event with its details to a SQLite database and returns true if the
     * insertion is successful.
     * 
     * @param eventname The name of the event.
     * @param name The description parameter is a String that represents the description of the
     * event. It provides additional information or details about the event.
     * @param age The "age" parameter represents the age requirement for the event. It specifies the
     * minimum age that participants must be in order to attend the event.
     * @param level The "level" parameter in the addEventAdmin method is used to specify the level of
     * the event. It could be a beginner level, intermediate level, or advanced level.
     * @param clubname The "ownerid" parameter is used to specify the equipment required for the
     * event. It could be any equipment or gear that participants need to have or use during the event.
     * @return The method returns a boolean value. It returns true if the data is inserted successfully
     * into the database, and false otherwise.
     */
    public boolean addEvent(String eventname, String name, String age, String level, String pace, String clubname, int participants, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EVENTNAME, eventname);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_AGEREQ, age);
        contentValues.put(COL_LEVEL, level);
        contentValues.put(COL_PACE, pace);
        contentValues.put(COL_CLUB_NAME, clubname);
        contentValues.put(COL_PARTICIPANTS, participants);
        contentValues.put(COL_DATE, date);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1; // Returns true if data is inserted successfully
    }
    
    /**
     * The function retrieves a list of event names from a SQLite database and returns them as an
     * ArrayList of strings.
     * 
     * @return The method is returning an ArrayList of Strings, which contains the event names from the
     * database.
     */
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> eventsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns to be retrieved
        String[] columns = {COL_EVENTNAME, COL_AGEREQ, COL_LEVEL, COL_PACE, COL_CLUB_NAME, COL_PARTICIPANTS, COL_DATE};
        String selection = COL_CLUB_NAME + "=?";
        String[] selectionArgs = {"admin"};

        // Query the database to get the Cursor containing the selected columns
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Check if the columns are present in the cursor
                int eventIndex = cursor.getColumnIndex(COL_EVENTNAME);
                int ageIndex = cursor.getColumnIndex(COL_AGEREQ);
                int levelIndex = cursor.getColumnIndex(COL_LEVEL);
                int paceIndex = cursor.getColumnIndex(COL_PACE);
                int nameIndex = cursor.getColumnIndex(COL_CLUB_NAME);

                if (ageIndex != -1 && levelIndex != -1 && paceIndex != -1 && nameIndex != -1) {
                    // Extract the values from the cursor
                    String eventnm = cursor.getString(eventIndex);
                    String age = cursor.getString(ageIndex);
                    String level = cursor.getString(levelIndex);
                    String pace = cursor.getString(paceIndex);
                    Event temp = new Event(eventnm, age, pace, level, "admin");
                    // Add the string to the list
                    eventsList.add(temp);

                }
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Close the database connection
        db.close();

        // Return the list of event details strings
        return eventsList;
    }

    public ArrayList<Event> getSearchEvents(String search) {
        ArrayList<Event> eventsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        SQLiteDatabase db3 = this.getReadableDatabase();

        // Define the columns to be retrieved
        String[] columns = {COL_EVENTNAME, COL_NAME, COL_AGEREQ, COL_LEVEL, COL_PACE, COL_CLUB_NAME, COL_PARTICIPANTS, COL_DATE};
        String selection = COL_CLUB_NAME + "=?";
        String selection2 = COL_EVENTNAME + "=?";
        String selection3 = COL_NAME + "=?";
        String[] selectionArgs = {search};

        // Query the database to get the Cursor containing the selected columns
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Cursor cursor2 = db2.query(TABLE_NAME, columns, selection2, selectionArgs, null, null, null);
        Cursor cursor3 = db3.query(TABLE_NAME, columns, selection3, selectionArgs, null, null, null);
        // Check if the cursor is not null and move to the first row
        //Checks for club name
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Check if the columns are present in the cursor
                int eventIndex = cursor.getColumnIndex(COL_EVENTNAME);
                int eventNameIndex = cursor.getColumnIndex(COL_NAME);
                int ageIndex = cursor.getColumnIndex(COL_AGEREQ);
                int levelIndex = cursor.getColumnIndex(COL_LEVEL);
                int paceIndex = cursor.getColumnIndex(COL_PACE);
                int nameIndex = cursor.getColumnIndex(COL_CLUB_NAME);
                int participantIndex = cursor.getColumnIndex(COL_PARTICIPANTS);
                int dateIndex = cursor.getColumnIndex(COL_DATE);

                if (eventIndex != -1 && ageIndex != -1 && levelIndex != -1 && paceIndex != -1 && nameIndex != -1) {
                    // Extract the values from the cursor
                    String eventnm = cursor.getString(eventIndex);
                    String namenm = cursor.getString(eventNameIndex);
                    String age = cursor.getString(ageIndex);
                    String level = cursor.getString(levelIndex);
                    String pace = cursor.getString(paceIndex);
                    String clubnm = cursor.getString(nameIndex);
                    String participants = cursor.getString(participantIndex);
                    String date = cursor.getString(dateIndex);

                    // Optional: You may extract other values if needed (e.g., participants, date)

                    // Create an Event object and add it to the list
                    Event temp = new Event(eventnm, age, pace, level, clubnm, participants, date, namenm);
                    eventsList.add(temp);
                }
            } while (cursor.moveToNext());


            // Close the cursor
            cursor.close();
        }

        //Checks for type
        if (cursor2 != null && cursor2.moveToFirst()) {
            do {
                // Check if the columns are present in the cursor
                int eventIndex2 = cursor2.getColumnIndex(COL_EVENTNAME);
                int eventNameIndex2 = cursor2.getColumnIndex(COL_NAME);
                int ageIndex2 = cursor2.getColumnIndex(COL_AGEREQ);
                int levelIndex2 = cursor2.getColumnIndex(COL_LEVEL);
                int paceIndex2 = cursor2.getColumnIndex(COL_PACE);
                int nameIndex2 = cursor2.getColumnIndex(COL_CLUB_NAME);
                int participantIndex2 = cursor2.getColumnIndex(COL_PARTICIPANTS);
                int dateIndex2 = cursor2.getColumnIndex(COL_DATE);

                if (ageIndex2 != -1 && levelIndex2 != -1 && paceIndex2 != -1 && nameIndex2 != -1) {
                    // Extract the values from the cursor
                    String eventnm = cursor2.getString(eventIndex2);
                    String namenm = cursor2.getString(eventNameIndex2);
                    String age = cursor2.getString(ageIndex2);
                    String level = cursor2.getString(levelIndex2);
                    String pace = cursor2.getString(paceIndex2);
                    String name = cursor2.getString(nameIndex2);
                    String participants = cursor2.getString(participantIndex2);
                    String date = cursor2.getString(dateIndex2);
                    if(!name.equals("admin")){
                        Event temp2 = new Event(eventnm, age, pace, level, name, participants, date, namenm);
                        // Add the string to the list
                        eventsList.add(temp2);
                    }
                }
            } while (cursor2.moveToNext());
            cursor2.close();
        }

        //Checks for event name
        if (cursor3 != null && cursor3.moveToFirst()) {
            do {
                // Check if the columns are present in the cursor
                int eventIndex3 = cursor3.getColumnIndex(COL_EVENTNAME);
                int eventNameIndex3 = cursor3.getColumnIndex(COL_NAME);
                int ageIndex3 = cursor3.getColumnIndex(COL_AGEREQ);
                int levelIndex3 = cursor3.getColumnIndex(COL_LEVEL);
                int paceIndex3 = cursor3.getColumnIndex(COL_PACE);
                int nameIndex3 = cursor3.getColumnIndex(COL_CLUB_NAME);
                int participantIndex3 = cursor3.getColumnIndex(COL_PARTICIPANTS);
                int dateIndex3 = cursor3.getColumnIndex(COL_DATE);

                if (ageIndex3 != -1 && levelIndex3 != -1 && paceIndex3 != -1 && nameIndex3 != -1) {
                    // Extract the values from the cursor
                    String eventnm = cursor3.getString(eventIndex3);
                    String newnm = cursor3.getString(eventNameIndex3);
                    String age = cursor3.getString(ageIndex3);
                    String level = cursor3.getString(levelIndex3);
                    String pace = cursor3.getString(paceIndex3);
                    String name = cursor3.getString(nameIndex3);
                    String participants = cursor3.getString(participantIndex3);
                    String date = cursor3.getString(dateIndex3);
                    if(!name.equals("admin")){
                        Event temp3 = new Event(eventnm, age, pace, level, name, participants, date, newnm);
                        // Add the string to the list
                        eventsList.add(temp3);
                    }
                }
            } while (cursor3.moveToNext());
            cursor3.close();
        }

        // Close the database connection
        db.close();
        db2.close();
        db3.close();

        // Return the list of event details strings
        return eventsList;
    }

    // The `getNumEvents()` method returns the number of events in the database. It does this by
    // calling the `getEventList()` method, which retrieves a list of event names from the database.
    // The size of this list is then returned as the number of events.
    public int getNumEvents() {
        ArrayList<Event> eventsList = getAllEvents();
        return eventsList.size();
    }

    public boolean eventFound(String eventName, String age, String pace, String level) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns to be retrieved
        String[] columns = {COL_EVENTNAME, COL_AGEREQ, COL_LEVEL, COL_PACE};
        String selection = COL_EVENTNAME + "=? AND " +
                COL_AGEREQ + "=? AND " +
                COL_LEVEL + "=? AND " +
                COL_PACE + "=?";
        String[] selectionArgs = {eventName, age, level, pace};

        // Query the database to get the Cursor containing the selected columns
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        boolean eventFound = false;

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            // If the cursor has at least one row, the event is found
            eventFound = true;
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        // Close the database connection
        db.close();

        // Return true if the event is found, false otherwise
        return eventFound;
    }



    /**
     * The deleteEvent function deletes an event from a SQLite database based on its id and returns
     * true if the deletion was successful.
     * 
     * @param name, age, pace, level The id parameter is the unique identifier of the event that you want to delete from
     * the database.
     * @return The method is returning a boolean value. It returns true if at least one row is affected
     * (user deleted), and false otherwise.
     */
    public boolean deleteEvent(String name, String age, String pace, String level) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause and arguments
        String whereClause = COL_EVENTNAME + "=? AND " +
                COL_AGEREQ + "=? AND " +
                COL_LEVEL + "=? AND " +
                COL_PACE + "=?";
        String[] whereArgs = {name, age, level, pace};

        // Execute the delete operation
        int result = db.delete(TABLE_NAME, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Return true if at least one row is affected (user deleted), false otherwise
        return result > 0;
    }


    public boolean deleteClubEvent(String name, String age, String pace, String level, String clubName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause and arguments
        String whereClause = COL_EVENTNAME + "=? AND " +
                COL_AGEREQ + "=? AND " +
                COL_LEVEL + "=? AND " +
                COL_PACE + "=? AND "+
                COL_CLUB_NAME + "=?";
        String[] whereArgs = {name, age, level, pace, clubName};

        // Execute the delete operation
        int result = db.delete(TABLE_NAME, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Return true if at least one row is affected (user deleted), false otherwise
        return result > 0;
    }


}
