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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etOldPass, etNewPass, etConfirmPass;
    Button btnSavePass;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        try {
            session = new SessionManager(this);

            etOldPass = findViewById(R.id.etOldPass);
            etNewPass = findViewById(R.id.etNewPass);
            etConfirmPass = findViewById(R.id.etConfirmPass);
            btnSavePass = findViewById(R.id.btnSavePass);

            btnSavePass.setOnClickListener(v -> {
                String old = etOldPass.getText().toString().trim();
                String newP = etNewPass.getText().toString().trim();
                String confirm = etConfirmPass.getText().toString().trim();

                if (old.isEmpty() || newP.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newP.equals(confirm)) {
                    etConfirmPass.setError("Password tidak sama!");
                    return;
                }

                try {
                    // Ambil user dari database
                    User user = AppDatabase.getInstance(this)
                            .userDao()
                            .getUserById(session.getUserId());

                    if (user == null) {
                        Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Cek password lama
                    if (!old.equals(user.password)) {
                        etOldPass.setError("Password lama salah!");
                        return;
                    }

                    // Simpan password baru
                    user.password = newP;
                    AppDatabase.getInstance(this).userDao().update(user);

                    Toast.makeText(this, "Password berhasil diganti!", Toast.LENGTH_SHORT).show();
                    finish();
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
