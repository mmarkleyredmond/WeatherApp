package com.mmarkley.weatherapp.datamodel.types;

public enum SearchResultType {
    CITY("City"),
    REGION("Region"),
    STATE("State"),
    PROVINCE("Province"),
    COUNTRY("Country"),
    CONTINENT("Continent");

    private final String someString;

    private SearchResultType(String s) {
        someString = s;
    }
    public static SearchResultType fromString(String s) {
        switch (s) {
            case "City":
                return CITY;
            case "Region":
                return REGION;
            case "State":
                return STATE;
            case "Province":
                return PROVINCE;
            case "Country":
                return COUNTRY;
            case "Continent":
                return CONTINENT;
            default:
                return null;
        }
    }

    public String getSomeString() {
        return someString;
    }
}
