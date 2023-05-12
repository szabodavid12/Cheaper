package com.example.cheaper;

import static com.example.cheaper.constants.GroceryConstants.ALDI_GROCERY_LOGO;
import static com.example.cheaper.constants.GroceryConstants.ALDI_GROCERY_NAME;
import static com.example.cheaper.constants.GroceryConstants.AUCHAN_GROCERY_LOGO;
import static com.example.cheaper.constants.GroceryConstants.AUCHAN_GROCERY_NAME;
import static com.example.cheaper.constants.GroceryConstants.COMBINED_GROCERY_LOGO;
import static com.example.cheaper.constants.GroceryConstants.COOP_GROCERY_LOGO;
import static com.example.cheaper.constants.GroceryConstants.COOP_GROCERY_NAME;
import static com.example.cheaper.constants.GroceryConstants.ITEM_NOT_FOUND_TEXT;
import static com.example.cheaper.constants.GroceryConstants.LINK_FOR_NOT_FOUND_ITEM;
import static com.example.cheaper.constants.GroceryConstants.SPAR_GROCERY_LOGO;
import static com.example.cheaper.constants.GroceryConstants.SPAR_GROCERY_NAME;
import static com.example.cheaper.constants.GroceryConstants.TESCO_GROCERY_LOGO;
import static com.example.cheaper.constants.GroceryConstants.TESCO_GROCERY_NAME;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheaper.constants.AldiConstants;
import com.example.cheaper.constants.AuchanConstants;
import com.example.cheaper.constants.TescoConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GroceryStoresActivity extends AppCompatActivity {

    private ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();
    private ArrayList<Grocery> groceries = new ArrayList<>();
    private ProgressBar progressBar;
    private GroceryAdapter groceryAdapter;
    private RecyclerView recyclerView;
    MyJavaScriptInterface jInterface = new MyJavaScriptInterface();
    public String html;

    int sizeOfJsoupData;

    int counter = 0;

    int maxSizeOfShoppingListItems;
    String nameTemp = "";
    String[] shoppingListItems;
    int groceryNumbersForSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_stores);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.groceryStoresRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groceryAdapter = new GroceryAdapter(shoppingItems, this, groceries);
        recyclerView.setAdapter(groceryAdapter);

        shoppingListItems = getIntent().getStringArrayExtra("itemNames");
        groceryNumbersForSearch = Integer.parseInt(getIntent().getStringExtra("groceryNumber"));
        maxSizeOfShoppingListItems = shoppingListItems.length;

        findFromSpar(shoppingListItems, 0);

        GroceryStoresActivity.Content content = new GroceryStoresActivity.Content();
        content.execute();

    }

    public class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(GroceryStoresActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(GroceryStoresActivity.this, android.R.anim.fade_out));
            groceryAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            findTheBestPrices();

            return null;
        }
    }

    private void findTheBestPrices(){
        String[] shoppingListItems = getIntent().getStringArrayExtra("itemNames");

        for (String item : shoppingListItems){
            String itemNameFromSearch = item;
            itemNameFromSearch = itemNameFromSearch.replaceAll("\\s+$", "");
            String[] splittedItemName = itemNameFromSearch.split(" ");

            findFromCoop(itemNameFromSearch, splittedItemName);
            findFromAldi(itemNameFromSearch);
            findFromTesco(itemNameFromSearch);
            findFromAuchan(itemNameFromSearch);
        }


        HashMap<String, Integer> priceNameOfGrocery = new HashMap<String, Integer>();
        HashMap<String, Integer> priceNameOfGroceryWithoutSpar = new HashMap<String, Integer>();


        int sumOfItemPricesSpar = getSumOfItemPrices(shoppingItems, SPAR_GROCERY_NAME);
        priceNameOfGrocery.put(SPAR_GROCERY_NAME, sumOfItemPricesSpar);

        int sumOfItemPricesCoop = getSumOfItemPrices(shoppingItems, COOP_GROCERY_NAME);
        priceNameOfGrocery.put(COOP_GROCERY_NAME, sumOfItemPricesCoop);

        int sumOfItemPricesAldi = getSumOfItemPrices(shoppingItems, ALDI_GROCERY_NAME);
        priceNameOfGrocery.put(ALDI_GROCERY_NAME, sumOfItemPricesAldi);

        int sumOfItemPricesTesco = getSumOfItemPrices(shoppingItems, TESCO_GROCERY_NAME);
        priceNameOfGrocery.put(TESCO_GROCERY_NAME, sumOfItemPricesTesco);

        int sumOfItemPricesAuchan = getSumOfItemPrices(shoppingItems, AUCHAN_GROCERY_NAME);
        priceNameOfGrocery.put(AUCHAN_GROCERY_NAME, sumOfItemPricesAuchan);

        priceNameOfGrocery = (HashMap<String, Integer>) sortByValue(priceNameOfGrocery);

        priceNameOfGroceryWithoutSpar = (HashMap<String, Integer>) priceNameOfGrocery.clone();
        priceNameOfGroceryWithoutSpar.remove("Spar");


        for (String groceryName : priceNameOfGrocery.keySet()){

            if (Objects.equals(groceryName, SPAR_GROCERY_NAME)){
                groceries.add(new Grocery(SPAR_GROCERY_NAME, sumOfItemPricesSpar + " HUF", SPAR_GROCERY_LOGO));
            }
            if (Objects.equals(groceryName, COOP_GROCERY_NAME)){
                groceries.add(new Grocery(COOP_GROCERY_NAME, sumOfItemPricesCoop + " HUF", COOP_GROCERY_LOGO));
            }
            if (Objects.equals(groceryName, ALDI_GROCERY_NAME)){
                groceries.add(new Grocery(ALDI_GROCERY_NAME, sumOfItemPricesAldi + " HUF", ALDI_GROCERY_LOGO));
            }
            if (Objects.equals(groceryName, TESCO_GROCERY_NAME)){
                groceries.add(new Grocery(TESCO_GROCERY_NAME, sumOfItemPricesTesco + " HUF", TESCO_GROCERY_LOGO));
            }
            if (Objects.equals(groceryName, AUCHAN_GROCERY_NAME)){
                groceries.add(new Grocery(AUCHAN_GROCERY_NAME, sumOfItemPricesAuchan + " HUF", AUCHAN_GROCERY_LOGO));
            }

        }
        String firstGroceryName = (String) priceNameOfGroceryWithoutSpar.keySet().toArray()[0];
        String secGroceryName = (String) priceNameOfGroceryWithoutSpar.keySet().toArray()[1];
        String thirdGroceryName = (String) priceNameOfGroceryWithoutSpar.keySet().toArray()[2];
        String forthGroceryName = (String) priceNameOfGroceryWithoutSpar.keySet().toArray()[3];

        ArrayList<ShoppingItem> shoppingItemsFirstGrocery = new ArrayList<>();
        ArrayList<ShoppingItem> shoppingItemsSecGrocery = new ArrayList<>();
        ArrayList<ShoppingItem> shoppingItemsThirdGrocery = new ArrayList<>();
        ArrayList<ShoppingItem> shoppingItemsForthGrocery = new ArrayList<>();

        getAllShoppingItemFromThisGrocery(firstGroceryName, shoppingItems, shoppingItemsFirstGrocery);
        getAllShoppingItemFromThisGrocery(secGroceryName, shoppingItems, shoppingItemsSecGrocery);
        getAllShoppingItemFromThisGrocery(thirdGroceryName, shoppingItems, shoppingItemsThirdGrocery);
        getAllShoppingItemFromThisGrocery(forthGroceryName, shoppingItems, shoppingItemsForthGrocery);


        ArrayList<ShoppingItem> combinedShopingItems = new ArrayList<>();
        if (groceryNumbersForSearch == 2){

            for (int i = 0; i < maxSizeOfShoppingListItems; i++) {

                if (Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0])) {
                    combinedShopingItems.add(
                            new ShoppingItem(
                            firstGroceryName + " - " + shoppingItemsFirstGrocery.get(i).getItemName(),
                            shoppingItemsFirstGrocery.get(i).getItemPrice(),
                            shoppingItemsFirstGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    firstGroceryName + " - " + shoppingItemsFirstGrocery.get(i).getItemName(),
                                    shoppingItemsFirstGrocery.get(i).getItemPrice(),
                                    shoppingItemsFirstGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName));
                } else {
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    secGroceryName + " - " + shoppingItemsSecGrocery.get(i).getItemName(),
                                    shoppingItemsSecGrocery.get(i).getItemPrice(),
                                    shoppingItemsSecGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    secGroceryName + " - " + shoppingItemsSecGrocery.get(i).getItemName(),
                                    shoppingItemsSecGrocery.get(i).getItemPrice(),
                                    shoppingItemsSecGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName));
                }
            }

            int sumOfCombinedItemsPrices = getSumOfItemPrices(combinedShopingItems);
            groceries.add(new Grocery(firstGroceryName + " - " + secGroceryName, String.valueOf(sumOfCombinedItemsPrices + " HUF"),COMBINED_GROCERY_LOGO));
        }

        if (groceryNumbersForSearch == 3){

            for (int i = 0; i < maxSizeOfShoppingListItems; i++) {

                if (Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0])
                        &&  Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsThirdGrocery.get(i).getItemPrice().split(" ")[0])) {
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    firstGroceryName + " - " + shoppingItemsFirstGrocery.get(i).getItemName(),
                                    shoppingItemsFirstGrocery.get(i).getItemPrice(),
                                    shoppingItemsFirstGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    firstGroceryName + " - " + shoppingItemsFirstGrocery.get(i).getItemName(),
                                    shoppingItemsFirstGrocery.get(i).getItemPrice(),
                                    shoppingItemsFirstGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName));
                } else if (Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0])
                        &&  Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsThirdGrocery.get(i).getItemPrice().split(" ")[0])) {
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    secGroceryName + " - " + shoppingItemsSecGrocery.get(i).getItemName(),
                                    shoppingItemsSecGrocery.get(i).getItemPrice(),
                                    shoppingItemsSecGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    secGroceryName + " - " + shoppingItemsSecGrocery.get(i).getItemName(),
                                    shoppingItemsSecGrocery.get(i).getItemPrice(),
                                    shoppingItemsSecGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName));
                } else {
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    thirdGroceryName + " - " + shoppingItemsThirdGrocery.get(i).getItemName(),
                                    shoppingItemsThirdGrocery.get(i).getItemPrice(),
                                    shoppingItemsThirdGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    thirdGroceryName + " - " + shoppingItemsThirdGrocery.get(i).getItemName(),
                                    shoppingItemsThirdGrocery.get(i).getItemPrice(),
                                    shoppingItemsThirdGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName));
                }
            }

            int sumOfCombinedItemsPrices = getSumOfItemPrices(combinedShopingItems);
            groceries.add(new Grocery(firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName, String.valueOf(sumOfCombinedItemsPrices + " HUF"),COMBINED_GROCERY_LOGO));
        }

        if (groceryNumbersForSearch == 4){

            for (int i = 0; i < maxSizeOfShoppingListItems; i++) {

                if (Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0])
                        && Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsThirdGrocery.get(i).getItemPrice().split(" ")[0])
                        && Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsForthGrocery.get(i).getItemPrice().split(" ")[0])) {
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    firstGroceryName + " - " + shoppingItemsFirstGrocery.get(i).getItemName(),
                                    shoppingItemsFirstGrocery.get(i).getItemPrice(),
                                    shoppingItemsFirstGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    firstGroceryName + " - " + shoppingItemsFirstGrocery.get(i).getItemName(),
                                    shoppingItemsFirstGrocery.get(i).getItemPrice(),
                                    shoppingItemsFirstGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));
                } else if (Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0])
                        && Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsThirdGrocery.get(i).getItemPrice().split(" ")[0])
                        && Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsForthGrocery.get(i).getItemPrice().split(" ")[0])) {
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    secGroceryName + " - " + shoppingItemsSecGrocery.get(i).getItemName(),
                                    shoppingItemsSecGrocery.get(i).getItemPrice(),
                                    shoppingItemsSecGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    secGroceryName + " - " + shoppingItemsSecGrocery.get(i).getItemName(),
                                    shoppingItemsSecGrocery.get(i).getItemPrice(),
                                    shoppingItemsSecGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));
                } else if (Integer.parseInt(shoppingItemsThirdGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsFirstGrocery.get(i).getItemPrice().split(" ")[0])
                        && Integer.parseInt(shoppingItemsThirdGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsSecGrocery.get(i).getItemPrice().split(" ")[0])
                        && Integer.parseInt(shoppingItemsThirdGrocery.get(i).getItemPrice().split(" ")[0]) < Integer.parseInt(shoppingItemsForthGrocery.get(i).getItemPrice().split(" ")[0])){
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    thirdGroceryName + " - " + shoppingItemsThirdGrocery.get(i).getItemName(),
                                    shoppingItemsThirdGrocery.get(i).getItemPrice(),
                                    shoppingItemsThirdGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    thirdGroceryName + " - " + shoppingItemsThirdGrocery.get(i).getItemName(),
                                    shoppingItemsThirdGrocery.get(i).getItemPrice(),
                                    shoppingItemsThirdGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));

                } else {
                    combinedShopingItems.add(
                            new ShoppingItem(
                                    forthGroceryName + " - " + shoppingItemsForthGrocery.get(i).getItemName(),
                                    shoppingItemsForthGrocery.get(i).getItemPrice(),
                                    shoppingItemsForthGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));
                    shoppingItems.add(
                            new ShoppingItem(
                                    forthGroceryName + " - " + shoppingItemsForthGrocery.get(i).getItemName(),
                                    shoppingItemsForthGrocery.get(i).getItemPrice(),
                                    shoppingItemsForthGrocery.get(i).getItemImageUrl(),
                                    firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName));
                }

            }

            int sumOfCombinedItemsPrices = getSumOfItemPrices(combinedShopingItems);
            groceries.add(new Grocery(firstGroceryName + " - " + secGroceryName + " - " + thirdGroceryName + " - " + forthGroceryName, String.valueOf(sumOfCombinedItemsPrices + " HUF"),COMBINED_GROCERY_LOGO));
        }

    }

    public ArrayList<ShoppingItem> getAllShoppingItemFromThisGrocery(String groceryName, ArrayList<ShoppingItem> shoppingItems, ArrayList<ShoppingItem> minimalShoppingItems) {

        for (ShoppingItem shoppingItem : shoppingItems) {
            if (Objects.equals(shoppingItem.getGroceryName(), groceryName)) {
                minimalShoppingItems.add(shoppingItem);
            }
        }
        return null;
    }

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(Map.Entry.comparingByValue());
        }

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private void findFromCoop(String itemNameFromSearch, String[] splittedItemName) {
        String groceryName = "Coop";

        try {
            String url = "https://cooponline.hu/?s=" + itemNameFromSearch +
                    "&dgwt-wcas-search-submit=&post_type=product&product_orderby=price";
            Document document = Jsoup.connect(url).get();

            Elements data = document.select("div.fusion-product-wrapper");

            String itemNameInsideHtml = "";
            String itemPrice = "";
            String itemImageUrl = "";
            int size = data.size();

            for (int i = 0; i < size; i++) {

                for (int j = 0; j < splittedItemName.length; j++) {
                    itemNameInsideHtml = data
                            .select("h3.product-title")
                            .eq(i)
                            .text();
                    Pattern pattern = Pattern.compile("([\\s]" +
                            splittedItemName[j] +
                            "\\s)|" +
                            "(^" + splittedItemName[j] + ")|" +
                            "(" + splittedItemName[j] + "$)", Pattern.CASE_INSENSITIVE);

                    Matcher matcher = pattern.matcher(itemNameInsideHtml);
                    boolean findOrNot = matcher.find();
                    if (findOrNot && j == splittedItemName.length - 1) {
                        itemPrice = data
                                .select("div.fusion-price-rating")
                                .eq(i)
                                .text();
                        itemImageUrl = data
                                .select("div.featured-image")
                                .select("img")
                                .eq(i)
                                .attr("src");
                        shoppingItems.add(new ShoppingItem(
                                itemNameInsideHtml,
                                itemPrice,
                                itemImageUrl,
                                groceryName
                        ));
                        return;
                    } else if (!findOrNot && i == size - 1) {
                        shoppingItems.add(new ShoppingItem(
                                itemNameFromSearch + ITEM_NOT_FOUND_TEXT,
                                "0",
                                LINK_FOR_NOT_FOUND_ITEM,
                                groceryName
                        ));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String itemNameFromSearch = "";

    private void findFromSpar(String[] shoppingListItems, int i) {
        itemNameFromSearch = shoppingListItems[i];
        itemNameFromSearch = itemNameFromSearch.replaceAll("\\s+$", "");

        WebView webView;
        webView = findViewById(R.id.webViewXml);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

                if (counter < shoppingListItems.length){
                    findFromSpar(shoppingListItems, counter);
                }

            }

        });

        webView.addJavascriptInterface(jInterface, "HTMLOUT");
        webView.loadUrl("https://www.spar.hu/onlineshop/search/?query=" + itemNameFromSearch + "&q=" + itemNameFromSearch + "&hitsPerPage=36&page=1&sort=best-price:asc&substringFilter=pos-visible:6108207");

    }

    private void findFromAldi(String itemNameFromSearch){
        String groceryName = "Aldi";

        String itemNameInsideHtml = "";
        String itemPrice = "";
        String itemImageUrl = "";

        for (String item : AldiConstants.aldiItems) {
            if (item.toLowerCase().contains(itemNameFromSearch.toLowerCase())){
                itemNameInsideHtml = item;
                itemPrice = AldiConstants.namePrice.get(itemNameInsideHtml);
                itemImageUrl = AldiConstants.nameImg.get(itemNameInsideHtml);
                break;
            }
        }
        if (!itemNameInsideHtml.equals("")){
            shoppingItems.add(new ShoppingItem(itemNameInsideHtml, itemPrice, itemImageUrl, groceryName));
        } else {
            shoppingItems.add(new ShoppingItem(itemNameFromSearch + ITEM_NOT_FOUND_TEXT, "0", LINK_FOR_NOT_FOUND_ITEM, groceryName));
        }

    }

    private void findFromTesco(String itemNameFromSearch){
        String groceryName = "Tesco";

        String itemNameInsideHtml = "";
        String itemPrice = "";
        String itemImageUrl = "";

        for (String item : TescoConstants.tescoItems) {
            if (item.toLowerCase().contains(itemNameFromSearch.toLowerCase())){
                itemNameInsideHtml = item;
                itemPrice = TescoConstants.namePrice.get(itemNameInsideHtml);
                itemImageUrl = TescoConstants.nameImg.get(itemNameInsideHtml);
                break;
            }
        }
        if (!itemNameInsideHtml.equals("")){
            shoppingItems.add(new ShoppingItem(itemNameInsideHtml, itemPrice, itemImageUrl, groceryName));
        } else {
            shoppingItems.add(new ShoppingItem(itemNameFromSearch + ITEM_NOT_FOUND_TEXT, "0", LINK_FOR_NOT_FOUND_ITEM, groceryName));
        }

    }

    private void findFromAuchan(String itemNameFromSearch){
        String groceryName = "Auchan";

        String itemNameInsideHtml = "";
        String itemPrice = "";
        String itemImageUrl = "";

        for (String item : AuchanConstants.auchanItems) {
            if (item.toLowerCase().contains(itemNameFromSearch.toLowerCase())){
                itemNameInsideHtml = item;
                itemPrice = AuchanConstants.namePrice.get(itemNameInsideHtml);
                itemImageUrl = AuchanConstants.nameImg.get(itemNameInsideHtml);
                break;
            }
        }
        if (!itemNameInsideHtml.equals("")){
            shoppingItems.add(new ShoppingItem(itemNameInsideHtml, itemPrice, itemImageUrl, groceryName));
        } else {
            shoppingItems.add(new ShoppingItem(itemNameFromSearch + ITEM_NOT_FOUND_TEXT, "0", LINK_FOR_NOT_FOUND_ITEM, groceryName));
        }

    }


    public int getSumOfItemPrices(ArrayList<ShoppingItem> shoppingItems, String groceryName) {
        int sumOfPrices = 0;
        for (ShoppingItem item : shoppingItems) {
            if (item.getGroceryName() == groceryName) {
                if (item.getItemPrice() != "") {
                    int itemPrice = Integer.parseInt(item.getItemPrice().replace(".", "").split(" ")[0]);
                    sumOfPrices += itemPrice;
                }
            }
        }
        return sumOfPrices;
    }

    public int getSumOfItemPrices(ArrayList<ShoppingItem> shoppingItems) {
        int sumOfPrices = 0;
        for (ShoppingItem item : shoppingItems) {
            if (item.getItemPrice() != "") {
                int itemPrice = Integer.parseInt(item.getItemPrice().replace(".", "").split(" ")[0]);
                sumOfPrices += itemPrice;
            }

        }
        return sumOfPrices;
    }


    public class MyJavaScriptInterface {
        @JavascriptInterface
        public void processHTML(String _html) {
            html = _html;

            Document document = Jsoup.parse(html);
            Elements data = document.select("div.productBox");

            sizeOfJsoupData = data.size();
            if (sizeOfJsoupData == 0){
                counter++;
            }
            if (sizeOfJsoupData != 0) {
                for (int i = 0; i < 1; i++){
                    String groceryName = "Spar";
                    String itemNameInsideHtml = data.select("h3.productTitle").eq(i).text();

                    if (!Objects.equals(nameTemp, itemNameInsideHtml)){
                        nameTemp = itemNameInsideHtml;
                        String itemPrice = data.select("div.actualPriceContainer.digit3").eq(i).text();
                        String itemImageUrl = data.select("div.productBoxImage").select("img").eq(i).attr("src");
                        ShoppingItem shoppingItem = new ShoppingItem(itemNameInsideHtml, itemPrice, itemImageUrl, groceryName);
                        shoppingItems.add(shoppingItem);
                        counter++;
                    }
                }
            }


        }
    }

}
