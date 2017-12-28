package com.riyan.hotelsemarang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riyan.hotelsemarang.R;
import com.riyan.hotelsemarang.adapter.HotelAdapter;
import com.riyan.hotelsemarang.db.DatabaseHelper;
import com.riyan.hotelsemarang.model.Hotel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    RecyclerView rvFavorit;
    DatabaseHelper database;
    ArrayList<Hotel> listHotelFavorite;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container,
                false);
        rvFavorit = (RecyclerView) view.findViewById(R.id.rv_favorite);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvFavorit.setLayoutManager(llm);

        database = new DatabaseHelper(getActivity());
        listHotelFavorite = database.getDataFavorite();
        HotelAdapter adapter = new HotelAdapter(getActivity(), listHotelFavorite);
        rvFavorit.setAdapter(adapter);
        return view;

    }

}
