package com.tatvasoft.tatvasoftassignment7.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.tatvasoft.tatvasoftassignment7.Fragment.CityListFragment;
import com.tatvasoft.tatvasoftassignment7.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        CityListFragment cityListFragment = new CityListFragment();
        fragmentManager.beginTransaction().add(R.id.containerMain,cityListFragment)
                .commit();


    }
}