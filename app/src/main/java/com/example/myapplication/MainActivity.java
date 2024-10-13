package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ClassAdapter classAdapter;
    Button addButton;
    Menu classMenu;
    ArrayList<Class> classes;
    DatabaseHandler databaseHandler;
    ListView lvClass;

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

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar_color)));

        databaseHandler = new DatabaseHandler(this);

////        Adding students to db
//        databaseHandler.addStudent(new Student(22520271, "Nguyen Thanh Duc", "2004-04-02"));
//        databaseHandler.addStudent(new Student(22528888, "Nguyen Tran Van Thien", "2004-01-01"));
//        databaseHandler.addStudent(new Student(22522222, "Nguyen Tran Van Thien Lanh", "2004-02-02"));
//        databaseHandler.addStudent(new Student(22520529, "Châu Ngọc Trầm Hương", "2004-03-03"));
//
////        Adding classes to db
//        databaseHandler.addClass(new Class("SE114.P11", "Nhập môn ứng dụng di động", "CNPM"));
//        databaseHandler.addClass(new Class("SE104.P11", "Nhập môn Công nghệ phần mềm", "CNPM"));
////        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! null
//        databaseHandler.addClass(new Class("IT008.P11", "Lập trình trực quan", null));
        databaseHandler.addClass(new Class("IT008.P112", "Lập trình trực quan2", null));
//
////        Adding student_in_class to db
//        databaseHandler.addStudentInClass(new StudentInClass(22520271, "SE114.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22520271, "SE104.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22520271, "IT008.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22528888, "SE114.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22528888, "SE104.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22522222, "SE114.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22522222, "IT008.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22520529, "SE104.P11"));
//        databaseHandler.addStudentInClass(new StudentInClass(22520529, "IT008.P11"));

        lvClass = findViewById(R.id.studentListView);
        classes = databaseHandler.loadAllClasses();
        classAdapter = new ClassAdapter(this, classes);
        lvClass.setAdapter(classAdapter);
        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, StudentList.class);
                intent.putExtra("classId", classAdapter.getItem(i).getId());
                startActivity(intent);
            }
        });

        lvClass.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, UpdateClass.class);
                Class oldClass = classAdapter.getItem(i);
                intent.putExtra("oldId", oldClass.getId());
                intent.putExtra("oldName", oldClass.getName());
                intent.putExtra("oldFaculty", oldClass.getFaculty());
                startActivity(intent);
                return true;
            }
        });

        addButton = findViewById(R.id.button_add_class);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddClass.class);
                startActivity(intent);
            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();
        classes = databaseHandler.loadAllClasses();
        render(classes);
    }

    public void render(ArrayList<Class> contacts) {
        classAdapter.setClasses(contacts);
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.class_list_options_menu, menu);
        classMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_select) {
            if (classAdapter.getCheckBoxVisibility()) {
                hideCheckBoxes();
                addButton.setVisibility(View.VISIBLE);

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                item.setTitle("Chọn");
                                classMenu.findItem(R.id.option_delete).setVisible(false);

                            }
                        }, 200);

            }
            else {
                showCheckBoxes();
                addButton.setVisibility(View.GONE);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                item.setTitle("Bỏ chọn");
                                classMenu.findItem(R.id.option_delete).setVisible(true);
                            }
                        }, 200);
            }

        }
        else if (item.getItemId() == R.id.option_add) {
            Intent intent = new Intent(MainActivity.this, AddClass.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.option_delete) {
            ArrayList<String> idsArrayList = getSelectedClassIds();
            if (idsArrayList.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn lớp để xóa", Toast.LENGTH_SHORT).show();
            }
            else {
                String[] ids = idsArrayList.toArray(new String[idsArrayList.size()]);
                int numberDeleted = databaseHandler.deleteClasses(ids);
                classes = databaseHandler.loadAllClasses();
                render(classes);
                databaseHandler.deleteAllStudentsInClass(ids);
                Toast.makeText(this, "Đã xóa thành công " + numberDeleted + " lớp học", Toast.LENGTH_SHORT).show();
                hideCheckBoxes();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                classMenu.findItem(R.id.option_select).setTitle("Chọn");
                                item.setVisible(false);

                            }
                        }, 200);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void showCheckBoxes() {
        classAdapter.setCheckBoxVisibility(true);
        classAdapter.notifyDataSetChanged();
    }

    private void hideCheckBoxes() {
        classAdapter.setCheckBoxVisibility(false);
        classAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> getSelectedClassIds() {
        ArrayList<String> selectedClassIds = new ArrayList<>();
        View view;
        for (int i = 0; i < lvClass.getCount(); i++) {
            view = lvClass.getChildAt(i);
            CheckBox checkBox = view.findViewById(R.id.cb_class);
            if (checkBox.isChecked()) {
                selectedClassIds.add(classAdapter.getItem(i).getId());
            }
        }
        return selectedClassIds;
    }
}