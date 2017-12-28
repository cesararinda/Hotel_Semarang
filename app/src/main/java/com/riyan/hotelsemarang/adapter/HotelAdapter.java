package com.riyan.hotelsemarang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.riyan.hotelsemarang.R;
import com.riyan.hotelsemarang.activities.DetailHotelActivity;
import com.riyan.hotelsemarang.helper.Constant;
import com.riyan.hotelsemarang.model.Hotel;

import java.util.List;


public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {


    private Context context;
    private List<Hotel> listHotel;
    public HotelAdapter(Context context, List<Hotel> listHotel) {
        this.context = context;
        this.listHotel = listHotel;
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(itemView);

    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemGambarHotel;
        TextView tvItemNamaHotel;
        TextView tvItemAlamatHotel;

        public HotelViewHolder(View itemView) {
            super(itemView);
            ivItemGambarHotel = (ImageView) itemView.findViewById(R.id.iv_item_gambar);
            tvItemNamaHotel = (TextView) itemView.findViewById(R.id.tv_item_nama);
            tvItemAlamatHotel = (TextView) itemView.findViewById(R.id.tv_item_alamat);
        }
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, final int position) {
        String linkGambar = listHotel.get(position).getGambarHotel();
        Glide.with(context).load("https://drive.google.com/thumbnail?id=" + linkGambar)
                .into(holder.ivItemGambarHotel);

        holder.tvItemNamaHotel.setText(listHotel.get(position).getNamaHotel());
        holder.tvItemAlamatHotel.setText(listHotel.get(position).getAlamatHotel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString(Constant.ID_Hotel, listHotel.get(position).getIdHotel());
                b.putString(Constant.NAMA_Hotel, listHotel.get(position).getNamaHotel());
                b.putString(Constant.GAMBAR_Hotel, listHotel.get(position).getGambarHotel());
                b.putString(Constant.ALAMAT_Hotel, listHotel.get(position).getAlamatHotel());
                b.putString(Constant.DESKRIPSI_Hotel, listHotel.get(position).getDeskripsiHotel());
                b.putString(Constant.LATITUDE_Hotel, listHotel.get(position).getLatitudeHotel());
                b.putString(Constant.LONGITUDE_Hotel, listHotel.get(position).getLongitudeHotel());
                //
                Intent i = new Intent(context, DetailHotelActivity.class);
                i.putExtras(b);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHotel.size();
    }
}
