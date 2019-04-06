package com.magdamiu.archcomponents.ui.items;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.magdamiu.archcomponents.database.Item;
import com.magdamiu.archcomponents.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    private LiveData<List<Item>> mAllItems;

    public ItemViewModel(Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteItem(Item item) {
        mRepository.deleteItem(item);
    }

    public void update(Item item) {
        mRepository.update(item);
    }
}

