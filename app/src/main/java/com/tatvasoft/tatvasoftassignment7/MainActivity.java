package com.tatvasoft.tatvasoftassignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.optionMenuMap)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerMain,new GoogleMapFragment())
                    .addToBackStack(null).commit();
        }
        return super.onOptionsItemSelected(item);
    }


}