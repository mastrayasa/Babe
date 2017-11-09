/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sibangstudio.babe;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class DirAdapter extends RecyclerView.Adapter<DirAdapter.NumberViewHolder> {

    private static final String TAG = DirAdapter.class.getSimpleName();


    private static int viewHolderCount;


    private Activity activity;
    private List<DirectoryData> dirItems;

    DirectoryData dir;

    public DirAdapter(Activity activity,  DirAdapterOnClickHandler mClickHandler) {

        this.activity = activity;
        this.mClickHandler = mClickHandler;
    }


    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);



        // COMPLETED (13) Use ColorUtils.getViewHolderBackgroundColorFromInstance and pass in a Context and the viewHolderCount
        // int backgroundColorForViewHolder = ColorUtils
        //    .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        // COMPLETED (14) Set the background color of viewHolder.itemView with the color from above
        // viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        // COMPLETED (15) Increment viewHolderCount and log its value
        viewHolderCount++;

        Log.d(TAG, "#####" + viewHolderCount);
        //Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
        //   + viewHolderCount);
        return viewHolder;
    }


    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param viewHolder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NumberViewHolder viewHolder, int position) {
        Log.d(TAG, "#" + position);


        dir = dirItems.get(position);

        // COMPLETED (12) Set the text of viewHolderIndex to "ViewHolder index: " + viewHolderCount
        // viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);
        viewHolder.txtJuduk.setText(dir.getJudul());

        viewHolder.txtViewCat.setText(dir.getCatTitle());


        String meta = "";

        if (dir.getAlamat() != null && !dir.getAlamat().isEmpty()) {
            meta = dir.getAlamat();
        }

       /* if ( dir.getKontak() != null && !dir.getKontak().isEmpty()) {
            meta = meta + activity.getString(R.string.dir_meta_phone) + " " +  dir.getKontak();
        }

        if ( dir.getUrl() != null && !dir.getUrl().isEmpty()) {
            meta = meta + "\n" + activity.getString(R.string.dir_meta_url) + " " +  dir.getUrl();
        }

        if ( dir.getEmail() != null && !dir.getEmail().isEmpty()) {
            meta = meta + "\n" + activity.getString(R.string.dir_meta_email) + " " +  dir.getEmail();
        }*/


        if (!meta.isEmpty()) {
            viewHolder.txtViewExp.setText(meta);
        } else {
            viewHolder.txtViewExp.setText(dir.getSingkat());
        }


        Uri builtUri = Uri.parse("https://infosulteng.com/thumb").buildUpon()
                .appendQueryParameter("src", dir.getImage_url())
                .appendQueryParameter("h", "180")
                .appendQueryParameter("h", "240")
                .build();

        Picasso.with(activity)
                .load(builtUri.toString())

                .into(viewHolder.imgDir);
        //holder.bind(position);
    }


    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {

        if (null == dirItems) return 0;

        //Log.d(TAG, "Besar" + dirItems.size());
        return dirItems.size();
    }

    public void setDirData(List<DirectoryData> weatherData) {
        dirItems = weatherData;
        notifyDataSetChanged();
    }




    /**
     * Cache of the children views for a list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtJuduk;
        TextView txtViewCat;
        ImageView imgDir;
        TextView txtViewExp;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         *
         * @param itemView The View that you inflated in
         *                 {@link DirAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public NumberViewHolder(View itemView) {
            super(itemView);

            txtViewExp = (TextView) itemView.findViewById(R.id.txtViewExpert);
            imgDir = (ImageView) itemView.findViewById(R.id.imgViewDirCat);
            txtViewCat = (TextView) itemView.findViewById(R.id.txtCat);
            txtJuduk = (TextView) itemView.findViewById(R.id.txtViewTitle);

            itemView.setOnClickListener(this);
        }


        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         *
         * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {

            //listItemNumberView.setText(String.valueOf(listIndex));
        }


        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = dir.getJudul();
            mClickHandler.onClick(weatherForDay);
        }
    }


    private final DirAdapterOnClickHandler mClickHandler;


    /**
     * The interface that receives onClick messages.
     */
    public interface DirAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public DirAdapter(DirAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


}
