package com.example.cheaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    private ArrayList<ShoppingItem> shoppingItems;
    private ArrayList<Grocery> groceries;
    private Context context;

    public GroceryAdapter(ArrayList<ShoppingItem> shoppingItems, Context context, ArrayList<Grocery> groceries) {
        this.shoppingItems = shoppingItems;
        this.groceries = groceries;
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_stores, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryAdapter.ViewHolder holder, int position) {



        Grocery grocery = groceries.get(position);
        holder.groceryName.setText(grocery.getGroceryName());
        Picasso.get().load(grocery.getGroceryLogo()).into(holder.groceryImage);
        holder.groceryPrice.setText(grocery.getSumPrice());


    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView groceryImage;
        TextView groceryName;
        TextView groceryPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groceryImage = itemView.findViewById(R.id.groceryImageView);
            groceryName = itemView.findViewById(R.id.groceryNameTextView);
            groceryPrice = itemView.findViewById(R.id.groceryPricesTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {Intent intent = new Intent(context, ShoppingItemListActivity.class);
            intent.putExtra("shoppingItems", getItemsFromThisGrocery(shoppingItems, (String) groceryName.getText()));
            context.startActivity(intent);

        }
    }

    private ArrayList<ShoppingItem> getItemsFromThisGrocery(ArrayList<ShoppingItem> shoppingItems ,String groceryName){
        ArrayList<ShoppingItem> itemsFromThisGrocery = new ArrayList<>();
        for (ShoppingItem item: shoppingItems){
            if (Objects.equals(item.getGroceryName(), groceryName)){
                itemsFromThisGrocery.add(item);
            }
        }
        return itemsFromThisGrocery;
    }

}
