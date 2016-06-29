package com.consto.android.rolegenerator;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Moritz on 27.06.2016.
 */
public class NewGameFragment extends Fragment {
    private Spinner spinnerDeck;
    private Spinner spinnerMode;
    private List<CardDeckOneSideDB> allDecks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_fragment, container, false);
        final List<CardDeckOneSideDB> cards = CardDeckOneSideDB.listAll(CardDeckOneSideDB.class);
        allDecks=cards;
        List<String> cardDecks = new ArrayList<>();
        for (CardDeckOneSideDB card : cards) {
            cardDecks.add(card.getName());
        }
        spinnerDeck = (Spinner) view.findViewById(R.id.spinnerDeck);
        spinnerMode = (Spinner) view.findViewById(R.id.spinnerDraftMode);
        ListView showcase = (ListView) view.findViewById(R.id.listViewCardsShowcase);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterDeck = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, cardDecks);
        ArrayAdapter<CharSequence> adapterMode = ArrayAdapter.createFromResource(view.getContext(),
                R.array.modes, android.R.layout.simple_spinner_item);
        final AdapterShowcase adapterShowcase = new AdapterShowcase(view.getContext(),
                R.layout.cards_preview_layout);

        // Specify the layout to use when the list of choices appears
        adapterDeck.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDeck.setAdapter(adapterDeck);
        spinnerMode.setAdapter(adapterMode);
        showcase.setAdapter(adapterShowcase);
        spinnerDeck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CardDeckOneSideDB cardDeck = cards.get(position);
                //TODO ADD Other card types
                HashMap<String, Integer> cardsOfDeck = cardDeck.getCards();
                adapterShowcase.clear();
                for (Map.Entry<String, Integer> e : cardsOfDeck.entrySet()) {
                    adapterShowcase.add(new Pair<String, String>(e.getKey(), e.getValue().toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public String[] getCards(){
        List<String> cards=new ArrayList<>();
        CardDeckOneSideDB deck= allDecks.get(spinnerDeck.getSelectedItemPosition());
        HashMap<String,Integer>cardsCompact=deck.getCards();

        for (Map.Entry<String,Integer> entry:cardsCompact.entrySet()){
            for (int i=0;i<entry.getValue();i++){
                cards.add(entry.getKey());
            }
        }
        Collections.shuffle(cards);
        return cards.toArray( new String[cards.size()]);
    }
    public String getName(){
        CardDeckOneSideDB deck= allDecks.get(spinnerDeck.getSelectedItemPosition());
        return deck.getName();
    }

}