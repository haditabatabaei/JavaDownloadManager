package Collection;

import download.Download;

import java.util.ArrayList;
import java.util.Queue;

public class DownloadCollection {
    private ArrayList<Download> processingDownloads;
    private ArrayList<Download> completedDownloads;
    private ArrayList<Download> queueDownloads;

    public DownloadCollection() {
        processingDownloads = new ArrayList<>();
        completedDownloads = new ArrayList<>();
        queueDownloads = new ArrayList<>();
    }

    public void addProccessingDownload(Download download) {
        if (!processingDownloads.contains(download)) {
            download.setDownloading(true);
            processingDownloads.add(download);
        }
    }

    public void addCompletedDownload(Download download) {
        if (!completedDownloads.contains(download)) {
            download.setDownloading(false);
            download.setFinished(true);
            completedDownloads.add(download);
        }
    }

    public void addQueueDownload(Download download, Queue<Download> queue) {
        if (!queueDownloads.contains(download)) {
            download.setInQueue(true);
            queueDownloads.add(download);
            queue.add(download);
        }
    }

    public void removeProcessingDownload(Download download) {
        if (processingDownloads.contains(download)) {
            processingDownloads.remove(download);
        }
    }

    public void removeCompletedDownload(Download download) {
        if (completedDownloads.contains(download))
            completedDownloads.remove(download);
    }

    public void removeQueueDownload(Download download, Queue<Download> queue) {
        if (queueDownloads.contains(download) && queue.contains(download)) {
            queueDownloads.remove(download);
            queue.remove(download);
        }
    }

    public ArrayList<Download> getCompletedDownloads() {
        return completedDownloads;
    }

    public ArrayList<Download> getProcessingDownloads() {
        return processingDownloads;
    }

    public ArrayList<Download> getQueueDownloads() {
        return queueDownloads;
    }
}
