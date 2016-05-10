package hukum2016.sikolin.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by Ari on 5/8/2016.
 */
public class MenuKantin {
    private String nama_menu,foto_menu,deskripsi,nama_penjual;
    private int harga_menu,jenis_menu,id_menu;
    private float rating;
    public MenuKantin(){
    }

    public MenuKantin(String nama_menu, String foto_menu, String deskripsi, String nama_penjual, int harga_menu, int jenis_menu, int id_menu, float rating) {
        this.nama_menu = nama_menu;
        this.foto_menu = foto_menu;
        this.deskripsi = deskripsi;
        this.nama_penjual = nama_penjual;
        this.harga_menu = harga_menu;
        this.jenis_menu = jenis_menu;
        this.id_menu = id_menu;
        this.rating = rating;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNama_penjual() {
        return nama_penjual;
    }

    public void setNama_penjual(String nama_penjual) {
        this.nama_penjual = nama_penjual;
    }

    public int getHarga_menu() {
        return harga_menu;
    }

    public void setHarga_menu(int harga_menu) {
        this.harga_menu = harga_menu;
    }

    public int getJenis_menu() {
        return jenis_menu;
    }

    public void setJenis_menu(int jenis_menu) {
        this.jenis_menu = jenis_menu;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Bitmap getImage() {
        byte[] imageAsBytes = Base64.decode(foto_menu.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
