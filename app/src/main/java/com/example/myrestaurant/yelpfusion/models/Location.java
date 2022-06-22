package com.example.myrestaurant.yelpfusion.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myrestaurant.rooms.CategoriesConverter;
import com.example.myrestaurant.rooms.LocationConverter;
import com.example.myrestaurant.rooms.TransactionsConverter;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;
@TypeConverters({CategoriesConverter.class, TransactionsConverter.class, LocationConverter.class})
@Entity(tableName = "locations")
public class Location implements Serializable {
    @PrimaryKey (autoGenerate = true)
    public int id;
    @ColumnInfo (name ="uID")
    public String uID;
    @ColumnInfo(name = "address1")
    public String address1;
    @ColumnInfo(name = "address2")
    public String address2;
    @ColumnInfo(name = "address3")
    public String address3;
    @ColumnInfo(name = "cross_streets")
    public String crossStreets;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "display_address")
    public String[] displayAddress;
    @ColumnInfo(name ="zip_code")
    public String zipCode;

    public String getuID() {
        return uID;
    }
    public void setuID(String uID) {
        this.uID = uID;
    }
    @JsonGetter("state")
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }


    @JsonGetter("address3")
    public String getAddress3() {
        return this.address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }


    @JsonGetter("cross_streets")
    public String getCrossStreets() {
        return this.crossStreets;
    }

    public void setCrossStreets(String crossStreets) {
        this.crossStreets = crossStreets;
    }


    @JsonGetter("address2")
    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }


    @JsonGetter("zip_code")
    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    @JsonGetter("city")
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @JsonGetter("country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonGetter("address1")
    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }


    @JsonGetter("display_address")
    public String[] getDisplayAddress() {
        return this.displayAddress;
    }

    public void setDisplayAddress(String[] displayAddress) {
        this.displayAddress = displayAddress;
    }


}
