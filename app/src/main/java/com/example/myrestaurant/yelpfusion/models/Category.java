package com.example.myrestaurant.yelpfusion.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myrestaurant.rooms.CategoriesConverter;
import com.example.myrestaurant.rooms.TransactionsConverter;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;
@TypeConverters({CategoriesConverter.class, TransactionsConverter.class})
@Entity(tableName = "category")
public class Category implements Serializable {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public String getuID() { return uID; }

    public void setuID(String uID) {
        this.uID = uID;
    }
    @ColumnInfo(name="first")
    public String uID;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "alias")
    public String alias;

    @JsonGetter("title")
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonGetter("alias")
    public String getAlias() {
        return this.alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }


}
