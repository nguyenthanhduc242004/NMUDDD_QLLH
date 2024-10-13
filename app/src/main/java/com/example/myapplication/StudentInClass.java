package com.example.myapplication;

import java.util.ArrayList;

public class StudentInClass {
    private int mssv;
    private String id;

    public StudentInClass(int mssv, String id) {
        this.mssv = mssv;
        this.id = id;
    }

    public int getMssv() {
        return mssv;
    }

    public void setMssv(int mssv) {
        this.mssv = mssv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
