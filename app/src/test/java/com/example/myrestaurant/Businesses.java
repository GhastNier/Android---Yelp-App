package com.example.myrestaurant;

import static com.example.myrestaurant.yelpfusion.models.Business.coordinates;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.myrestaurant.rooms.CategoriesConverter;
import com.example.myrestaurant.rooms.TransactionsConverter;
import com.example.myrestaurant.yelpfusion.models.Business;
import com.example.myrestaurant.yelpfusion.models.Category;
import com.example.myrestaurant.yelpfusion.models.Coordinates;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName = "businesses")
public class Businesses {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "uID")
    public String uID;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name="is_closed")
    public boolean isClosed;

    @ColumnInfo(name = "display_phone")
    public String displayPhone;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @ColumnInfo(name = "rating")
    public double rating;

    @ColumnInfo(name = "address1")
    public String address1;

    @ColumnInfo(name = "address2")
    public String address2;

    @ColumnInfo(name = "address3")
    public String address3;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "price_int")
    public int price_int;

    @TypeConverters({CategoriesConverter.class})
    @ColumnInfo(name = "categories")
    public ArrayList<Object> category;

    @TypeConverters({TransactionsConverter.class})
    @ColumnInfo(name = "transactions")
    public String[] transactions;

    @ColumnInfo(name = "restURL")
    public String restURL;

    @ColumnInfo(name = "is_favorite")
    public boolean is_favorite;

}

