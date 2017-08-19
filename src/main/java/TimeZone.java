import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;

import static java.time.Instant.ofEpochMilli;


/*
    In this application our CSV file contains a date, a UTC time and longitude and latitude. Our goal is to use the long / lat data to get the localized time
    and convert the UTC date time into that localized variant.
    By making a HTTPS request to the google maps API (specifically the timezone API) we can get the timezone of the given longitude and latitude coordinates
    and the milliseconds from our date from the epoch time.

    Example input and output:
    Input
        2013-07-10 02:52:49,-44.490947,171.220966
    Output
        2013-07-10 02:52:49,-44.490947,171.220966,Pacific/Auckland,2013-07-10 14:52:49

 */

public class TimeZone {
    
    
    public static final String apiKey = "" // Your API key here. Get one from https://developers.google.com/maps/documentation/timezone/start
    public static final String requestURL = "https://maps.googleapis.com/maps/api/timezone/json?";

    public static void main (String[] args) throws IOException{

        String dateTime, latitude, longitude;
        LocalDateTime date;

        Scanner coordinates = new Scanner (new FileReader("csv.csv"));

        coordinates.useDelimiter(",");

        while (coordinates.hasNextLine()) {
            dateTime = coordinates.next();
            dateTime = dateTime.substring(0, 10) + "T" + dateTime.substring(11);
            date = LocalDateTime.parse(dateTime);
            latitude = coordinates.next();
            longitude = coordinates.next();

            System.out.println("Input date: " + date.toString().replace("T", " ") + ","
                    + latitude + "," + longitude);

            getTimezone(date, latitude, longitude);
        }

        coordinates.close();
    }

    private static void getTimezone(LocalDateTime date, String latitude, String longitude) {
        long epochSec = date.atZone(ZoneId.of("Etc/GMT")).toEpochSecond();
        StringBuilder jsonString =  new StringBuilder();

        try {
            URL url = new URL(requestURL + "location=" + latitude + "," + longitude + "&timestamp=" + epochSec + apiKey);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            String output;

            while ((output = br.readLine()) != null) {
                jsonString.append(output);
            }

            connection.disconnect();

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
        } catch (IOException e) {
            System.out.println("IO Error");
        }

        Gson gson = new Gson();
        JsonObject ob = gson.fromJson(jsonString.toString(), JsonObject.class);

        Instant instant = ofEpochMilli((epochSec)  * 1000); //epochSec is in seconds. Need to convert to milliseconds first.
        LocalDateTime newDate = LocalDateTime.ofInstant(instant, ZoneId.of(ob.timeZoneId));

        String finalString  = date.toString().replace("T", " ") + "," + latitude + "," +
                longitude + "," + ob.timeZoneId + "," + newDate.toString().replace("T", " ");
        System.out.println("Output date: " + finalString);
        
        // Needs refactor
    }
}
