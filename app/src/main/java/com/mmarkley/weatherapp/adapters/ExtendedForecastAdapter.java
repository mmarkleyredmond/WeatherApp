package com.mmarkley.weatherapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mmarkley.weatherapp.R;
import com.mmarkley.weatherapp.datamodel.weatherbit.DailyForecastWeather;
import com.mmarkley.weatherapp.datamodel.weatherbit.EmbeddedWeather;

import java.util.List;

public class ExtendedForecastAdapter extends RecyclerView.Adapter<ExtendedForecastViewHolder> {

    private List<DailyForecastWeather> dailyForecastWeather;
    Context context;
    private LayoutInflater layoutInflater;

    public ExtendedForecastAdapter(@NonNull Context context, List<DailyForecastWeather> results) {
        this.dailyForecastWeather = results;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExtendedForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.extended_forecast, parent, false);

        return new ExtendedForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtendedForecastViewHolder holder, int position) {
        if (0 <= position && position < dailyForecastWeather.size() - 1) {
            DailyForecastWeather dailyWeather = dailyForecastWeather.get(position);
            if (null != dailyWeather) {
                holder.precipitation.setText(String.valueOf(dailyWeather.getPrecip()));
                holder.minTempValue.setText(String.valueOf(dailyWeather.getMin_temp()));
                holder.maxTempValue.setText(String.valueOf(dailyWeather.getMax_temp()));
                holder.date.setText(dailyWeather.getValid_date());
                EmbeddedWeather embeddedWeather = dailyWeather.getWeather();
                if (null != embeddedWeather) {
                    String uri = "@drawable/" + embeddedWeather.getIcon();  // where myresource (without the extension) is the file

                    int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());

                    Drawable res = context.getResources().getDrawable(imageResource);
                    holder.weatherIcon.setImageDrawable(res);
                    holder.description.setText(embeddedWeather.getDescription());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (null != dailyForecastWeather) {
            return dailyForecastWeather.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
