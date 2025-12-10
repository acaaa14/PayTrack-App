package com.example.tagihan123.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tagihan123.utils.NotificationHelper;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String nama = intent.getStringExtra("nama");
        String tanggal = intent.getStringExtra("tanggal");

        NotificationHelper.showNotification(
                context,
                "Tagihan Jatuh Tempo!",
                nama + " jatuh tempo pada " + tanggal

        );
    }
}
