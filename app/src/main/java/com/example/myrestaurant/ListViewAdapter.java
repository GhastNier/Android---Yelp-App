package com.example.myrestaurant;

import static com.example.myrestaurant.MainActivity.businessesDao;
import static com.example.myrestaurant.MainActivity.limit;
import static com.example.myrestaurant.MainActivity.restCat;
import static com.example.myrestaurant.MainActivity.restLoc;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.myrestaurant.rooms.ApiConst;
import com.example.myrestaurant.yelpfusion.connection.YelpFusionApi;
import com.example.myrestaurant.yelpfusion.connection.YelpFusionApiFactory;
import com.example.myrestaurant.yelpfusion.models.Business;
import com.example.myrestaurant.yelpfusion.models.Category;
import com.example.myrestaurant.yelpfusion.models.Location;
import com.example.myrestaurant.yelpfusion.models.SearchResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewAdapter extends BaseAdapter {
    private static YelpFusionApi yelpFusionApi;
    private static Context context;
    private static List<Business> businessesList;
    private TextView mRestName, mCategories, mRestAddress, mPhoneNumber, mRestAddress2;
    private ImageView mFav, mRestBanner, mDelivery, mStar1, mStar2, mStar3, mStar4, mStar5, mPickup, mBooking, mPrice1, mPrice2, mPrice3, mPrice4;
    private Business business;
    private Location location;
    private static ListView listView;

    public ListViewAdapter(Context mContext, List<Business> mList) {
        context = mContext;
        businessesList = mList;
    }

    @Override
    public int getCount() {
        return businessesList.size();
    }

    @Override
    public Object getItem(int position) {
        return getList(position);
    }

    @Override
    public long getItemId(int position) {
        return businessesDao.getBusiness(businessesList.get(position).getFirst()).get(0).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.rest_list, null);
        }

        //TextViews
        mRestName = convertView.findViewById(R.id.rest_name);
        mRestAddress = convertView.findViewById(R.id.rest_add);
        mRestAddress2 = convertView.findViewById(R.id.rest_add2);
        mDelivery = convertView.findViewById(R.id.delivery);
        mPhoneNumber = convertView.findViewById(R.id.rest_phone);

        //ImageViews
        mRestBanner = convertView.findViewById(R.id.rest_image_img);
        mStar1 = convertView.findViewById(R.id.star5);
        mStar2 = convertView.findViewById(R.id.star4);
        mStar3 = convertView.findViewById(R.id.star3);
        mStar4 = convertView.findViewById(R.id.star2);
        mStar5 = convertView.findViewById(R.id.star1);
        mPrice1 = convertView.findViewById(R.id.price1);
        mPrice2 = convertView.findViewById(R.id.price2);
        mPrice3 = convertView.findViewById(R.id.price3);
        mPrice4 = convertView.findViewById(R.id.price4);
        mFav = convertView.findViewById(R.id.fav);
        mCategories = convertView.findViewById(R.id.rest_categories);
        mPickup = convertView.findViewById(R.id.pick_up);
        mBooking = convertView.findViewById(R.id.booking);

        if (getList(position).is_favourite == 1) {
            mFav.setImageResource(R.drawable.ic_fav);
        } else {
            mFav.setImageResource(R.drawable.ic_not_fav);
        }

        //Setters
        convertView = setCurrentItems(position, convertView);

        return convertView;
    }

    private View setCurrentItems(int position, View convertView) {
        business = businessesList.get(position);
        location = businessesList.get(position).getLocation();
        try {
            setImages(convertView, business);
            setAddressPhone(business);
            notifyDataSetChanged();
        } catch (NullPointerException e) {
            Log.println(Log.INFO, "NUll pointer", e.toString());
        }
        return convertView;
    }

    private void setCategories(Business business) {
        if (business.categories != null) {
            StringBuilder build = new StringBuilder();
            int count = 0;
            build.append("Categories: \n");
            for (Category c : business.categories) {
                count++;
                build.append(c.getTitle());
                if (business.categories.size() - count > 0) {
                    build.append(" - ");
                }
            }
            mCategories.setText(build.toString());
        } else {
            mCategories.setVisibility(View.GONE);
        }
    }

    private void setPrice(Business business) {
        business.setPriceToInt();
        switch (business.getPriceInt()) {
            case 1:
                mPrice1.setVisibility(View.VISIBLE);
                mPrice2.setVisibility(View.GONE);
                mPrice3.setVisibility(View.GONE);
                mPrice4.setVisibility(View.GONE);
                break;
            case 2:
                mPrice1.setVisibility(View.VISIBLE);
                mPrice2.setVisibility(View.VISIBLE);
                mPrice3.setVisibility(View.GONE);
                mPrice4.setVisibility(View.GONE);
                break;
            case 3:
                mPrice1.setVisibility(View.VISIBLE);
                mPrice2.setVisibility(View.VISIBLE);
                mPrice3.setVisibility(View.VISIBLE);
                mPrice4.setVisibility(View.GONE);
                break;
            case 4:
                mPrice1.setVisibility(View.VISIBLE);
                mPrice2.setVisibility(View.VISIBLE);
                mPrice3.setVisibility(View.VISIBLE);
                mPrice4.setVisibility(View.VISIBLE);
                break;
            default:
                mPrice1.setVisibility(View.GONE);
                mPrice2.setVisibility(View.GONE);
                mPrice3.setVisibility(View.GONE);
                mPrice4.setVisibility(View.GONE);
                break;
        }
    }

    private void setAddressPhone(Business businesses) {
        setCategories(business);
        mRestName.setText(businesses.getName());
        mRestAddress.setText(location.getAddress1());
        setRating(business);
        setPrice(business);

        StringBuilder addPart2 = new StringBuilder();
        String add2 = location.getAddress2();
        String add3 = location.getAddress3();
        if (add2 != null && add3 != null) {
            addPart2.append(add2).append(" ,").append(add3);
        } else if (add3 == null && add2 != null) {
            addPart2.append(add2);
        } else if (add3 != null) {
            addPart2.append(add3);
        } else if (!addPart2.toString().isEmpty()) {
            mRestAddress2.setText(addPart2.toString());
        } else {
            mRestAddress2.setVisibility(View.GONE);
        }
        String phone = business.getDisplayPhone().trim().replace("-", "");
        if (!(phone.length() < 12)) {
            String phoneSection1 = phone.substring(0, 2);
            String phoneIndicative = phone.substring(2, 6);
            String phoneNumber = phone.substring(6, 9);
            String phoneLastDigits = phone.substring(9, 13);
            StringBuilder sb = new StringBuilder();
            sb.append(phoneSection1).append(" (").append(phoneIndicative).append(") ").append(phoneNumber).append("-").append(phoneLastDigits);
            mPhoneNumber.setText(sb.toString());
        } else {
            mPhoneNumber.setVisibility(View.GONE);
        }
        flagCheck(business.getTransaction1(), business.getTransaction2(), business.getTransaction3());
    }

    private void setImages(View v, Business business) {
        Glide.with(v.getContext()).load(business.getImageUrl()).into(mRestBanner);
    }

    private void flagCheck(String transaction1, String transaction2, String transaction3) {
        int pick = 0, book = 0, del = 0;
        String[] transactions = new String[3];
        if (transaction1 != null) {
            transactions[0] = transaction1;
        } else {
            transactions[0] = "nothing";
        }
        if (transaction2 != null) {
            transactions[1] = transaction2;
        } else {
            transactions[1] = "nothing";
        }
        if (transaction3 != null) {
            transactions[2] = transaction3;
        } else {
            transactions[2] = "nothing";
        }
        for (String s : transactions) {
            switch (s) {
                case "pickup":
                    pick = 1;
                    break;
                case "delivery":
                    del = 1;
                    break;
                case "restaurant_reservation": {
                    book = 1;
                }
                case "nothing":
                    break;
            }
        }
        flagSetUp(pick, book, del);
    }

    private void flagSetUp(int pick, int book, int del) {
        if (book == 1) {
            mBooking.setVisibility(View.VISIBLE);
            mBooking.setImageResource(R.drawable.pickup);
        } else {
            mBooking.setVisibility(View.GONE);
        }
        if (del == 1) {
            mDelivery.setVisibility(View.VISIBLE);
            mDelivery.setImageResource(R.drawable.ic_car_solid);
        } else {
            mDelivery.setVisibility(View.GONE);
        }
        if (pick == 1) {
            mPickup.setVisibility(View.VISIBLE);
            mPickup.setImageResource(R.drawable.pickup);
        } else {
            mPickup.setVisibility(View.GONE);
        }
    }

    private void setRating(Business business) {
        String rating = business.getRating();
        switch (ratingTranspose(rating)) {
            case 501: {
                mStar1.setVisibility(View.VISIBLE);
                mStar1.setImageResource(R.drawable.ic_half_star);
                mStar2.setVisibility(View.GONE);
                mStar3.setVisibility(View.GONE);
                mStar4.setVisibility(View.GONE);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 100: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.GONE);
                mStar3.setVisibility(View.GONE);
                mStar4.setVisibility(View.GONE);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 101: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_half_star);
                mStar3.setVisibility(View.GONE);
                mStar4.setVisibility(View.GONE);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 200: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_star);
                mStar3.setVisibility(View.GONE);
                mStar4.setVisibility(View.GONE);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 201: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_star);
                mStar3.setVisibility(View.VISIBLE);
                mStar3.setImageResource(R.drawable.ic_half_star);
                mStar4.setVisibility(View.GONE);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 300: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_star);
                mStar3.setVisibility(View.VISIBLE);
                mStar3.setImageResource(R.drawable.ic_star);
                mStar4.setVisibility(View.GONE);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 301: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_star);
                mStar3.setVisibility(View.VISIBLE);
                mStar3.setImageResource(R.drawable.ic_star);
                mStar4.setVisibility(View.VISIBLE);
                mStar4.setImageResource(R.drawable.ic_half_star);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 400: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_star);
                mStar3.setVisibility(View.VISIBLE);
                mStar3.setImageResource(R.drawable.ic_star);
                mStar4.setVisibility(View.VISIBLE);
                mStar4.setImageResource(R.drawable.ic_star);
                mStar5.setVisibility(View.GONE);
                break;
            }
            case 401: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_star);
                mStar3.setVisibility(View.VISIBLE);
                mStar3.setImageResource(R.drawable.ic_star);
                mStar4.setVisibility(View.VISIBLE);
                mStar4.setImageResource(R.drawable.ic_star);
                mStar5.setVisibility(View.VISIBLE);
                mStar5.setImageResource(R.drawable.ic_half_star);
                break;
            }
            case 500: {
                mStar1.setImageResource(R.drawable.ic_star);
                mStar2.setVisibility(View.VISIBLE);
                mStar2.setImageResource(R.drawable.ic_star);
                mStar3.setVisibility(View.VISIBLE);
                mStar3.setImageResource(R.drawable.ic_star);
                mStar4.setVisibility(View.VISIBLE);
                mStar4.setImageResource(R.drawable.ic_star);
                mStar5.setVisibility(View.VISIBLE);
                mStar5.setImageResource(R.drawable.ic_star);
                break;
            }
        }
    }

    private Business getList(int position) {
        return businessesList.get(position);
    }

    public static void setSpinner(View view) {
        ListView listView = view.getRootView().findViewById(R.id.list_items);
        Spinner spin = (Spinner) view.findViewById(R.id.spinner2);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListViewAdapter rla;
                switch (position) {
                    case 1:
                        rla = new ListViewAdapter(listView.getContext(), businessesDao.orderByNameAsc());
                        listView.setAdapter(rla);
                        rla.notifyDataSetChanged();
                        break;
                    case 2:
                        rla = new ListViewAdapter(listView.getContext(), businessesDao.orderByNameDesc());
                        listView.setAdapter(rla);
                        rla.notifyDataSetChanged();
                        break;
                    case 3:
                        rla = new ListViewAdapter(listView.getContext(), businessesDao.orderByPriceDesc());
                        listView.setAdapter(rla);
                        rla.notifyDataSetChanged();
                        break;
                    case 4:
                        rla = new ListViewAdapter(listView.getContext(), businessesDao.orderByPriceAsc());
                        listView.setAdapter(rla);
                        rla.notifyDataSetChanged();
                        break;
                    case 5:
                        rla = new ListViewAdapter(listView.getContext(), businessesDao.orderByRatingDesc());
                        listView.setAdapter(rla);
                        rla.notifyDataSetChanged();
                        break;
                    case 6:
                        businessesDao.orderByRatingDesc();
                        rla = new ListViewAdapter(listView.getContext(), businessesDao.orderByRatingAsc());
                        listView.setAdapter(rla);
                        rla.notifyDataSetChanged();
                        break;
                    default:
                        rla = new ListViewAdapter(listView.getContext(), businessesDao.getAll());
                        listView.setAdapter(rla);
                        rla.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static void apiCall(String query,ListView listView) {

        /////Setup Search Param
        Map<String, String> params = new HashMap<>();
        params.put("term", query);
        params.put("location", restLoc);
        params.put("limit", String.valueOf(limit));
        params.put("categories", restCat);

        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
        try {
            yelpFusionApi = apiFactory.createAPI(ApiConst.SECRET);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ///// Call API
        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);

        call.enqueue(new Callback<SearchResponse>() {
            ///// SET UP CALL BACK
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                assert response.body() != null;
                    if (response.isSuccessful()) {
                        businessesDao.deleteBusinesses();
                        List<Business> businesses = response.body().getBusinesses();
                        businessesDao.insertBusiness(businesses);
                        for (Business b : businesses) {
                            for (Category c : b.categories) {
                                c.setuID(b.getFirst());
                                businessesDao.insertCategories(c);
                            }
                        }
                        ListViewAdapter listViewAdapter = new ListViewAdapter(listView.getContext(), businessesDao.getAll());
                        listView.setAdapter(listViewAdapter);
                        listViewAdapter.notifyDataSetChanged();
                    }
                }
            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                Log.println(Log.INFO, "URL", ApiConst.HOST + ApiConst.PATH_RESTAURANT);
                Log.println(Log.INFO, "Response Values", "ERROR: " + t);
                call.cancel();
                responseError(t);
            }
        });
    }

    private static void responseError(Throwable t) {
        Log.println(Log.INFO, "ERROR MESSAGE FOR CALL", t.getMessage());
        Toast.makeText(context.getApplicationContext(),"Something went wrong !", Toast.LENGTH_LONG).show();
    }

    private int ratingTranspose(String string) {
        switch (string) {
            case "0.5":
                return 501;
            case "1.0":
                return 100;
            case "1.5":
                return 101;
            case "2.0":
                return 200;
            case "2.5":
                return 201;
            case "3.0":
                return 300;
            case "3.5":
                return 301;
            case "4.0":
                return 400;
            case "4.5":
                return 401;
            case "5.0":
                return 500;
        }
        return -1;
    }
}
