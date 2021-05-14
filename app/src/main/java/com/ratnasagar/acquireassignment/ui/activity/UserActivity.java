package com.ratnasagar.acquireassignment.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ratnasagar.acquireassignment.R;
import com.ratnasagar.acquireassignment.databinding.ActivityUserBinding;
import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.model.User;
import com.ratnasagar.acquireassignment.ui.adapter.PhotoListAdapter;
import com.ratnasagar.acquireassignment.ui.adapter.UserListAdapter;
import com.ratnasagar.acquireassignment.ui.viewmodel.MainActivityViewModel;
import com.ratnasagar.acquireassignment.ui.viewmodel.UserActivityViewModel;
import com.ratnasagar.acquireassignment.ui.viewmodel.UserViewModelFactory;
import com.ratnasagar.acquireassignment.ui.viewmodel.ViewModelFactory;

import java.util.List;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding userBinding;
    private UserActivityViewModel userActivityViewModel;
    private UserListAdapter userListAdapter;
    List<User> userList;
    private SearchView searchView;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBinding= DataBindingUtil.setContentView(this,R.layout.activity_user);
        userActivityViewModel=new ViewModelProvider(this, new UserViewModelFactory(this)).get(UserActivityViewModel.class);

        userBinding.progressBar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userActivityViewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> user) {

                userBinding.progressBar.setVisibility(View.GONE);
                userList = user;
                if (userList!=null && userList.size()>0)
                {userBinding.tvNoDataFound.setVisibility(View.GONE);
                    userListAdapter = new UserListAdapter(userList,UserActivity.this);
                    userBinding.recyclerList.setAdapter(userListAdapter);
                }
                else
                {
                    userBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                }
            }
        });


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //Toast.makeText(UserActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Toast.makeText(MainActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                userList.remove(position);
                if (userList!=null && userList.size()>0)
                {
                    userBinding.tvNoDataFound.setVisibility(View.GONE);
                    userListAdapter = new UserListAdapter(userList,UserActivity.this);
                    userBinding.recyclerList.setAdapter(userListAdapter);
                }
                else
                {
                    userBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                }
                userListAdapter.notifyDataSetChanged();

            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(userBinding.recyclerList);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.search, menu);
            final MenuItem searchItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            EditText searchEditText = searchView.findViewById(R.id.search_src_text);
            searchEditText.setHint("Search Title");
            searchEditText.setHintTextColor(getResources().getColor(R.color.grey));
            searchEditText.setTextColor(getResources().getColor(R.color.white));

            searchEditText.setCursorVisible(true);
            searchView.setIconified(true);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if(userList.size() != 0) {
                        userListAdapter.getFilter().filter(newText);
                    }else{
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(userList.size() != 0) {
                        userListAdapter.getFilter().filter(query);
                    }else{
                    }
                    return false;
                }
            });
        }catch (Exception ex){
            Log.i("tag",ex.toString());
        }
//        searchView.onActionViewCollapsed();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}