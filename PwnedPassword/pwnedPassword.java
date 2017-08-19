import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/*
    Simple Java console application that checks if a password has appeared in a known data breach using Troy Hunt's
    Have I Been Pwned API (https://haveibeenpwned.com/API/v2).
 */

public class pwnedPassword {

    public static void main(String[] args) {

        // Obviously this can be changed and edited to something else. This is just
        // the most basic example of password checking.
        System.out.print("Please enter a password: ");
        Scanner scan = new Scanner(System.in);
        String password = scan.next();

        try {
            // Open a connection to the API site.
            URL url = new URL("https://haveibeenpwned.com/api/v2/pwnedpassword/" + password);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            /*
             * @param userAgent: this request header must be set otherwise any call will result in an HTTP 403 response
             * userAgent is the application / customer consuming the API.
             */

            connection.setRequestProperty("User-Agent", "Java Pwned Password Checker"); //Put your application name here
            int statusCode = connection.getResponseCode();

            // From the API docs, if status code 200 is returned, the password has appeared in a breach.
            // If status code is 404, the password has not appeared in a breach.
            if (statusCode == 200) System.out.println("Your password has been breached. Please consider using a different password.");
            else if (statusCode == 404) System.out.println("Your password is safe. It has not appeared in known data breaches.");

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: Please enter a valid URL");
        } catch (IOException e) {
            System.out.println("IOException: Please enter a valid URL");
        } finally {
            scan.close();
        }
    }
}
