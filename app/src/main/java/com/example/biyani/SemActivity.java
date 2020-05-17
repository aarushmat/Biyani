package com.example.biyani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem);
        getSupportActionBar().setTitle("Select Semester");
    }
}
