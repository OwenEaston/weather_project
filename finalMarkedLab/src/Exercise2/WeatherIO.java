package Exercise2;

import java.io.*;
import java.util.*;

public class WeatherIO {

    // stores the weather data list as a field inside this class
    private final ArrayList<WeatherData> weatherDataList = new ArrayList<>();

    // read the csv file
    public void readWeatherCSV(String fileName) {
        weatherDataList.clear();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader(fileName));
            // skips the header row
            scanner.nextLine();

            // reads each line of the csv file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");  // splits by commas

                // gets the relevant information from the columns in csv
                int year = Integer.parseInt(tokens[1].trim());
                int month = Integer.parseInt(tokens[2].trim());
                double maxTemp = Double.parseDouble(tokens[3].trim());
                double minTemp = Double.parseDouble(tokens[4].trim());
                double rainAmount = Double.parseDouble(tokens[6].trim());
                double meanTemp = Double.parseDouble(tokens[10].trim());

                // creates a WeatherData object and adds relevant info
                WeatherData weatherData = new WeatherData(year, month, maxTemp, minTemp, meanTemp, rainAmount);
                weatherDataList.add(weatherData);
            }
        } catch (IOException e) {
            System.out.println("Cannot find the CSV file.");
            System.out.println("Terminating program.");
            System.exit(0);

        } finally {
            // closes scanner
            if (scanner != null) {
                scanner.close();
            }
        }
    }


    public ArrayList<WeatherData> getAllWeatherData() {
        return weatherDataList;
    }

    public ArrayList<WeatherData> searchWeatherData(int year) {
        // makes an arraylist which we will be filtering
        ArrayList<WeatherData> filteredList = new ArrayList<>();

        // iterates through each entry, checking if the year is equal to the parameter and adds to the list if it is
        for (WeatherData weatherData : weatherDataList) {
            if (weatherData.getYear() == year) {
                filteredList.add(weatherData);
            }
        }
        return filteredList;
    }

    public Map<Integer, Double> getAverageRainfallByYear() {
        // defining variables to find the rain amount each year
        Map<Integer, Double> totalRainfallByYear = new HashMap<>();
        Map<Integer, Integer> amountOfYears = new HashMap<>();
        Map<Integer, Double> averageRainfallByYear = new HashMap<>();

        // for loop goes over every row in the data list
        for (WeatherData weatherData : weatherDataList) {
            int year = weatherData.getYear();
            double rainAmount = weatherData.getRainAmount();

            // calculates the amount of years that appear (e.g. there could be 7 rows from 2012)
            // first checks if the year in the row is already part of the amountOfYears hashmap, if it is, add 1 to the count
            // amountOfYears holds the value of the year as the key, and the amount of time it appears in the data list
            if (totalRainfallByYear.containsKey(year)) {
                totalRainfallByYear.put(year, totalRainfallByYear.get(year) + rainAmount);
                amountOfYears.put(year, amountOfYears.get(year) + 1);
            }
            // if the year ISN'T in the hash map, add the year and 1 to the count
            else {
                totalRainfallByYear.put(year, rainAmount);
                amountOfYears.put(year, 1);
            }
        }

        // loops over every year stored in totalRainfallByYear
        for (int year : totalRainfallByYear.keySet()) {
            // gets the total rainfall for the current year
            double totalRainfall = totalRainfallByYear.get(year);
            // gets the amount of years for the current year
            int countYears = amountOfYears.get(year);
            // calculates the average
            double averageRainfall = totalRainfall / countYears;

            // stores result in hashmap
            averageRainfallByYear.put(year, averageRainfall);
        }
        return averageRainfallByYear;
    }

    public Map<Integer, Double> getAverageTemperatureByYear() {
        // hashmaps to store total temperature and count for each year
        Map<Integer, Double> averageTemperatureByYear = new HashMap<>();
        Map<Integer, Double> totalTemperatureByYear = new HashMap<>();
        Map<Integer, Integer> amountOfYears = new HashMap<>();

        // loops over data list
        for (WeatherData weatherData : weatherDataList) {
            int year = weatherData.getYear();
            double meanTemp = weatherData.getMeanTemp();

            // if the year already exists, then add 1 onto the count value
            if (totalTemperatureByYear.containsKey(year)) {
                totalTemperatureByYear.put(year, totalTemperatureByYear.get(year) + meanTemp);
                amountOfYears.put(year, amountOfYears.get(year) + 1);
            } else {
                // if it's the first occurrence of this year, add it to the list and 1
                totalTemperatureByYear.put(year, meanTemp);
                amountOfYears.put(year, 1);
            }
        }

        // calculates the average temperature by year
        for (int year : totalTemperatureByYear.keySet()) {
            // gets the total temperature for current year
            double totalTemperature = totalTemperatureByYear.get(year);
            // gets the count of the years of current year
            int count = amountOfYears.get(year);
            // calculates the average
            double averageTemperature = totalTemperature / count;

            // stores results in variable
            averageTemperatureByYear.put(year, averageTemperature);
        }

        return averageTemperatureByYear;
    }

    // this method is used to calculate and return the top 3 weather data (coldest/hottest depending on asc/desc)
    public List<Map.Entry<Integer, Double>> getTop3WeatherData(boolean isAscending) {
        // gets the average temperature by year using previous method
        Map<Integer, Double> averageTemperatureByYear = getAverageTemperatureByYear();
        // puts the entries into a list
        List<Map.Entry<Integer, Double>> sortedList = new ArrayList<>(averageTemperatureByYear.entrySet());

        // sort by temperature
        if (isAscending) {
            // ascending order (coldest first)
            sortedList.sort(Map.Entry.comparingByValue());
        }
        else {
            // descending order (hottest first)
            sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        }

        // sorts list by top 3
        int limit = Math.min(3, sortedList.size());
        return sortedList.subList(0, limit);
    }

    public List<Map.Entry<Integer, Double>> get3Coldest() {
        // gets the top 3 coldest years by sorting in ascending order
        return getTop3WeatherData(true);
    }

    public List<Map.Entry<Integer, Double>> get3Hottest() {
        // gets the top 3 hottest years by sorting in descending order
        return getTop3WeatherData(false);
    }
}
