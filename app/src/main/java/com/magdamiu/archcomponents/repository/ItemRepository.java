package com.magdamiu.archcomponents.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.magdamiu.archcomponents.database.Item;
import com.magdamiu.archcomponents.database.ItemDao;
import com.magdamiu.archcomponents.database.ItemRoomDatabase;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;

    public ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    public void insert(Item item) {
        new InsertAsyncTask(mItemDao).execute(item);
    }

    public void update(Item item)  {
        new UpdateItemAsyncTask(mItemDao).execute(item);
    }

    public void deleteAll()  {
        new DeleteAllItemsAsyncTask(mItemDao).execute();
    }

    public void deleteItem(Item item) {
        new DeleteItemAsyncTask(mItemDao).execute(item);
    }

    private static class InsertAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao mAsyncTaskDao;

        InsertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAllItemsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao mAsyncTaskDao;

        DeleteAllItemsAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao mAsyncTaskDao;

        DeleteItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.deleteItem(params[0]);
            return null;
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao mAsyncTaskDao;

        UpdateItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
