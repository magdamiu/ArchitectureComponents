package com.magdamiu.archcomponents.ui.items;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magdamiu.archcomponents.database.Item;
import com.magdamiu.archcomponents.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Item> mItems;
    private static ClickListener clickListener;

    ItemsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            holder.itemItemView.setText(current.getName());
        } else {
            holder.itemItemView.setText(R.string.no_item);
        }
    }

    void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }

    public Item getItemAtPosition(int position) {
        return mItems.get(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemItemView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            itemItemView = itemView.findViewById(R.id.text_view_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ItemsAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}

