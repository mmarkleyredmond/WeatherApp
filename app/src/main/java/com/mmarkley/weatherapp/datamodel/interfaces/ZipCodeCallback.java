package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.ZipCodeResponse;

public interface ZipCodeCallback {
    void onZipCodeLookupSuccess(ZipCodeResponse response);
    void onZipCodeLookupFailure(Throwable throwable);
}
