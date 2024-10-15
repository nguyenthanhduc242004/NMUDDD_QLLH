package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {
    StudentAdapter studentAdapter;
    Menu studentMenu;
    Button addButton;
    TextView tvDob;
    TextView numberOfStudentsTextView;
    ArrayList<Student> students;
    DatabaseHandler databaseHandler;
    String classId;
    ListView lvStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        numberOfStudentsTextView = findViewById(R.id.numberOfStudents);
        TextView currentClassNameTextView = findViewById(R.id.currentClassName);
        tvDob = findViewById(R.id.tv_dob);

        databaseHandler = new DatabaseHandler(this);
        classId = getIntent().getStringExtra("classId");
        students = databaseHandler.loadAllStudentInClass(classId);

        numberOfStudentsTextView.setText("Số lượng: " + students.size());
        currentClassNameTextView.setText("Lớp: " + classId);

        studentAdapter = new StudentAdapter(this, students);
        lvStudent = findViewById(R.id.studentListView);
        lvStudent.setAdapter(studentAdapter);

        addButton = findViewById(R.id.button_add_student);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentList.this, AddStudent.class);
                intent.putExtra("classId", classId);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        students = databaseHandler.loadAllStudentInClass(classId);
        studentAdapter.setStudents(students);
        studentAdapter.notifyDataSetChanged();
        numberOfStudentsTextView.setText("Số lượng: " + students.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_list_options_menu, menu);
        studentMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_select) {
            if (studentAdapter.getCheckBoxVisibility()) {
                hideCheckBoxes();
                addButton.setVisibility(View.VISIBLE);
                tvDob.setPadding(0, 0, 0, 0);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                item.setTitle("Chọn");
                                studentMenu.findItem(R.id.option_delete).setVisible(false);

                            }
                        }, 200);

            }
            else {
                showCheckBoxes();
                tvDob.setPadding(0, 0, 40, 0);
                addButton.setVisibility(View.GONE);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                item.setTitle("Bỏ chọn");
                                studentMenu.findItem(R.id.option_delete).setVisible(true);
                            }
                        }, 200);
            }

        }
        else if (item.getItemId() == R.id.option_add) {
            Intent intent = new Intent(StudentList.this, AddStudent.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.option_delete) {
            ArrayList<Integer> mssvArrayList = getSelectedStudentMssvs();
            if (mssvArrayList.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn sinh viên để xóa", Toast.LENGTH_SHORT).show();
            }
            else {
//                int[] mssvs = mssvArrayList.stream().mapToInt(i -> i).toArray();
                int numberDeleted = databaseHandler.deleteStudentsInClass(mssvArrayList, classId);
                students = databaseHandler.loadAllStudentInClass(classId);
                render(students);
                Toast.makeText(this, "Đã xóa thành công " + numberDeleted + " sinh viên", Toast.LENGTH_SHORT).show();
                hideCheckBoxes();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                studentMenu.findItem(R.id.option_select).setTitle("Chọn");
                                item.setVisible(false);

                            }
                        }, 200);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCheckBoxes() {
        studentAdapter.setCheckBoxVisibility(true);
        studentAdapter.notifyDataSetChanged();
    }

    private void hideCheckBoxes() {
        studentAdapter.setCheckBoxVisibility(false);
        studentAdapter.notifyDataSetChanged();
    }

    private ArrayList<Integer> getSelectedStudentMssvs() {
        ArrayList<Integer> selectedStudentMssvs = new ArrayList<>();
        View view;
        for (int i = 0; i < lvStudent.getCount(); i++) {
            view = lvStudent.getChildAt(i);
            CheckBox checkBox = view.findViewById(R.id.cb_student);
            if (checkBox.isChecked()) {
                selectedStudentMssvs.add(studentAdapter.getItem(i).getMSSV());
            }
        }
        return selectedStudentMssvs;
    }

    public void render(ArrayList<Student> students) {
        studentAdapter.setStudents(students);
        studentAdapter.notifyDataSetChanged();
    }
}