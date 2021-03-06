package com.sibangstudio.babe;

import android.content.Context;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DirAdapter.DirAdapterOnClickHandler {

    final static String BASE_URL = "https://infosulteng.com/api";

    private static final int NUM_LIST_ITEMS = 100;

    /*
     * References to RecyclerView and Adapter to reset the list to its
     * "pretty" state when the reset menu item is clicked.
     */
    private DirAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    List<DirectoryData> dirList = new ArrayList<DirectoryData>();

    JSONArray jArray = null;
    JSONObject resultRoot = null;
    JSONObject json_data = null;
    JSONObject json_Detail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_numbers);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new DirAdapter(MainActivity.this, this);
        mRecyclerView.setAdapter(mAdapter);



        loadDirData();

    }

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadDirData() {
        showDirDataView();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("dir")
                .appendPath("terbaru")
                .build();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, builtUri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        olahData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorMessage();
                mErrorMessageDisplay.setText(  error.getMessage() );
            }
        });

        mLoadingIndicator.setVisibility(View.VISIBLE);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showDirDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    public void olahData(String s) {
        //Log.e("log_tag", s);
        mLoadingIndicator.setVisibility(View.INVISIBLE);


        try {

            resultRoot = new JSONObject(s);
            boolean status = resultRoot.getBoolean("status");
            if (status) {
                jArray = resultRoot.getJSONArray("items");


                // deklarasikan panjang array sejumlah array jarray

                if (jArray.length() > 0) {
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);

                        DirectoryData aha = MyFunction.setDariJson(json_data);

                        dirList.add(aha);
                        Log.e("Add", aha.getJudul());
                    }

                    mAdapter.setDirData(dirList);
                }

            }

        } catch (JSONException e1) {
            Toast.makeText(getBaseContext(), "Opsss...", Toast.LENGTH_LONG)
                    .show();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onClick(String weatherForDay) {
        Context context = this;
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mAdapter.setDirData(null);
            loadDirData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
