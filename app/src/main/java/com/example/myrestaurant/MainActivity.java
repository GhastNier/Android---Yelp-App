package com.example.myrestaurant;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myrestaurant.databinding.ActivityMainBinding;
import com.example.myrestaurant.rooms.AppDatabase;
import com.example.myrestaurant.rooms.BusinessesDao;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static AppBarConfiguration appBarConfiguration;
    public static final String restCat = "Restaurant";
    public static final int limit = 15;
    public static final String restLoc = "Montreal";
    public static BusinessesDao businessesDao;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStart() {
        super.onStart();
        createDataBase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createBinding();
        DrawerLayout drawer = binding.drawLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorites, R.id.delete_fav)
                .setOpenableLayout(drawer)
                .build();
        setSupportActionBar(binding.appBarMainDragwer.toolbar);
        getSupportActionBar().hide();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    private void createDataBase() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        businessesDao = db.businessesDao();
    }

    private void createBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void removeAll(MenuItem item) {
        AlertDialog.Builder adb = new AlertDialog.Builder(binding.getRoot().getContext());
        adb.setTitle("Remove favorites")
                .setMessage("Do you really want to clear the data?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        businessesDao.deleteAll();
                        Toast.makeText(getApplicationContext(), "You've removed all entries", Toast.LENGTH_SHORT).show();
                        ListViewAdapter lva = new ListViewAdapter(binding.getRoot().findViewById(R.id.favorites_list).getContext(),businessesDao.getFavourite());
                        lva.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        adb.show();
    }
}