package com.example.biodata;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView txt = findViewById(R.id.txtWelcome);
        String username = getIntent().getStringExtra("username");
        txt.setText("Selamat datang, " + username + "!");
    }
}
