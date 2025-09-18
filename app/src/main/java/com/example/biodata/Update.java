package com.example.biodata;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;

public class Update extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText text1, text2, text3, text4, text5, text6, text7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DataHelper(this);
        text1 = findViewById(R.id.editText1);
        text2 = findViewById(R.id.editText2);
        text3 = findViewById(R.id.editText3);
        text4 = findViewById(R.id.editText4);
        text5 = findViewById(R.id.editText5);
        text6 = findViewById(R.id.editText6);
        text7 = findViewById(R.id.editText7);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = ?",
                new String[]{getIntent().getStringExtra("nama")});

        if (cursor.moveToFirst()) {
            text1.setText(cursor.getString(0));
            text2.setText(cursor.getString(1));
            text3.setText(cursor.getString(2));
            text4.setText(cursor.getString(3));
            text5.setText(cursor.getString(4));
            text6.setText(cursor.getString(5));
            text7.setText(cursor.getString(6));
        }
        cursor.close();

        ton1 = findViewById(R.id.button1);
        ton2 = findViewById(R.id.button2);

        ton1.setOnClickListener(v -> {
            SQLiteDatabase db1 = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nama", text2.getText().toString());
            values.put("tgl", text3.getText().toString());
            values.put("jk", text4.getText().toString());
            values.put("alamat", text5.getText().toString());
            values.put("telpon", text6.getText().toString());
            values.put("email", text7.getText().toString());

            int result = db1.update("biodata", values, "id=?",
                    new String[]{text1.getText().toString()});

            if (result > 0) {
                Toast.makeText(Update.this, "Update Berhasil", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(Update.this, "Update Gagal", Toast.LENGTH_LONG).show();
            }
        });

        ton2.setOnClickListener(v -> finish());
    }
}