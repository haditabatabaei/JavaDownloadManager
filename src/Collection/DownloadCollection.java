package Collection;

import download.Download;
import gui.Panels.DownloadPanel;

import java.util.ArrayList;


public class DownloadCollection {
    private static ArrayList<Download> removedDownloads;
    public static ArrayList<DownloadQueue> queues;

    public int maximumSizeOfProcessingDl;

    public DownloadCollection() {
        removedDownloads = new ArrayList<>();
        queues = new ArrayList<>();
    }

    public static DownloadQueue findQueueByName(String name) {
        for (DownloadQueue queue : queues)
            if (queue.getName().equals(name))
                return queue;
        return null;
    }

    public static void createNewQueue(String name) {
        queues.add(new DownloadQueue(name));
    }

    public static void removeQueue(String name) {
        DownloadQueue toRemove = findQueueByName(name);
        queues.remove(toRemove);
    }

    public static void changeQueueName(String sourceName, String targetName) {
        for (DownloadQueue queue : queues) {
            if (queue.getName().equals(sourceName))
                queue.setName(targetName);
        }
    }

    public static DownloadQueue findQueueByItems(ArrayList<DownloadPanel> items) {
        for (DownloadQueue queue : queues)
            if (queue.getItems() == items)
                return queue;
        return null;
    }

    public static DownloadQueue findShownQueue() {
        for (DownloadQueue queue : queues)
            if (queue.isShown())
                return queue;
        return null;
    }
}