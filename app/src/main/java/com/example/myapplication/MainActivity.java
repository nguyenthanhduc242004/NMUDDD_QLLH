package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = (ListView) findViewById(R.id.studentListView);

        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(22520271, "Nguyen Thanh Duc", "2004-04-02"));
        students.add(new Student(22528888, "Nguyen Tran Van Thien", "2004-01-01"));
        students.add(new Student(22522222, "Nguyen Tran Van Thien Lanh", "2004-02-02"));

        Class[] classList = {new Class("SE114.P11", "Nhập môn ứng dụng di động", students),
                new Class("SE104.P11", "Nhập môn Công nghệ phần mềm", students),
                new Class("SS010.P11", "Lịch sử Đảng Cộng sản Việt Nam", students)};

//        ArrayAdapter<Class> classAdapter = new ArrayAdapter<Class>(this, R.layout.list_view, classList);
//        ClassAdapter classAdapter = new ClassAdapter(getApplicationContext(), classList);
        ClassAdapter classAdapter = new ClassAdapter(this, classList);
        listView.setAdapter(classAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, StudentList.class);
                intent.putExtra("classId", classList[i].getId());
                intent.putParcelableArrayListExtra("studentList", classList[i].getStudents());
                startActivity(intent);
            }
        });


    }
}