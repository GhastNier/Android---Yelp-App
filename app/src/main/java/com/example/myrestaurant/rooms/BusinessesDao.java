package com.example.myrestaurant.rooms;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myrestaurant.yelpfusion.models.Business;
import com.example.myrestaurant.yelpfusion.models.Category;
import com.example.myrestaurant.yelpfusion.models.Location;

import java.util.List;

@Dao
public interface BusinessesDao {

    @Query("SELECT * FROM business WHERE is_favourite IS 0 ORDER BY is_favourite DESC")
    List<Business> getAll();

    @Query("SELECT * FROM business WHERE first IS :uID")
    List<Business> getBusiness(String uID);

    @Query("Select * FROM business WHERE is_favourite IS 0 ORDER BY name ASC")
    List<Business> orderByNameAsc();

    @Query("Select * FROM business WHERE is_favourite IS 0 ORDER BY rating ASC")
    List<Business> orderByRatingAsc();

    @Query("Select * FROM business WHERE is_favourite IS 0 ORDER BY price_int ASC")
    List<Business> orderByPriceAsc();

    @Query("Select * FROM business WHERE is_favourite IS 0 ORDER BY name DESC")
    List<Business> orderByNameDesc();

    @Query("Select * FROM business WHERE is_favourite IS 0 ORDER BY rating DESC")
    List<Business> orderByRatingDesc();

    @Query("Select * FROM business WHERE is_favourite IS 0 ORDER BY price_int DESC")
    List<Business> orderByPriceDesc();

    @Query("Delete from category WHERE first = :uID")
    void deleteCategory(String uID);
    @Query("DELETE FROM business WHERE is_favourite IS 0")
    void deleteBusinesses();
    @Query("Select * FROM business WHERE is_favourite IS 1")
    List<Business> getFavourite();
    @Insert
    void insertBusiness(List<Business> business);
    @Insert
    void insertCategories(Category categories);
    @Insert
    void insertLocations(Location locations);
    @Update
    void update(Business business);
    @Delete
    void deleteBus(Business business);
    @Delete
    void deleteCat(Category category);
    @Delete
    void deleteLoc(Location location);
}
