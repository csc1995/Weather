package com.ccjeng.weather.view.activity.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ccjeng.weather.R;
import com.ccjeng.weather.model.City;
import com.ccjeng.weather.view.adapter.WeatherCurrentAdapter;

/**
 * Created by andycheng on 2016/9/11.
 */
public class CurrentView {

    private final String TAG = this.getClass().getSimpleName();

    private View view;
    private Context context;
    private City city;

    private RecyclerView todayRecyclerView;

    public CurrentView(View view, Context context, City city) {
        this.view = view;
        this.context = context;
        this.city = city;

        todayRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(context));

    }


    public void setData() {
        WeatherCurrentAdapter adapter = new WeatherCurrentAdapter(city);
        todayRecyclerView.setAdapter(adapter);
    }
}
