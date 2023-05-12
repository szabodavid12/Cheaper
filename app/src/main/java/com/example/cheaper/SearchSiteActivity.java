package com.example.cheaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class SearchSiteActivity extends AppCompatActivity {

    EditText shoppingListItemsEditText;
    EditText groceryNumberForSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_site);
        shoppingListItemsEditText = findViewById(R.id.shoppingListItemsEditTextXml);
        groceryNumberForSearchEditText = findViewById(R.id.numberOfGroceryEditTextXml);
        groceryNumberForSearchEditText.setText("1");

    }

    public void onFindTheBestPrices(View view) {
        Intent intent = new Intent(this, GroceryStoresActivity.class);
        intent.putExtra("itemNames", shoppingListItemsEditText.getText().toString().split("\n"));
        intent.putExtra("groceryNumber", groceryNumberForSearchEditText.getText().toString());
        startActivity(intent);
    }

}