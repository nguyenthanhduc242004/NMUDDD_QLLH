package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<Class> classes;
    LayoutInflater inflater;
    private boolean checkBoxVisibility;

    public ClassAdapter(@NonNull Context context, ArrayList<Class> classes) {
        this.context = context;
        this.classes = classes;
        inflater = LayoutInflater.from(context);
        this.checkBoxVisibility = false;
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Class getItem(int i) {
        return classes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.class_item, null);
        TextView classId = view.findViewById(R.id.classId);
        TextView className = view.findViewById(R.id.className);
        TextView classFaculty = view.findViewById(R.id.classFaculty);
        classId.setText(classes.get(i).getId());
        className.setText(classes.get(i).getName());
        String faculty = classes.get(i).getFaculty();
        CheckBox checkBox = view.findViewById(R.id.cb_class);
        if (faculty != null && !faculty.isEmpty()) {
            classFaculty.setText("Khoa: " + classes.get(i).getFaculty());
        }
        else {
            classFaculty.setVisibility(View.GONE);
            classId.setPadding(0, 4, 0, 4);
            className.setPadding(0, 4, 0, 4);
            checkBox.setPadding(0, 4, 0, 4);

        }
        if (checkBoxVisibility) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }
        return view;
    }

    public void setCheckBoxVisibility(boolean visibility) {
        this.checkBoxVisibility = visibility;
    }

    public boolean getCheckBoxVisibility() {
        return checkBoxVisibility;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }
}
