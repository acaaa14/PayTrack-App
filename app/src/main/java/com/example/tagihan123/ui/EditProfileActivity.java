package com.example.tagihan123.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tagihan123.R;
import com.example.tagihan123.data.db.AppDatabase;
import com.example.tagihan123.data.model.User;
import com.example.tagihan123.utils.SessionManager;

public class EditProfileActivity extends AppCompatActivity {

    EditText etName, etPhone;
    Button btnSaveProfile;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        try {
            session = new SessionManager(this);

            etName = findViewById(R.id.etName);
            etPhone = findViewById(R.id.etPhone);
            btnSaveProfile = findViewById(R.id.btnSaveProfile);

            // Load data dari database
            User user = AppDatabase.getInstance(this).userDao().getUserById(session.getUserId());
            if (user != null) {
                etName.setText(user.name);
                etPhone.setText(user.phone);
            }

            btnSaveProfile.setOnClickListener(v -> {
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(this, "Nama dan No HP tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Update ke database
                    User user1 = AppDatabase.getInstance(this).userDao().getUserById(session.getUserId());
                    if (user1 != null) {
                        user1.name = name;
                        user1.phone = phone;
                        AppDatabase.getInstance(this).userDao().update(user1);

                        Toast.makeText(this, "Profil berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
