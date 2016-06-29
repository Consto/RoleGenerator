package com.consto.android.rolegenerator;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Moritz on 19.06.2016.
 */
public class CreateDeckOneSide extends Fragment {
    private LinearLayout rolesLinearLayout;
    private List<View> rows = new ArrayList<View>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_one_side_deck_fragment, container, false);
        this.view = view;
        rolesLinearLayout = (LinearLayout) view.findViewById(R.id.scrollViewLinearLayout);
        addEmptyRow(inflater, rolesLinearLayout);
        return view;
    }


    private void addEmptyRow(final LayoutInflater inflater, final LinearLayout rolesLinearLayout) {
        final View row = inflater.inflate(R.layout.one_side_row_layout, rolesLinearLayout, false);
        EditText name = (EditText) row.findViewById(R.id.editTextName);
        ImageButton delete = (ImageButton) row.findViewById(R.id.buttonDelete);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(((EditText) rows.get(rows.size() - 1).findViewById(R.id.editTextName)).getText().toString())) {
                    addEmptyRow(inflater, rolesLinearLayout);
                }


            }


        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rows.size() != 1) {
                    rolesLinearLayout.removeView(row);
                    rows.remove(row);
                }
            }
        });

        rows.add(row);
        rolesLinearLayout.addView(row);
    }

    public HashMap<String, Integer> getCards() {
        HashMap<String, Integer> cards = new HashMap<>();
        if (getName() == "") {
            ((EditText) view.findViewById(R.id.editTextDeckName)).setError("Please select a name");
            return new HashMap<>();
        } else {
            for (View row : rows) {
                String text = ((EditText) row.findViewById(R.id.editTextName)).getText().toString();
                String amount = ((EditText) row.findViewById(R.id.editTextAmount)).getText().toString();
                if (text.length() != 0) {
                    if (amount.length() != 0) {
                        cards.put(text, Integer.valueOf(amount));
                    } else {
                        cards.put(text, 1);
                    }
                }

            }
            return cards;
        }
    }

    public String getName() {
        if (TextUtils.isEmpty(((EditText) view.findViewById(R.id.editTextDeckName)).getText().toString())) {
            return "";
        }
        return ((EditText) view.findViewById(R.id.editTextDeckName)).getText().toString();
    }
}