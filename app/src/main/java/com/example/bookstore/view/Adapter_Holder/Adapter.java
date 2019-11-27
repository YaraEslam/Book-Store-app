package com.example.bookstore.view.Adapter_Holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.network.model.ItemModel;


import java.util.List;


public class Adapter extends RecyclerView.Adapter<Holder> {

    private List<ItemModel> itemModels;
    private Context context;
    private onItemClicked onItemClicked;
    public Enum layout;

    public Adapter( List<ItemModel> itemModels, Context context,onItemClicked onItemClicked, Enum layout) {
        this.context = context;
        this.itemModels = itemModels;
        this.onItemClicked = onItemClicked;
        this.layout = layout;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        switch (layout){
            case Home:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_home, parent,false);
                break;
            case User:
            case Featured:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent,false);
                break;
        }
            return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.bind(itemModels.get(position), context, layout);
        holder.itemView.setOnClickListener(view -> onItemClicked.onClick(position));

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }
}
