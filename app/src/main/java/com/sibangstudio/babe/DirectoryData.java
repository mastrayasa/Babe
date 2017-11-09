package com.sibangstudio.babe;

import java.io.Serializable;

/**
 * Created by ibti on 5/15/17.
 */

public class DirectoryData implements Serializable {
    private String jenis;
    private String id;
    private String judul;
    private String name;
    private String singkat;
    private String isi;
    private String image_url;
    private String des;
    private String kontak;
    private String catTitle;
    private String catName;
    private String alamat;
    private String email;
    private String url;
    private String tips;
    private String lat;
    private String lng;



    public DirectoryData(){

    }
    public DirectoryData(String id, String judul, String singkat, String isi, String image_url, String des) {
        this.setId(id);
        this.setName(getName());
        this.setJudul(judul);
        this.setSingkat(singkat);
        this.setIsi(isi);
        this.setImage_url(image_url);
        this.setImage_url(des);
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingkat() {
        return singkat;
    }

    public void setSingkat(String singkat) {
        this.singkat = singkat;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }


    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
}
