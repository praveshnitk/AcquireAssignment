package com.ratnasagar.acquireassignment.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ratnasagar.acquireassignment.R;
import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.model.User;
import com.ratnasagar.acquireassignment.ui.activity.UserLocation;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>implements Filterable {

    List<User> listItems,filterList;
    Activity mContext;

    public UserListAdapter(List<User> listItems, Activity context) {
        this.listItems = listItems;
        mContext = context;
        this.filterList = new ArrayList<User>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(filterList.get(position).getName());
        holder.tvEmail.setText(filterList.get(position).getEmail());
        holder.tvLocation.setText(filterList.get(position).getAddress().getStreet()+" , "+filterList.get(position).getAddress().getSuite()+" , "+filterList.get(position).getAddress().getCity()+" , "+filterList.get(position).getAddress().getZipcode());
        holder.tvAddress.setText(filterList.get(position).getCompany().getName());
        holder.tvPhone.setText(filterList.get(position).getPhone());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserLocation.class);
                intent.putExtra("latitude",filterList.get(position).getAddress().getGeo().getLat());
                intent.putExtra("longitude",filterList.get(position).getAddress().getGeo().getLng());
                intent.putExtra("title",filterList.get(position).getName());
                intent.putExtra("address",filterList.get(position).getAddress().getStreet()+" , "+filterList.get(position).getAddress().getSuite()+" , "+filterList.get(position).getAddress().getCity()+" , "+filterList.get(position).getAddress().getZipcode());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvEmail,tvLocation,tvAddress,tvPhone;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvEmail=itemView.findViewById(R.id.tvEmail);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvPhone=itemView.findViewById(R.id.tvPhone);
            cardView=itemView.findViewById(R.id.cardView);
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
                    List<User> filteredList = new ArrayList<>();
                    for (User row : listItems) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
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
                filterList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}

