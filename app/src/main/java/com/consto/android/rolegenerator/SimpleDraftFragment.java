package com.consto.android.rolegenerator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Moritz on 27.06.2016.
 */
public class SimpleDraftFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_draft_fragment, container, false);
        TextView textViewCard = (TextView) view.findViewById(R.id.textViewCard);
        FloatingActionButton nextButton = (FloatingActionButton) view.findViewById(R.id.buttonNextCard);
        Bundle extras = getArguments();
        String[] cards = extras.getStringArray("cards");
        String name = extras.getString("name");

        View card = view.findViewById(R.id.card);
        textViewCard.setText("Tap card to view role");
        SimpleDraftListener listener=new SimpleDraftListener(nextButton, textViewCard, cards);
        card.setOnClickListener(listener);
        nextButton.setOnClickListener(listener);
        return view;
    }


}
