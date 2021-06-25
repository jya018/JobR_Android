package com.capstone.JobR;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterButtonPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_register);

        final Button employee = (Button) findViewById(R.id.employee);
        final Button student = (Button) findViewById(R.id.student);
        //회사원 가입
        employee.setOnClickListener(v -> {
            Intent registerIntent = new Intent(getApplicationContext(), NewEmployActivity.class);
            startActivity(registerIntent);
        });
        //취준생 가입
        student.setOnClickListener(v -> {
            Intent registerIntent = new Intent(getApplicationContext(), NewStudentActivity.class);
            startActivity(registerIntent);
        });

    }
}
