package com.example.fundoonotes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fundoonotes.data.model.LoggedInUser;


public class DataBaseTools extends SQLiteOpenHelper {
    private final static int DB_Version = 10;

    public DataBaseTools(Context context) {
        super(context, "Register.db",null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table login (userId Integer primary key autoincrement, username text unique, " +
                "firstName text, lastName text, password text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            System.out.println("Upgrade DB oldVersion="+oldVersion+" - newVersion="+newVersion);
            onCreate(db);
            if (oldVersion<10) {
                String query = "create table login (userId Integer primary key autoincrement, username text unique, " +
                        "firstName text, lastName text, password text)";
                db.execSQL(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);
        System.out.println("DOWNGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
    }

    public LoggedInUser insertUser(LoggedInUser queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", queryValues.getUsername());
        values.put("firstName", queryValues.getFirstName());
        values.put("lastName", queryValues.getLastName());
        values.put("password", queryValues.getPassword());
        queryValues.userId = (database.insert("login", null, values));
        database.close();
        return queryValues;
    }


    public LoggedInUser getUser(String username) {
        String query = "Select * from login where username ='"+username+"'";
        LoggedInUser myUser = new LoggedInUser(username,"", "", "");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            myUser.userId = (cursor.getLong(cursor.getColumnIndex("userId")));
            myUser.firstName = (cursor.getString(cursor.getColumnIndex("firstName")));
            myUser.lastName = (cursor.getString(cursor.getColumnIndex("lastName")));
            myUser.password = (cursor.getString(cursor.getColumnIndex("password")));
            cursor.moveToNext();
        }
        return myUser;
    }
}
