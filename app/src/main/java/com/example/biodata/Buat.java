package com.example.biodata;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;

public class Buat extends AppCompatActivity {
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText text2, text3, text4, text5, text6, text7; // ✅ Hapus text1 (id tidak perlu diinput)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat);

        dbHelper = new DataHelper(this);

        // ✅ Hapus text1 (id)
        text2 = findViewById(R.id.editText2);
        text3 = findViewById(R.id.editText3);
        text4 = findViewById(R.id.editText4);
        text5 = findViewById(R.id.editText5);
        text6 = findViewById(R.id.editText6);
        text7 = findViewById(R.id.editText7);

        ton1 = findViewById(R.id.button1);
        ton2 = findViewById(R.id.button2);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = text2.getText().toString().trim();
                String tgl = text3.getText().toString().trim();
                String jk = text4.getText().toString().trim();
                String alamat = text5.getText().toString().trim();
                String telpon = text6.getText().toString().trim();
                String email = text7.getText().toString().trim();

                // ✅ Validasi input
                if (nama.isEmpty() || tgl.isEmpty() || jk.isEmpty() ||
                        alamat.isEmpty() || telpon.isEmpty() || email.isEmpty()) {
                    Toast.makeText(Buat.this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("nama", nama);
                values.put("tgl", tgl);
                values.put("jk", jk);
                values.put("alamat", alamat);
                values.put("telpon", telpon);
                values.put("email", email);

                long result = db.insert("biodata", null, values);
                if (result == -1) {
                    Toast.makeText(Buat.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Buat.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        ton2.setOnClickListener(v -> finish());
    }
}