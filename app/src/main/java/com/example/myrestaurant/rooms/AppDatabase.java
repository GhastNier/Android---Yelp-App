package com.example.myrestaurant.rooms;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myrestaurant.yelpfusion.models.Business;
import com.example.myrestaurant.yelpfusion.models.Category;
import com.example.myrestaurant.yelpfusion.models.Location;
@TypeConverters({TransactionsConverter.class, CategoriesConverter.class})
@Database(entities = {Business.class, Location.class, Category.class}, version = 2)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract BusinessesDao businessesDao();
    private static volatile AppDatabase instance = null;
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class,"businesses.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    }

