package com.mad.lab02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements DailyOffer.OnFragmentInteractionListener, Reservation.OnFragmentInteractionListener, Home.OnFragmentInteractionListener, Profile.OnFragmentInteractionListener{

    private static final String CheckPREF = "First Run";
    private Profile profile;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item ->  {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();
                    return true;
                case R.id.navigation_dailyoffer:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DailyOffer()).commit();
                    return true;
                case R.id.navigation_reservation:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Reservation()).commit();
                    return true;
            }
            return false;
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home()).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences first_check = getSharedPreferences(CheckPREF, 0);
        Log.d("MAIN","resuming");

        if(first_check.getBoolean("firsRun",false)){
            first_check.edit().clear().commit();
            goHome();
        }
    }


    public void goHome() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.getMenu().getItem(3).setChecked(true);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(0).setChecked(false);
    }
}
