package com.example.myrestaurant.yelpfusion.models;

import static com.example.myrestaurant.MainActivity.businessesDao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myrestaurant.rooms.CategoriesConverter;
import com.example.myrestaurant.rooms.LocationConverter;
import com.example.myrestaurant.rooms.TransactionsConverter;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@TypeConverters({CategoriesConverter.class, TransactionsConverter.class, LocationConverter.class})
@Entity(tableName = "business")
public class Business implements Serializable {
    @PrimaryKey (autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "is_closed")
    boolean isClosed;
    @ColumnInfo(name = "display_phone")
    public String displayPhone;
    @ColumnInfo(name = "location")
    public Location location;
    @ColumnInfo(name = "image_url")
    public String imageUrl;
    @ColumnInfo(name = "phone")
    public String phone;
    @ColumnInfo(name = "is_claimed")
    public boolean isClaimed;
    @ColumnInfo(name = "url")
    public String url;
    @TypeConverters({CategoriesConverter.class})
    @ColumnInfo(name = "categories")
    public List<Category> categories;
    @Ignore
    @ColumnInfo(name = "hours")
    public ArrayList<Hour> hours;
    @ColumnInfo(name = "distance")
    public double distance;
    @ColumnInfo(name = "text")
    public String text;
    @ColumnInfo(name = "price")
    public String price;
    @ColumnInfo(name = "review_count")
    public int reviewCount;
    @ColumnInfo(name = "rating")
    public String rating;
    @Ignore
    @ColumnInfo(name = "coordinates")
    public static Coordinates coordinates;
    @Ignore
    @ColumnInfo(name = "photos")
    public ArrayList<String> photos;
    @ColumnInfo(name = "price_int")
    public int price_int;
    @ColumnInfo(name = "transactions")
    public String[] transactions;
    @ColumnInfo(name="first")
    public String first;

    public void setTransaction1(String transaction1) {
        this.transaction1 = transaction1;
    }
    public void setTransaction2(String transaction2) {
        this.transaction2 = transaction2;
    }

    public void setTransaction3(String transaction3) {
        this.transaction3 = transaction3;
    }

    @ColumnInfo(name = "first_transaction")
    public String transaction1;
    @ColumnInfo(name = "second_transaction")
    public String transaction2;
    @ColumnInfo(name = "third_transaction")
    public String transaction3;
    public int is_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(int is_favourite) {
        this.is_favourite = is_favourite;
    }

    @ColumnInfo(name = "is_favourite")
    public int is_favourite;

    public boolean getIsClosed() {
        return this.isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    @JsonGetter("display_phone")
    public String getDisplayPhone() {
        return this.displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    @JsonGetter("id")
    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    @JsonGetter("image_url")
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonGetter("location")
    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    @JsonGetter("phone")
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonGetter("is_claimed")
    public boolean getIsClaimed() {
        return this.isClaimed;
    }

    public void setIsClaimed(boolean isClaimed) {
        this.isClaimed = isClaimed;
    }

    @JsonGetter("url")
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @JsonGetter("categories")
    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


    @JsonGetter("hours")
    public ArrayList<Hour> getHours() {
        return this.hours;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }

    @JsonGetter("distance")
    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @JsonGetter("name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("text")
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonGetter("price")
    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @JsonGetter("review_count")
    public int getReviewCount() {
        return this.reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    @JsonGetter("rating")
    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @JsonGetter("coordinates")
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonGetter("photos")
    public ArrayList<String> getPhotos() {
        return this.photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    @JsonGetter("transactions")
    public String[] getTransactions(){return this.transactions;}
    public void setTransactions(String[] transactions){this.transactions = transactions;}

    public String getTransaction1() {
        return this.transaction1;
    }
    public String getTransaction2(){return this.transaction2;}
    public String getTransaction3(){return this.transaction3;}

    public void setSubTrans(String[] transactions) {
        this.transaction1 = transactions[0];
        this.transaction2 = transactions[1];
        this.transaction3 = transactions[2];
    }

    public int getPriceInt() {
        return price_int;
    }

    public void setPrice_int(int priceInt) {
        this.price_int = priceInt;
    }

    public void setPriceToInt() {
        switch (this.getPrice()) {
            case "$":
                setPrice_int(1);
                businessesDao.update(this);
                break;
            case "$$":
                setPrice_int(2);
                businessesDao.update(this);
                break;
            case "$$$":
                setPrice_int(3);
                businessesDao.update(this);
                break;
            case "$$$$":
                setPrice_int(4);
                businessesDao.update(this);
                break;
        }
    }
}
