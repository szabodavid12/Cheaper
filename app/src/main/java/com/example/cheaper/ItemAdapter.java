package com.example.cheaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<ShoppingItem> shoppingItems;
    private Context context;

    public ItemAdapter(ArrayList<ShoppingItem> shoppingItems, Context context) {
        this.shoppingItems = shoppingItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        ShoppingItem shoppingItem = shoppingItems.get(position);
        Picasso.get().load(shoppingItem.getItemImageUrl().isEmpty() ? null : shoppingItem.getItemImageUrl()).into(holder.itemImage);
        holder.itemName.setText(shoppingItem.getItemName());
        holder.itemPrice.setText(shoppingItem.getItemPrice());
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImageView);
            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemPrice = itemView.findViewById(R.id.itemPriceTextView);
        }
    }

}
