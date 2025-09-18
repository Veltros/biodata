package com.example.biodata;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Lihat_Bio extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton2;
    TextView text1, text2, text3, text4, text5, text6, text7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_bio);

        dbHelper = new DataHelper(this);
        text1 = findViewById(R.id.textView1);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);
        text4 = findViewById(R.id.textView4);
        text5 = findViewById(R.id.textView5);
        text6 = findViewById(R.id.textView6);
        text7 = findViewById(R.id.textView7);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // ✅ PERBAIKAN: Gunakan parameterized query untuk hindari SQL Injection
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = ?",
                new String[]{getIntent().getStringExtra("nama")});

        if (cursor.moveToFirst()) {
            text1.setText(cursor.getString(0)); // id
            text2.setText(cursor.getString(1)); // nama
            text3.setText(cursor.getString(2)); // tgl
            text4.setText(cursor.getString(3)); // jk
            text5.setText(cursor.getString(4)); // alamat
            text6.setText(cursor.getString(5)); // telpon
            text7.setText(cursor.getString(6)); // email
        }

        // ✅ Jangan lupa close cursor untuk hindari memory leak
        cursor.close();

        ton2 = findViewById(R.id.button1);
        ton2.setOnClickListener(v -> finish());
    }
}