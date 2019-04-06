package com.magdamiu.archcomponents.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "item")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    public Item(@NonNull String name) {
        this.mName = name;
    }

    @Ignore
    public Item(int id, @NonNull String name) {
        this.id = id;
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
