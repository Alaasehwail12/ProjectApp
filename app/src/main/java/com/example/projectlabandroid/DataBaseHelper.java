package com.example.projectlabandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE admin(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL,PHOTO BLOB)");
        sqLiteDatabase.execSQL("CREATE TABLE traniee(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL,PHOTO BLOB,mobile_number TEXT,ADDRESS TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE instructor(EMAIL TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT NOT NULL,PHOTO BLOB,mobile_number TEXT,ADDRESS TEXT,DEGREE TEXT,SPECIALIZATION TEXT )");
        sqLiteDatabase.execSQL("CREATE TABLE Course(CNum INTEGER  PRIMARY KEY AUTOINCREMENT, Ctitle TEXT PRIMARY KEY, CTopics TEXT, prerequisites TEXT, PHOTO BLOB, DEADLINECOURSE DATE, COURSESTARTDATE DATE, SCHEDULE_COURSE TIME, COURSEVENUE TEXT)");
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Course");
        sqLiteDatabase.execSQL("CREATE TABLE available_Course(CNum INTEGER PRIMARY KEY , Ctitle TEXT,Name TEXT ,CTopics TEXT, prerequisites TEXT, PHOTO BLOB, DEADLINECOURSE DATE, COURSESTARTDATE DATE, SCHEDULE_COURSE TIME, COURSEVENUE TEXT, FOREIGN KEY (CNum) REFERENCES Course(CNum))");

        sqLiteDatabase.execSQL("CREATE TABLE Course_instructor( EMAIL TEXT , Ctitle TEXT, PRIMARY KEY(EMAIL, Ctitle), FOREIGN KEY (EMAIL) REFERENCES instructor(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (Ctitle) REFERENCES Course(Ctitle))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<String> make_course_avalabile(String courseTitle) {
        List<String> instructorNames = new ArrayList<>();

        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        String sql_query = "SELECT * FROM Course_instructor " +
                "JOIN Course ON Course_instructor.Ctitle = Course.Ctitle " +
                "WHERE Course.Ctitle = ?";

        String sql_query2 = "SELECT FIRSTNAME, LASTNAME FROM instructor " +
                "JOIN Course_instructor ON Course_instructor.EMAIL = instructor.EMAIL " +
                "JOIN Course ON Course_instructor.Ctitle = Course.Ctitle " +
                "WHERE Course.Ctitle = ?";

        Cursor cursor = sqLiteDatabaseR.rawQuery(sql_query2, new String[]{courseTitle});

        while (cursor.moveToNext()) {
            String instructorName = cursor.getString(0) +" "+ cursor.getString(1);
            instructorNames.add(instructorName);
        }

        cursor.close();
        return instructorNames;
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


    public boolean insertCourse(Course course) {

        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM Course WHERE Ctitle = \"" + course.getCtitle() + "\";", null);
        if(!cursor.moveToFirst()) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Ctitle", course.getCtitle());
            contentValues.put("CTopics", course.getCTopics());
            contentValues.put("prerequisites", course.getPrerequisites());
            contentValues.put("PHOTO", course.getPhoto());
            contentValues.put("DEADLINECOURSE", course.getDeadline());
            contentValues.put("COURSESTARTDATE", course.getStartDateCourse());
            contentValues.put("SCHEDULE_COURSE", course.getSchedule());
            contentValues.put("COURSEVENUE", course.getVenue());

            long newRowId = sqLiteDatabase.insert("Course", null, contentValues);
            course.setCNum(String.valueOf(newRowId)); // Set the generated CNum value in the Course object
            return true;
        }
        return false;
    }

    public void insertavailableCourse(Course course,String name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("CNum", course.getCNum());
        contentValues.put("Ctitle", course.getCtitle());
        contentValues.put("Name", name);
        contentValues.put("CTopics", course.getCTopics());
        contentValues.put("prerequisites", course.getPrerequisites());
        contentValues.put("PHOTO", course.getPhoto());
        contentValues.put("DEADLINECOURSE", course.getDeadline());
        contentValues.put("COURSESTARTDATE",course.getStartDateCourse());
        contentValues.put("SCHEDULE_COURSE", course.getSchedule());
        contentValues.put("COURSEVENUE", course.getVenue());

        sqLiteDatabase.insert("available_Course", null, contentValues);
        // course.setCNum(String.valueOf(newRowId)); // Set the generated CNum value in the Course object
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

    public Course getAllCoursesbytitel(String title) { //read from database it returns cursor object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Course WHERE Course.Ctitle = ?", new String[]{title});
        Course course = null;

        if (cursor != null && cursor.moveToFirst()) {
            //  String courseTitle = course.getCtitle();
            course = new Course(title);

            String courseDescription = cursor.getString(2);
            String prerequisites = cursor.getString(3);
            byte []  PHOTO =  cursor.getBlob(4);
            String DEADLINECOURSE = cursor.getString(5);
            String COURSESTARTDATE = cursor.getString(6);
            String SCHEDULE_COURSE = cursor.getString(7);
            String COURSEVENUE = cursor.getString(8);

            course.setCTopics(courseDescription);
            course.setPrerequisites(prerequisites);
            course.setPhoto(PHOTO);
            course.setDeadline(DEADLINECOURSE);
            course.setStartDateCourse(COURSESTARTDATE);
            course.setSchedule(SCHEDULE_COURSE);
            course.setVenue(COURSEVENUE);

            //   course = new Course(title, courseDescription,prerequisites,PHOTO,DEADLINECOURSE,COURSESTARTDATE,SCHEDULE_COURSE,COURSEVENUE);
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }
        return course;
    }


    public Cursor getAllAvailableCourses() { //read from database it returns cursor object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM available_Course ", null); //null value returned when an error ocurred
    }


    public Cursor getAllinstroucter_course() { //read from database it returns cursor object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Course_instructor", null); //null value returned when an error ocurred
    }

    public void removeCoursebyCnum(int id) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM Course "+ ";", null);
        if (cursor.moveToFirst()) {
            sqLiteDatabase.execSQL("DELETE FROM Course WHERE CNum = " + id +  ";");
        }
    }

    public void removeavailableCoursebyCnum(int id) {
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabaseR.rawQuery("SELECT * FROM available_Course "+ ";", null);
        if (cursor.moveToFirst()) {
            sqLiteDatabase.execSQL("DELETE FROM available_Course WHERE CNum = " + id +  ";");
        }
    }

    public void editCoursebyCnum(Course old, Course newCourse,int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE COURSE SET  CTitle = ?, CTopics = ?, prerequisites = ?, photo = ?," +
                " DEADLINECOURSE = ?,COURSESTARTDATE = ?,SCHEDULE_COURSE = ? , COURSEVENUE = ? WHERE CNum = ?";
        db.execSQL(sql, new String[]{newCourse.getCtitle(), newCourse.getCTopics(),
                newCourse.getPrerequisites(), String.valueOf(newCourse.getPhoto()),newCourse.getDeadline(),newCourse.getStartDateCourse(),
                newCourse.getSchedule(),newCourse.getVenue(), String.valueOf(id)});
    }
}

