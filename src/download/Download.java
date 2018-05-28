package Download;

import Collection.DownloadQueue;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

/**
 * This class represents a Download
 */
public class Download {
    private String fileName;
    private String urlAddress;
    private String date;
    private String time;
    private File file;
    private JProgressBar downloadProgressBar;
    private boolean isDownloading;
    private boolean isFinished;
    private final int fullFileSize;
    private int downloadedSize;
    private boolean isInQueue;
    private ImageIcon fileIcon;
    private String savePath;
    private boolean isLaunchedAfter;
    private boolean isSelect;
    private boolean isPaused;
    private boolean isCancled;
    private String queueName;

    public Download(String urlAddress, String fileName, int fullFileSize) {
        time = LocalTime.now().toString();
        date = LocalDate.now().toString();
        this.urlAddress = urlAddress;
        this.fileName = fileName;
        this.fullFileSize = fullFileSize;
        downloadProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        downloadProgressBar.setVisible(true);
        downloadProgressBar.setStringPainted(true);
        int randomPercent = new Random().nextInt(100);
        downloadProgressBar.setValue(randomPercent);
        downloadedSize = (randomPercent * fullFileSize) / 100;
        fileIcon = new ImageIcon(getClass().getResource("..//icons//trash_empty.png"));
        if (downloadProgressBar.getValue() == 100) {
            isDownloading = false;
            isFinished = true;
        }
        isPaused = false;
        isCancled = false;
    }

    /**
     * check whether file is downloading or not
     *
     * @return true if it's downloading
     */
    public boolean isDownloading() {
        return isDownloading;
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

    public int getDownloadedSize() {
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

    public void setDownloadedSize(int size) {
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
    public void start() {
        System.out.println(toString() + " Started");
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
            System.out.println(toString() + " Paused");
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
    public void resume() {
        if (isPaused || isCancled) {
            isPaused = false;
            isCancled = false;
            System.out.println(toString() + " Resumed");
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
        System.out.println(toString() + " Opened.");
    }

    /**
     * open file path
     */
    public void openFolder() {
        System.out.println(savePath + " directory opened.");
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}