package com.example.cheaper.constants;



import java.util.ArrayList;
import java.util.HashMap;

public class AldiConstants {
    public static ArrayList<String> aldiItems = new ArrayList<>();
    public static HashMap<String, String> namePrice = new HashMap<String, String>();
    public static HashMap<String, String> nameImg = new HashMap<String, String>();

    public static void loadAldiItems(){
        aldiItems.add("Orbit winterfrost rágógumi, 14 g");
        aldiItems.add("Szénsavas üdítőital Fanta narancs, 2 liter");
        aldiItems.add("Coca-Cola Zero colaízű energiamentes szénsavas üdítőital édesítőszerekkel 500 ml");
        aldiItems.add("MILFINA UHT tej 2,8% zsírtartalom csavarkupakkal, 1 liter");
        aldiItems.add("GYŐRI Belvita JóReggelt! Kakaós keksz, 50 g");
        aldiItems.add("Jegestea zöldtea-citrom, 0,5 l");
        aldiItems.add("Sport étcsokoládéval mártott rumos ízű kakaós szelet 31 g");

        namePrice.put("Orbit winterfrost rágógumi, 14 g", "519 Ft"); //219
        namePrice.put("Szénsavas üdítőital Fanta narancs, 2 liter", "609 Ft");
        namePrice.put("Coca-Cola Zero colaízű energiamentes szénsavas üdítőital édesítőszerekkel 500 ml", "349 Ft");
        namePrice.put("MILFINA UHT tej 2,8% zsírtartalom csavarkupakkal, 1 liter", "219 Ft");
        namePrice.put("GYŐRI Belvita JóReggelt! Kakaós keksz, 50 g", "179 Ft");
        namePrice.put("Jegestea zöldtea-citrom, 0,5 l", "229 Ft");
        namePrice.put("Sport étcsokoládéval mártott rumos ízű kakaós szelet 31 g", "149 Ft");

        nameImg.put("Orbit winterfrost rágógumi, 14 g", "https://huproductimages.blob.core.windows.net/interface/large/cd7bdfaa494f4858bf4a911996e187bd");
        nameImg.put("Szénsavas üdítőital Fanta narancs, 2 liter", "https://huproductimages.blob.core.windows.net/interface/large/67c53bab76d1488b82a04a26227a1a0e");
        nameImg.put("Coca-Cola Zero colaízű energiamentes szénsavas üdítőital édesítőszerekkel 500 ml", "https://huproductimages.blob.core.windows.net/interface/large/f50db87abee94c0aa3da9b39dd13944a");
        nameImg.put("MILFINA UHT tej 2,8% zsírtartalom csavarkupakkal, 1 liter", "https://huproductimages.blob.core.windows.net/interface/large/c66a5dbf76bb47ccb4112ff622c6a57c");
        nameImg.put("GYŐRI Belvita JóReggelt! Kakaós keksz, 50 g", "https://huproductimages.blob.core.windows.net/interface/large/af0be5009ce84c4b961c8e97ec13b79c");
        nameImg.put("Jegestea zöldtea-citrom, 0,5 l", "https://huproductimages.blob.core.windows.net/interface/large/086e86e585354a9bafce019a53dfba8b");
        nameImg.put("Sport étcsokoládéval mártott rumos ízű kakaós szelet 31 g", "https://huproductimages.blob.core.windows.net/interface/large/ff893ba68ce6409a85df77528ce2b39e");


    }

    private AldiConstants(){}
}
