package com.example.tagihan123.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tagihan123.R;
import com.example.tagihan123.data.db.AppDatabase;
import com.example.tagihan123.data.model.Bill;
import com.example.tagihan123.utils.SessionManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBillActivity extends AppCompatActivity {

    EditText etBillName, etBillAmount;
    Button btnPickDate, btnSaveBill;
    TextView tvSelectedDate;
    RadioGroup rgPaymentStatus;
    RadioButton rbBelumBayar, rbSudahBayar;
    String selectedDate = "";
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        try {
            etBillName = findViewById(R.id.etBillName);
            etBillAmount = findViewById(R.id.etBillAmount);
            btnPickDate = findViewById(R.id.btnPickDate);
            btnSaveBill = findViewById(R.id.btnSaveBill);
            tvSelectedDate = findViewById(R.id.tvSelectedDate);
            rgPaymentStatus = findViewById(R.id.rgPaymentStatus);
            rbBelumBayar = findViewById(R.id.rbBelumBayar);
            rbSudahBayar = findViewById(R.id.rbSudahBayar);

            session = new SessionManager(this);

            // PILIH TANGGAL
            btnPickDate.setOnClickListener(v -> {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(this,
                        (view, y, m, d) -> {
                            Calendar chosen = Calendar.getInstance();
                            chosen.set(y, m, d);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            selectedDate = sdf.format(chosen.getTime());
                            tvSelectedDate.setText(selectedDate);
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                ).show();
            });

            // SIMPAN TAGIHAN
            btnSaveBill.setOnClickListener(v -> {
                String name = etBillName.getText().toString().trim();
                String amountStr = etBillAmount.getText().toString().trim();

                if (name.isEmpty() || amountStr.isEmpty() || selectedDate.isEmpty()) {
                    Toast.makeText(this, "Lengkapi semua field!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double amount = Double.parseDouble(amountStr);

                    // Cek status pembayaran dari RadioButton
                    boolean isPaid = rbSudahBayar.isChecked();
                    String status = isPaid ? "Lunas" : "Belum Bayar";

                    Bill bill = new Bill();
                    bill.name = name;
                    bill.amount = amount;
                    bill.dueDate = selectedDate;
                    bill.isPaid = isPaid;
                    bill.status = status;
                    bill.userId = session.getUserId();

                    AppDatabase.getInstance(this).billDao().insert(bill);

                    Toast.makeText(this, "Tagihan tersimpan!", Toast.LENGTH_SHORT).show();
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
