package Collection;

import Download.Download;

import java.util.ArrayList;


public class DownloadCollection {
    private ArrayList<Download> processingDownloads;
    private ArrayList<Download> completedDownloads;
    private ArrayList<Download> queueDownloads;
    private ArrayList<DownloadQueue> queues;

    public int maximumSizeOfProcessingDl;

    public DownloadCollection() {
        processingDownloads = new ArrayList<>();
        completedDownloads = new ArrayList<>();
        queueDownloads = new ArrayList<>();
        queues = new ArrayList<>();
    }

    public void addProccessingDownload(Download download) {
        if (!processingDownloads.contains(download)) {
            if (maximumSizeOfProcessingDl == 0) {
                download.setDownloading(true);
                processingDownloads.add(download);

            } else {
                if (processingDownloads.size() < maximumSizeOfProcessingDl) {
                    download.setDownloading(true);
                    processingDownloads.add(download);
                }
            }

        }
    }

    public void addCompletedDownload(Download download) {
        if (!completedDownloads.contains(download)) {
            download.setDownloading(false);
            download.setFinished(true);
            completedDownloads.add(download);
        }
    }

    public void addQueueDownload(Download download, DownloadQueue queue) {
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

    public void removeQueueDownload(Download download, DownloadQueue queue) {
        if (queueDownloads.contains(download)) {
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

    public int getMaximumSizeOfProcessingDl() {
        return maximumSizeOfProcessingDl;
    }

    public void setMaximumSizeOfProcessingDl(int maximumSizeOfProcessingDl) {
        this.maximumSizeOfProcessingDl = maximumSizeOfProcessingDl;
    }

    public ArrayList<DownloadQueue> getQueues() {
        return queues;
    }

    public void addQueue(String name, String time, String date) {
        DownloadQueue tmpDownloadQueue = new DownloadQueue(name);
        tmpDownloadQueue.setStartDate(date);
        tmpDownloadQueue.setStartTime(time);
        boolean flag = true;
        for (DownloadQueue queue : queues) {
            if (queue.equals(tmpDownloadQueue)) {
                flag = false;
                break;
            }
        }
        if (flag)
            queues.add(tmpDownloadQueue);
        else
            System.out.println("Error. Duplicate Download queue");
    }

    public void removeQueue() {

    }
}