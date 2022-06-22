package com.example.myrestaurant;

import static com.example.myrestaurant.ListViewAdapter.apiCall;
import static com.example.myrestaurant.MainActivity.businessesDao;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myrestaurant.databinding.ListFragBinding;
import com.example.myrestaurant.yelpfusion.models.Business;

public class FirstFragment extends Fragment {
    private ListViewAdapter listViewAdapter;
    ListView listView;
    ListFragBinding binding;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ListFragBinding.inflate(inflater, container, false);
        apiCall("Steak", binding.listItems);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createSearch();
        listView = binding.listItems;
        ListViewAdapter.setSpinner(binding.listHead.spinner2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(FirstFragment.this.getContext());
                if (businessesDao.getAll().get(position).is_favourite == 0) {
                    dialog.setTitle("Add to favorites")
                            .setMessage("Would you like to add " + businessesDao.getAll().get(position).getName() + " to your favorites?")
                            .setCancelable(true)
                            .setPositiveButton("Add", (dialog1, which) -> {
                                Business b = businessesDao.getAll().get(position);
                                b.setIs_favourite(1);
                                businessesDao.update(b);
                                listViewAdapter = new ListViewAdapter(FirstFragment.this.getContext(), businessesDao.getAll());
                                listView.setAdapter(listViewAdapter);
                                listViewAdapter.notifyDataSetChanged();
                            })
                            .setNegativeButton("Cancel", (dialog12, which) -> {
                                dialog12.dismiss();
                                Toast.makeText(view1.getRootView().getContext(), "Item wasn't added to favorite list.", Toast.LENGTH_SHORT).show();
                            });

                } else if (businessesDao.getAll().get(position).is_favourite == 1) {

                    dialog.setTitle("Remove from favorites")
                            .setMessage("Would you like to remove " + businessesDao.getAll().get(position).getName() + " to your favorites?")
                            .setPositiveButton("Remove", (dialog13, which) -> {
                                Business b = businessesDao.getAll().get(position);
                                b.setIs_favourite(0);
                                businessesDao.update(b);
                                listViewAdapter = new ListViewAdapter(FirstFragment.this.getContext(), businessesDao.getAll());
                                listView.setAdapter(listViewAdapter);
                                listViewAdapter.notifyDataSetChanged();
                            })
                            .setNegativeButton("Cancel", (dialog14, which) -> {
                                dialog14.dismiss();
                                Toast.makeText(view1.getRootView().getContext(), "Item wasn't removed from favorite list.", Toast.LENGTH_SHORT).show();
                            });
                }

                dialog.setCancelable(true).show();
            }
        });
    }

    private void createSearch() {
        SearchView searchView = (SearchView) binding.searchBar;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                ListViewAdapter.apiCall(query,listView);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}