package Exercise2;

public class WeatherData {
    // declares the fields to store data
    private final int year;
    private final int month;
    private final double maxTemp;
    private final double minTemp;
    private final double meanTemp;
    private final double rainAmount;

    // constructor to initialize the data when an object is created
    public WeatherData(int year, int month, double maxTemp, double minTemp, double meanTemp, double rainAmount) {
        this.year = year;
        this.month = month;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.meanTemp = meanTemp;
        this.rainAmount = rainAmount;
    }

    // overrides the toString method to print the weather data in a readable format
    @Override
    public String toString() {
        return "Year: " + year +
                " Month: " + month +
                " Max temp: " + maxTemp +
                " Min temp: " + minTemp +
                " Mean temp: " + meanTemp +
                " Rain amount: " + rainAmount;
    }

    // getter methods to access each field individually
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public double getMaxTemp() {
        return maxTemp;
    }
    public double getMinTemp() {
        return minTemp;
    }
    public double getMeanTemp() {
        return meanTemp;
    }
    public double getRainAmount() {
        return rainAmount;
    }
}
