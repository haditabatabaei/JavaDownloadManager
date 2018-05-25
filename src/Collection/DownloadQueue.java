package Collection;

import Download.Download;
import gui.Panels.QueueSettings;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * This class represents a simple Download queue
 */
public class DownloadQueue {
    private ArrayList<Download> items;
    private String name;
    private String startTime;
    private String startDate;
    private QueueSettings queueSettings;


    public DownloadQueue(String name) {
        items = new ArrayList<>();
        this.name = name;
        startTime = LocalTime.now().toString();
        startDate = LocalDate.now().toString();
        queueSettings = new QueueSettings();
    }

    public void showMySettings(){
        queueSettings.fillFrameWithThisQueue(this);
        queueSettings.makeVisible();
    }

    public DownloadQueue() {
        items = new ArrayList<>();
        name = "UnNamed.";
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean add(Download download) {
        if (!(items.contains(download))) {
            items.add(download);
            return true;
        }
        return false;
    }

    public boolean remove(Download download) {
        if (items.contains(download)) {
            items.remove(download);
            return true;
        }
        return false;
    }

    public boolean equals(DownloadQueue queue) {
        return this.name.equals(queue.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public QueueSettings getQueueSettings() {
        return queueSettings;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }
}