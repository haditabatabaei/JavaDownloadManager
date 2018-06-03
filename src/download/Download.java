package download;

import gui.MainGui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class represents a Download
 */
public class Download extends Thread {
    private String fileName;
    private String urlAddress;
    private String date;
    private String time;
    private File file;
    private JProgressBar downloadProgressBar;
    private boolean isDownloading;
    private boolean isFinished;
    private float fullFileSize;
    private float downloadedSize;
    private boolean isInQueue;
    private ImageIcon fileIcon;
    private String savePath;
    private boolean isLaunchedAfter;
    private boolean isSelect;
    private boolean isPaused;
    private boolean isCancled;
    private String queueName;
    public static final int BUFFER_SIZE = 4096;
    private int downloadSpeed;

    public Download(String urlAddress, String fileName, float fullFileSize) {
        super();
        time = LocalTime.now().toString();
        date = LocalDate.now().toString();
        this.urlAddress = urlAddress;
        this.fileName = fileName;
        this.fullFileSize = fullFileSize;
        downloadProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        downloadProgressBar.setVisible(true);
        downloadProgressBar.setStringPainted(true);
        fileIcon = new ImageIcon(getClass().getResource("..//icons//trash_empty.png"));
        if (downloadProgressBar.getValue() == 100) {
            isDownloading = false;
            isFinished = true;
        }
        isPaused = false;
        isCancled = false;
        downloadedSize = 0;
    }

    /**
     * check whether file is downloading or not
     *
     * @return true if it's downloading
     */
    public boolean isDownloading() {
        return isDownloading;
    }

    public int showPercent() {
        return (int) (downloadedSize / fullFileSize) * 100;
    }

    /**
     * checks whether downloading is finished or not
     *
     * @return true if it's finished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * checks whether a Download is in queue or not
     *
     * @return true if it's in queue
     */
    public boolean isInQueue() {
        return isInQueue;
    }

    public float getFullFileSize() {
        return fullFileSize;
    }

    public float getDownloadedSize() {
        return downloadedSize;
    }

    public JProgressBar getDownloadProgressBar() {
        return downloadProgressBar;
    }


    public String getFileName() {
        return fileName;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public String toString() {
        String toReturn = "";
        toReturn += "File Name : " + fileName + "\nurl address : " + urlAddress + "\nSize: " + downloadedSize + " / " + fullFileSize;
        return toReturn;
    }

    public void setDownloadProgressBar(JProgressBar downloadProgressBar) {
        this.downloadProgressBar = downloadProgressBar;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDownloadedSize(float size) {
        downloadedSize = size;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public void print() {
        System.out.println(toString());
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public File getFile() {
        return file;
    }

    public ImageIcon getFileIcon() {
        return fileIcon;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    public void setInQueue(boolean inQueue) {
        isInQueue = inQueue;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getSavePath() {
        return savePath;
    }

    /**
     * sets save path
     *
     * @param savePath save path
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * checked whether file is going open
     *
     * @return true if option is active
     */
    public boolean isLaunchedAfter() {
        return isLaunchedAfter;
    }

    /**
     * set launch option
     *
     * @param launchedAfter true means file will be open after completeing Download
     */
    public void setLaunchedAfter(boolean launchedAfter) {
        isLaunchedAfter = launchedAfter;
    }

    /**
     * starts Download
     */
    public void startDownload() {
        System.out.println(toString() + " Started");

        File targetFile;
        synchronized (this) {
            File dir = new File(savePath + "\\");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            targetFile = new File(savePath + "\\" + fileName);
            if (!targetFile.exists()) {
                try {
                    targetFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        HttpURLConnection connection;
        try {
            URL url = new URL(urlAddress);
            if (MainGui.isFilterSite(urlAddress)) {

            } else {
                connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();
                System.out.println("Response code : " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Connection OK.");
                    String disposition = connection.getHeaderField("Content-Disposition");
                    String contentType = connection.getContentType();
                    int contentLength = connection.getContentLength();
                    float contentInFloat = (float) contentLength;
                    System.out.println("Content Length:" + contentLength);
                    System.out.println("Content-Type = " + contentType);
                    System.out.println("Content-Disposition = " + disposition);
                    System.out.println("Content-Length = " + contentInFloat);
                    System.out.println("fileName = " + fileName);
                    fullFileSize = contentLength / (1024 * 1024);
                    System.out.println("full file size :" + fullFileSize + "MB");
                    downloadedSize = 0;
                    downloadProgressBar.setValue(0);
                    InputStream inputStream = connection.getInputStream();
                    String toSave = savePath + "\\" + fileName;
                    FileOutputStream outputStream = new FileOutputStream(toSave);
                    int bytesRead = -1;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    System.out.println("Getting bytes now.");
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        long start = System.nanoTime();
                        outputStream.write(buffer, 0, bytesRead);
                        System.out.println("number of bytes read : " + bytesRead);
                        downloadedSize += bytesRead;
                        downloadProgressBar.setValue(showPercent());
                        System.out.println("Percent : " + (downloadedSize / contentInFloat) * 100);
                        downloadProgressBar.setValue((int) ((downloadedSize / contentInFloat) * 100));
                        long end = System.nanoTime();
                        System.out.println("Start nano time : " + start);
                        System.out.println("End nano time : " + end);
                        float speed = (bytesRead / (end - start));
                        System.out.println("Speed [Bytes]/[nanoTime]: " + speed);
                    }
                    outputStream.close();
                    inputStream.close();
                    System.out.println("File Downloaded.");
                    isDownloading = false;
                    isFinished = true;
                    MainGui.checkForFinishedDownloads();
                } else {
                    System.out.println("File Did not download.");
                }
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * cancels Download
     */
    public void cancel() {
        if (!isCancled) {
            isCancled = true;
            isPaused = true;
            System.out.println(toString() + " Stopped/Canceled");
        } else {
            System.out.println("is Canceled.");
        }
    }

    /**
     * pause the Download
     */
    public void pause() {
        if (!isPaused) {
            isPaused = true;
            suspend();
        } else {
            System.out.println("is Paused Already.");
        }
    }

    /**
     * check whether it's paused or not
     *
     * @return true if it's paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * checkes whether canceled or not
     *
     * @return true if canceled
     */
    public boolean isCancled() {
        return isCancled;
    }

    /**
     * removes Download
     */
    public void remove() {
        System.out.println(toString() + " Removed");
    }

    /**
     * resumes Download
     */
    public void resumeDownload() {
        if (isPaused || isCancled) {
            isPaused = false;
            isCancled = false;
            interrupt();
        } else {
            System.out.println("Not paused or cancel");
        }
    }

    /**
     * check whether selected or not
     *
     * @return true / false
     */
    public boolean isSelected() {
        return isSelect;
    }

    /**
     * select Download
     */
    public void select() {
        isSelect = !isSelected();
    }

    /**
     * opens file
     */
    public void open() {
        if (isFinished) {
            File file = new File(savePath + "\\" + fileName);
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(toString() + " Opened.");
        } else
            System.out.println("Download is not completed.");
    }

    /**
     * open file path
     */
    public void openFolder() {
        try {
            Desktop.getDesktop().open(new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(savePath + " directory opened.");
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        if (isInQueue)
            this.queueName = queueName;
    }

    public String toStringForSave() {

        String toReturn = "{\n[FILENAME]:" + fileName + "\n[DOWNLOADURL]:" + urlAddress;
        toReturn += "\n[FULLSIZE]:" + fullFileSize + "\n[DOWNLOADED]:" + downloadedSize;
        if (isInQueue()) {
            toReturn += "\n[QUEUENAME]:" + queueName;
        } else {
            toReturn += "\n[QUEUENAME]:";
        }
        toReturn += "\n[DATE]:" + date + "\n[TIME]:" + time + "\n[SAVEPATH]:" + savePath;
        if (isDownloading())
            toReturn += "\n[DOWNLOADING]:true";
        else
            toReturn += "\n[DOWNLOADING]:false";
        if (isLaunchedAfter)
            toReturn += "\n[LAUNCHAFTER]:true";
        else
            toReturn += "\n[LAUNCHAFTER]:false";

        if (isFinished)
            toReturn += "\n[FINISHED]:true";
        else
            toReturn += "\n[FINISHED]:false";

        toReturn += "\n}\n";

        return toReturn;
    }

    public String toRemovedString() {
        return "{\n[FILENAME]:" + fileName + "\n[DOWNLOADURL]:" + urlAddress + "\n}";
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public void run() {
        System.out.println("Run Called.");
        startDownload();
    }
}