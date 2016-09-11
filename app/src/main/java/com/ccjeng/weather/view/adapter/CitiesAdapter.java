package com.ccjeng.weather.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ccjeng.weather.R;
import com.ccjeng.weather.model.City;
import com.ccjeng.weather.utils.Formatter;
import com.mikepenz.iconics.view.IconicsImageView;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/9/9.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    boolean compact = false;
    private ArrayList<City> cities;
    private LayoutInflater inflater;
    private Context context;
    //Weather it should show temperature in celsius or not
    private boolean celsius = true;

    public CitiesAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cities = new ArrayList<>();
        this.context = context;
/*
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String syncConnPref = sharedPref.getString(SettingsActivity.PREF_UNIT, "celsius");
        if (syncConnPref.equals("celsius")) {
            showTemperaturesInCelsius();
        } else {
            showTemperaturesInFahrenheit();
        }

        compact = sharedPref.getBoolean(SettingsActivity.GRIDVIEW, false);
        */
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public void showTemperaturesInCelsius() {
        celsius = true;
        notifyDataSetChanged();
    }

    public void showTemperaturesInFahrenheit() {
        celsius = false;
        notifyDataSetChanged();
    }

    public void addCity(City city) {
        cities.add(city);
        notifyItemInserted(cities.size() - 1);
    }

    public void updateCity(City city) {
        int cityIndex = cities.indexOf(city);
        if (cityIndex == -1) return;
        notifyItemChanged(cityIndex);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.city_view, parent, false);
        /*
        if (compact) {
            view = inflater.inflate(R.layout.city_view_compact, parent, false);
        } else {
            view = inflater.inflate(R.layout.city_view, parent, false);
        }*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        City city = cities.get(position);

        holder.cityName.setText(city.getName());

        if (cities.get(position).getCityWeather().getCurrently() != null) {
          //  holder.view.setBackgroundColor(city.getCityWeather().getCurrently().getColor(context));

            holder.icon.setIcon(city.getCityWeather().getCurrently().getIconImage(context));
            holder.icon.setColor(city.getCityWeather().getCurrently().getIconColor(context));

            double temperature = city.getCityWeather().getCurrently().getTemperature();

            holder.temp.setText(Formatter.formatTemperature(temperature,celsius)  + "°");
            holder.temp.setVisibility(View.VISIBLE);
            holder.icon.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
        } else {
            holder.view.setBackgroundColor(Color.GRAY);
            holder.temp.setVisibility(View.GONE);
            holder.icon.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
        }
/*
        if (city.getState() == City.STATE_FETCHING) {
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.progressBar.setVisibility(View.GONE);
        }
  */
    }

    @Override
    public int getItemViewType(int position) {
        return compact? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void onItemDismiss(int adapterPosition) {
        cities.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView temp;
        LinearLayout view;
        ProgressBar progressBar;
        IconicsImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = (LinearLayout) itemView.findViewById(R.id.container);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            temp = (TextView) itemView.findViewById(R.id.temp);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            icon = (IconicsImageView) itemView.findViewById(R.id.icon);
        }
    }

    public void setCompactView(boolean compact) {
        this.compact = compact;
    }
}
