package com.example.myrestaurant.rooms;

import androidx.room.TypeConverter;

import com.example.myrestaurant.yelpfusion.models.Location;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class LocationConverter {
    @TypeConverter
    public static Location objectFromString(String value) {
        Type listType = new TypeToken<Location>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String stringFromObjects(Location list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
