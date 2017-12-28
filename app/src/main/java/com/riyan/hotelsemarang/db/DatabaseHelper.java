package com.riyan.hotelsemarang.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.riyan.hotelsemarang.model.Hotel;

import java.util.ArrayList;



public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "dbHotel";
    private final static String DATABASE_TABLE = "table_Hotel";
    private final static String Hotel_ID = "_id";
    private final static String NAMA_Hotel = "nama_Hotel";
    private final static String GAMBAR_Hotel = "gambar_Hotel";
    private final static String ALAMAT_Hotel = "alamat_Hotel";
    private final static String DESKRIPSI_Hotel = "deskripsi_Hotel";
    private final static String LATITUDE_Hotel = "latitude_Hotel";
    private final static String LONGITUDE_Hotel = "longitude_Hotel";

    private final static int DATABASE_VERSION = 4;
    private final static String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE
            + " (" + Hotel_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAMA_Hotel + " VARCHAR(200), "
            + GAMBAR_Hotel + " VARCHAR(200), "
            + ALAMAT_Hotel + " TEXT, "
            + DESKRIPSI_Hotel + " TEXT, "
            + LATITUDE_Hotel + " VARCHAR(20), "
            + LONGITUDE_Hotel + " VARCHAR(20));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insertData(String namaHotel,
                           String gambarHotel,
                           String alamatHotel,
                           String deskripsiHotel,
                           String latHotel,
                           String longHotel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMA_Hotel, namaHotel);
        contentValues.put(GAMBAR_Hotel, gambarHotel);
        contentValues.put(ALAMAT_Hotel, alamatHotel);
        contentValues.put(DESKRIPSI_Hotel, deskripsiHotel);
        contentValues.put(LATITUDE_Hotel, latHotel);
        contentValues.put(LONGITUDE_Hotel, longHotel);
        long id = db.insert(DATABASE_TABLE, null, contentValues);
        db.close();
        return id;
    }

    public ArrayList<Hotel> getDataFavorite() {
        ArrayList<Hotel> listHotelFavorite = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columName = {Hotel_ID, NAMA_Hotel,
                GAMBAR_Hotel,
                ALAMAT_Hotel,
                DESKRIPSI_Hotel,
                LATITUDE_Hotel,
                LONGITUDE_Hotel};

        Cursor cursor = db.query(DATABASE_TABLE, columName, null, null,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idHotel = cursor.getInt(cursor.getColumnIndex(Hotel_ID));
                String namaHotel = cursor.getString(cursor.getColumnIndex(NAMA_Hotel));
                String gambarHotel = cursor.getString(cursor.getColumnIndex(GAMBAR_Hotel));
                String alamatHotel = cursor.getString(cursor.getColumnIndex(ALAMAT_Hotel));
                String deskripsiHotel = cursor.getString(cursor.getColumnIndex(DESKRIPSI_Hotel));
                String latHotel = cursor.getString(cursor.getColumnIndex(LATITUDE_Hotel));
                String longHotel = cursor.getString(cursor.getColumnIndex(LONGITUDE_Hotel));

                Hotel hotelFavorite = new Hotel(String.valueOf(idHotel),
                        namaHotel,
                        gambarHotel,
                        deskripsiHotel,
                        alamatHotel,
                        latHotel,
                        longHotel);
                listHotelFavorite.add(hotelFavorite);
            }
        }
        db.close();
        return listHotelFavorite;
    }

    public int delete(String namaHotel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String namaKolomnya = NAMA_Hotel + " = ?";
        String[] nilaiFieldnya = {namaHotel};
        int count = db.delete(DATABASE_TABLE, namaKolomnya, nilaiFieldnya);
        return count;
    }

}
