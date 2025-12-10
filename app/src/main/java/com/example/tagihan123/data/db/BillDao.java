package com.example.tagihan123.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tagihan123.data.model.Bill;

import java.util.List;

@Dao
public interface BillDao {

    @Insert
    long insert(Bill bill);

    @Query("SELECT * FROM bills WHERE userId = :userId ORDER BY dueDate ASC")
    List<Bill> getBillsByUser(int userId);

    @Query("SELECT * FROM bills")
    List<Bill> getAllBills();

    @Update
    void update(Bill bill);

    @Delete
    void delete(Bill bill);
}
