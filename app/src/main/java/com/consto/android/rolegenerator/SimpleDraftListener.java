package com.consto.android.rolegenerator;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Moritz on 29.06.2016.
 */
public class SimpleDraftListener implements OnClickListener {
    private  String[] cards;
    private  FloatingActionButton nextButton;
    private  TextView text;
    private int counter = 0;
    public SimpleDraftListener(FloatingActionButton next, TextView text,String[] cards){
        this.nextButton=next;
        this.text=text;
        this.cards=cards;
    }

    @Override
    public void onClick(View v) {
       int id= v.getId();
        if(id==nextButton.getId()){
            if (counter<cards.length) {
                text.setText("Tap card to view role");
            }else{
                text.setText("All cards have been shown");
            }
            nextButton.setVisibility(View.INVISIBLE);

        }else if(nextButton.getVisibility() != View.VISIBLE&&counter<cards.length){
            nextButton.setVisibility(View.VISIBLE);
            text.setText(cards[counter]);
            counter++;
        }

    }
}
