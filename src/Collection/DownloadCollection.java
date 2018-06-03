package Collection;

import download.Download;
import gui.Panels.DownloadPanel;

import java.util.ArrayList;
import java.util.Iterator;


public class DownloadCollection {
    private ArrayList<Download> processingDownloads;
    private ArrayList<Download> completedDownloads;
    private ArrayList<Download> queueDownloads;
    private ArrayList<Download> removedDownloads;

    private ArrayList<DownloadQueue> queues;

    public int maximumSizeOfProcessingDl;

    public DownloadCollection() {
        processingDownloads = new ArrayList<>();
        completedDownloads = new ArrayList<>();
        queueDownloads = new ArrayList<>();
        removedDownloads = new ArrayList<>();
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
        queue.add(download);
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

    public void removeDownloadFromQueue(Download download, DownloadQueue queue) {
        queue.remove(download);
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

    public void removeQueue(DownloadQueue queue) {
        queues.remove(queue);
    }

    public void removeQueue(String queueName) {
        Iterator it = queues.iterator();
        while (it.hasNext()) {
            DownloadQueue tmp = (DownloadQueue) it.next();
            if (tmp.getName().equals(queueName))
                it.remove();
        }
    }

    public DownloadQueue getQueueByName(String name) {
        for (int i = 0; i < queues.size(); i++)
            if (queues.get(i).getName().equals(name))
                return queues.get(i);

        return null;
    }

    public boolean hasDuplicateSelectedQueues() {
        int counter = 1;
        for (DownloadQueue downloadQueue : queues) {
            if (downloadQueue.isSelected())
                counter++;
        }
        return counter > 1;
    }

    public void printAllQueuesAndTheirDownload() {
        for (DownloadQueue downloadQueue : queues) {
            System.out.println("Queue Name : " + downloadQueue.getName());
            for (Download download : downloadQueue.getItems())
                download.print();
        }
    }

    public DownloadQueue findSpecialDownloadQueue(Download download) {
        for (DownloadQueue downloadQueue : queues) {
            for (Download itDownload : downloadQueue.getItems()) {
                if (itDownload == download) {
                    return downloadQueue;
                }
            }
        }
        return null;
    }

    public void addRemovedDownload(Download removedDownload) {
        if (!removedDownloads.contains(removedDownload))
            removedDownloads.add(removedDownload);
    }

    public void removeRemovedDownload(Download removedDownload) {
        removedDownloads.remove(removedDownload);
    }

    public ArrayList<Download> getRemovedDownloads() {
        return removedDownloads;
    }

    public static void sortArrayList(ArrayList<DownloadPanel> arrayListToSort, int orderType, int sortType) {
        if (orderType == 0) {
            switch (sortType) {
                //DESCENDING SORT BY NAME
                case 1:
                    for (int i = 0; i < arrayListToSort.size() - 1; i++) {
                        for (int j = 0; j < arrayListToSort.size() - 1; j++) {
                            if (arrayListToSort.get(i).getDownload().getFileName().compareTo(arrayListToSort.get(j + 1).getDownload().getFileName()) <= 0 && i < (j + 1)) //NOTE: additional condition for indices
                            {
                                DownloadPanel tmpDownloadPanel = arrayListToSort.get(j + 1);
                                arrayListToSort.set(j + 1, arrayListToSort.get(i));
                                arrayListToSort.set(i, tmpDownloadPanel);
                            }
                        }
                    }
                    break;
                //DESCENDING SORT BY SIZE
                case 2:
                    for (int i = 0; i < arrayListToSort.size() - 1; i++) {
                        for (int j = 0; j < arrayListToSort.size() - 1; j++) {
                            if (arrayListToSort.get(i).getDownload().getFullFileSize() < arrayListToSort.get(j + 1).getDownload().getFullFileSize() && i < (j + 1)) //NOTE: additional condition for indices
                            {
                                DownloadPanel tmpDownloadPanel = arrayListToSort.get(j + 1);
                                arrayListToSort.set(j + 1, arrayListToSort.get(i));
                                arrayListToSort.set(i, tmpDownloadPanel);
                            }
                        }
                    }
                    break;
                //DESCENDING SORT BY TIME
                case 3:
                    for (int i = 0; i < arrayListToSort.size() - 1; i++) {
                        for (int j = 0; j < arrayListToSort.size() - 1; j++) {
                            if (arrayListToSort.get(i).getDownload().getTime().compareTo(arrayListToSort.get(j + 1).getDownload().getTime()) <= 0 && i < (j + 1)) //NOTE: additional condition for indices
                            {
                                DownloadPanel tmpDownloadPanel = arrayListToSort.get(j + 1);
                                arrayListToSort.set(j + 1, arrayListToSort.get(i));
                                arrayListToSort.set(i, tmpDownloadPanel);
                            }
                        }
                    }
                    break;
            }
        } else {
            switch (sortType) {
                //ASCENDING SORT BY NAME
                case 1:
                    for (int i = 0; i < arrayListToSort.size() - 1; i++) {
                        for (int j = 0; j < arrayListToSort.size() - 1; j++) {
                            if (arrayListToSort.get(i).getDownload().getFileName().compareTo(arrayListToSort.get(j + 1).getDownload().getFileName()) >= 0 && i < (j + 1)) //NOTE: additional condition for indices
                            {
                                DownloadPanel tmpDownloadPanel = arrayListToSort.get(j + 1);
                                arrayListToSort.set(j + 1, arrayListToSort.get(i));
                                arrayListToSort.set(i, tmpDownloadPanel);
                            }
                        }
                    }
                    break;
                //ASCENDING SORT BY SIZE
                case 2:
                    for (int i = 0; i < arrayListToSort.size() - 1; i++) {
                        for (int j = 0; j < arrayListToSort.size() - 1; j++) {
                            if (arrayListToSort.get(i).getDownload().getFullFileSize() > arrayListToSort.get(j + 1).getDownload().getFullFileSize() && i < (j + 1)) //NOTE: additional condition for indices
                            {
                                DownloadPanel tmpDownloadPanel = arrayListToSort.get(j + 1);
                                arrayListToSort.set(j + 1, arrayListToSort.get(i));
                                arrayListToSort.set(i, tmpDownloadPanel);
                            }
                        }
                    }
                    break;
                //ASCENDING SORT BY TIME
                case 3:
                    for (int i = 0; i < arrayListToSort.size() - 1; i++) {
                        for (int j = 0; j < arrayListToSort.size() - 1; j++) {
                            if (arrayListToSort.get(i).getDownload().getTime().compareTo(arrayListToSort.get(j + 1).getDownload().getTime()) >= 0 && i < (j + 1)) //NOTE: additional condition for indices
                            {
                                DownloadPanel tmpDownloadPanel = arrayListToSort.get(j + 1);
                                arrayListToSort.set(j + 1, arrayListToSort.get(i));
                                arrayListToSort.set(i, tmpDownloadPanel);
                            }
                        }
                    }
                    break;
            }
        }
    }

    public void swapDownloadPanel(DownloadPanel source, DownloadPanel target) {
        DownloadPanel tmp = new DownloadPanel();
        tmp = source;
        source = target;
        target = tmp;
    }
}