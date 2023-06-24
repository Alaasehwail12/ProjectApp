package com.example.projectlabandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE admin(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL,PHOTO BLOB)");
        sqLiteDatabase.execSQL("CREATE TABLE traniee(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL,PHOTO BLOB,mobile_number TEXT,ADDRESS TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE instructor(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL,PHOTO BLOB,mobile_number TEXT,ADDRESS TEXT,DEGREE TEXT,SPECIALIZATION TEXT )");
        sqLiteDatabase.execSQL("CREATE TABLE Course(CNum TEXT PRIMARY KEY, Ctitle TEXT, CTopics TEXT, prerequisites TEXT ,PHOTO BLOB )");
        sqLiteDatabase.execSQL("CREATE TABLE Course_instructor( EMAIL TEXT , Ctitle TEXT, PRIMARY KEY(EMAIL, Ctitle), FOREIGN KEY (EMAIL) REFERENCES instructor(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (Ctitle) REFERENCES Course(Ctitle))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertadmin(admin user) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM admin WHERE EMAIL = \"" + user.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", user.getEmail());
            contentValues.put("FIRSTNAME", user.getFirst_name());
            contentValues.put("LASTNAME", user.getLast_name());
            contentValues.put("PASSWORD", user.getPassword());
            contentValues.put("PHOTO", user.getBytes());
            sqLiteDatabase.insert("admin", null, contentValues);
            return true;}
        return false;
    }

    public boolean inserttrainee(trainee user) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM traniee WHERE EMAIL = \"" + user.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", user.getEmail());
            contentValues.put("FIRSTNAME", user.getFirst_name());
            contentValues.put("LASTNAME", user.getLast_name());
            contentValues.put("PASSWORD", user.getPassword());
            contentValues.put("PHOTO", user.getPhoto());
            contentValues.put("mobile_number", user.getMobile_number()); // Changed column name to "mobile_number"
            contentValues.put("ADDRESS", user.getAddress());

            sqLiteDatabase.insert("traniee", null, contentValues);
            return true;
        }
        return false;

    }


    public boolean insertinstructor(instructor user) {

        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM instructor WHERE EMAIL = \"" + user.getEmail() + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", user.getEmail());
            contentValues.put("FIRSTNAME", user.getFirst_name());
            contentValues.put("LASTNAME", user.getLast_name());
            contentValues.put("PASSWORD", user.getPassword());
            contentValues.put("PHOTO", user.getPhoto());
            contentValues.put("mobile_number", user.getMobile_number());
            contentValues.put("ADDRESS", user.getAddress());
            contentValues.put("SPECIALIZATION", user.getSpecialization());
            contentValues.put("DEGREE", user.getDegree());
            sqLiteDatabase.insert("instructor", null, contentValues);
            return true;
        }
        return false;

    }
    public void insert_instructor_courses(instructor user, String Ctitle) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM Course_instructor WHERE EMAIL = \"" + user.getEmail() + "\" AND Ctitle = \"" + Ctitle + "\";", null);
        if (!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EMAIL", user.getEmail());
            contentValues.put("Ctitle", Ctitle);
            sqLiteDatabase.insert("Course_instructor", null, contentValues);
        }
    }
    public Cursor getAllInstructors() { //read from database it returns cursor object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR", null); //null value returned when an error ocurred
    }

    public Cursor getAllAdmins() { //read from database it returns cursor object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM ADMIN", null); //null value returned when an error ocurred
    }

    public Cursor getAllTrainees() { //read from database it returns cursor object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM traniee", null); //null value returned when an error ocurred
    }

}
