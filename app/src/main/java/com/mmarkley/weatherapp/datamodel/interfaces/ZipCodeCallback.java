package com.mmarkley.weatherapp.datamodel.interfaces;

public interface ZipCodeCallback {
    void onZipCodeLookupSuccess();
    void onZipCodeLookupFailure();
}
