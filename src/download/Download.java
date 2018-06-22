package download;

import gui.MainGui;
import gui.Panels.DownloadPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents a Download
 */
public class Download implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String urlAddress;
    private String date;
    private LocalTime time;
    private transient File file;
    private transient JProgressBar downloadProgressBar;
    private boolean isDownloading;
    private boolean isFinished;
    private double fullFileSize;
    private double downloadedSize;
    private boolean isInQueue;
    private ImageIcon fileIcon;
    private String savePath;
    private boolean isLaunchedAfter;
    private boolean isSelect;
    private boolean isPaused;
    private boolean isCancled;
    private String queueName;
    public static final int BUFFER_SIZE = 4096;
    private double downloadSpeed;
    private transient DownloadManager downloadManager;
    private transient ExecutorService executorService;
    // public

    public void setFullFileSize(double fullFileSize) {
        this.fullFileSize = fullFileSize;
    }

    public Download(String urlAddress, String fileName, float fullFileSize) {
        super();
        time = LocalTime.now();
        date = LocalDate.now().toString();
        this.urlAddress = urlAddress;
        this.fileName = fileName;
        this.fullFileSize = fullFileSize;
        downloadProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        fileIcon = new ImageIcon(getClass().getResource("..//icons//trash_empty.png"));
        if (downloadProgressBar.getValue() == 100) {
            isDownloading = false;
            isFinished = true;
        }
        downloadProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        downloadProgressBar.setVisible(true);
        downloadProgressBar.setStringPainted(true);
        isPaused = false;
        isCancled = false;
        downloadedSize = 0;
        executorService = Executors.newSingleThreadExecutor();
    }


    public void createProgressbar() {
        downloadProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        downloadProgressBar.setVisible(true);
        downloadProgressBar.setStringPainted(true);
    }

    public double getTimeInDouble() {
        return (double) time.getHour();
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

    public double getFullFileSize() {
        return fullFileSize;
    }

    public double getDownloadedSize() {
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

    public void setDownloadedSize(double size) {
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

    public String getTimeInString() {
        return time.toString();
    }

    public LocalTime getTime() {
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
    public void startDownload(DownloadPanel downloadPanel) {
        downloadManager = new DownloadManager(downloadPanel);
        executorService.execute(downloadManager);
    }

    public int getPercent() {
        int toReturn = (int) ((downloadedSize / fullFileSize) * 100);
        return toReturn;
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public ExecutorService getExecutorService() {
        return executorService;
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
            isDownloading = false;
            isFinished = false;
            System.out.println("Paused.");
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
            isDownloading = true;
            System.out.println("Resumed.");
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

    // pub

    public void setTime(LocalTime time) {
        this.time = time;
    }
}