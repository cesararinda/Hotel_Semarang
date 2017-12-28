package com.riyan.hotelsemarang.activities;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.riyan.hotelsemarang.R;
import com.riyan.hotelsemarang.helper.ServiceClient;
import com.riyan.hotelsemarang.helper.ServiceGenerator;
import com.riyan.hotelsemarang.model.ListHotel;
import com.riyan.hotelsemarang.model.Hotel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LokasiAll extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_all);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    List<Hotel> listHotel = new ArrayList<>();
    ProgressDialog pd;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);
        Call<ListHotel> getListHotel = service.getHotel("hotel");

        pd = new ProgressDialog(LokasiAll.this);
        pd.setMessage("Please wait . . .");
        pd.show();

        getListHotel.enqueue(new Callback<ListHotel>() {
            @Override
            public void onResponse(Call<ListHotel> call, Response<ListHotel> response) {
                pd.dismiss();
                listHotel = response.body().getListHotelSemarang();

                for (int i = 0; i < listHotel.size(); i++) {
                    Double latHotel = Double.valueOf(listHotel.get(i).getLatitudeHotel());
                    Double longHotel = Double.valueOf(listHotel.get(i).getLongitudeHotel());

                    LatLng lokasiHotel = new LatLng(latHotel, longHotel);
                    mMap.addMarker(new MarkerOptions().position(lokasiHotel).title(listHotel.get(i).getNamaHotel()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasiHotel));
                }
            }

            @Override
            public void onFailure(Call<ListHotel> call, Throwable t) {
                Toast.makeText(LokasiAll.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
