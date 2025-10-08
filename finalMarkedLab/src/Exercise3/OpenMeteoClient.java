package Exercise3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class OpenMeteoClient {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private static String weatherJson;
    private static String weatherForecastJson;
    private static double lat;
    private static double lon;
    private static double latF;
    private static double lonF;

    // HashMap to store weather data for cities and assign key/value pairings for each city/weather data
    private static HashMap<String, String> cityWeatherCache = new HashMap<>();
    // HashMap to store weather data for cities and assign key/value pairings for each city/forecast data
    private static HashMap<String, String> cityForecastCache = new HashMap<>();

    private static String getWeather(double lat, double lon) throws Exception {
        String params = String.format(
                "latitude=%.4f&longitude=%.4f&current=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m",
                lat, lon
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?" + params))
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
    }

    // updated this code for efficency using hashmaps and cache
    public static void updateCurrentWeather(String city) throws Exception {
        // checks if weather data is inside of the cache
        if (cityWeatherCache.containsKey(city)) {
            // if in cache, use the stored data
            weatherJson = cityWeatherCache.get(city);
        } else {
            // if it isn't in cache, fetch the data
            setCoords(city, false);
            weatherJson = getWeather(lat, lon);
            // store the data in cache for future usage
            cityWeatherCache.put(city, weatherJson);
        }
    }

    public static String getCurrentTemperature()  {
        return weatherJson.split("\"temperature_2m\":")[2].split(",")[0];
    }

    public static String getCurrentPrecipitation() {
        return weatherJson.split("\"precipitation\":")[2].split(",")[0];
    }

    public static String getCurrentHumidity() {
        return weatherJson.split("\"relative_humidity_2m\":")[2].split(",")[0];
    }

    public static String getCurrentWind() {
        return weatherJson.split("\"wind_speed_10m\":")[2].split(",")[0].replace("}","");
    }


    public static void updateForecast(String city) throws Exception {
        // Check if forecast data for this city is already cached
        if (cityForecastCache.containsKey(city)) {
            weatherForecastJson = cityForecastCache.get(city);
        } else {
            setCoords(city, true);
            weatherForecastJson = getForecastWeather(latF, lonF);
            // Cache the forecast data for the city
            cityForecastCache.put(city, weatherForecastJson);
        }
    }


    public static String[] getForecastTime() {
        int timeStart = weatherForecastJson.indexOf("\"time\":[") + "\"time\":[".length();
        int timeEnd = weatherForecastJson.indexOf("]", timeStart);
        String timeStr = weatherForecastJson.substring(timeStart, timeEnd);
        return timeStr.replace("[", "").replace("]", "").replace("\"", "").split(",");
    }

    public static String[] getForecastTemperature() {
        int tempStart = weatherForecastJson.indexOf("\"temperature_2m\":[") + "\"temperature_2m\":[".length();
        int tempEnd = weatherForecastJson.indexOf("]", tempStart);
        String tempStr = weatherForecastJson.substring(tempStart, tempEnd);
        return tempStr.replace("[", "").replace("]", "").split(",");
    }

    // method to return the temperature at a specific time
    public static String getTemperatureAtTime(String targetTime) {
        // arrays to store all the times and temperatures
        String[] forecastTimes = getForecastTime();
        String[] forecastTemperatures = getForecastTemperature();

        // loops through all the time entries to find one that matches the time given
        for (int i = 0; i < forecastTimes.length; i++) {
            // if the time at the index fits the one given, return the temperature at that same index
            if (forecastTimes[i].equals(targetTime)) {
                return forecastTemperatures[i] + " °C";
            }
        }
        // error if no match is found
        return "Error";
    }

    public static String[] getForecastPrecipitation() {
        int precipStart = weatherForecastJson.indexOf("\"precipitation\":[") + "\"precipitation\":[".length();
        int precipEnd = weatherForecastJson.indexOf("]", precipStart);
        String precipStr = weatherForecastJson.substring(precipStart, precipEnd);
        return precipStr.replace("[", "").replace("]", "").split(",");
    }

    // method to return the precipitation at a specific time
    public static String getPrecipitationAtTime(String targetTime) {
        // arrays to store all the times and precipitation values
        String[] forecastTimes = getForecastTime();
        String[] forecastPrecipitation = getForecastPrecipitation();

        // loops through all the time entries to find one that matches the time given
        for (int i = 0; i < forecastTimes.length; i++) {
            // if the time at the index fits the one given, return the precipitation at that same index
            if (forecastTimes[i].equals(targetTime)) {
                return forecastPrecipitation[i] + " mm";
            }
        }
        // error if no match is found
        return "Error";
    }

    public static String[] getForecastWind() {
        int windStart = weatherForecastJson.indexOf("\"wind_speed_10m\":[") + "\"wind_speed_10m\":[".length();
        int windEnd = weatherForecastJson.indexOf("]", windStart);
        String windStr = weatherForecastJson.substring(windStart, windEnd);
        return windStr.replace("[", "").replace("]", "").split(",");
    }

    // method to return the precipitation at a specific time
    public static String getWindAtTime(String targetTime) {
        // arrays to store all the times and precipitation values
        String[] forecastTimes = getForecastTime();
        String[] forecastWind = getForecastWind();

        // loops through all the time entries to find one that matches the time given
        for (int i = 0; i < forecastTimes.length; i++) {
            // if the time at the index fits the one given, return the precipitation at that same index
            if (forecastTimes[i].equals(targetTime)) {
                return forecastWind[i] + " km/h";
            }
        }
        // error if no match is found
        return "Error";
    }

    public static String[] getForecastHumidity() {
        int humStart = weatherForecastJson.indexOf("\"relative_humidity_2m\":[") + "\"relative_humidity_2m\":[".length();
        int humEnd = weatherForecastJson.indexOf("]", humStart);
        String humStr = weatherForecastJson.substring(humStart, humEnd);
        return humStr.replace("[", "").replace("]", "").replace("\"", "").split(",");
    }

    // method to return the humidity at a specific time
    public static String getHumidityAtTime(String targetTime) {
        // arrays to store all the times and humidity values
        String[] forecastTimes = getForecastTime();
        String[] forecastHumidity = getForecastHumidity();

        // loops through all the time entries to find one that matches the time given
        for (int i = 0; i < forecastTimes.length; i++) {
            // if the time at the index fits the one given, return the humidity at that same index
            if (forecastTimes[i].equals(targetTime)) {
                return forecastHumidity[i] + " %";
            }
        }
        // error if no match is found
        return "Error";
    }

    private static String getForecastWeather(double lat, double lon) throws Exception {
        String params = String.format(
                "latitude=%.4f&longitude=%.4f&hourly=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m",
                lat, lon
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?" + params))
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
    }


    private static void setCoords(String city, boolean forecast) throws Exception{
        String geoJson = searchLocation(city);
        String coords = geoJson.split("\"latitude\":")[1].split(",")[0]
                + ":"
                + geoJson.split("\"longitude\":")[1].split(",")[0];
        double[] returnCoords = new double[2];
        if (forecast){
            latF = Double.parseDouble(coords.split(":")[0]); // lat
            lonF = Double.parseDouble(coords.split(":")[1]); // lon
        }
        else{
            lat = Double.parseDouble(coords.split(":")[0]); // lat
            lon = Double.parseDouble(coords.split(":")[1]); // lon
        }
    }

    private static String searchLocation(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
    }
}
