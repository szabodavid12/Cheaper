package com.example.cheaper.constants;

import java.util.ArrayList;
import java.util.HashMap;

public class TescoConstants {

    public static ArrayList<String> tescoItems = new ArrayList<>();
    public static HashMap<String, String> namePrice = new HashMap<String, String>();
    public static HashMap<String, String> nameImg = new HashMap<String, String>();

    public static void loadAldiItems(){
        tescoItems.add("Orbit Spearmint mentaízű cukormentes rágógumi édesítőszerrel 14 g");
        tescoItems.add("Fanta narancs ízű szénsavas üdítőital cukorral és édesítőszerekkel 330 ml");
        tescoItems.add("XIXO Cola kólaízű szénsavas üdítőital cukorral és édesítőszerrel 250 ml");
        tescoItems.add("Riska UHT tej 2,8% 200 ml");
        tescoItems.add("Belvita törökmogyorós és mézes, gabonás, omlós keksz csokoládédarabokkal 50 g");
        tescoItems.add("Stockwell & Co. aromatizált, filteres eper és birsalma ízű gyümölcstea 20 filter 40 g");
        tescoItems.add("Sport étcsokoládéval mártott rumos ízű kakaós szelet 25 g");

        namePrice.put("Orbit Spearmint mentaízű cukormentes rágógumi édesítőszerrel 14 g", "500 Ft"); //245
        namePrice.put("Fanta narancs ízű szénsavas üdítőital cukorral és édesítőszerekkel 330 ml", "325 Ft");
        namePrice.put("XIXO Cola kólaízű szénsavas üdítőital cukorral és édesítőszerrel 250 ml", "209 Ft");
        namePrice.put("Riska UHT tej 2,8% 200 ml", "150 Ft"); //225
        namePrice.put("Belvita törökmogyorós és mézes, gabonás, omlós keksz csokoládédarabokkal 50 g", "185 Ft");
        namePrice.put("Stockwell & Co. aromatizált, filteres eper és birsalma ízű gyümölcstea 20 filter 40 g", "129 Ft"); //199
        namePrice.put("Sport étcsokoládéval mártott rumos ízű kakaós szelet 25 g", "155 Ft");

        nameImg.put("Orbit Spearmint mentaízű cukormentes rágógumi édesítőszerrel 14 g", "https://secure.ce-tescoassets.com/assets/HU/822/0000050173822/ShotType1_540x540.jpg");
        nameImg.put("Fanta narancs ízű szénsavas üdítőital cukorral és édesítőszerekkel 330 ml", "https://secure.ce-tescoassets.com/assets/HU/805/5449000214805/ShotType1_540x540.jpg");
        nameImg.put("XIXO Cola kólaízű szénsavas üdítőital cukorral és édesítőszerrel 250 ml", "https://secure.ce-tescoassets.com/assets/HU/757/5999885747757/ShotType1_540x540.jpg");
        nameImg.put("Riska UHT tej 2,8% 200 ml", "https://secure.ce-tescoassets.com/assets/HU/711/5998200567711/ShotType1_540x540.jpg");
        nameImg.put("Belvita törökmogyorós és mézes, gabonás, omlós keksz csokoládédarabokkal 50 g", "https://secure.ce-tescoassets.com/assets/HU/217/7622300538217/ShotType1_540x540.jpg");
        nameImg.put("Stockwell & Co. aromatizált, filteres eper és birsalma ízű gyümölcstea 20 filter 40 g", "https://secure.ce-tescoassets.com/assets/HU/048/5051007138048/ShotType1_540x540.jpg");
        nameImg.put("Sport étcsokoládéval mártott rumos ízű kakaós szelet 25 g", "https://secure.ce-tescoassets.com/assets/HU/318/0000059905318/ShotType1_540x540.jpg");

    }



    private TescoConstants(){}
}
