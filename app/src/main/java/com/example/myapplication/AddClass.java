package com.example.myapplication;

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

import java.util.ArrayList;

public class AddClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView inputId = findViewById(R.id.inputMssv);
        TextView inputName = findViewById(R.id.inputName);
        TextView inputFaculty = findViewById(R.id.inputDob);
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = inputId.getText().toString();
                String name = inputName.getText().toString();
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                if (id.isEmpty() && name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập ID và tên lớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên lớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ArrayList<String> classIds = databaseHandler.loadAllClasseIds();
                    if (classIds.contains(id)) {
                        Toast.makeText(getApplicationContext(), "ID của lớp này đã tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                String faculty = inputFaculty.getText().toString();
                databaseHandler.addClass(new Class(id, name, faculty));
                Toast.makeText(getApplicationContext(), "Thêm lớp thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}