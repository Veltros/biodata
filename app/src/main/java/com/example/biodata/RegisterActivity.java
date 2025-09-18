package com.example.biodata;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    DataHelper dbHelper;
    EditText edtUser, edtPass;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DataHelper(this);
        edtUser = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Username / Password kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", user);
            values.put("password", pass);

            long result = db.insert("user", null, values);

            if (result == -1) {
                Toast.makeText(getApplicationContext(), "❌ Registrasi gagal (username sudah ada)", Toast.LENGTH_SHORT).show();
                Log.e("REGISTER", "Insert gagal untuk user: " + user);
            } else {
                Toast.makeText(getApplicationContext(), "✅ Registrasi berhasil, silakan login", Toast.LENGTH_SHORT).show();
                Log.d("REGISTER", "Insert berhasil. ID: " + result);

                // 👉 Arahkan ke LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // tutup RegisterActivity biar ga bisa balik dengan tombol back
            }
        });
    }
}
