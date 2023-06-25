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
        sqLiteDatabase.execSQL("CREATE TABLE Course(CNum INTEGER PRIMARY KEY AUTOINCREMENT, Ctitle TEXT, CTopics TEXT, prerequisites TEXT ,PHOTO BLOB )");
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

    public void insertCourse(Course course) {
      //  SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
       // Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM Course WHERE Ctitle = \"" + course.getCtitle(), null);

     //   if (cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            int i = 0;
            //contentValues.put("CNum", i++);
            contentValues.put("Ctitle", course.getCtitle());
            contentValues.put("CTopics", course.getCTopics());
            contentValues.put("prerequisites", course.getPrerequisites());
            contentValues.put("PHOTO", course.getPhoto());
            sqLiteDatabase.insert("course", null, contentValues);

       // }
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
////////////////////////admin//////////////
    public boolean isadminRegistered(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM admin WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }

    public boolean correctadminSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM admin WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }


    public admin getadminByEmail(String email){
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        admin user = new admin();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM admin WHERE EMAIL = \"" + email + "\";", null);
        if (cursor.moveToFirst()) {
            user.setEmail(cursor.getString(0));
            user.setFirst_name(cursor.getString(1));
            user.setLast_name(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setBytes(cursor.getBlob(4));
        }
        return user;
    }

//////////////////////////trainee/////////////////////

    public boolean istraineeRegistered(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM traniee WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }


    public boolean correcttranieeSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM traniee WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }


    public trainee gettraineeByEmail(String email){
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        trainee user = new trainee();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM traniee WHERE EMAIL = \"" + email + "\";", null);
        if (cursor.moveToFirst()) {
            user.setEmail(cursor.getString(0));
            user.setFirst_name(cursor.getString(1));
            user.setLast_name(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setPhoto(cursor.getBlob(4));
            user.setMobile_number(cursor.getString(5));
            user.setAddress(cursor.getString(6));
        }
        return user;
    }


    /////////////////////////////////////instructor////////////////

    public boolean isinstructorRegistered(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\";", null);
        return cursor.moveToFirst();
    }


    public boolean correctinstructoSignIn(String email, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\" AND PASSWORD = \"" + password + "\";", null);
        return cursor.moveToFirst();
    }


    public instructor getinstructoByEmail(String email){
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        instructor user = new instructor();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM INSTRUCTOR WHERE EMAIL = \"" + email + "\";", null);
        if (cursor.moveToFirst()) {
            user.setEmail(cursor.getString(0));
            user.setFirst_name(cursor.getString(1));
            user.setLast_name(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setPhoto(cursor.getBlob(4));
            user.setMobile_number(cursor.getString(5));
            user.setAddress(cursor.getString(6));
            user.setSpecialization(cursor.getString(7));
            user.setDegree(cursor.getString(8));
        }
        return user;
    }
////////////////////////////////////////////////////////////////////////
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

    public Cursor getAllCourses() { //read from database it returns cursor object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Course", null); //null value returned when an error ocurred
    }

}
