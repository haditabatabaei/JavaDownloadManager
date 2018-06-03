package Connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionForSize extends Thread {
    private String urlAddress;
    private int fileSize;

    public ConnectionForSize(String urlAddress) {
        super();
        this.urlAddress = urlAddress;
    }

    public void run() {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            System.out.println("Connection for size response code : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                fileSize = connection.getContentLength();
                System.out.println("connection for size response content length : " + fileSize);
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getFileSize() {
        return fileSize;
    }
}
