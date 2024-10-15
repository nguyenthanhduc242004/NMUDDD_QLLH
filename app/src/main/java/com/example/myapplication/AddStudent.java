package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddStudent extends AppCompatActivity {
    String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        classId = getIntent().getStringExtra("classId");
        TextView inputMssv = findViewById(R.id.inputMssv);
        TextView inputName = findViewById(R.id.inputName);
        TextView inputDob = findViewById(R.id.inputDob);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mssvString = inputMssv.getText().toString();
                String name = inputName.getText().toString();
                String dob = inputDob.getText().toString();
                if (mssvString.isEmpty() || name.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                int mssv;
                try {
                    mssv = Integer.parseInt(mssvString);
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "MSSV phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.addStudent(new Student(mssv, name, dob));
                databaseHandler.addStudentInClass(new StudentInClass(mssv, classId));
                Toast.makeText(getApplicationContext(), "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}