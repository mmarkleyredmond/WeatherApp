# WeatherApp
Simple Weather application that the user can enter a location and see the weather at that location
There are several ways to identify the location:
1) Click on the map. This will load information based on the Latitude and Longitude returned by the
map when the user clicks.
2) Click the floating button. This will bring up a search box. You can type in a city name or you can
type in a zipcode. Zipcode handling is primitive, so it doesn't deal with +4 zipcodes

## How it works.
The application makes use of Google Maps, Google Maps Geocoding, MetaWeather.com data, and WeatherBit.io data

### Google Maps
This is used to display a marker where the weather information is being retrieved for
### MetaWeather.com
This used to retrieve Latitude and Longitude for search strings.
### WeatherBit.io
This used to retrieve the current and forecast weather information
### Google Geocoding
This used to retrieve information for a zipcode that can then be passed to WeatherBit.io
for the details

## Details
The current weather is shown as a window floating above the map. There is a button in the window that, when clicked, retrieves the 14 day (actually 16 day) extended forecast for the location.

There is a floating button on the lower right, that, when clicked, hides the current forecast and brings up a search window. The user can type a name or a zipcode into the the edit field. When the hit the enter key, the name/zipcode will be searched for.
If it was a zipcode and the information is retrieved, then a marker is placed on the map at that location, the maps is zoomed to that location and the current weather is retrieved.

The user can also click on the `Go` button to initiate the search instead of pressing `Enter` on the soft keyboard.
The user can also choose to click on the map. An attempt will be made to get the current weather for that location, if it fails, nothing happens.

## Things that got left out
1. No unit tests.
2. No functional tests.




