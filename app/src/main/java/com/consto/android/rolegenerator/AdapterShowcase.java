package com.consto.android.rolegenerator;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Moritz on 27.06.2016.
 */
public class AdapterShowcase extends ArrayAdapter<Pair<String,String>> {
    public AdapterShowcase(Context context, int textViewResourceId, ArrayList<Pair<String,String>> items) {
        super(context, textViewResourceId, items);
    }
    public AdapterShowcase(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public View getView(int position, View view, ViewGroup parent) {
        TextView name;
        TextView amount;

        //if (view == null) {
            view = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.cards_preview_layout, parent, false);


             name= (TextView) view.findViewById(R.id.textName);
             amount= (TextView) view.findViewById(R.id.textAmount);


       // } else {
        //   Log.i("Test","Hä");
       // }

        Pair<String,String> item = getItem(position);
        if (item!= null) {

            name.setText(item.first);
            amount.setText(item.second);
        }

        return view;
    }
}
