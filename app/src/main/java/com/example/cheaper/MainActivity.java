package com.example.cheaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cheaper.constants.AldiConstants;
import com.example.cheaper.constants.AuchanConstants;
import com.example.cheaper.constants.TescoConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AldiConstants.loadAldiItems();
        TescoConstants.loadAldiItems();
        AuchanConstants.loadAuchanItems();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToNewShoppingAct(View view) {
        Intent intent = new Intent(this, SearchSiteActivity.class);
        startActivity(intent);
    }
}
