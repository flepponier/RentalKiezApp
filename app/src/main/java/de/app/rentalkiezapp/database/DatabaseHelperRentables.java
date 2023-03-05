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

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String RENTABLES_TABLE = "RENTABLES_TABLE";
    public static final String ID = "id";
    public static final String IMAGE = "image";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String STATE = "state";
    public static final String EMAIL = "email";
    public static final String TAKEN = "taken";
    public static final String SQL_CREATE = "CREATE TABLE RENTABLES_TABLE_V1 " + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            IMAGE + " TEXT, " + TITLE + " TEXT, " + DESCRIPTION + " TEXT, " + STATE + " TEXT, \n" +
            EMAIL + " TEXT, " + TAKEN + " INT)";




    @SuppressLint("RestrictedApi")
    public DatabaseHelper(@Nullable Context context) {
        super(context, "RENTALKIEZ.db", null, 1);
        Log.d(LOG_TAG, "DbHelper created database: " + getDatabaseName());
    }

    //called first time database is created
    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Table was created using the following query: " + SQL_CREATE);
            db.execSQL(SQL_CREATE);
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

    public boolean addAllRentables (){
        DatabaseDataForTableRentables object = new DatabaseDataForTableRentables();
        List<RentObject> list = new ArrayList<>();
        list = object.getRentablesArray();
        RentObject rentObject = list.get(1);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


            cv.put(IMAGE, rentObject.getImageReference());
            cv.put(TITLE, rentObject.getTitle());
            cv.put(DESCRIPTION, rentObject.getDescription());
            cv.put(STATE, rentObject.getState());
            cv.put(EMAIL, rentObject.getEmail());
            cv.put(TAKEN, rentObject.getTaken());

            long insert = db.insert(RENTABLES_TABLE, null, cv);
            db.close();
            if(insert==-1){
                return false;
            }
            else{
                return true;
            }

    }

    public ArrayList<RentObject> getUserEntries(String email){
        ArrayList<RentObject> returnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+RENTABLES_TABLE+" WHERE UPPER(email)=UPPER('florian.eppe@web.de')";

        //get results from Database
        Cursor cursor = db.rawQuery(query, null);

        //assign values of each row of results to a RentObject and add to List
        if(cursor.moveToFirst()){
            do {
                RentObject rentObject = new RentObject();
                rentObject.setId(cursor.getInt(0));
                rentObject.setImageReference(cursor.getString(1));
                rentObject.setTitle(cursor.getString(2));
                rentObject.setDescription(cursor.getString(3));
                rentObject.setState(cursor.getString(4));
                rentObject.setEmail(cursor.getString(5));
                rentObject.setTaken(cursor.getInt(6) == 1 ? true : false);

                returnList.add(rentObject);
            }while(cursor.moveToNext());
            }

        //close cursor and db
        cursor.close();
        db.close();

        return returnList;
    }

    public ArrayList<RentObject> getTextSearchEntries(String search){
        ArrayList<RentObject> returnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+RENTABLES_TABLE+" WHERE title LIKE '% "+search+"%'";

        //get results from Database
        Cursor cursor = db.rawQuery(query, null);

        //assign values of each row of results to a RentObject and add to List
        if(cursor.moveToFirst()){
            do {
                RentObject rentObject = new RentObject();
                rentObject.setId(cursor.getInt(0));
                rentObject.setImageReference(cursor.getString(1));
                rentObject.setTitle(cursor.getString(2));
                rentObject.setDescription(cursor.getString(3));
                rentObject.setState(cursor.getString(4));
                rentObject.setEmail(cursor.getString(5));
                rentObject.setTaken(cursor.getInt(6) == 1 ? true : false);

                returnList.add(rentObject);
            }while(cursor.moveToNext());
        }

        //close cursor and db
        cursor.close();
        db.close();

        return returnList;
    }

}
