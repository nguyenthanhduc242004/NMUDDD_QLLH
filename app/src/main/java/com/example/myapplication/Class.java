package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Class {
    private String id;
    private String name;
    private String faculty;
//    private ArrayList<Student> students;

//    public Class(String id, String name, String faculty, ArrayList<Student> students) {
//        this.id = id;
//        this.name = name;
//        this.faculty = faculty;
//        this.students = students;
//    }

    public Class(String id, String name, String faculty) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
    }

    public Class(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ArrayList<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(ArrayList<Student> students) {
//        this.students = students;
//    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

//    public int getNumberOfStudents() {
//        return students.size();
//    }
}
