package com.venak.exhangerates.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.venak.exhangerates.model.ConvertHistory;

import java.util.List;

@Dao
public interface ConvertHistoryDao {
    @Query("SELECT * FROM ConvertHistory")
    List<ConvertHistory> getAll();

    @Query("SELECT * FROM ConvertHistory WHERE ConvertHistory.`from` LIKE :key LIMIT 1")
    List<ConvertHistory> findByKey(String key);

    @Insert
    void insertAll(List<ConvertHistory> convertHistories);

    @Insert
    void insert(ConvertHistory convertHistory);

    @Update
    void update(ConvertHistory convertHistory);


    @Delete
    void delete(ConvertHistory convertHistory);
}
