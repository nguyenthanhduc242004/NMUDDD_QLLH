package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> students;
    LayoutInflater inflater;
    boolean checkBoxVisibility;

    public StudentAdapter(@NonNull Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
        inflater = LayoutInflater.from(context);
        this.checkBoxVisibility = false;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int i) {
        return students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.student_item, null);
        TextView studentMSSV = (TextView) view.findViewById(R.id.studentMSSV);
        TextView studentName = (TextView) view.findViewById(R.id.studentName);
        TextView studentDOB = (TextView) view.findViewById(R.id.studentDob);
        studentMSSV.setText(String.valueOf(students.get(i).getMSSV()));
        studentName.setText(students.get(i).getName());
//        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
//        studentDOB.setText(dt1.format(students.get(i).getDob()));
        studentDOB.setText(students.get(i).getDob());
        CheckBox checkBox = view.findViewById(R.id.cb_student);
        if (checkBoxVisibility) {
            checkBox.setVisibility(View.VISIBLE);
            setStudentDobPaddingRight(studentDOB,4);
        }
        else {
            checkBox.setVisibility(View.GONE);
            setStudentDobPaddingRight(studentDOB,20);
        }
        return view;
    }

    public boolean getCheckBoxVisibility() {
        return checkBoxVisibility;
    }

    public void setCheckBoxVisibility(boolean checkBoxVisibility) {
        this.checkBoxVisibility = checkBoxVisibility;
    }

    public void setStudentDobPaddingRight(TextView studentDOB, int paddingRight) {
        studentDOB.setPadding(0, 0, paddingRight, 0);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
