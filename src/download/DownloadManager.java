package download;


import ActionHandlers.CentralCommand;
import gui.MainGui;
import gui.Panels.DownloadPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;


public class DownloadManager implements Runnable {
    private DownloadPanel downloadPanel;
    private Download download;
    private File file;
    private URL url;
    private HttpURLConnection connection;
    private String type;
    private double fullSize;
    private static final int BUFFER_SIZE = 4096;
    private double startByte;

    public DownloadManager(DownloadPanel downloadPanel) {
        this.downloadPanel = downloadPanel;
        download = downloadPanel.getDownload();
        file = download.getFile();
        startByte = 0;
    }

    @Override
    public void run() {
        try {
            url = new URL(download.getUrlAddress());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                fullSize = (double) connection.getContentLength();
                download.setFullFileSize(fullSize);
                type = connection.getContentType();
                file = new File(MainGui.defaultSavePath + "\\" + download.getFileName());
                if (file.createNewFile())
                    System.out.println("file to save created.");
                else
                    System.out.println("file to save exists.");

                FileOutputStream toFile = new FileOutputStream(file);
                //     RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                //   randomAccessFile.seek(0);
                InputStream inputFromConnection = connection.getInputStream();
                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                download.setDownloading(true);
                download.setFinished(false);
                while (true) {
                    bytesRead = inputFromConnection.read(buffer);
                    if ((bytesRead == -1))
                        break;
                    while (true) {
                        if (!download.isPaused()) {
                            // connection.setRequestProperty();
                            //randomAccessFile.seek((long) startByte);
                            break;
                        }
                    }
                    startByte += bytesRead;
                    download.setDownloadedSize(download.getDownloadedSize() + bytesRead);
                    downloadPanel.update();
                    // randomAccessFile.write(buffer, 0, bytesRead);
                    toFile.write(buffer, 0, bytesRead);
                }
                toFile.close();
                inputFromConnection.close();
                connection.disconnect();
                download.setFinished(true);
                download.setDownloading(false);
                CentralCommand.checkAndMoveFinishedDownloads();
                if (download.isLaunchedAfter()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                }
            } else
                JOptionPane.showMessageDialog(null, "Something wrong with connection : \nRESPONSE CODE : " + responseCode, "Error", JOptionPane.WARNING_MESSAGE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
