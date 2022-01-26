package com.example.restaurantproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Order> {

    private  final LayoutInflater inflater;
    private final ArrayList<Order> orders;

    private static class ViewHolder{
        public TextView foodRowTV;
        public TextView costRowTV;
        public TextView quantityRowTV;
        public TextView priceRowTV;
    }

    public Adapter(Context context, ArrayList<Order> orders) {
        super(context, R.layout.list_row, orders);
        inflater = LayoutInflater.from(context);
        this.orders = orders;
    }

    public int getCount(){
        return orders.size();
    }

    public Order getItem (int position){
        return  orders.get(position);
    }

    public long getToItem (int position){
        return position;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_row, null);
            holder.foodRowTV = convertView.findViewById(R.id.foodRowTV);
            holder.costRowTV = convertView.findViewById(R.id.costRowTV);
            holder.quantityRowTV = convertView.findViewById(R.id.quantityRowTV);
            holder.priceRowTV = convertView.findViewById(R.id.priceRowTV);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.foodRowTV.setText(orders.get(position).getFoodName());
        holder.quantityRowTV.setText(String.valueOf(orders.get(position).getFoodQuantity()));
        holder.costRowTV.setText(String.valueOf(orders.get(position).getCost()));
        holder.priceRowTV.setText(String.valueOf(orders.get(position).getPrice()));
        return convertView;
    }



}
