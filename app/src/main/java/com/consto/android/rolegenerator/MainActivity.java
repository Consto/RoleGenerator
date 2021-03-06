package com.consto.android.rolegenerator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private NewCardsFragment createGameFragment;
    private CreateDeckOneSide createDeckOneSideFragment;
    private NewGameFragment newGameFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();
        StartPageFragment startPage = new StartPageFragment();
        fragmentManager.beginTransaction().add(R.id.container_content, startPage).commit();
        createGameFragment = new NewCardsFragment();
        createDeckOneSideFragment = new CreateDeckOneSide();
        newGameFragment = new NewGameFragment();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_content, createGameFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_play) {
            List<CardDeckOneSideDB> sets = CardDeckOneSideDB.listAll(CardDeckOneSideDB.class);
            Log.i("MainActivity", "" + sets.size());
            if (sets.size() == 0) {
                Toast.makeText(getBaseContext(), "Create a card set first", Toast.LENGTH_LONG).show();

            } else {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_content, newGameFragment);
                fragmentTransaction.commit();
                for (CardDeckOneSideDB i : sets) {
                    Log.i("MainActivity", "Name: " + i.getName() + " --- " + i.getCards());
                }
            }

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buttonNext(View view) {
        int id = view.getId();
        if (id == R.id.buttonNextNewCards) {
            Log.i("test", "parent: " + view.getParent().toString());
            Spinner gameType = (Spinner) findViewById(R.id.spinnerGameType);
            fragmentManager.beginTransaction().replace(R.id.container_content, createDeckOneSideFragment).commit();
        } else if (id == R.id.buttonNextOneCard1) {

            HashMap<String, Integer> cards = createDeckOneSideFragment.getCards();
            if (cards.size() > 1) {
                CardDeckOneSideDB cardDeck = new CardDeckOneSideDB(createDeckOneSideFragment.getName(), cards);
                cardDeck.save();
                Toast.makeText(getBaseContext(), "Card Deck saved", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.buttonNextNewGame) {
            SimpleDraftFragment simpleDraftFragment = new SimpleDraftFragment();
            Bundle extras =new Bundle();
            extras.putStringArray("cards",newGameFragment.getCards());
            extras.putString("name",newGameFragment.getName());
            simpleDraftFragment.setArguments(extras);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_content, simpleDraftFragment);
            fragmentTransaction.commit();
        }


    }
}
