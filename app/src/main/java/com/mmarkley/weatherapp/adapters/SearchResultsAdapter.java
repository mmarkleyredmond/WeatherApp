package com.mmarkley.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mmarkley.weatherapp.R;
import com.mmarkley.weatherapp.activities.MapsActivity;
import com.mmarkley.weatherapp.datamodel.WeatherSearchResult;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsViewHolder> {

    private MapsActivity activity;
    private LayoutInflater layoutInflater;
    private List<WeatherSearchResult> searchResults;

    public SearchResultsAdapter(MapsActivity activity, List<WeatherSearchResult> results) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        this.searchResults = results;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.search_result_layout, parent, false);
        return new SearchResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position) {
        if(0 <= position && position < searchResults.size()) {
            WeatherSearchResult result = searchResults.get(position);
            if(null != result) {
                holder.title.setText(result.getTitle());
                holder.mainView.setTag(result);
                holder.mainView.setOnClickListener(v -> {
                    WeatherSearchResult item = (WeatherSearchResult)holder.mainView.getTag();
                    if(null != item) {
                        activity.navigateTo(item);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(null != searchResults) {
            return searchResults.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
