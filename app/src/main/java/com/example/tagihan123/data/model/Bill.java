package com.example.tagihan123.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bills")
public class Bill {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public double amount;
    public String dueDate; // tanggal dalam string
    public boolean isPaid;
    public String status;   // "Belum Bayar" / "Lunas"
    public int userId;
}
