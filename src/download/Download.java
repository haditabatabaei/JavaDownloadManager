package download;

import javax.swing.*;
import java.io.File;

public class Download {
    private String fileName;
    private String urlAddress;
    private String date;
    private String time;
    private File file;
    private JProgressBar downloadProgressBar;
    private boolean isDownloading;
    private boolean isFinished;
    private final float fullFileSize;
    private float downloadedSize;
    private boolean isInQueue;
    private ImageIcon fileIcon;

    public Download(String urlAddress) {
        this.urlAddress = urlAddress;
        downloadProgressBar = new JProgressBar(0, 100);
        fileName = "";
        fullFileSize = 0;
        fileIcon = new ImageIcon(getClass().getResource("..//icons//trash_empty.png"));
        time = "22:01:00";
        date = "2013-05-23";
    }

    public Download(String urlAddress, String fileName, float fullFileSize) {
        time = "22:01:00";
        date = "2013-05-23";
        this.urlAddress = urlAddress;
        this.fileName = fileName;
        this.fullFileSize = fullFileSize;
        downloadProgressBar = new JProgressBar(0, 100);
        fileIcon = new ImageIcon(getClass().getResource("..//icons//trash_empty.png"));
    }

    public Download() {
        time = "22:01:00";
        date = "2013-05-23";
        urlAddress = "";
        fileName = "";
        fullFileSize = 0;
        downloadProgressBar = new JProgressBar(0, 100);
        fileIcon = new ImageIcon(getClass().getResource("..//icons//trash_empty.png"));
    }

    public Download(int i) {
        fileName = "DownloadFileName.exe";
        urlAddress = "http://www.test.com/files/DownloadFileName.exe";
        time = "22:01:00";
        date = "2013-05-23";
        downloadProgressBar = new JProgressBar(0, 100);
/*
        fileIcon = new ImageIcon(getClass().getResource("..//icons//trash_empty"));
*/
        isDownloading = true;
        fullFileSize = 500;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public boolean isFinished() {
        return isFinished;
    }

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
        toReturn += "File Name : " + fileName + "\nurl address : " + urlAddress + "\nFile Size : " + downloadedSize;
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
}
