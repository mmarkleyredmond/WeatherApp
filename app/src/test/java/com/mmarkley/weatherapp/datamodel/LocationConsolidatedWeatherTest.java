package com.mmarkley.weatherapp.datamodel;

import org.json.JSONObject;
import org.junit.Test;

public class LocationConsolidatedWeatherTest {

    private static final String testString = "{\"id\":5179068351774720,\"weather_state_name\":\"Heavy Cloud\",\"weather_state_abbr\":\"hc\",\"wind_direction_compass\":\"WSW\",\"created\":\"2019-09-25T03:12:16.718702Z\",\"applicable_date\":\"2019-09-24\",\"min_temp\":18.45,\"max_temp\":26.855,\"the_temp\":29.37,\"wind_speed\":2.98508551509584,\"wind_direction\":244.79982993115448,\"air_pressure\":1010.54,\"humidity\":63,\"visibility\":15.120446591903285,\"predictability\":71}";
    @Test
    public void fromJSONObject() throws Exception {
        JSONObject jsonObject = new JSONObject(testString);
        LocationConsolidatedWeather weather = LocationConsolidatedWeather.fromJSONObject(jsonObject);
    }
}