package com.example.tagihan123.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tagihan123.data.db.AppDatabase;
import com.example.tagihan123.data.model.Bill;
import com.example.tagihan123.utils.NotificationHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DailyCheckReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DailyCheckReceiver", "Checking bills for today...");

        // Ambil tanggal hari ini
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String today = sdf.format(Calendar.getInstance().getTime());

        // Ambil semua tagihan dari database
        List<Bill> allBills = AppDatabase.getInstance(context).billDao().getAllBills();

        int unpaidCount = 0;

        for (Bill bill : allBills) {
            // Cek jika tagihan jatuh tempo hari ini dan belum dibayar
            if (bill.dueDate != null && bill.dueDate.equals(today) && !bill.isPaid) {
                unpaidCount++;

                // Kirim notifikasi untuk setiap tagihan
                NotificationHelper.showNotification(
                    context,
                    "Tagihan Jatuh Tempo Hari Ini!",
                    bill.name + " - Rp " + String.format("%.0f", bill.amount) + " harus segera dibayar!"
                );
            }
        }

        // Jika ada banyak tagihan, kirim notifikasi ringkasan
        if (unpaidCount > 1) {
            NotificationHelper.showNotification(
                context,
                "Peringatan Tagihan!",
                "Anda memiliki " + unpaidCount + " tagihan yang jatuh tempo hari ini!"
            );
        }

        Log.d("DailyCheckReceiver", "Found " + unpaidCount + " bills due today");
    }
}
