package com.example.biodata;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnTambah, btnLogout;
    DataHelper dbHelper;
    ArrayList<String> daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView1);
        btnTambah = findViewById(R.id.button1);
        btnLogout = findViewById(R.id.buttonLogout);
        dbHelper = new DataHelper(this);
        daftar = new ArrayList<>();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Buat.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(MainActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        // Klik untuk lihat detail
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = daftar.get(position);
                Intent intent = new Intent(MainActivity.this, Lihat_Bio.class);
                intent.putExtra("nama", selectedName);
                startActivity(intent);
            }
        });

        // Long click untuk edit/hapus
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = daftar.get(position);
                showOptionsDialog(selectedName);
                return true;
            }
        });

        RefreshList();
    }

    private void showOptionsDialog(final String selectedName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilihan untuk: " + selectedName);
        builder.setItems(new CharSequence[]{"Edit", "Hapus"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Edit
                        Intent intent = new Intent(MainActivity.this, Update.class);
                        intent.putExtra("nama", selectedName);
                        startActivity(intent);
                        break;
                    case 1: // Hapus
                        deleteData(selectedName);
                        break;
                }
            }
        });
        builder.show();
    }

    private void deleteData(String nama) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus Data");
        builder.setMessage("Yakin hapus data " + nama + "?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int result = db.delete("biodata", "nama=?", new String[]{nama});

                if (result > 0) {
                    Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    RefreshList();
                } else {
                    Toast.makeText(MainActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Tidak", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshList();
    }

    public void RefreshList() {
        daftar.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM biodata", null);

        if (cursor.moveToFirst()) {
            do {
                daftar.add(cursor.getString(1)); // kolom nama
            } while (cursor.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, daftar);
        listView.setAdapter(adapter);
        cursor.close();
    }
}