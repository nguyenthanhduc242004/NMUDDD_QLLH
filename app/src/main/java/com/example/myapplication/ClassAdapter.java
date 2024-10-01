package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ClassAdapter extends BaseAdapter {
    Context context;
    Class[] classes;
    LayoutInflater inflater;
    public ClassAdapter(@NonNull Context context, Class[] classes) {
        this.context = context;
        this.classes = classes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return classes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.class_item, null);
        TextView classId = (TextView) view.findViewById(R.id.classId);
        TextView className = (TextView) view.findViewById(R.id.className);
        classId.setText(classes[i].getId());
        className.setText(classes[i].getName());
        return view;
    }
}
