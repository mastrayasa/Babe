package com.sibangstudio.babe;

import android.net.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by ibti on 6/6/17.
 */

public class MyFunction {

    public static final String TAG_NAME = "name";
    public static final String TAG_JUDUL = "title";
    public static final String TAG_SINGKAT = "singkat";
    public static final String TAG_ISI = "isi";
    public static final String TAG_IMAGE = "image";

    public static String rupiah(int harga){


        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);

    }


    public static DirectoryData setDariJson(JSONObject json_data){
        DirectoryData dirs = new DirectoryData();
        try {
        dirs.setJudul(json_data.getString(TAG_JUDUL));
        dirs.setName(json_data.getString(TAG_NAME));
        dirs.setImage_url(json_data.getString(TAG_IMAGE));
        dirs.setSingkat(json_data.getString(TAG_SINGKAT));
        dirs.setCatTitle(json_data.getString("cat_title"));
        dirs.setDes(json_data.getString("des"));

        JSONObject options = json_data.getJSONObject("options");

        if ( options.has("alamat") ) {
            dirs.setAlamat( options.getString("alamat") );
        }

        if ( options.has("contact") ) {
            dirs.setKontak( options.getString("contact") );
        }

        if ( options.has("email") ) {
            dirs.setEmail( options.getString("email") );
        }

        if ( options.has("url") ) {
            dirs.setUrl( options.getString("url") );
        }

        if ( options.has("tips") ) {
            dirs.setTips( options.getString("tips") );
        }

        if ( options.has("map") ) {
            JSONObject map = options.getJSONObject("map");
            dirs.setLat( map.getString("lat") );
            dirs.setLng( map.getString("lng") );
        }
        } catch (JSONException e1) {

        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return dirs;
    }
}
