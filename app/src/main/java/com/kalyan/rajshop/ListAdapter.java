package com.kalyan.rajshop;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String itemName;
    private String subItem;
    private String price;
    private Context mContext;
    private List<lists> li;
    public ListAdapter(Context mContext, String itemName, String subItem,String price){
        this.itemName= itemName;
        this.subItem= subItem;
        this.price = price;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listsample,parent,false);
        return new ListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String itemName = li.get(position).getItemName();
        String subItem = li.get(position).getSubItem();
        String price = li.get(position).getPrice();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView itemName,subItem,price;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            subItem = itemView.findViewById(R.id.subItem);
            price = itemView.findViewById(R.id.price);
        }
    }
}
