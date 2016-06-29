package com.consto.android.rolegenerator;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moritz on 20.06.2016.
 */
public class CardDeckOneSideDB extends SugarRecord {

//    private HashMap<String, Integer> cards;
    private String cardsStr;
    private String name;

    public CardDeckOneSideDB() {

    }

    public CardDeckOneSideDB(String name, HashMap<String, Integer> cards) {
        this.name = name;
        cardsStr = cards.toString();
        this.cardsStr = cardsStr.substring(1, cardsStr.length() - 1).replaceAll("\\s+", "");

        Log.i("Test", cardsStr);

    }

    public HashMap<String, Integer> getCards() {
        HashMap<String, Integer> cards = new HashMap<>();
        String[] entries = cardsStr.split(",");

        for (String e : entries) {
            String[] keyValue = e.split("=");
            cards.put(keyValue[0], Integer.valueOf(keyValue[1]));
        }
        return cards;
    }

    public String getName() {
        return name;
    }
}
