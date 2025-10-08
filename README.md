# weather_project
This is a compilation of three seperate "projects".

First is a basic program which utilises abstract classes and the "draw" method to create ASCII art of shapes with random dimensions.

The second project reads a CSV files to gather weather information, then displays a list of commands in the terminal which the user can select from using their numbers. This is achieved using scanners, arrays, IO methods, maps, and lists.

The third and biggest project is a weather checking application using the OpenMeteoClient API. This API allows access to every cities weather data, including their current weather and forecast weather. The key features of this project include:
. Searching a city by its name
. Viewing the current/forecast temperature, precipitation, wind speed, and humidity of the chosen city
. Hourly forecast options for 1, 6 and 12 hours ahead
. Data catching
. User-friendly GUI with tab navigation
This was all achieved using multiple technologies/concepts, including Java Swing (JFrame, JPanel, JButton, JLabel, JTextField, JTabbedPane for GUI construction), Java HTTP client for API calls, Event handling with ActionListener, JSON parsing, Data caching with HashMap, Formatting, OOP concepts such as inheritance. The project itself is divided into 3 classes, the "WeatherGUI" handles the gui construction and organises the interface. The "WeatherActionListeners" is used to handle interactions and updates from the gui's buttons. And the "OpenMeteoClient" which connects to the OpenMeteoClient API and sorts the data.
