package com.example.lotters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class registerActivity extends AppCompatActivity {

    private EditText numeRegister, passwordRegister, emailRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        numeRegister = findViewById(R.id.nume);
        passwordRegister = findViewById(R.id.registerPassword);
        emailRegister = findViewById(R.id.email);

    }
}