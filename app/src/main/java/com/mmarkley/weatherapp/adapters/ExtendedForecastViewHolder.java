package com.mmarkley.weatherapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mmarkley.weatherapp.R;

class ExtendedForecastViewHolder extends RecyclerView.ViewHolder {

    View mainView;
    TextView minTempValue;
    TextView maxTempValue;
    ImageView weatherIcon;
    TextView precipitation;
    TextView description;
    TextView date;

    ExtendedForecastViewHolder(@NonNull View itemView) {
        super(itemView);
        mainView = itemView;
        mainView.setTag(this);
        maxTempValue = itemView.findViewById(R.id.extended_forecast_max_temp_value);
        minTempValue = itemView.findViewById(R.id.extended_forecast_min_temp_value);
        weatherIcon = itemView.findViewById(R.id.extended_weather_icon);
        precipitation = itemView.findViewById(R.id.extended_forecast_chance_of_precipitation_value);
        description = itemView.findViewById(R.id.extended_forecast_description_value);
        date = itemView.findViewById(R.id.extended_forecast_date_value);
    }
}
