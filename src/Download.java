import javax.swing.*;

public class Download {
    private String fileName;
    private String urlAddress;
    private float size;// this is in megabytes.
    private JProgressBar downloadProgressBar;

    public Download(String urlAddress) {
        this.urlAddress = urlAddress;
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

}
