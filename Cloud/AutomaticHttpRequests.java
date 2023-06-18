import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class AutomaticHttpRequests {
    private static final String API_URL = "https://proxyxoay.net/api/rotating-proxy/change-key-ip/4d29d827-b4f9-4e38-9234-c21ecef95ede";
    private static final int INTERVAL_MS = 3 * 60 * 1000; // 3 minutes

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new HttpRequestTask(), 0, INTERVAL_MS);
    }

    private static class HttpRequestTask extends TimerTask {
        @Override
        public void run() {
            try {
                // Create URL object
                URL url = new URL(API_URL);

                // Open connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Get the response
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Process the response as needed
                    System.out.println("Response: " + response.toString());
                } else {
                    System.out.println("Request failed with response code: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
