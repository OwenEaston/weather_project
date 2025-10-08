package Exercise3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherActionListeners implements ActionListener {
    private final JPanel currentButtonPanel;
    private final JPanel bottomPanel;
    private final JPanel forecastBottomPanel;
    private final JPanel forecastButtonPanel;
    // gets the current hour and formats it. this is used to get accurate times for forecast
    SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
    int currentHour = Integer.parseInt(hourFormat.format(new Date()));

    // initialising all the variables which will be used later
    private final JTextField textField;
    private final JLabel cityResult;
    private final JLabel placeholderLabel;
    private final JLabel forecastPlaceholderLabel;
    private final JLabel currentWeatherLabel;
    private final JLabel forecastWeatherLabel;
    private String forecastTime;
    String cityName;

    // constructor for this class. it takes in all the buttons as parameters. it then stores them with "this."
    // with this constructor, we are now able to access the variables of MyMain inside this class
    public WeatherActionListeners(
            JTextField textField, JLabel cityResult,
            JButton precipitationButton, JButton temperatureButton, JButton windButton, JButton humidityButton,
            JButton forecastPrecipitationButton, JButton forecastTemperatureButton, JButton forecastWindButton, JButton forecastHumidityButton,
            JButton forecast1hr, JButton forecast6hr, JButton forecast12hr,
            JLabel placeholderLabel, JLabel forecastPlaceholderLabel,
            JLabel currentWeatherLabel, JLabel forecastWeatherLabel,
            JPanel currentButtonPanel, JPanel bottomPanel,
            JPanel forecastButtonPanel, JPanel forecastBottomPanel
    ) {
        this.textField = textField;
        this.cityResult = cityResult;
        this.placeholderLabel = placeholderLabel;
        this.forecastPlaceholderLabel = forecastPlaceholderLabel;
        this.currentWeatherLabel = currentWeatherLabel;
        this.forecastWeatherLabel = forecastWeatherLabel;
        this.currentButtonPanel = currentButtonPanel;
        this.bottomPanel = bottomPanel;
        this.forecastButtonPanel = forecastButtonPanel;
        this.forecastBottomPanel = forecastBottomPanel;
        makePanelsInvisible();
    }

    // makes all the panels invisible at the start, as to not let people with no city selected interact with further tabs
    private void makePanelsInvisible() {
        currentButtonPanel.setVisible(false);
        bottomPanel.setVisible(false);
        forecastButtonPanel.setVisible(false);
        forecastBottomPanel.setVisible(false);
    }

    // overrides ActionListener method and gets the city input when enter is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        // only activated when Enter key is pressed in the text field
        if (e.getSource() == textField) {
            cityName = textField.getText();

            try {
                // attempt to fetch the data with the given city name
                OpenMeteoClient.updateCurrentWeather(cityName);
                OpenMeteoClient.updateForecast(cityName);

                // display that the city is valid
                cityResult.setText("City name is valid: " + cityName);
                cityResult.setForeground(Color.GREEN);

                // change the weather tab titles
                currentWeatherLabel.setText("Current Weather Information For: " + cityName);
                forecastWeatherLabel.setText("Forecast Weather Information For: " + cityName);

                // show the panels
                currentButtonPanel.setVisible(true);
                bottomPanel.setVisible(true);
                forecastButtonPanel.setVisible(true);
                forecastBottomPanel.setVisible(true);

            } catch (Exception ex) {
                // if fetching data didn't come back as valid, then display
                cityResult.setText("City name is not valid: " + cityName);
                cityResult.setForeground(Color.RED);

                // change titles to show that the city is invalid
                currentWeatherLabel.setText("Invalid city");
                forecastWeatherLabel.setText("Invalid city");

                // hide all panels
                currentButtonPanel.setVisible(false);
                bottomPanel.setVisible(false);
                forecastButtonPanel.setVisible(false);
                forecastBottomPanel.setVisible(false);
            }
        }
    }

    // action listeners for current weather buttons. these methods call the current weather information for the
    // button they are representing then they change the placeholderLabel to display the relevant information
    public void currentPrecipitationAction(ActionEvent e) {
        String precipitation = OpenMeteoClient.getCurrentPrecipitation();
        placeholderLabel.setText("Current Precipitation: " + precipitation + " mm");
    }

    public void currentTemperatureAction(ActionEvent e) {
        String temperature = OpenMeteoClient.getCurrentTemperature();
        placeholderLabel.setText("Current Temperature: " + temperature + " °C");
    }

    public void currentWindAction(ActionEvent e) {
        String windSpeed = OpenMeteoClient.getCurrentWind();
        placeholderLabel.setText("Current Wind Speed: " + windSpeed + " km/h");
    }

    public void currentHumidityAction(ActionEvent e) {
        String humidity = OpenMeteoClient.getCurrentHumidity();
        placeholderLabel.setText("Current Humidity: " + humidity + " %");
    }

    // action listeners for forecast weather buttons. these methods check if there is a forecastTime selected, these
    // being the 1hr/6hr/12hr forecast options. if there is one selected then we call the appropriate method in
    // OpenMeteoClient with the forecastTime as the parameter. we then display the results of whatever information we receive.
    // if there isn't a time selected, then we simply display "Time not selected"
    public void forecastPrecipitationAction(ActionEvent e) {
        if (forecastTime != null) {
            String precipitation = OpenMeteoClient.getPrecipitationAtTime(forecastTime);
            forecastPlaceholderLabel.setText("Forecast Precipitation at " + forecastTime + " : " + precipitation);
        } else {
            forecastPlaceholderLabel.setText("Time not selected");
        }
    }

    public void forecastTemperatureAction(ActionEvent e) {
        if (forecastTime != null) {
            String temp = OpenMeteoClient.getTemperatureAtTime(forecastTime);
            forecastPlaceholderLabel.setText("Forecast Temperature at " + forecastTime + " GMT: " + temp);
        } else {
            forecastPlaceholderLabel.setText("Time not selected");
        }
    }

    public void forecastWindAction(ActionEvent e) {
        if (forecastTime != null) {
            String wind = OpenMeteoClient.getWindAtTime(forecastTime);
            forecastPlaceholderLabel.setText("Forecast Wind at " + forecastTime + " GMT: " + wind);
        } else {
            forecastPlaceholderLabel.setText("Time not selected");
        }
    }

    public void forecastHumidityAction(ActionEvent e) {
        if (forecastTime != null) {
            String humidity = OpenMeteoClient.getHumidityAtTime(forecastTime);
            forecastPlaceholderLabel.setText("Forecast Humidity at " + forecastTime + " GMT: " + humidity);
        } else {
            forecastPlaceholderLabel.setText("Time not selected");
        }
    }

    // action listeners for forecast time buttons. these forecast time buttons are what give us the range of hours
    // for the forecast. these are done by gathering the current forecastTimeList and then choosing the appropriate
    // index from that list to represent the forecast time
    public void forecast1hrAction(ActionEvent e) {
        forecastWeatherLabel.setText("Forecast Weather Information For: " + cityName + " in 1 hour");
        String[] forecastTimeList = OpenMeteoClient.getForecastTime();
        forecastTime = forecastTimeList[currentHour+1];
    }

    public void forecast6hrAction(ActionEvent e) {
        forecastWeatherLabel.setText("Forecast Weather Information For: " + cityName + " in 6 hours");
        String[] forecastTimeList = OpenMeteoClient.getForecastTime();
        forecastTime = forecastTimeList[currentHour+6];
    }

    public void forecast12hrAction(ActionEvent e) {
        forecastWeatherLabel.setText("Forecast Weather Information For: " + cityName + " in 12 hours");
        String[] forecastTimeList = OpenMeteoClient.getForecastTime();
        forecastTime = forecastTimeList[currentHour+12];
    }
}