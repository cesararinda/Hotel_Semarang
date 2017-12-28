package com.riyan.hotelsemarang.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.riyan.hotelsemarang.R;
import com.riyan.hotelsemarang.adapter.HotelAdapter;
import com.riyan.hotelsemarang.helper.ServiceClient;
import com.riyan.hotelsemarang.helper.ServiceGenerator;
import com.riyan.hotelsemarang.model.ListHotel;
import com.riyan.hotelsemarang.model.Hotel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerViewHome;
    List<Hotel> hotelList = new ArrayList<>();
    ProgressDialog pd;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewHome = (RecyclerView) view.findViewById(R.id.rv_home);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(),2));

//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerViewHome.setLayoutManager(linearLayoutManager);
        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);
        Call<ListHotel> getListHotel = service.getHotel("hotel");

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Load Data From Server");
        pd.show();

        getListHotel.enqueue(new Callback<ListHotel>() {
            @Override
            public void onResponse(Call<ListHotel> call, Response<ListHotel> response) {
                pd.dismiss();
                hotelList = response.body().getListHotelSemarang();
                HotelAdapter adapter = new HotelAdapter(getActivity(), hotelList);
                recyclerViewHome.setAdapter(adapter);
                Toast.makeText(getActivity(), "Item Count : "+adapter.getItemCount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ListHotel> call, Throwable t) {
                Toast.makeText(getActivity(), "Info : "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
