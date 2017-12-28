package com.riyan.hotelsemarang.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class ListHotel {

    @SerializedName("hotel")
    private List<Hotel> listHotelSemarang;

    public List<Hotel> getListHotelSemarang() {
        return listHotelSemarang;
    }
}
