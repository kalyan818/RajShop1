package com.kalyan.rajshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    private ArrayList<String> itemName = new ArrayList();
    private ArrayList<String> subItem = new ArrayList();
    private ArrayList<String> price = new ArrayList();
    private Context mContext;

    public SampleAdapter(ArrayList<String> itemName, ArrayList<String> subItem, ArrayList<String> price, Context mContext) {
        this.itemName = itemName;
        this.subItem = subItem;
        this.price = price;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listsample,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(itemName.get(position));
        holder.subItem.setText(subItem.get(position));
        holder.price.setText(price.get(position));
    }

    @Override
    public int getItemCount() {
        return itemName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,subItem,price;
        RelativeLayout parentlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            subItem = itemView.findViewById(R.id.subItem);
            price = itemView.findViewById(R.id.price);
        }
    }
}
