package com.example.tagihan123.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tagihan123.R;
import com.example.tagihan123.utils.AlarmScheduler;
import com.example.tagihan123.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    Button btnProfile, btnAdd, btnList, btnLogout;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            session = new SessionManager(this);

            // Cek apakah user sudah login
            if (session.getUserId() == -1) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }

            // Aktifkan alarm untuk cek tagihan jatuh tempo setiap hari
            AlarmScheduler.scheduleDailyCheck(this);

            btnProfile = findViewById(R.id.btnProfile);
            btnAdd = findViewById(R.id.btnAdd);
            btnList = findViewById(R.id.btnList);
            btnLogout = findViewById(R.id.btnLogout);

            btnProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
            btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddBillActivity.class)));
            btnList.setOnClickListener(v -> startActivity(new Intent(this, ListBillsActivity.class)));
            btnLogout.setOnClickListener(v -> {
                session.clearSession();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
