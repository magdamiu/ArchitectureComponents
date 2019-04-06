package com.magdamiu.archcomponents.ui.items;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.magdamiu.archcomponents.database.Item;
import com.magdamiu.archcomponents.R;
import com.magdamiu.archcomponents.ui.additem.AddTaskActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 11;
    public static final int UPDATE_ITEM_ACTIVITY_REQUEST_CODE = 22;

    public static final String EXTRA_DATA_UPDATE_ITEM = "extra_item_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private ItemViewModel mItemViewModel;

    private RecyclerView mRecyclerViewItems;
    private ItemsAdapter mItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        initView();

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable final List<Item> items) {
                mItemsAdapter.setItems(items);
            }
        });

        setItemHelper(mItemsAdapter);
    }

    private void initView() {
        mRecyclerViewItems = findViewById(R.id.recycler_view_list);
        mItemsAdapter = new ItemsAdapter(this);
        mRecyclerViewItems.setAdapter(mItemsAdapter);
        mRecyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        mItemsAdapter.setOnItemClickListener(new ItemsAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Item item = mItemsAdapter.getItemAtPosition(position);
                launchUpdateItemActivity(item);
            }
        });
    }

    private void setItemHelper(final ItemsAdapter adapter) {
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Item myItem = adapter.getItemAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_item) + " " +
                                        myItem.getName(), Toast.LENGTH_LONG).show();

                        mItemViewModel.deleteItem(myItem);
                    }
                });
        helper.attachToRecyclerView(mRecyclerViewItems);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            mItemViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Item item = new Item(data.getStringExtra(AddTaskActivity.EXTRA_REPLY));
            mItemViewModel.insert(item);
        } else if (requestCode == UPDATE_ITEM_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String item_data = data.getStringExtra(AddTaskActivity.EXTRA_REPLY);
            int id = data.getIntExtra(AddTaskActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                mItemViewModel.update(new Item(id, item_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateItemActivity(Item item) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_ITEM, item.getName());
        intent.putExtra(EXTRA_DATA_ID, item.getId());
        startActivityForResult(intent, UPDATE_ITEM_ACTIVITY_REQUEST_CODE);
    }

    public void addNewItemOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);
    }
}