/**
 * The UDBHelper class is a helper class for managing a SQLite database for user information in an
 * Android application.
 */
package com.example.seg2105project.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    // The above code is declaring a private static final variable named DATABASE_NAME and assigning it
   // the value "user.db". This variable is likely being used to store the name of a database file.
    private static final String TABLE_NAME = "users";
    // The above code is declaring a private static final variable named TABLE_NAME and assigning it
    // the value "users".
    private static final String COL_ID = "id";
    // The above code is declaring a private static final variable named COL_ID with the value "id".
    private static final String COL_CLUB_NAME = "clubname";
    private static final String COL_USERNAME = "username";
    // The above code is declaring a private static final variable named COL_USERNAME with the value
    // "username".
    private static final String COL_PASSWORD = "password";
    // The above code is declaring a private static final variable named "COL_PASSWORD" with the value
    // "password".
    private static final String COL_TYPE = "type";
    // The above code is declaring a private static final variable named COL_TYPE with the value
    // "type".
    private static final String COL_CLUB = "club";
    // The above code is declaring a private static final variable named "COL_CLUB" with the value
    // "club".
    private int members;
    // The above code is declaring a private integer variable named "members".
    private int club;
    // The above code is declaring a private integer variable named "club".



    // The code is defining a constructor for a class called UDBHelper. This constructor takes a
    // parameter of type Context and calls the constructor of the superclass (SQLiteOpenHelper) with
    // the appropriate arguments. The superclass constructor is used to initialize the database name
    // and version.
    public UDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

   /**
    * The function creates a table in a SQLite database with columns for ID, username, password, club,
    * and type.
    *
    * @param udatabase The parameter "udatabase" is an instance of the SQLiteDatabase class. It
    * represents the database on which the SQL statement will be executed.
    */
    @Override
    public void onCreate(SQLiteDatabase udatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CLUB_NAME + " TEXT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_CLUB + " TEXT, " +
                COL_TYPE + " TEXT)";
        members = 0;
        club = 0;

        udatabase.execSQL(createTable);
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
     * The function addUser adds a new user to a database table and returns true if the insertion is
     * successful.
     *
     * @param username The username of the user you want to add.
     * @param password The "password" parameter is a String that represents the password for the user
     * being added.
     * @param type The "type" parameter is a String that represents the type of user being added. It
     * can have two possible values: "M" for member and "C" for club.
     * @return The method is returning a boolean value. It returns true if the data is inserted
     * successfully into the database, and false otherwise.
     */
    public boolean addUser(String clubname, String username, String password, String type) {
        if(type.equals("M")){
            members = members + 1;
        }else if(type.equals("C")){
            club = club + 1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CLUB_NAME, clubname);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_TYPE, type);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Returns true if data is inserted successfully
    }


    /**
     * The function checks if a given username exists in a database table called "users".
     *
     * @param username The username parameter is a string that represents the username to be validated.
     * @return The method is returning a boolean value. It returns true if there is at least one row in
     * the "users" table with the specified username, and false otherwise.
     */
    public boolean validateUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[] {username});
        return cursor.getCount() > 0;

    }
    /**
     * The function checks if a given username and password combination exists in the "users" table of
     * a SQLite database.
     *
     * @param username The username parameter is a String that represents the username of the user
     * whose password needs to be validated.
     * @param password The password parameter is a String that represents the password entered by the
     * user.
     * @return The method is returning a boolean value. It returns true if there is a match for the
     * given username and password in the "users" table of the database, and false otherwise.
     */
    public boolean validateUserPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        return cursor.getCount() > 0;

    }

    /**
     * The function retrieves the user type from a database based on the provided username and
     * password.
     *
     * @param username The username is a String parameter that represents the username of the user
     * trying to log in.
     * @param password The password parameter is the password entered by the user.
     * @return The method is returning the user type as a String.
     */
    public String userType(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userType = null;

        String[] columns = {COL_TYPE};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_TYPE);
            if (columnIndex != -1) {
                userType = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return userType;
    }

    public String getClubName(String username) {
        if (username != null) {
            SQLiteDatabase db = this.getReadableDatabase();
            String clubName = null;

            String[] columns = {COL_CLUB_NAME};
            String selection = COL_USERNAME + "=?";
            String[] selectionArgs = {username};

            Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COL_CLUB_NAME);
                if (columnIndex != -1) {
                    clubName = cursor.getString(columnIndex);
                }
                cursor.close();
            }

            db.close();

            return clubName;
        }
        return "FAILED";
    }



    /**
    * The function retrieves a list of usernames from a database table where the type is "M" (member).
    * 
    * @return The method is returning an ArrayList of Strings, which contains the usernames of members
    * from a database table.
    */
    public ArrayList<String> getMemberList(){
        ArrayList<String> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_USERNAME};
        String selection = COL_TYPE + "=?";
        String[] selectionArgs = {"M"};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(COL_USERNAME);
            if(columnIndex != -1){
                do{
                    String entry = cursor.getString(columnIndex);
                    entries.add(entry);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return entries;
    }
    /**
     * The function retrieves a list of club names from a database table.
     * 
     * @return The method is returning an ArrayList of Strings, which contains the usernames of all the
     * entries in the database table where the type is "C" (presumably representing clubs).
     */
    public ArrayList<String> getClubList(){
        ArrayList<String> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_USERNAME};
        String selection = COL_TYPE + "=?";
        String[] selectionArgs = {"C"};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(COL_USERNAME);
            if(columnIndex != -1){
                do{
                    String entry = cursor.getString(columnIndex);
                    entries.add(entry);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return entries;
    }

    /**
     * The function retrieves a list of members in a specific club from a database.
     * 
     * @param clubName The clubName parameter is a String that represents the name of the club for
     * which we want to retrieve the members.
     * @return The method is returning an ArrayList of Strings, which contains the usernames of members
     * in a specific club.
     */
    public ArrayList<String> getMembersInClub(String clubName) {
        ArrayList<String> membersInClub = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_USERNAME};
        String selection = COL_TYPE + "=? AND " + COL_CLUB + "=?";
        String[] selectionArgs = {"M", clubName};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_USERNAME);
            if (columnIndex != -1) {
                do {
                    String entry = cursor.getString(columnIndex);
                    membersInClub.add(entry);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return membersInClub;
    }

    /**
     * The function returns the number of members in a club.
     * 
     * @param clubName The clubName parameter is a String that represents the name of the club for
     * which we want to retrieve the number of members.
     * @return The number of members in the specified club.
     */
    public int getclubMemberNum(String clubName){
        return getMembersInClub(clubName).size();
    }


    /**
     * The function returns the value of the variable "members".
     * 
     * @return The method is returning the value of the variable "members".
     */
    public int getAllMembers() {
        return getMemberList().size();
    }

   /**
    * The function "getNumClubs" returns the value of the variable "club".
    * 
    * @return The method is returning the value of the variable "club".
    */
    public int getNumClubs() {
        ArrayList<String> club = getClubList();
        return club.size();
    }

    /**
     * The function deletes a row from a SQLite database table based on the provided username.
     * 
     * @param username The username parameter is a String that represents the username of the user you
     * want to delete from the database.
     * @return The method is returning a boolean value. It returns true if at least one row is affected
     * (user deleted), and false otherwise.
     */
    public boolean delete(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause and arguments
        String whereClause = COL_USERNAME + "=?";
        String[] whereArgs = {username};

        // Execute the delete operation
        int result = db.delete(TABLE_NAME, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Return true if at least one row is affected (user deleted), false otherwise
        return result > 0;
    }
   /**
    * The `clubEnrol` function updates the club name for a given username in a database table and
    * returns true if the update is successful.
    * 
    * @param username The username is a String that represents the username of the user who wants to
    * enroll in a club.
    * @param clubName The name of the club that the user wants to enroll in.
    * @return The method is returning a boolean value. It returns true if at least one row is affected
    * (club added), and false otherwise.
    */
    public boolean clubEnrol(String username, String clubName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to hold the new values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CLUB, clubName);

        // Define the where clause and arguments
        String whereClause = COL_USERNAME + "=?";
        String[] whereArgs = {username};

        // Execute the update operation
        int result = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Return true if at least one row is affected (club added), false otherwise
        return result > 0;
    }

/**
 * The function retrieves the user type from a SQLite database based on the provided username.
 * 
 * @param username The username parameter is a String that represents the username of the user whose
 * type we want to retrieve from the database.
 * @return The method is returning the user type as a String.
 */

    public String getUserType(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userType = null;

        String[] columns = {COL_TYPE};
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_TYPE);
            if (columnIndex != -1) {
                userType = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return userType;
    }



    /**
     * The function retrieves the club associated with a given username from a SQLite database.
     * 
     * @param username The username of the user for whom you want to retrieve the club.
     * @return The method is returning the user's club as a String.
     */
    public String getUserClub(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userClub = null;

        String[] columns = {COL_CLUB};
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_CLUB);
            if (columnIndex != -1) {
                userClub = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return userClub;
    }


}