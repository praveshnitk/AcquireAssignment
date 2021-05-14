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
import com.ratnasagar.acquireassignment.databinding.ActivityMainBinding;
import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.ui.adapter.PhotoListAdapter;
import com.ratnasagar.acquireassignment.ui.viewmodel.MainActivityViewModel;
import com.ratnasagar.acquireassignment.ui.viewmodel.ViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private MainActivityViewModel mainActivityViewModel;
    private SearchView searchView;
    private PhotoListAdapter photoListAdapter;
    List<Photo> photoList;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainActivityViewModel=new ViewModelProvider(this, new ViewModelFactory(this)).get(MainActivityViewModel.class);
        mainBinding.setMainViewModel(mainActivityViewModel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mainBinding.setLifecycleOwner(this);

        mainBinding.progressBar.setVisibility(View.VISIBLE);

        mainActivityViewModel.getUserList().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photoList2) {

                mainBinding.progressBar.setVisibility(View.GONE);
                photoList = photoList2;
                if (photoList!=null && photoList.size()>0)
                {
                    mainBinding.tvNoDataFound.setVisibility(View.GONE);
                    photoListAdapter = new PhotoListAdapter(photoList,MainActivity.this);
                    mainBinding.recyclerList.setAdapter(photoListAdapter);
                }
                else
                {
                    mainBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                }
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Toast.makeText(MainActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                photoList.remove(position);
                if (photoList!=null && photoList.size()>0)
                {
                    mainBinding.tvNoDataFound.setVisibility(View.GONE);
                    photoListAdapter = new PhotoListAdapter(photoList,MainActivity.this);
                    mainBinding.recyclerList.setAdapter(photoListAdapter);
                }
                else
                {
                    mainBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                }
                photoListAdapter.notifyDataSetChanged();

            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mainBinding.recyclerList);
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
                    if(photoList.size() != 0) {
                        photoListAdapter.getFilter().filter(newText);
                    }else{
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(photoList.size() != 0) {
                        photoListAdapter.getFilter().filter(query);
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