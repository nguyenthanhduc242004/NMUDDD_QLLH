package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Student implements Parcelable {
    private int MSSV;
    private String name;
    private String dob;

    public Student(int MSSV, String hoTen, String dob) {
        this.MSSV = MSSV;
        this.name = hoTen;
        this.dob = dob;
    }

    protected Student(Parcel in) {
        MSSV = in.readInt();
        name = in.readString();
//        dob = (Date) in.readSerializable();
        dob = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getMSSV() {
        return MSSV;
    }

    public void setMSSV(int MSSV) {
        this.MSSV = MSSV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(MSSV);
        parcel.writeString(name);
        parcel.writeString(dob);
    }
}
