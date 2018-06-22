package Collection;

import gui.Panels.DownloadPanel;
import gui.Panels.QueueSettings;

import javax.swing.text.html.HTMLDocument;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents a simple Download queue
 */
public class DownloadQueue implements Serializable {
    private ArrayList<DownloadPanel> items;
    private String name;
    private String startTime;
    private String startDate;
    private transient QueueSettings queueSettings;
    private boolean isSelected;
    private boolean isShown;

    public DownloadQueue(String name) {
        items = new ArrayList<>();
        this.name = name;
        startTime = LocalTime.now().toString();
        startDate = LocalDate.now().toString();
        isSelected = false;
        queueSettings = new QueueSettings();
        queueSettings.fillSettingsWithMyStats(this);
    }

    public void createQueueSettings() {
        queueSettings = new QueueSettings();
        queueSettings.fillSettingsWithMyStats(this);
    }

    public ArrayList<DownloadPanel> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public void addDownloadPanel(DownloadPanel downloadPanel) {
        items.add(downloadPanel);
    }

    public void removeDownloadPanel(DownloadPanel downloadPanel) {
        Iterator it = items.iterator();
        while (it.hasNext())
            if (it.next() == downloadPanel)
                it.remove();
    }

    public void select() {
        if (!isSelected)
            isSelected = true;
    }

    public void deselect() {
        if (isSelected)
            isSelected = false;
    }

    public void showMySettings() {
        queueSettings.makeVisible();
    }

    public void hideMySettings() {
        queueSettings.makeHidden();
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public QueueSettings getQueueSettings() {
        return queueSettings;
    }

    public void show() {
        if (!isShown)
            isShown = true;
    }

    public void hide() {
        if (isShown)
            isShown = false;
    }

    public boolean isShown() {
        return isShown;
    }
}