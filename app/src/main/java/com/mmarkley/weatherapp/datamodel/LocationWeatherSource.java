package com.mmarkley.weatherapp.datamodel;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class LocationWeatherSource {
    private String title;
    private String sourceUrl;
    private String slug;
    private int crawlRate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getCrawlRate() {
        return crawlRate;
    }

    public void setCrawlRate(int crawlRate) {
        this.crawlRate = crawlRate;
    }

    public static LocationWeatherSource fromJSONObject(@NonNull JSONObject jsonObject) {
        LocationWeatherSource source = new LocationWeatherSource();
        try {
            if(jsonObject.has("title")) {
                source.setTitle(jsonObject.getString("title"));
            }
            if(jsonObject.has("url")) {
                source.setTitle(jsonObject.getString("url"));
            }
            if(jsonObject.has("slug")) {
                source.setSlug(jsonObject.getString("slug"));
            }
            if(jsonObject.has("crawl_rate")) {
                source.setCrawlRate(jsonObject.getInt("crawl_rate"));
            }
        } catch(Exception ignored) {
            source = null;
        }

        return source;
    }
}
