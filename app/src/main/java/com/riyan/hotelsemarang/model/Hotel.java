package com.riyan.hotelsemarang.model;

import com.google.gson.annotations.SerializedName;


public class Hotel {

    @SerializedName("id")
    private String idHotel;

    @SerializedName("nama")
    private String namaHotel;

    @SerializedName("gambar")
    private String gambarHotel;

    @SerializedName("deskripsi")
    private String deskripsiHotel;

    @SerializedName("alamat")
    private String alamatHotel;

    @SerializedName("lat")
    private String latitudeHotel;

    @SerializedName("lnj")
    private String longitudeHotel;

    public Hotel(String idHotel, String namaHotel, String gambarHotel, String deskripsiHotel,
                 String alamatHotel, String latitudeHotel, String longitudeHotel){
        this.idHotel = idHotel;
        this.namaHotel = namaHotel;
        this.gambarHotel = gambarHotel;
        this.deskripsiHotel = deskripsiHotel;
        this.alamatHotel = alamatHotel;
        this.latitudeHotel = latitudeHotel;
        this.longitudeHotel = longitudeHotel;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public String getNamaHotel() {
        return namaHotel;
    }

    public String getGambarHotel() {
        return gambarHotel;
    }

    public String getDeskripsiHotel() {
        return deskripsiHotel;
    }

    public String getAlamatHotel() {
        return alamatHotel;
    }

    public String getLatitudeHotel() {
        return latitudeHotel;
    }

    public String getLongitudeHotel() {
        return longitudeHotel;
    }
}
