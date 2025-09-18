package com.example.biodata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "biodatadiri.db";
    private static final int DATABASE_VERSION = 4; // ✅ Versi dinaikkan

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE biodata(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // ✅ Auto-increment
                "nama TEXT, " +
                "tgl TEXT, " +
                "jk TEXT, " +
                "alamat TEXT, " +
                "telpon TEXT, " +
                "email TEXT);";
        db.execSQL(sql);

        String userTable = "CREATE TABLE user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT);";
        db.execSQL(userTable);

        db.execSQL("INSERT INTO user(username, password) VALUES('admin','admin');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS biodata");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
}