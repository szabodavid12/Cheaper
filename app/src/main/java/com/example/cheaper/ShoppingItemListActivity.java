package com.example.cheaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingItemListActivity extends AppCompatActivity {

    ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_item_list);

        recyclerView = findViewById(R.id.itemListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingItems = (ArrayList<ShoppingItem>) getIntent().getSerializableExtra("shoppingItems");
        itemAdapter = new ItemAdapter(shoppingItems, this);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }



}