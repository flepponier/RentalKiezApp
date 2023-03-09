package de.app.rentalkiezapp.database;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import de.app.rentalkiezapp.entity.RentObject;

public class DataSourceRentables extends SQLiteOpenHelper {

    public static final String RENTABLES_TABLE = "RENTABLES_TABLE";
    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TAKEN = "taken";
    public static final String CREATE_RENTABLES_TABLE = "CREATE TABLE "+ RENTABLES_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_IMAGE + " TEXT, " + COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " + COLUMN_TAKEN + " INT)";


    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "zipcode";
    public static final String COLUMN_STREET = "zipcode";
    public static final String COLUMN_ZIPCODE = "zipcode";
    public static final String COLUMN_CITY = "zipcode";


    public static final String CREATE_USER_TABLE = "CREATE TABLE USERS_TABLE (" +
            COLUMN_EMAIL+" TEXT PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRSTNAME+" TEXT, " +
            COLUMN_LASTNAME+" TEXT, " +
            COLUMN_STREET+" TEXT, " +
            COLUMN_ZIPCODE+"  INTEGER, " +
            COLUMN_CITY+" TEXT" +
            ")";



    @SuppressLint("RestrictedApi")
    public DataSourceRentables(@Nullable Context context) {
        super(context, "RENTALKIEZ.db", null, 2);
        Log.d(LOG_TAG, "DbHelper created database: " + getDatabaseName());
    }

    //called first time database is created
    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_RENTABLES_TABLE);
            Log.d(LOG_TAG, "Table was created using the following query: " + CREATE_RENTABLES_TABLE);
            db.execSQL(CREATE_USER_TABLE);
            Log.d(LOG_TAG, "Table was created using the following query: " + CREATE_USER_TABLE);

        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "an error occurred while trying to create table: " + ex.getMessage());
        }
        //addAllRentables ();
    }

    //called if database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<RentObject> getUserEntries(String emailUser){
        ArrayList<RentObject> returnList;

        SQLiteDatabase db = getReadableDatabase();

        String[] columns ={COLUMN_ID, COLUMN_IMAGE, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_EMAIL, COLUMN_TAKEN};
        String selection= COLUMN_EMAIL +"= ? COLLATE NOCASE";
        String[] selectionArgs = {emailUser};

        Cursor cursor = db.query(RENTABLES_TABLE, columns, selection, selectionArgs, null, null, null);
        //get results from Database
       // Cursor cursor = db.rawQuery(query, null);

        //assign values of each row of results to a RentObject and add to List
        returnList=fillList(cursor);

        //close cursor and db
        cursor.close();
        db.close();

        return returnList;
    }

    public ArrayList<RentObject> getTextSearchEntries(String search){
        ArrayList<RentObject> returnList;

        String[] columns ={COLUMN_ID, COLUMN_IMAGE, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_EMAIL, COLUMN_TAKEN};
        String selection= COLUMN_DESCRIPTION +" LIKE ? COLLATE NOCASE";
        String[] selectionArgs = {"%"+search+"%"};
        SQLiteDatabase db = getReadableDatabase();

        //get results from Database
        Cursor cursor = db.query(RENTABLES_TABLE, columns, selection, selectionArgs, null, null, null);

        //fill List with RentObjects
        returnList=fillList(cursor);

        //close cursor and db
        cursor.close();
        db.close();

        return returnList;
    }

    @SuppressLint("RestrictedApi")
    public ArrayList<RentObject> getZipcodeSearchEntries(int zipcode){
        ArrayList<RentObject> returnList= new ArrayList<>();
        SQLiteDatabase db;


        //FIRST STEP: Get all emails from USERS_TABLE with matching zipcode
        db = getReadableDatabase();
        String[] columnsEmail ={COLUMN_EMAIL};
        String selectionEmail= COLUMN_ZIPCODE +" LIKE ?";
        String[] selectionArgsEmail = {String.valueOf(zipcode)};

        //1.1.:get results from database
        Cursor cursorEmail = db.query(USERS_TABLE, columnsEmail, selectionEmail, selectionArgsEmail, null, null, null);

        //1.2.: add email-value of each row to selectionArgsRent if there are results -> to prepare follow up database query
        if(cursorEmail.moveToFirst()){
            String[] selectionArgsRent = new String[cursorEmail.getCount()+1];
            selectionArgsRent[0]="0";
            int i = 1;
            do {
                selectionArgsRent[i]=cursorEmail.getString(0);
                i++;
            } while (cursorEmail.moveToNext());

            String[] columnsRent ={COLUMN_ID, COLUMN_IMAGE, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_EMAIL, COLUMN_TAKEN};

            //prepare WHERE-Statement
            String selectionRent= COLUMN_TAKEN+"= ? AND ("+COLUMN_EMAIL +" = ? ";
            //String selectionAlternative= "taken = ? AND (email= ? )";
            for(int j=0;j<selectionArgsRent.length-2;j++){
                selectionRent+="OR " +COLUMN_EMAIL +" = ? ";
            }
            selectionRent+=")";

            //2.2. get results from database
            Cursor cursorRentObjects = db.query(RENTABLES_TABLE, columnsRent, selectionRent, selectionArgsRent, null, null, null);
            //2.3. fill List with RentObjects
            returnList=fillList(cursorRentObjects);

            cursorRentObjects.close();
        }


        //Second Step: Get RentObjects with matching email
        cursorEmail.close();

        db.close();

        return returnList;
        }



    public boolean updateTaken(int idRentObject, boolean newtaken){
        //String query = "UPDATE "+RENTABLES_TABLE+" SET "+TAKEN+" ="+!taken+" WHERE id="+idRentObject;
        //String query = "UPDATE RENTABLES_TABLE SET taken =false WHERE id=1";
        //db.execSQL(query);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TAKEN, newtaken);
        db.update(RENTABLES_TABLE, values, COLUMN_ID +"="+idRentObject,null);

        db.close();
        return true;
    }

    //assign values of each row of result to a RentObject and add to List
    private ArrayList<RentObject> fillList(Cursor cursor){
        ArrayList<RentObject> returnList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                RentObject rentObject = new RentObject();
                rentObject.setId(cursor.getInt(0));
                rentObject.setImageReference(cursor.getString(1));
                rentObject.setTitle(cursor.getString(2));
                rentObject.setDescription(cursor.getString(3));
                rentObject.setEmail(cursor.getString(4));
                rentObject.setTaken(cursor.getInt(5) == 1 ? true : false);

                returnList.add(rentObject);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return returnList;
    }


    public ArrayList<RentObject> getSpinnerEntries(String[] args) {
        ArrayList<RentObject> returnList = new ArrayList<>();
        String queryRentObjects;
        SQLiteDatabase db;


        //FIRST STEP: Get all emails from USERS_TABLE with matching zipcode
        db = getReadableDatabase();
        String[] columnsEmail ={COLUMN_EMAIL};
        String selectionEmail= COLUMN_ZIPCODE +" BETWEEN ? AND ?";
        String[] selectionArgsEmail = args;

        //1.1.:get results from database
        Cursor cursorEmail = db.query(USERS_TABLE, columnsEmail, selectionEmail, selectionArgsEmail, null, null, null);

        //1.2.: add email-value of each row to selectionArgsRent if there are results -> to prepare follow up database query
        if(cursorEmail.moveToFirst()){
            String[] selectionArgsRent = new String[cursorEmail.getCount()+1];
            selectionArgsRent[0]="0";
            int i = 1;
            do {
                selectionArgsRent[i]=cursorEmail.getString(0);
                i++;
            } while (cursorEmail.moveToNext());

            String[] columnsRent ={COLUMN_ID, COLUMN_IMAGE, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_EMAIL, COLUMN_TAKEN};

            //prepare WHERE-Statement
            String selectionRent= COLUMN_TAKEN+"= ? AND ("+COLUMN_EMAIL +" = ? ";
            //String selectionAlternative= "taken = ? AND (email= ? )";
            for(int j=0;j<selectionArgsRent.length-2;j++){
                selectionRent+="OR " +COLUMN_EMAIL +" = ? ";
            }
            selectionRent+=")";

            //2.2. get results from database
            Cursor cursorRentObjects = db.query(RENTABLES_TABLE, columnsRent, selectionRent, selectionArgsRent, null, null, null);
            //2.3. fill List with RentObjects
            returnList=fillList(cursorRentObjects);

            cursorRentObjects.close();
        }


        //Second Step: Get RentObjects with matching email
        cursorEmail.close();

        db.close();

        return returnList;
    }
}
