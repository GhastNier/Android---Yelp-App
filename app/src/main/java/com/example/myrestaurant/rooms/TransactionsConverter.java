package com.example.myrestaurant.rooms;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class TransactionsConverter {
    @TypeConverter
    public static String[] arrayFromString(String value) {
        Type listType = new TypeToken<String[]>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String stringFromArray(String[] list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
