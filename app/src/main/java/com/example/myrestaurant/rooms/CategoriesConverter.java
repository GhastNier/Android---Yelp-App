package com.example.myrestaurant.rooms;

import androidx.room.TypeConverter;

import com.example.myrestaurant.yelpfusion.models.Category;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriesConverter {
    @TypeConverter
    public static List<Category> objectFromString(String value) {
        Type listType = new TypeToken<List<Category>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String stringFromObjects(List<Category> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
