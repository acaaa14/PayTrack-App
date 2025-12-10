package com.example.tagihan123.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tagihan123.R;
import com.example.tagihan123.data.db.AppDatabase;
import com.example.tagihan123.data.model.Bill;

import java.text.DateFormat;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    List<Bill> bills;
    Context ctx;

    public BillAdapter(List<Bill> bills, Context ctx) {
        this.bills = bills;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(ctx).inflate(R.layout.item_bill, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Bill b = bills.get(pos);

        h.tvBillName.setText(b.name);
        h.tvBillAmount.setText("Rp " + String.format("%.0f", b.amount));
        h.tvBillDate.setText("Jatuh Tempo: " + b.dueDate);

        // Update status berdasarkan isPaid
        if (b.isPaid) {
            b.status = "Lunas";
            h.tvBillStatus.setText("Status: Lunas");
            h.tvBillStatus.setTextColor(0xFF4CAF50); // Hijau
        } else {
            b.status = "Belum Bayar";
            h.tvBillStatus.setText("Status: Belum Bayar");
            h.tvBillStatus.setTextColor(0xFFD32F2F); // Merah
        }

        h.btnUpdate.setOnClickListener(v -> {
            // Dialog untuk pilih status
            new AlertDialog.Builder(ctx)
                    .setTitle("Ubah Status Pembayaran")
                    .setItems(new String[]{"Belum Bayar", "Sudah Bayar (Lunas)"}, (dialog, which) -> {
                        if (which == 0) {
                            // Belum Bayar
                            b.isPaid = false;
                            b.status = "Belum Bayar";
                        } else {
                            // Sudah Bayar
                            b.isPaid = true;
                            b.status = "Lunas";
                        }
                        AppDatabase.getInstance(ctx).billDao().update(b);
                        notifyItemChanged(pos);
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        h.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(ctx)
                    .setTitle("Hapus Tagihan")
                    .setMessage("Apakah Anda yakin?")
                    .setPositiveButton("Hapus", (d, w) -> {
                        AppDatabase.getInstance(ctx).billDao().delete(b);
                        bills.remove(pos);
                        notifyItemRemoved(pos);
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBillName, tvBillAmount, tvBillDate, tvBillStatus;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBillName = itemView.findViewById(R.id.tvBillName);
            tvBillAmount = itemView.findViewById(R.id.tvBillAmount);
            tvBillDate = itemView.findViewById(R.id.tvBillDate);
            tvBillStatus = itemView.findViewById(R.id.tvBillStatus);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
