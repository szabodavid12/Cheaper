package com.example.cheaper.constants;

import java.util.ArrayList;
import java.util.HashMap;

public class AuchanConstants {

    public static ArrayList<String> auchanItems = new ArrayList<>();
    public static HashMap<String, String> namePrice = new HashMap<String, String>();
    public static HashMap<String, String> nameImg = new HashMap<String, String>();

    public static void loadAuchanItems(){

        auchanItems.add("Orbit Professional Mints Freshmint mentaízű töltetlen keménycukorka 18 g");
        auchanItems.add("Fanta narancs ízű szénsavas üdítőital cukorral és édesítőszerekkel 330 ml");
        auchanItems.add("XIXO Cola kólaízű szénsavas üdítőital cukorral és édesítőszerrel 250 ml");
        auchanItems.add("Mizo boci uht félzsíros tej 2,8% 200 ml");
        auchanItems.add("Belvita erdei gyümölcsös, gabonás, omlós keksz 50 g");
        auchanItems.add("XIXO Ice Tea eperízű fekete tea 250 ml");
        auchanItems.add("Sport étcsokoládéval mártott rumos ízű kakaós szelet 25 g");

        namePrice.put("Orbit Professional Mints Freshmint mentaízű töltetlen keménycukorka 18 g", "505 Ft"); //205
        namePrice.put("Fanta narancs ízű szénsavas üdítőital cukorral és édesítőszerekkel 330 ml", "319 Ft");
        namePrice.put("XIXO Cola kólaízű szénsavas üdítőital cukorral és édesítőszerrel 250 ml", "169 Ft");
        namePrice.put("Mizo boci uht félzsíros tej 2,8% 200 ml", "165 Ft");
        namePrice.put("Belvita erdei gyümölcsös, gabonás, omlós keksz 50 g", "179 Ft");
        namePrice.put("XIXO Ice Tea eperízű fekete tea 250 ml", "169 Ft");
        namePrice.put("Sport étcsokoládéval mártott rumos ízű kakaós szelet 25 g", "149 Ft");

        nameImg.put("Orbit Professional Mints Freshmint mentaízű töltetlen keménycukorka 18 g", "https://ahuazurewebblob0.azureedge.net/auchan/cache/product_medium/product/7250526/01_302701_front_105113.png.webp");
        nameImg.put("Fanta narancs ízű szénsavas üdítőital cukorral és édesítőszerekkel 330 ml", "https://ahuazurewebblob0.azureedge.net/auchan/cache/product_medium/product/13098448/01_13790_front%20081540.png");
        nameImg.put("XIXO Cola kólaízű szénsavas üdítőital cukorral és édesítőszerrel 250 ml", "https://ahuazurewebblob0.azureedge.net/auchan/cache/product_medium/product/7428185/01_35035103_front%20122514.png");
        nameImg.put("Mizo boci uht félzsíros tej 2,8% 200 ml", "https://ahuazurewebblob0.azureedge.net/auchan/cache/product_medium/product/7247914/01_106479_front_090909.png");
        nameImg.put("Belvita erdei gyümölcsös, gabonás, omlós keksz 50 g", "https://ahuazurewebblob0.azureedge.net/auchan/cache/product_medium/product/14913699/01_34774_front_112612.png");
        nameImg.put("XIXO Ice Tea eperízű fekete tea 250 ml", "https://ahuazurewebblob0.azureedge.net/auchan/cache/product_medium/product/12972958/01_1950370_front_154519.png");
        nameImg.put("Sport étcsokoládéval mártott rumos ízű kakaós szelet 25 g", "https://ahuazurewebblob0.azureedge.net/auchan/cache/product_medium/product/16166572/01_50743_front_144644.png.webp");

    }


    private AuchanConstants(){}
}
