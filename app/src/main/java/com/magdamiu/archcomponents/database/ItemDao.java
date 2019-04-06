package com.magdamiu.archcomponents.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Query("DELETE FROM item")
    void deleteAll();

    @Delete
    void deleteItem(Item item);

    @Query("SELECT * from item LIMIT 1")
    Item[] getAnyItem();

    @Query("SELECT * from item ORDER BY name ASC")
    LiveData<List<Item>> getAllItems();

    @Update
    void update(Item... item);
}
