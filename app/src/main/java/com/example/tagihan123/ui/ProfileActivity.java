package com.example.tagihan123.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tagihan123.R;
import com.example.tagihan123.data.db.AppDatabase;
import com.example.tagihan123.data.model.User;
import com.example.tagihan123.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhone;
    Button btnEditProfile, btnChangePassword;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try {
            session = new SessionManager(this);

            tvName = findViewById(R.id.tvName);
            tvEmail = findViewById(R.id.tvEmail);
            tvPhone = findViewById(R.id.tvPhone);
            btnEditProfile = findViewById(R.id.btnEditProfile);
            btnChangePassword = findViewById(R.id.btnChangePassword);

            // 🔹 AMBIL DATA USER DARI DATABASE
            User user = AppDatabase.getInstance(this).userDao().getUserById(session.getUserId());

            if (user != null) {
                tvName.setText("Nama: " + user.name);
                tvEmail.setText("Email: " + user.email);
                tvPhone.setText("No HP: " + user.phone);
            }

            // 🔹 KETIKA "UBAH PROFIL" DIKLIK
            btnEditProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            });

            // 🔹 KETIKA "UBAH PASSWORD" DIKLIK
            btnChangePassword.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            // Reload profile bila selesai mengedit
            User user = AppDatabase.getInstance(this).userDao().getUserById(session.getUserId());

            if (user != null) {
                tvName.setText("Nama: " + user.name);
                tvEmail.setText("Email: " + user.email);
                tvPhone.setText("No HP: " + user.phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
