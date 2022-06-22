package com.example.myrestaurant;

import static com.example.myrestaurant.MainActivity.businessesDao;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myrestaurant.databinding.FavoritesViewBinding;

public class SecondFragment extends Fragment {
    private ListViewAdapter SecondListAdapter;
    ListView listView;
    FavoritesViewBinding binding;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FavoritesViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = binding.favoritesList;
        SecondListAdapter = new ListViewAdapter(getContext(),businessesDao.getFavourite());
        listView.setAdapter(SecondListAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
            {
                Log.println(Log.INFO,"Favorite Before",String.valueOf(businessesDao.getAll().get(position).is_favourite()));
                dialog.setTitle("Remove from favorites")
                        .setMessage("Would you like to remove " + businessesDao.getAll().get(position).getName() + " to your favorites?")

                        .setPositiveButton("Remove", (dialog13, which) -> {
                            Log.println(Log.INFO,"Favorite After",String.valueOf(businessesDao.getAll().get(position).is_favourite()));
                            businessesDao.getAll().get(position).setIs_favourite(0);
                            businessesDao.update(businessesDao.getAll().get(position));
                            SecondListAdapter = new ListViewAdapter(SecondFragment.this.getContext(), businessesDao.getFavourite());
                            binding.favoritesList.setAdapter(SecondListAdapter);
                            SecondListAdapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("Cancel", (dialog14, which) -> {
                            dialog14.dismiss();
                            Toast.makeText(view1.getRootView().getContext(), "Item wasn't removed from favorite list.", Toast.LENGTH_SHORT).show();
                        });
            }
            dialog.setCancelable(true).show();
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}