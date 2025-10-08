package Exercise2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // creates an object named weatherIO to handle the weather data
        WeatherIO weatherIO = new WeatherIO();
        // loads the weather data from the CSV file
        // this code has been prone to errors with finding the file destination. they way it works now is that
        // the string within the below parameter continues off the end of the file path (in my own personal path, that is
        // "Desktop/oopintelcode/finalMarkedLab"). from here, java has that as the automatic default file path, the string
        // then adds "src/Exercise2/Heathrow.csv" to it, allowing it to dive deeper into the files and file the destination.
        // finally, the path is now "Desktop/oopintelcode/finalMarkedLab/src/Exercise2/Heathrow.csv". if any modifications
        // beyond the "finalMarkedLab" part are made, the code will fail to run as it does not recognise the path provided.
        weatherIO.readWeatherCSV("src\\Exercise2\\Heathrow.csv");

        // creates variable to store input
        String input;

        // start an infinite loop to show the menu unless the user quits
        while (true) {
            System.out.println("1. Display all weather data");
            System.out.println("2. Search weather data by year");
            System.out.println("3. Show average rainfall by year");
            System.out.println("4. Show average temperature by year");
            System.out.println("5. Show the 3 coldest years");
            System.out.println("6. Show the 3 hottest years");
            System.out.println("Type 'quit' to exit");
            System.out.print("Enter your choice: ");

            // creates a scanner to get the users input
            Scanner scanner = new Scanner(System.in);
            // puts the users input on a variable and removes extra space
            input = scanner.nextLine().trim();

            // checks the users input and does the action according to it
            if (input.equals("1")) {
                // gets all weather data and puts it in a list
                List<WeatherData> data = weatherIO.getAllWeatherData();
                // loops through the data and prints each entry
                for (WeatherData weatherData : data) {
                    System.out.println(weatherData);
                }
            } else if (input.equals("2")) {
                System.out.print("Enter the year to search for weather data: ");
                try {
                    // attempt to parse the year as an int
                    int year = Integer.parseInt(scanner.nextLine().trim());
                    // search through the data by year
                    List<WeatherData> data = weatherIO.searchWeatherData(year);
                    // loop through and print data
                    for (WeatherData weatherData : data) {
                        System.out.println(weatherData);
                    }
                // catches error if the users input isn't a valid number
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid year.");
                }
            } else if (input.equals("3")) {
                // gets average rainfall by year data and puts it in a map
                Map<Integer, Double> averageRainfallByYear = weatherIO.getAverageRainfallByYear();
                // loop through and print each entry
                for (Map.Entry<Integer, Double> entry : averageRainfallByYear.entrySet()) {
                    System.out.println("Year: " + entry.getKey() + ", Average Rainfall: " + entry.getValue());
                }
            } else if (input.equals("4")) {
                // gets average temperature by year data and puts it in a map
                Map<Integer, Double> averageTemperatureByYear = weatherIO.getAverageTemperatureByYear();
                // loop through and print each entry
                for (Map.Entry<Integer, Double> entry : averageTemperatureByYear.entrySet()) {
                    System.out.println("Year: " + entry.getKey() + ", Average Temperature: " + entry.getValue());
                }
            } else if (input.equals("5")) {
                // gets the 3 coldest years
                List<Map.Entry<Integer, Double>> coldestYears = weatherIO.get3Coldest();
                // loop through and print each entry
                for (Map.Entry<Integer, Double> entry : coldestYears) {
                    System.out.println("Year: " + entry.getKey() + ", Coldest Temperature: " + entry.getValue());
                }
            } else if (input.equals("6")) {
                // gets the 3 coldest years
                List<Map.Entry<Integer, Double>> hottestYears = weatherIO.get3Hottest();
                // loop through and print each entry
                for (Map.Entry<Integer, Double> entry : hottestYears) {
                    System.out.println("Year: " + entry.getKey() + ", Average Temperature: " + entry.getValue());
                }
            } else if (input.equalsIgnoreCase("quit")) {
                // exits program when "quit" is typed
                break;
            // if the input is valid, but not recognised in the "if" statements, then this else is chosen
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}