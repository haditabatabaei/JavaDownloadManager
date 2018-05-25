package Collection;

import Download.Download;

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


    public DownloadQueue(String name) {
        items = new ArrayList<>();
        this.name = name;
        startTime = LocalTime.now().toString();
        startDate = LocalDate.now().toString();
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
}