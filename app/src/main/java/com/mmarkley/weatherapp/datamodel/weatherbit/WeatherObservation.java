package com.mmarkley.weatherapp.datamodel.weatherbit;
/*
            {
               "data":[
                  {
                     "wind_cdir":"NE",
                     "rh":59,
                     "pod":"d",
                     "lon":"-78.63861",
                     "pres":1006.6,
                     "timezone":"America\/New_York",
                     "ob_time":"2017-08-28 16:45",
                     "country_code":"US",
                     "clouds":75,
                     "vis":10,
                     "wind_spd":6.17,
                     "wind_cdir_full":"northeast",
                     "app_temp":24.25,
                     "state_code":"NC",
                     "ts":1503936000,
                     "h_angle":0,
                     "dewpt":15.65,
                     "weather":{
                        "icon":"c03d",
                        "code":"803",
                        "description":"Broken clouds"
                     },
                     "uv":2,
                     "aqi":45,
                     "station":"CMVN7",
                     "wind_dir":50,
                     "elev_angle":63,
                     "datetime":"2017-08-28:17",
                     "precip":0,
                     "ghi":444.4,
                     "dni":500,
                     "dhi":120,
                     "solar_rad":350,
                     "city_name":"Raleigh",
                     "sunrise":"10:44",
                     "sunset":"23:47",
                     "temp":24.19,
                     "lat":"35.7721",
                     "slp":1022.2
                  }
               ],
               "count":1
            }
 */
public class WeatherObservation {
    private String wind_cdir;

    private int rh;

    private String pod;

    private double lon;

    private double pres;

    private String timezone;

    private String ob_time;

    private String country_code;

    private double clouds;

    private double vis;

    private double wind_spd;

    private String wind_cdir_full;

    private double app_temp;

    private String state_code;

    private long ts;

    private double h_angle;

    private double dewpt;

    private EmbeddedWeather weather;

    private double uv;

    private double aqi;

    private String station;

    private double wind_dir;

    private double elev_angle;

    private String date_time;

    private double precip;

    private double ghi;

    private double dni;

    private double dhi;

    private double solar_rad;

    private String city_name;

    private String sunrise;

    private String sunset;

    private double temp;

    private double lat;

    private double slp;

    public String getWind_cdir() {
        return wind_cdir;
    }

    public void setWind_cdir(String wind_cdir) {
        this.wind_cdir = wind_cdir;
    }

    public int getRh() {
        return rh;
    }

    public void setRh(int rh) {
        this.rh = rh;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getPres() {
        return pres;
    }

    public void setPres(double pres) {
        this.pres = pres;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getOb_time() {
        return ob_time;
    }

    public void setOb_time(String ob_time) {
        this.ob_time = ob_time;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public double getVis() {
        return vis;
    }

    public void setVis(double vis) {
        this.vis = vis;
    }

    public double getWind_spd() {
        return wind_spd;
    }

    public void setWind_spd(double wind_spd) {
        this.wind_spd = wind_spd;
    }

    public String getWind_cdir_full() {
        return wind_cdir_full;
    }

    public void setWind_cdir_full(String wind_cdir_full) {
        this.wind_cdir_full = wind_cdir_full;
    }

    public double getApp_temp() {
        return app_temp;
    }

    public void setApp_temp(double app_temp) {
        this.app_temp = app_temp;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public double getH_angle() {
        return h_angle;
    }

    public void setH_angle(double h_angle) {
        this.h_angle = h_angle;
    }

    public double getDewpt() {
        return dewpt;
    }

    public void setDewpt(double dewpt) {
        this.dewpt = dewpt;
    }

    public EmbeddedWeather getWeather() {
        return weather;
    }

    public void setWeather(EmbeddedWeather weather) {
        this.weather = weather;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public double getAqi() {
        return aqi;
    }

    public void setAqi(double aqi) {
        this.aqi = aqi;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(double wind_dir) {
        this.wind_dir = wind_dir;
    }

    public double getElev_angle() {
        return elev_angle;
    }

    public void setElev_angle(double elev_angle) {
        this.elev_angle = elev_angle;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public double getGhi() {
        return ghi;
    }

    public void setGhi(double ghi) {
        this.ghi = ghi;
    }

    public double getDni() {
        return dni;
    }

    public void setDni(double dni) {
        this.dni = dni;
    }

    public double getDhi() {
        return dhi;
    }

    public void setDhi(double dhi) {
        this.dhi = dhi;
    }

    public double getSolar_rad() {
        return solar_rad;
    }

    public void setSolar_rad(double solar_rad) {
        this.solar_rad = solar_rad;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getSlp() {
        return slp;
    }

    public void setSlp(double slp) {
        this.slp = slp;
    }
}
/*

 */

