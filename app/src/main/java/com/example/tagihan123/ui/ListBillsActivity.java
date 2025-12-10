package com.example.tagihan123.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tagihan123.R;
import com.example.tagihan123.data.db.AppDatabase;
import com.example.tagihan123.data.model.Bill;
import com.example.tagihan123.utils.AlarmScheduler;
import com.example.tagihan123.utils.SessionManager;

import java.util.List;

public class ListBillsActivity extends AppCompatActivity {

    RecyclerView rvBills;
    Button btnCheckNow;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bills);

        try {
            session = new SessionManager(this);
            rvBills = findViewById(R.id.rvBills);
            btnCheckNow = findViewById(R.id.btnCheckNow);

            rvBills.setLayoutManager(new LinearLayoutManager(this));

            List<Bill> bills = AppDatabase.getInstance(this)
                    .billDao()
                    .getBillsByUser(session.getUserId());

            BillAdapter adapter = new BillAdapter(bills, this);
            rvBills.setAdapter(adapter);

            // Tombol untuk cek tagihan jatuh tempo sekarang
            btnCheckNow.setOnClickListener(v -> {
                AlarmScheduler.checkBillsNow(this);
                Toast.makeText(this, "Mengecek tagihan jatuh tempo...", Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
