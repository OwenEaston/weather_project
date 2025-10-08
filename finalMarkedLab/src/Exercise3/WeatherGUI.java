package Exercise3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherGUI extends JFrame {
    public static void main(String[] args) {
        final String CITY_NOT_SELECTED = "City Not Selected";
        final String ENTER_CITY_NAME = "Enter City Name: ";
        List<String> cityHistoryList = new ArrayList<String>();

        // creates a JFrame, this is used to display the GUI
        JFrame frame = new JFrame("City Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // allows multiple tabs in a single JFrame window
        JTabbedPane tabbedPane = new JTabbedPane();

        // ------------------- CITY CHOICE TAB -------------------
        // this section is where the user inputs their city choice
        JPanel cityChoicePanel = new JPanel();
        // border layout is used to allow easier placement of JPanel components
        cityChoicePanel.setLayout(new BorderLayout());

        // textfield allows the user to type their desired city name
        JTextField textField = new JTextField(20);
        JLabel cityResult = new JLabel(ENTER_CITY_NAME);
        cityResult.setFont(new Font("Arial", Font.PLAIN, 30));
        cityResult.setHorizontalAlignment(JLabel.CENTER);

        // add the footer label to the bottom of the frame
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(textField);
        cityChoicePanel.add(inputPanel, BorderLayout.CENTER);
        cityChoicePanel.add(cityResult, BorderLayout.NORTH);
        // the name of the tab
        tabbedPane.addTab("City Choice", cityChoicePanel);

        // ------------------- CURRENT WEATHER TAB -------------------
        // the current weather tab is where the user sees the weather at current time for their desired city
        JPanel currentWeatherPanel = new JPanel();
        currentWeatherPanel.setLayout(new BorderLayout());

        JLabel currentWeatherLabel = new JLabel(CITY_NOT_SELECTED, JLabel.CENTER);
        currentWeatherLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        // creates a panel to store all the buttons we will add
        JPanel currentButtonPanel = new JPanel();
        currentButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // 4 buttons are created for each weather option
        JButton precipitationButton = new JButton("Current Precipitation");
        JButton temperatureButton = new JButton("Current Temperature");
        JButton windButton = new JButton("Current Wind");
        JButton humidityButton = new JButton("Current Humidity");

        // .add adds the buttons into the currentButtonPanel
        currentButtonPanel.add(precipitationButton);
        currentButtonPanel.add(temperatureButton);
        currentButtonPanel.add(windButton);
        currentButtonPanel.add(humidityButton);

        // placeholderLabel will be used to display the weather info
        JLabel placeholderLabel = new JLabel("Select a button above to view current weather data.", JLabel.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.ITALIC, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        bottomPanel.add(placeholderLabel);
        currentWeatherPanel.add(currentWeatherLabel, BorderLayout.NORTH);
        currentWeatherPanel.add(currentButtonPanel, BorderLayout.CENTER);
        currentWeatherPanel.add(bottomPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Current Weather", currentWeatherPanel);

        // ------------------- FORECAST WEATHER TAB -------------------
        // this tab will be used to see the forecast for 1hr, 6hrs and 12hrs later for the desired city
        JPanel forecastWeatherPanel = new JPanel();
        forecastWeatherPanel.setLayout(new BorderLayout());

        JLabel forecastWeatherLabel = new JLabel(CITY_NOT_SELECTED, JLabel.CENTER);
        forecastWeatherLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        JPanel forecastButtonPanel = new JPanel();
        forecastButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // buttons for the weather options
        JButton forecastPrecipitationButton = new JButton("Forecast Precipitation");
        JButton forecastTemperatureButton = new JButton("Forecast Temperature");
        JButton forecastWindButton = new JButton("Forecast Wind");
        JButton forecastHumidityButton = new JButton("Forecast Humidity");

        JPanel forecastTimeButtonPanel = new JPanel();
        forecastTimeButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // buttons for the time options
        JButton forecast1hr = new JButton("Forecast 1 hour later");
        JButton forecast6hr = new JButton("Forecast 6 hours later");
        JButton forecast12hr = new JButton("Forecast 12 hours later");

        forecastTimeButtonPanel.add(forecast1hr);
        forecastTimeButtonPanel.add(forecast6hr);
        forecastTimeButtonPanel.add(forecast12hr);

        forecastButtonPanel.add(forecastTimeButtonPanel);
        forecastButtonPanel.add(forecastPrecipitationButton);
        forecastButtonPanel.add(forecastTemperatureButton);
        forecastButtonPanel.add(forecastWindButton);
        forecastButtonPanel.add(forecastHumidityButton);

        JLabel forecastPlaceholderLabel = new JLabel("Select a button above to view current weather data.", JLabel.CENTER);
        forecastPlaceholderLabel.setFont(new Font("Arial", Font.ITALIC, 20));

        JPanel forecastBottomPanel = new JPanel();
        forecastBottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        forecastBottomPanel.add(forecastPlaceholderLabel);
        forecastWeatherPanel.add(forecastWeatherLabel, BorderLayout.NORTH);
        forecastWeatherPanel.add(forecastButtonPanel, BorderLayout.CENTER);
        forecastWeatherPanel.add(forecastBottomPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Forecast Weather", forecastWeatherPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);

        // create an instance for class "WeatherActionListeners"
        WeatherActionListeners actionListeners = new WeatherActionListeners(
                textField, cityResult, precipitationButton, temperatureButton, windButton, humidityButton,
                forecastPrecipitationButton, forecastTemperatureButton, forecastWindButton, forecastHumidityButton,
                forecast1hr, forecast6hr, forecast12hr, placeholderLabel, forecastPlaceholderLabel, currentWeatherLabel,
                forecastWeatherLabel, currentButtonPanel, bottomPanel, forecastButtonPanel, forecastBottomPanel
        );


        // adds the actions listeners from the WeatherActionListeners class
        // these lines activate when one of the buttons are pressed, this is done by connecting the button
        // to a specific method located in WeatherActionListeners (using the newly created object named "actionListeners.")
        // the :: is the method reference, it tells java which method to use when clicked. this is used to save space
        // as otherwise we would have to write out the whole method or call it, which both take a lot more space to write.
        textField.addActionListener(actionListeners);
        precipitationButton.addActionListener(actionListeners::currentPrecipitationAction);
        temperatureButton.addActionListener(actionListeners::currentTemperatureAction);
        windButton.addActionListener(actionListeners::currentWindAction);
        humidityButton.addActionListener(actionListeners::currentHumidityAction);
        forecastPrecipitationButton.addActionListener(actionListeners::forecastPrecipitationAction);
        forecastTemperatureButton.addActionListener(actionListeners::forecastTemperatureAction);
        forecastWindButton.addActionListener(actionListeners::forecastWindAction);
        forecastHumidityButton.addActionListener(actionListeners::forecastHumidityAction);
        forecast1hr.addActionListener(actionListeners::forecast1hrAction);
        forecast6hr.addActionListener(actionListeners::forecast6hrAction);
        forecast12hr.addActionListener(actionListeners::forecast12hrAction);
    }
}
