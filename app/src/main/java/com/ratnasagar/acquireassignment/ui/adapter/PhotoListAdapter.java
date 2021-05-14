package com.ratnasagar.acquireassignment.ui.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ratnasagar.acquireassignment.R;
import com.ratnasagar.acquireassignment.model.Photo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder>implements Filterable {

    List<Photo> listItems,filterList;
    Activity mContext;

    public PhotoListAdapter(List<Photo> listItems, Activity context) {
        this.listItems = listItems;
        mContext = context;
        this.filterList = new ArrayList<Photo>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent,false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTitle.setText(filterList.get(position).getTitle());
        Picasso
                .get()
                .load(filterList.get(position).getUrl())
                .placeholder(R.drawable.search)
                .noFade()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.imageViewAvatar);
            mTitle=itemView.findViewById(R.id.tvTitle);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = listItems;
                } else {
                    List<Photo> filteredList = new ArrayList<>();
                    for (Photo row : listItems) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<Photo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}

