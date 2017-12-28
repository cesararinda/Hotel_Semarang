package com.riyan.hotelsemarang.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.riyan.hotelsemarang.R;
import com.riyan.hotelsemarang.db.DatabaseHelper;
import com.riyan.hotelsemarang.helper.Constant;

public class DetailHotelActivity extends AppCompatActivity {
    ImageView ivGambarHotel;
    TextView tvAlamatHotel, tvDeskripsiHotel;
    String idHotel, namaHotel, gambarHotel, alamatHotel,deskripsiHotel, latHotel, longHotel;

    private static final String TAG_PREF = "setting";
    private static final String TAG_FAV = "favorite";

    Boolean isFav;
    Button buttonMaps;
    FloatingActionButton fab;
    DatabaseHelper database = new DatabaseHelper(this);
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        //hubungkan java dengan xml nya
        ivGambarHotel = (ImageView) findViewById(R.id.iv_detail_gambar);
        tvAlamatHotel = (TextView) findViewById(R.id.tv_detail_alamat);
        tvDeskripsiHotel = (TextView)findViewById(R.id.tv_detail_deskripsi);
        buttonMaps = (Button) findViewById(R.id.btn_maps);


        // menampung data yang dikirim
        Bundle b = getIntent().getExtras();
        idHotel = b.getString(Constant.ID_Hotel);
        namaHotel = b.getString(Constant.NAMA_Hotel);
        gambarHotel = b.getString(Constant.GAMBAR_Hotel);
        alamatHotel = b.getString(Constant.ALAMAT_Hotel);
        deskripsiHotel = b.getString(Constant.DESKRIPSI_Hotel);
        latHotel = b.getString(Constant.LATITUDE_Hotel);
        longHotel = b.getString(Constant.LONGITUDE_Hotel);

        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creates an Intent that will load a map of San Francisc
                String strUri = "http://maps.google.com/maps?q=loc:" + latHotel + "," + longHotel + " (" + namaHotel + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

            }
        });

        //memasukan data yang didapat ke layout
        getSupportActionBar().setTitle(namaHotel);
        Glide.with(this).load("https://drive.google.com/thumbnail?id=" + gambarHotel).into(ivGambarHotel);
        tvAlamatHotel.setText(alamatHotel);
        tvDeskripsiHotel.setText(deskripsiHotel);

        pref = getSharedPreferences(TAG_PREF, MODE_PRIVATE);
        isFav = pref.getBoolean(TAG_FAV + idHotel, false);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        cekFavorit(isFav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFav) {
                    //jika sebelumnya favorit
                    // data favorit akan didelete dari database favorit yang ada di SQLite
                    //menghapus Hotel di database
                    database.delete(namaHotel);
                    Snackbar.make(view,"Favorites has been remove", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    SharedPreferences sp = getSharedPreferences(TAG_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
//membuat status fav menjadi false
                    editor.putBoolean(TAG_FAV + idHotel, false);
                    editor.commit();
                    isFav = false;
                } else {
                    //jika sebelumnya not favorit
                    //data akan dimasukan ke dalam database favorit
                    //memasukan Hotel favorit ke database
                    long id = database.insertData(namaHotel,
                            gambarHotel,
                            alamatHotel,
                            deskripsiHotel,
                            latHotel,
                            longHotel);
                    if (id <= 0) {
                        Snackbar.make(view,"Failed add to Favorites",Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view,"Added to Favorites", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        SharedPreferences sp = getSharedPreferences(TAG_PREF,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(TAG_FAV + idHotel, true);
                        editor.commit();
                        isFav = true;
                        fab.setImageResource(R.drawable.ic_action_favorite);
                    }
                }
//memanggil cekFavorit saat fab diklik
                cekFavorit(isFav);
            }

        });
    }
    private void cekFavorit(Boolean isFav) {
        if (isFav) {
            fab.setImageResource(R.drawable.ic_action_favorite);
        } else {
            fab.setImageResource(R.drawable.ic_action_notfavorite);
        }
    }
}
