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

import org.w3c.dom.Text;

public class UpdateClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvOldName = findViewById(R.id.tv_old_name);
        TextView tvOldId = findViewById(R.id.tv_old_id);
        TextView tvOldFaculty = findViewById(R.id.tv_old_faculty);

        TextView tvNewId = findViewById(R.id.tv_new_id);
        TextView tvNewName = findViewById(R.id.tv_new_name);
        TextView tvNewFaculty = findViewById(R.id.tv_new_faculty);

        Bundle bundle = getIntent().getExtras();
        String oldId  = bundle.getString("oldId");
        tvOldId.setText(oldId);
        tvOldName.setText(bundle.getString("oldName"));
        tvOldFaculty.setText("Khoa: " + bundle.getString("oldFaculty"));

        Button updateButton = findViewById(R.id.btn_update_class);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newId = tvNewId.getText().toString();
                String newName = tvNewName.getText().toString();
                if (newId.isEmpty() && newName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập ID và tên lớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (newId.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (newName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên lớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                String newFaculty = tvNewFaculty.getText().toString();
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.updateClass(oldId, new Class(newId, newName, newFaculty));
                Toast.makeText(getApplicationContext(), "Chỉnh sửa lớp thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}