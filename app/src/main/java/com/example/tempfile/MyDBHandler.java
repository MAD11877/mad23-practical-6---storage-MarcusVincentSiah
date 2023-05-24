package com.example.tempfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCIPTION = "description";
    public static final String COLUMN_FOLLOWED = "followed";
    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory,
                       int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_USERS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCIPTION + " TEXT,"
                + COLUMN_FOLLOWED + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);

    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCIPTION, user.description);
        values.put(COLUMN_FOLLOWED, user.followed);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> userList = new ArrayList<User>();
        String query = "Select * From " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            User newUser = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3) == 1);
            userList.add(newUser);
        }
        db.close();
        return userList;
    }

    public void updateUser(User user){
        // Update users set followed = 1/0 where _id = xxx
        String UPDATE_VALUE = "UPDATE " + TABLE_USERS + " SET " + COLUMN_FOLLOWED + " = " + user.followed
                + " WHERE " + COLUMN_ID + " = " + user.id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UPDATE_VALUE, null);
        cursor.moveToFirst();
        db.close();
    }
}
