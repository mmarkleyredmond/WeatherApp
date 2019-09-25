package com.mmarkley.weatherapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mmarkley.weatherapp.R;

public class SearchResultsViewHolder extends RecyclerView.ViewHolder {

    View mainView;
    TextView title;

    SearchResultsViewHolder(@NonNull View itemView) {
        super(itemView);
        mainView = itemView;
        title = itemView.findViewById(R.id.search_result_title);
    }
}
