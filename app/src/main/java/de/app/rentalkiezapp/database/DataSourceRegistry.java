package de.app.rentalkiezapp.database;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import de.app.rentalkiezapp.entity.User;

public class DataSourceRegistry extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String STREET = "street";
    public static final String ZIPCODE = "zipcode";
    public static final String CITY = "city";
    public static final String TAKEN = "taken";
    public static final String SQL_CREATE = "CREATE TABLE UZERZ " + " (" + EMAIL + " TEXT PRIMARY KEY, \n" +
            FIRSTNAME + " TEXT, " + LASTNAME + " TEXT, " + STREET + " TEXT, \n" +
            ZIPCODE + " INTEGER, " + CITY + " TEXT)";


    private Context context;

    @SuppressLint("RestrictedApi")
    public DataSourceRegistry(@Nullable Context context) {
        super(context, "RENTALKIEZ.db", null, 1);
        this.context=context;
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
    }

    //called if database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser (User user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

            cv.put(EMAIL, user.getEmail());
            cv.put(FIRSTNAME, user.getFirstname());
            cv.put(LASTNAME, user.getLastname());
            cv.put(STREET, user.getStreet());
            cv.put(ZIPCODE, user.getZipcode());
            cv.put(CITY, user.getCity());

            long insert = db.insert(USERS_TABLE, null, cv);
            db.close();
            if(insert==-1){
                return false;
            }
            else{
                return true;
            }

    }
}
