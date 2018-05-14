package download;

import javax.swing.*;

public class Download {
    private String fileName;
    private String urlAddress;
    private float size;// this is in megabytes.
    private JProgressBar downloadProgressBar;
    private String date;
    private String time;

    public Download(String urlAddress) {
        this.urlAddress = urlAddress;
        downloadProgressBar = new JProgressBar(0, 100);
    }

    public Download(String urlAddress, String fileName) {
        this.urlAddress = urlAddress;
        this.fileName = fileName;
        downloadProgressBar = new JProgressBar(0, 100);
    }

    public Download() {
        urlAddress = "";
        this.fileName = "";
        downloadProgressBar = new JProgressBar(0, 100);
    }

    public String getFileName() {
        return fileName;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public float getSize() {
        return size;
    }

    public JProgressBar getDownloadPrgressBar() {
        return downloadProgressBar;
    }

    public String toString() {
        String toReturn = "";
        toReturn += "File Name : " + fileName + "\nurl address : " + urlAddress + "\nFile Size : " + size;
        return toReturn;
    }

    public void setDownloadProgressBar(JProgressBar downloadProgressBar) {
        this.downloadProgressBar = downloadProgressBar;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSize(float size) {
        this.size = size;
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
}
