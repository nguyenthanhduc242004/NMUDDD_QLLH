package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "classManager";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CLASS = "classes";
    public static final String COLUMN_CLASS_ID = "id";
    public static final String COLUMN_CLASS_NAME = "name";
    public static final String COLUMN_CLASS_FACULTY = "faculty";
    public static final String SQL_CREATE_TABLE_CLASS = "CREATE TABLE " + TABLE_CLASS + "(" + COLUMN_CLASS_ID + " TEXT PRIMARY KEY, " + COLUMN_CLASS_NAME + " TEXT, " + COLUMN_CLASS_FACULTY + " TEXT" + ")";
    public static final String SQL_DELETE_TABLE_CLASS = "DROP TABLE IF EXISTS " + TABLE_CLASS;

    private static final String TABLE_STUDENT = "students";
    public static final String COLUMN_STUDENT_MSSV = "mssv";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_DOB = "dob";
    public static final String SQL_CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + "(" + COLUMN_STUDENT_MSSV + " INTEGER PRIMARY KEY, " + COLUMN_STUDENT_NAME + " TEXT, " + COLUMN_STUDENT_DOB + " TEXT" + ")";
    public static final String SQL_DELETE_TABLE_STUDENT = "DROP TABLE IF EXISTS " + TABLE_STUDENT;

    private static final String TABLE_STUDENT_IN_CLASS = "studentInClass";
    public static final String SQL_CREATE_TABLE_STUDENT_IN_CLASS = "CREATE TABLE " + TABLE_STUDENT_IN_CLASS + "(" + COLUMN_STUDENT_MSSV + ", " + COLUMN_CLASS_ID + ", " + "PRIMARY KEY(" + COLUMN_STUDENT_MSSV + ", " + COLUMN_CLASS_ID + "))";
    public static final String SQL_DELETE_TABLE_STUDENT_IN_CLASS = "DROP TABLE IF EXISTS " + TABLE_STUDENT_IN_CLASS;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //    onCreate(): Đây là nơi để chúng ta viết những câu lệnh tạo bảng. Nó được gọi khi database đã được tạo.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CLASS);
        db.execSQL(SQL_CREATE_TABLE_STUDENT);
        db.execSQL(SQL_CREATE_TABLE_STUDENT_IN_CLASS);
    }

    //    onUpgrade(): Nó được gọi khi database được nâng cấp, ví dụ như chỉnh sửa cấu trúc các bảng, thêm những thay đổi cho database,..
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_CLASS);
        db.execSQL(SQL_DELETE_TABLE_STUDENT);
        db.execSQL(SQL_DELETE_TABLE_STUDENT_IN_CLASS);
        onCreate(db);
    }



//    CLASS CRUD: Begin
    public void addClass(Class myClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_ID, myClass.getId());
        values.put(COLUMN_CLASS_NAME, myClass.getName());
        values.put(COLUMN_CLASS_FACULTY, myClass.getFaculty());

        db.insert(TABLE_CLASS, null, values);
        db.close();
    }

    public boolean updateClass(String id, Class myClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_ID, myClass.getId());
        values.put(COLUMN_CLASS_NAME, myClass.getName());
        values.put(COLUMN_CLASS_FACULTY, myClass.getFaculty());
        int rowAffected = db.update(TABLE_CLASS, values, COLUMN_CLASS_ID + "= ?", new String[]{id});
        db.close();
        return rowAffected > 0;
    }

    public int deleteClasses(String[] ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = String.format(COLUMN_CLASS_ID + " in (%s)", new Object[] { TextUtils.join(",", Collections.nCopies(ids.length, "?")) });
        int rowAffected = db.delete(TABLE_CLASS, whereClause, ids);
        db.close();
        return rowAffected;
    }

    public ArrayList<Class> loadAllClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_CLASS_ID,
                COLUMN_CLASS_NAME,
                COLUMN_CLASS_FACULTY
        };
        Cursor cursor = db.query(TABLE_CLASS, projection, null, null, null, null, null);
        ArrayList<Class> classes = new ArrayList<Class>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String faculty = cursor.getString(2);
            classes.add(new Class(id, name, faculty));
        }
        db.close();
        return classes;
    }
    public ArrayList<String> loadAllClasseIds() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_CLASS_ID
        };
        Cursor cursor = db.query(TABLE_CLASS, projection, null, null, null, null, null);
        ArrayList<String> classIds = new ArrayList<String>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            classIds.add(id);
        }
        db.close();
        return classIds;
    }

    public Class getClassById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_CLASS_ID,
                COLUMN_CLASS_NAME,
                COLUMN_CLASS_FACULTY
        };
        Cursor cursor = db.query(TABLE_CLASS, projection, COLUMN_CLASS_ID + "= ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String faculty = cursor.getString(2);
            db.close();
            return new Class(id, name, faculty);
        }
        return null;
    }
//    CLASS CRUD: End



//    STUDENT CRUD: Begin
    public void addStudent(Student student, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues studentValues = new ContentValues();
        studentValues.put(COLUMN_STUDENT_MSSV, student.getMSSV());
        studentValues.put(COLUMN_STUDENT_NAME, student.getName());
        studentValues.put(COLUMN_STUDENT_DOB, student.getDob());
        db.insert(TABLE_STUDENT, null, studentValues);

        ContentValues studentInClassValues = new ContentValues();
        studentInClassValues.put(COLUMN_STUDENT_MSSV, student.getMSSV());
        studentInClassValues.put(COLUMN_CLASS_ID, id);
        db.insert(TABLE_STUDENT_IN_CLASS, null, studentInClassValues);

        db.close();
    }

//    public boolean updateStudent(Student student) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_STUDENT_MSSV, student.getMSSV());
//        values.put(COLUMN_STUDENT_NAME, student.getName());
//        values.put(COLUMN_STUDENT_DOB, student.getDob());
//        int rowAffected = db.update(TABLE_STUDENT, values, COLUMN_STUDENT_MSSV + "= ?", new String[]{String.valueOf(student.getMSSV())});
//        db.close();
//        return rowAffected > 0;
//    }

    public boolean deleteStudent(int mssv) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowAffected = db.delete(TABLE_STUDENT, COLUMN_STUDENT_MSSV + "= ?", new String[]{String.valueOf(mssv)});
        db.close();
        return rowAffected > 0;
    }

    public int deleteAllStudentsInClass(String[] ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = String.format(COLUMN_CLASS_ID + " in (%s)", new Object[] { TextUtils.join(",", Collections.nCopies(ids.length, "?")) });
        int rowAffected = db.delete(TABLE_STUDENT_IN_CLASS, whereClause, ids);
        db.close();
        return rowAffected;
    }

    public ArrayList<Student> loadAllStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_STUDENT_MSSV,
                COLUMN_STUDENT_NAME,
                COLUMN_STUDENT_DOB
        };
        Cursor cursor = db.query(TABLE_STUDENT, projection, null, null, null, null, null);
        ArrayList<Student> students = new ArrayList<Student>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String dob = cursor.getString(2);
            students.add(new Student(id, name, dob));
        }
        db.close();
        return students;
    }

    public ArrayList<Student> loadAllStudentInClass(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_STUDENT_MSSV,
                COLUMN_STUDENT_NAME,
                COLUMN_STUDENT_DOB
        };
        String query = "SELECT S." + COLUMN_STUDENT_MSSV + ", " + COLUMN_STUDENT_NAME + ", " + COLUMN_STUDENT_DOB
                + " FROM " + TABLE_STUDENT + " AS S, " + TABLE_STUDENT_IN_CLASS + " AS SIC"
                + " WHERE SIC." + COLUMN_CLASS_ID + " = ? AND " + "SIC." + COLUMN_STUDENT_MSSV + " = S." + COLUMN_STUDENT_MSSV;
        Cursor cursor = db.rawQuery(query, new String[]{id});
        ArrayList<Student> students = new ArrayList<Student>();
        while (cursor.moveToNext()) {
            int mssv = cursor.getInt(0);
            String name = cursor.getString(1);
            String dob = cursor.getString(2);
            students.add(new Student(mssv, name, dob));
        }
        db.close();
        return students;
    }

    public Student getStudentByMssv(int mssv){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_STUDENT_MSSV,
                COLUMN_STUDENT_NAME,
                COLUMN_STUDENT_DOB
        };
        Cursor cursor = db.query(TABLE_STUDENT, projection, COLUMN_STUDENT_MSSV + "= ?", new String[]{String.valueOf(mssv)}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String dob = cursor.getString(2);
            db.close();
            return new Student(mssv, name, dob);
        }
        return null;
    }
//    STUDENT CRUD: END



//    STUDENT_IN_CLASS CRUD: Begin
    public void addStudentInClass(StudentInClass studentInClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_MSSV, studentInClass.getMssv());
        values.put(COLUMN_CLASS_ID, studentInClass.getId());

        db.insert(TABLE_STUDENT_IN_CLASS, null, values);
        db.close();
    }

    public boolean updateStudentInClass(Student student, Class myClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_MSSV, student.getMSSV());
        values.put(COLUMN_CLASS_ID, myClass.getId());
        int rowAffected = db.update(TABLE_STUDENT_IN_CLASS, values, COLUMN_STUDENT_MSSV + "= ? OR " + COLUMN_CLASS_ID + "= ?", new String[]{String.valueOf(student.getMSSV()), myClass.getId()});
        db.close();
        return rowAffected > 0;
    }

    public int deleteStudentsInClass(ArrayList<Integer> mssvs, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

//         Convert each integer to a string

        int count = 0;
        for (int i = 0; i < mssvs.size(); i++) {
            count += db.delete(TABLE_STUDENT_IN_CLASS,COLUMN_CLASS_ID + " = ? AND " + COLUMN_STUDENT_MSSV + " = ?", new String[]{id, String.valueOf(mssvs.get(i))});
        }
        db.close();
        return count;
    }

    public ArrayList<StudentInClass> loadAllStudentInClass() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_STUDENT_MSSV,
                COLUMN_CLASS_ID
        };
        Cursor cursor = db.query(TABLE_STUDENT_IN_CLASS, projection, null, null, null, null, null);
        ArrayList<StudentInClass> studentsInClasses = new ArrayList<StudentInClass>();
        while (cursor.moveToNext()) {
            int mssv = cursor.getInt(0);
            String id = cursor.getString(1);
            studentsInClasses.add(new StudentInClass(mssv, id));
        }
        db.close();
        return studentsInClasses;
    }

    public ArrayList<String> getClassIdsContainStudent(int mssv){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_CLASS_ID
        };
        Cursor cursor = db.query(TABLE_STUDENT_IN_CLASS, projection, COLUMN_STUDENT_MSSV + "= ?", new String[]{String.valueOf(mssv)}, null, null, null);
        ArrayList<String> classIdsContainStudent = new ArrayList<String>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            classIdsContainStudent.add(id);
        }
        db.close();
        return classIdsContainStudent;
    }

    public ArrayList<Integer> getStudentsMssvInClass(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_STUDENT_MSSV
        };
        Cursor cursor = db.query(TABLE_STUDENT_IN_CLASS, projection, COLUMN_CLASS_ID + "= ?", new String[]{id}, null, null, null);
        ArrayList<Integer> studentsMssvInClass = new ArrayList<Integer>();
        while (cursor.moveToNext()) {
            int mssv = cursor.getInt(0);
            studentsMssvInClass.add(mssv);
        }
        db.close();
        return studentsMssvInClass;
    }
//    STUDENT_IN_CLASS CRUD: End
}




