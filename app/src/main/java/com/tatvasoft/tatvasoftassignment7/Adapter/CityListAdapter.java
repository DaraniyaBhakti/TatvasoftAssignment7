package com.tatvasoft.tatvasoftassignment7.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.tatvasoftassignment7.DatabaseHelper.Database;
import com.tatvasoft.tatvasoftassignment7.R;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListViewHolder>{

    ArrayList<String> cityNamesList = new ArrayList<>();

    public ClickListener clickListener;

    public CityListAdapter(ClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public CityListAdapter.CityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.city_list_row,parent,false);
        return new CityListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityListAdapter.CityListViewHolder holder, int position) {

        holder.savedCityTextView.setText(cityNamesList.get(position));
        holder.deleteImg.setOnClickListener(view -> {
            Database db = new Database(view.getContext());
            db.deleteByName(cityNamesList.get(position));
            cityNamesList.remove(cityNamesList.get(position));
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(view -> clickListener.CityClickListener(cityNamesList.get(position)));

    }

    @Override
    public int getItemCount() {
        return cityNamesList.size();
    }

    public static class CityListViewHolder extends RecyclerView.ViewHolder {
            TextView savedCityTextView;
            ImageView deleteImg;
        public CityListViewHolder(@NonNull View itemView) {
                super(itemView);
                savedCityTextView= itemView.findViewById(R.id.savedCityTextView);
                deleteImg = itemView.findViewById(R.id.deleteImg);
            }
    }



    public ArrayList<String> getCityNamesList() {
        return cityNamesList;
    }

    public void setCityNamesList(ArrayList<String> cityNamesList) {
        this.cityNamesList = cityNamesList;
    }
    public interface ClickListener{
        void CityClickListener(String str);
    }

}
