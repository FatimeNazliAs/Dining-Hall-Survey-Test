package dininghall.asstpackages.widget.weather;

import lombok.Data;
import org.primefaces.shaded.json.JSONObject;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@ManagedBean(name = "weatherView")
@RequestScoped
@Data
public class WeatherView {
    private String location;
    private String temperature;
    private String humidity;
    private String windSpeed;


    public void getWeather() {
        try {
            // Make a request to the OpenWeatherMap API
            String apiKey = "YOUR_API_KEY";

            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey;

            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            // Parse the JSON response
            JSONObject json = new JSONObject(response.toString());

            JSONObject main = json.getJSONObject("main");

            JSONObject wind = json.getJSONObject("wind");

            // Set the weather data to be displayed on the web page
            location = json.getString("name");

            temperature = main.getDouble("temp") + " °C";

            humidity = main.getDouble("humidity") + " %";

            windSpeed = wind.getDouble("speed") + " m/s";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
