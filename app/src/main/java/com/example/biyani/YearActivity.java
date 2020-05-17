package com.example.biyani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class YearActivity extends AppCompatActivity {
 TextView year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        year=findViewById(R.id.year);
        getSupportActionBar().setTitle("Select Year");
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YearActivity.this,PackageActivity.class));
            }
        });
    }
}
