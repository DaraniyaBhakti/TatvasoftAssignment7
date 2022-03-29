package com.tatvasoft.tatvasoftassignment7.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.tatvasoftassignment7.Adapter.CityListAdapter;
import com.tatvasoft.tatvasoftassignment7.DatabaseHelper.Database;
import com.tatvasoft.tatvasoftassignment7.R;
import com.tatvasoft.tatvasoftassignment7.Utils.Constant;

import java.util.ArrayList;
import java.util.Objects;

public class CityListFragment extends Fragment  implements CityListAdapter.ClickListener{

    private final ArrayList<String> cityNamesList = new ArrayList<>();
    private CityListAdapter  cityListAdapter = new CityListAdapter(this);
    private Database database;
    private TextView tvNoCityFound;
    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(getContext());
        getDataByCursor();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.title_city_list));
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cityNamesList.clear();
        getDataByCursor();
        tvNoDataFoundVisibility();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNoCityFound = view.findViewById(R.id.tvNoCityFound);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cityListAdapter = new CityListAdapter(this);
        cityListAdapter.setCityNamesList(cityNamesList);
        recyclerView.setAdapter(cityListAdapter);
        cityListAdapter.notifyDataSetChanged();
        tvNoDataFoundVisibility();
    }

    public void getDataByCursor()
    {
        Cursor dataCursor = database.getData();
        if (dataCursor.getCount() != 0){
            while (dataCursor.moveToNext()){
                if(!cityNamesList.contains(dataCursor.getString(1))) {
                    cityNamesList.add(dataCursor.getString(1));
                    cityListAdapter.notifyDataSetChanged();
                }
            }

        }
    }
    @Override
    public void CityClickListener(String str) {
        CityFragment fragment = new CityFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CITY_NAME,str);
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerMain,fragment)
                .addToBackStack(null)
                .commit();
    }
    public void tvNoDataFoundVisibility()
    {
        if((cityListAdapter.getCityNamesList().size()) == 0 ){
            tvNoCityFound.setVisibility(View.VISIBLE);
        }else {
            tvNoCityFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain,new GoogleMapFragment())
                .addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
    }

}