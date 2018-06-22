package ActionHandlers;

import Collection.DownloadCollection;
import Collection.DownloadQueue;
import download.Download;
import gui.MainGui;
import gui.Panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CentralCommand implements ActionListener {
    public static final String COMMAND_START_DOWNLOAD = "start";
    public static final String COMMAND_PAUSE_DOWNLOAD = "pause";
    public static final String COMMAND_CANCEL_DOWNLOAD = "cancel";
    public static final String COMMAND_RESUME_DOWNLOAD = "resume";
    public static final String COMMAND_SHOW_NEW_DOWNLOAD = "shownewdownload";
    public static final String COMMAND_SHOW_SETTINGS = "showsettings";
    public static final String COMMAND_SHOW_QUEUE = "showqueuewindow";
    public static final String COMMAND_SHOW_SEARCH = "showsearchwindow";
    public static final String COMMAND_SHOW_SORT = "showarrangewindow";
    public static final String COMMAND_SHOW_PROCESSING_DOWNLOADS = "showprocessing";
    public static final String COMMAND_SHOW_COMPLETED_DOWNLOADS = "showcompleted";
    public static final String COMMAND_SHOW_QUEUE_SELECTION_WINDOW = "showqueueselectionwindow";
    public static final String COMMAND_SHOW_QUEUE_MANAGER_FRAME = "showqueuemanagerframe";
    public static final String COMMAND_SHOW_QUEUE_SETTINGS_FRAME = "showqueuesettingsframe";
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_SYSTEM_TRAY = "systemtray";
    public static final String COMMAND_NEW_DOWNLOAD = "newdownload";
    public static final String COMMAND_HELP = "help";
    public static final String COMMAND_REGISTER = "register";
    public static final String COMMAND_REMOVE_DOWNLOAD = "remove";
    public static final String COMMAND_ABOUT = "about";
    public static final String COMMAND_CHANGE_TO_DEFAULT_THEME = "changetodefault";
    public static final String COMMAND_CHANGE_TO_WINDOWS_THEME = "changetowindows";
    public static final String COMMAND_CHANGE_TO_NIMBUS_THEME = "changetowindows";
    public static final String COMMAND_OPEN_DOWNLOAD_FOLDER = "opendownloadfolder";
    public static final String COMMAND_OPEN_DOWNLOAD_FILE = "opendownloadfile";
    public static final String COMMAND_AUTOMATIC_DOWNLOAD_STATUS = "automaticdownloadstatus";
    public static final String COMMAND_MANUAL_DOWNLOAD_STATUS = "mamnualdownloadstatus";
    public static final String COMMAND_QUEUE_DOWNLOAD_STATUS = "queuedownloadstatus";
    public static final String COMMAND_CREATE_NEW_DOWNLOAD = "createnewdownload";
    public static final String COMMAND_CANCEL_NEW_DOWNLOAD_FRAME = "cancelnewdownloadframe";
    public static final String COMMAND_REOPEN_FRAME_FOR_TRAY = "reopenframefortray";
    public static final String COMMAND_ADD_NEW_QUEUE = "addnewqueue";
    public static final String COMMAND_REMOVE_SELECTED_QUEUE = "removeselectedqueue";
    public static final String COMMAND_SELECT_QUEUE = "selectqueue";
    public static final String COMMAND_APPLY_QUEUE_SETTINGS = "applyqueuesettings";
    public static final String COMMAND_CANCEL_QUEUE_SETTINGS = "cancelqueuesettings";
    public static final String COMMAND_SELECT_QUEUE_FOR_SHOW = "selectqueueforshow";
    public static final String COMMAND_START_SORT = "startsort";
    public static final String COMMAND_APPLY_SETTINGS = "applyttingswindow";

    @Override
    public void actionPerformed(ActionEvent e) {
        String COMMAND = "";
        if (e.getSource() instanceof JButton)
            COMMAND = ((JButton) e.getSource()).getActionCommand();
        else if (e.getSource() instanceof JMenuItem)
            COMMAND = ((JMenuItem) e.getSource()).getActionCommand();
        else if (e.getSource() instanceof JRadioButton)
            COMMAND = ((JRadioButton) e.getSource()).getActionCommand();

        switch (COMMAND) {

            case COMMAND_SELECT_QUEUE_FOR_SHOW:
                selectQueueForShowing();
            case COMMAND_CANCEL_QUEUE_SETTINGS:
                cancelQueueSettings();
                break;
            case COMMAND_APPLY_QUEUE_SETTINGS:
                applyQueueSettings();
                break;
            case COMMAND_SHOW_QUEUE_SETTINGS_FRAME:
                showQueueSettingsFrame();
                break;
            case COMMAND_SHOW_QUEUE_MANAGER_FRAME:
                NewDownloadFrame.queuePanel.makeVisible();
                break;
            case COMMAND_ADD_NEW_QUEUE:
                addNewQueue();
                break;
            case COMMAND_REMOVE_SELECTED_QUEUE:
                removeSelectedQueue();
                break;
            case COMMAND_SELECT_QUEUE:
                chooseSelectedQueue();
                break;
            case COMMAND_EXIT:
                System.exit(0);
                saveDownloadsToFile();
                break;
            case COMMAND_SHOW_PROCESSING_DOWNLOADS:
                readyProcessingIndexingForShow();
                showProcessing();
                break;
            case COMMAND_SHOW_COMPLETED_DOWNLOADS:
                showCompleted();
                break;
            case COMMAND_SHOW_QUEUE:
                showQueueSelectionFrame();
                break;
            case COMMAND_SHOW_SEARCH:
                searchProtocol();
                showSearchResult();
                break;
            case COMMAND_SHOW_NEW_DOWNLOAD:
                showNewDownloadFrame();
                setSettings();
                break;
            case COMMAND_SHOW_SETTINGS:
                showSettingsFrame();
                break;
            case COMMAND_APPLY_SETTINGS:
                applySettings();
                break;
            case COMMAND_SHOW_SORT:
                showSortFrame();
                break;
            case COMMAND_SHOW_QUEUE_SELECTION_WINDOW:
                showQueueSelectionFrame();
                break;
            case COMMAND_NEW_DOWNLOAD:
                createNewDownload();
                updateButtonNumbers();
                MainGui.newDownloadFrame.makeHidden();
                break;
            case COMMAND_CANCEL_NEW_DOWNLOAD_FRAME:
                MainGui.newDownloadFrame.makeHidden();
                break;
            case COMMAND_REMOVE_DOWNLOAD:
                findAndRemoveSelectedDownloads();
                updateButtonNumbers();
                break;
            case COMMAND_ABOUT:
                showAboutMessage();
                break;
            case COMMAND_SYSTEM_TRAY:
                break;
            case COMMAND_REOPEN_FRAME_FOR_TRAY:
                MainGui.frame.setVisible(true);
                // MainGui.systemTray
                break;
            case COMMAND_START_SORT:
                sort();
                break;
            case COMMAND_PAUSE_DOWNLOAD:
                pauseSelectedDownloads();
                break;
            case COMMAND_RESUME_DOWNLOAD:
                resumeSelectedDownloads();
                break;
        }
    }

    private void resumeSelectedDownloads() {
        for (DownloadPanel downloadPanel : MainGui.processingDownloadsPanelList)
            if (downloadPanel.isSelected())
                if (downloadPanel.getDownload().isPaused()) {
                    downloadPanel.getDownload().resumeDownload();
                }
    }

    private void pauseSelectedDownloads() {
        for (DownloadPanel downloadPanel : MainGui.processingDownloadsPanelList)
            if (downloadPanel.isSelected())
                if (downloadPanel.getDownload().isDownloading()) {
                    System.out.println("Pause selected.");
                    downloadPanel.getDownload().pause();
                }
    }

    public void reloadDownloadsFromFile() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MainGui.listDownloadFile));
            MainGui.revivedDownloadList = (ArrayList<Download>) objectInputStream.readObject();
            for (int i = 0; i < MainGui.revivedDownloadList.size(); i++) {
                MainGui.revivedDownloadList.get(i).print();
                DownloadPanel tmpPanel = new DownloadPanel();
                tmpPanel.addDownload(MainGui.revivedDownloadList.get(i));
                MainGui.processingDownloadsPanelList.add(tmpPanel);
            }
            readyProcessingIndexingForShow();
            MainGui.revivedDownloadList.clear();
            System.out.println("Loading proccessing downloads completed.");
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("io no load file");
        } catch (ClassNotFoundException e) {
            System.out.println("no load file.");
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MainGui.listQueueFile));
            DownloadCollection.queues = (ArrayList<DownloadQueue>) objectInputStream.readObject();
            for (DownloadQueue queue : DownloadCollection.queues) {
                QueuePanel.comboBox.addItem(queue.getName());
                queue.createQueueSettings();
                for (DownloadPanel panel : queue.getItems())
                    panel.setMouseListener();
            }

            System.out.println("Loading queues downloads completed.");
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("io no load file");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        updateButtonNumbers();
    }

    public void saveQueuesToFile() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(MainGui.listQueueFile));
            writer.writeObject(DownloadCollection.queues);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applySettings() {
        Pattern p = Pattern.compile("[1-9][0-9]*");
        String numOfDownloadsInput = MainGui.settingsFrame.getNumberOfDownloads().getText();
        Matcher m = p.matcher(numOfDownloadsInput);
        boolean numOk = false;
        boolean saveOk;
        boolean siteOk;
        int num = 0;
        if (m.matches())
            numOk = true;

        if (numOk) {
            num = Integer.parseInt(numOfDownloadsInput);
            MainGui.downloadsSimetanLimit = num;
        }
        MainGui.filterSiteList.clear();
        for (int i = 0; i < SettingsFrame.filteredCombobox.getItemCount(); i++)
            MainGui.filterSiteList.add(SettingsFrame.filteredCombobox.getItemAt(i));

        MainGui.defaultSavePath = MainGui.settingsFrame.getDefSavePath().getText();
        saveSettingsToFile();
        MainGui.settingsFrame.makeHidden();
    }

    private void saveSettingsToFile() {
        try {
            FileWriter writer = new FileWriter(MainGui.settingsFile);
            String toWrite = "[LIMIT]:" + MainGui.downloadsSimetanLimit + "\n[DEFSAVEPATH]:" + MainGui.defaultSavePath + "\n";
            for (String filterSite : MainGui.filterSiteList)
                toWrite += "[FILTERSITE]:" + filterSite + "\n";

            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRemovedDownloadsToFile() {
        try {
            FileWriter writer = new FileWriter(MainGui.listRemovedFile, true);
            String toWrite = "";
            for (DownloadPanel downloadPanel : MainGui.removedDownloadsPanelList)
                toWrite += "[URL]:" + downloadPanel.getDownload().getUrlAddress() + "\n";
            writer.write(toWrite);
            writer.close();
            MainGui.removedDownloadsPanelList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectQueueForShowing() {
        if (QueueSelectionFrame.selectableComboBox.getItemCount() != 0) {
            String selectedQueueName = QueueSelectionFrame.selectableComboBox.getSelectedItem() + "";
            if (selectedQueueName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Create a queue first. list is empty.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                DownloadQueue selectedQueue = DownloadCollection.findQueueByName(selectedQueueName);
            }
            makeThisOneShown(DownloadCollection.findQueueByName(selectedQueueName));
            readyQueueIndexingForShow(selectedQueueName);
            showQueue();
        } else
            JOptionPane.showMessageDialog(null, "Select queue.or create using manager in new download window\n", "Error", JOptionPane.WARNING_MESSAGE);
    }

    private void cancelQueueSettings() {
        DownloadQueue selectedQueue = DownloadCollection.findQueueByName(QueuePanel.comboBox.getSelectedItem() + "");
        selectedQueue.hideMySettings();
    }

    private void applyQueueSettings() {
        boolean nameCheck = false;
        boolean dateCheck = false;
        boolean timeCheck = false;
        DownloadQueue selectedQueue = DownloadCollection.findQueueByName(QueuePanel.comboBox.getSelectedItem() + "");
        QueueSettings selectedQueueSettingsFrame = selectedQueue.getQueueSettings();
        String inputQueueName = selectedQueueSettingsFrame.getNameTextField().getText();
        if (!QueuePanel.hasRepetiveInCombobox(inputQueueName))
            nameCheck = true;
                /*
                DATE CHECK
                 */

                /*
                TIME CHECK;
                 */
        if (nameCheck) {
            String old = selectedQueue.getName();
            DownloadCollection.changeQueueName(old, inputQueueName);
            QueuePanel.comboBox.removeItem(QueuePanel.comboBox.getSelectedItem());
            QueuePanel.updateQueueList();
            JOptionPane.showMessageDialog(null, "Name Changed from : " + old + " to " + inputQueueName, "Name Changed.", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showQueueSettingsFrame() {
        try {
            String selectedQueueName = QueuePanel.comboBox.getSelectedItem() + "";
            if (!selectedQueueName.isEmpty()) {
                DownloadQueue selectedQueue = DownloadCollection.findQueueByName(selectedQueueName);
                selectedQueue.showMySettings();
            } else {
                JOptionPane.showMessageDialog(null, "Nothing is selected.\nSelect or add a queue first.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "Nothing is selected.\nSelect or add a queue first.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void chooseSelectedQueue() {
        if (QueuePanel.comboBox.getItemCount() != 0) {
            String selectedName = QueuePanel.comboBox.getSelectedItem() + "";
            DownloadQueue selectedQueue = DownloadCollection.findQueueByName(selectedName);
            selectedQueue.select();
            NewDownloadFrame.selectedQueueName.setText("Selected Queue : " + selectedQueue.getName());
            NewDownloadFrame.selectedQueueName.revalidate();
            JOptionPane.showMessageDialog(null, selectedName + " Selected.", "Select", JOptionPane.INFORMATION_MESSAGE);
            QueuePanel.frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to select.Try add a queue first.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removeSelectedQueue() {
        DownloadCollection.removeQueue(QueuePanel.comboBox.getSelectedItem() + "");
        QueuePanel.comboBox.removeItem(QueuePanel.comboBox.getSelectedItem());
    }

    private void addNewQueue() {
        String queueName;
        while (true) {
            try {
                queueName = JOptionPane.showInputDialog("Enter Queue Name (Without Spaces) : ").replaceAll(" ", "");
            } catch (NullPointerException em) {
                System.out.println("cancel or close.");
                break;
            }

            if (queueName.equals("")) {
                JOptionPane.showMessageDialog(null, "Queue must have a name\nEnter Again.", "Error.", JOptionPane.WARNING_MESSAGE);
            } else {
                if (!QueuePanel.hasRepetiveInCombobox(queueName)) {
                    QueuePanel.comboBox.addItem(queueName);
                    DownloadCollection.createNewQueue(queueName);
                    QueuePanel.comboBox.revalidate();
                    JOptionPane.showMessageDialog(null, "Queue :" + queueName + " created successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Duplicate Queue Name.\nTry Again.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private static void setSettings() {
        MainGui.newDownloadFrame.getSavePath().setText(MainGui.defaultSavePath);
    }

    private static void showProcessing() {
        if (!(MainGui.processingScrollPane.isVisible())) {
            MainGui.processingScrollPane.setVisible(true);
            MainGui.completedScrollPane.setVisible(false);
            MainGui.queueScrollPane.setVisible(false);
            MainGui.searchResultScrollPane.setVisible(false);
            MainGui.frame.revalidate();
            System.out.println("show proc called.");
        }
    }

    private static void showCompleted() {
        if (!(MainGui.completedScrollPane.isVisible())) {
            MainGui.processingScrollPane.setVisible(false);
            MainGui.completedScrollPane.setVisible(true);
            MainGui.queueScrollPane.setVisible(false);
            MainGui.searchResultScrollPane.setVisible(false);
            MainGui.frame.revalidate();
            System.out.println("show completed called.");
        }
    }

    private static void readyQueueIndexingForShow(String toShowName) {
        DownloadQueue toShow = DownloadCollection.findQueueByName(toShowName);
        MainGui.queueIndexingPanel.removeAll();
        for (DownloadPanel downloadPanel : toShow.getItems()) {
            MainGui.queueIndexingPanel.add(downloadPanel);
        }
        MainGui.gQueue.setRows(toShow.getItems().size());
        updateButtonNumbers();
        MainGui.queuesBtn.setText("Queue[" + toShow.getName() + "] (" + toShow.getItems().size() + ")");
    }

    private static void readyProcessingIndexingForShow() {
        MainGui.processingIndexingPanel.removeAll();
        for (DownloadPanel downloadPanel : MainGui.processingDownloadsPanelList) {
            MainGui.processingIndexingPanel.add(downloadPanel);
        }
        MainGui.gProcess.setRows(MainGui.processingDownloadsPanelList.size());
        MainGui.frame.revalidate();
    }

    private static void readyCompletedIndexingForShow() {
        MainGui.completedIndexingPanel.removeAll();
        for (DownloadPanel downloadPanel : MainGui.completedDownloadsPanelList) {
            MainGui.completedIndexingPanel.add(downloadPanel);
        }
        MainGui.gCompleted.setRows(MainGui.completedDownloadsPanelList.size());
        MainGui.frame.revalidate();
    }

    public static void checkAndMoveFinishedDownloads() {
        Iterator it = MainGui.processingDownloadsPanelList.iterator();
        while (it.hasNext()) {
            DownloadPanel thisPanel = (DownloadPanel) it.next();
            if (thisPanel.getDownload().isFinished()) {
                it.remove();
                MainGui.completedDownloadsPanelList.add(thisPanel);
            }
        }
        readyProcessingIndexingForShow();
        readyCompletedIndexingForShow();
        updateButtonNumbers();
    }

    private static void showQueue() {
        if (!(MainGui.queueScrollPane.isVisible())) {
            MainGui.processingScrollPane.setVisible(false);
            MainGui.completedScrollPane.setVisible(false);
            MainGui.queueScrollPane.setVisible(true);
            MainGui.searchResultScrollPane.setVisible(false);
            MainGui.frame.revalidate();
            System.out.println("show queue called.");
        }
    }

    private static void showSearchResult() {
        if (!(MainGui.searchResultScrollPane.isVisible())) {
            MainGui.processingScrollPane.setVisible(false);
            MainGui.completedScrollPane.setVisible(false);
            MainGui.queueScrollPane.setVisible(false);
            MainGui.searchResultScrollPane.setVisible(true);
            MainGui.frame.revalidate();
            System.out.println("show search called.");
        }
    }

    private static void showNewDownloadFrame() {
        if (MainGui.newDownloadFrame == null)
            MainGui.newDownloadFrame = new NewDownloadFrame();
        MainGui.newDownloadFrame.makeVisible();
        MainGui.newDownloadFrame.reset();
        System.out.println("new download window opened.");
    }

    private static void showSettingsFrame() {
        if (MainGui.settingsFrame == null)
            MainGui.settingsFrame = new SettingsFrame();
        MainGui.settingsFrame.makeVisisble();
        System.out.println("Showing settings frame");
    }

    private static void showSortFrame() {
        if (MainGui.sortSelectionFrame == null)
            MainGui.sortSelectionFrame = new SortSelectionFrame();
        MainGui.sortSelectionFrame.makeVisibile();
        System.out.println("Showing sort frame");
    }

    private static void showQueueSelectionFrame() {
        if (MainGui.queueSelectionFrame == null)
            MainGui.queueSelectionFrame = new QueueSelectionFrame();
        MainGui.queueSelectionFrame.makeVisible();
        System.out.println("Showing queue selection frame");
    }

    private static void createNewDownload() {
        NewDownloadFrame downloadFrame = MainGui.newDownloadFrame;
        String url = downloadFrame.getAddressTextField().getText();
        String name = downloadFrame.getNameTextField().getText();
        String savePath = downloadFrame.getSavePath().getText();
        String queueName = "";
        int type = 0;
        boolean willLaunch;
        if (downloadFrame.getManual().isSelected())
            type = 1;
        else if (downloadFrame.getQueue().isSelected()) {
            type = 2;
            queueName = NewDownloadFrame.selectedQueueName.getText().replace("Selected Queue : ", "");
        }

        willLaunch = downloadFrame.getLaunchCheckBox().isSelected();

        Download tmpDownload = new Download(url, name, 0);
        tmpDownload.setSavePath(savePath);
        tmpDownload.setLaunchedAfter(willLaunch);
        if (type == 2) {
            tmpDownload.setInQueue(true);
            tmpDownload.setQueueName(queueName);
        }

        DownloadPanel tmpDownloadPanel = new DownloadPanel();
        tmpDownloadPanel.addDownload(tmpDownload);
        switch (type) {
            case 0:
                MainGui.processingDownloadsPanelList.add(tmpDownloadPanel);
                addProccessingDownloadtoGui(tmpDownloadPanel);
                break;
            case 2:
                addQueueDownload(tmpDownloadPanel);
                break;
        }
    }

    private static void addProccessingDownloadtoGui(DownloadPanel downloadPanel) {
        MainGui.gProcess.setRows(MainGui.gProcess.getRows() + 1);
        MainGui.processingIndexingPanel.add(downloadPanel);
        MainGui.frame.revalidate();
        downloadPanel.getDownload().startDownload(downloadPanel);

    }

    private static void addQueueDownload(DownloadPanel downloadPanel) {
        try {
            DownloadQueue tmpQueue = DownloadCollection.findQueueByName(downloadPanel.getDownload().getQueueName());
            tmpQueue.addDownloadPanel(downloadPanel);
        } catch (NullPointerException e) {
            System.out.println("No Queue with that name.");
        }
    }

    private void findAndRemoveSelectedDownloads() {

        int visibleSide = -1;
        if (MainGui.processingScrollPane.isVisible())
            visibleSide = 0;
        else if (MainGui.completedScrollPane.isVisible())
            visibleSide = 1;
        else if (MainGui.queueScrollPane.isVisible())
            visibleSide = 2;
        DownloadQueue selectedQueue = null;
        if (visibleSide == 2)
            for (DownloadQueue downloadQueue : DownloadCollection.queues)
                if (downloadQueue.isShown()) {
                    selectedQueue = downloadQueue;
                    break;
                }

        switch (visibleSide) {
            case 0:
                Iterator it = MainGui.processingDownloadsPanelList.iterator();
                while (it.hasNext()) {
                    DownloadPanel thisDownloadPanel = (DownloadPanel) it.next();
                    if (thisDownloadPanel.isSelected()) {
                        //first we will remove this from our download panel array list.
                        it.remove();
                        //now we delete it from gui and fix grid layout for our gui
                        MainGui.processingIndexingPanel.remove(thisDownloadPanel);
                        MainGui.gProcess.setRows(MainGui.gProcess.getRows() - 1);
                        //adding this removed panel to removed list for last saving to removed.jdm
                        MainGui.removedDownloadsPanelList.add(thisDownloadPanel);
                    }
                }
                break;
            case 1:
                it = MainGui.completedDownloadsPanelList.iterator();
                while (it.hasNext()) {
                    DownloadPanel thisDownloadPanel = (DownloadPanel) it.next();
                    if (thisDownloadPanel.isSelected()) {
                        it.remove();

                        MainGui.completedIndexingPanel.remove(thisDownloadPanel);
                        MainGui.gCompleted.setRows(MainGui.gCompleted.getRows() - 1);

                        //adding this removed panel to removed list for last saving to removed.jdm
                        MainGui.removedDownloadsPanelList.add(thisDownloadPanel);
                    }
                }
                break;
            case 2:
                it = selectedQueue.getItems().iterator();
                while (it.hasNext()) {
                    DownloadPanel thisDownloadPanel = (DownloadPanel) it.next();
                    if (thisDownloadPanel.isSelected()) {
                        it.remove();

                        MainGui.queueIndexingPanel.remove(thisDownloadPanel);
                        MainGui.gQueue.setRows(MainGui.gQueue.getRows() - 1);

                        //adding this removed panel to removed list for last saving to removed.jdm
                        MainGui.removedDownloadsPanelList.add(thisDownloadPanel);
                    }
                }
                break;
        }
        saveRemovedDownloadsToFile();
        MainGui.frame.revalidate();
    }

    private void showAboutMessage() {
        String title = "About Us";
        String message = "Java Download Manager\nSeyed Mohammad Hadi\n";
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void updateButtonNumbers() {
        MainGui.processingBtn.setText("Processing" + " (" + MainGui.processingDownloadsPanelList.size() + ")");
        MainGui.completedBtn.setText("Completed" + " (" + MainGui.completedDownloadsPanelList.size() + ")");
        MainGui.queuesBtn.setText("Queues");
        MainGui.searchBtn.setText("Search" + " (" + MainGui.searchResultDownloadsPanelList.size() + ")");
        MainGui.frame.revalidate();
    }

    private void searchProtocol() {
        try {
            String inputSearch = JOptionPane.showInputDialog(null, "Enter part of name or url address:", "Search...", JOptionPane.QUESTION_MESSAGE);
            fillSearchArrayList(inputSearch);
            applySearchResultToGui();
        } catch (NullPointerException e1) {
            System.out.println("Null entered in search box.exception handled");
        }
    }

    private void applySearchResultToGui() {
        MainGui.searchResultIndexingPanel.removeAll();
        for (DownloadPanel downloadPanel : MainGui.searchResultDownloadsPanelList)
            MainGui.searchResultIndexingPanel.add(downloadPanel);
        MainGui.gSearch.setRows(MainGui.searchResultDownloadsPanelList.size());
        MainGui.searchBtn.setText("Search | Results : " + MainGui.searchResultDownloadsPanelList.size());
    }

    private void fillSearchArrayList(String input) {
        MainGui.searchResultDownloadsPanelList.clear();
        for (DownloadPanel downloadPanel : MainGui.processingDownloadsPanelList)
            if (downloadPanel.getDownload().getFileName().contains(input) || downloadPanel.getDownload().getUrlAddress().contains(input))
                MainGui.searchResultDownloadsPanelList.add(downloadPanel);

        for (DownloadPanel downloadPanel : MainGui.completedDownloadsPanelList)
            if (downloadPanel.getDownload().getFileName().contains(input) || downloadPanel.getDownload().getUrlAddress().contains(input))
                MainGui.searchResultDownloadsPanelList.add(downloadPanel);

        for (DownloadQueue downloadQueue : DownloadCollection.queues)
            for (DownloadPanel downloadPanel : downloadQueue.getItems())
                if (downloadPanel.getDownload().getFileName().contains(input) || downloadPanel.getDownload().getUrlAddress().contains(input))
                    MainGui.searchResultDownloadsPanelList.add(downloadPanel);
    }

    private void makeThisOneShown(DownloadQueue downloadQueue) {
        for (DownloadQueue queue : DownloadCollection.queues) {
            if (queue == downloadQueue)
                queue.show();
            else
                queue.hide();
        }
    }

    private void sort() {
        System.out.println("Sort Called.");
        int orderType = 0;
        if (SortSelectionFrame.descendingOrder.isSelected())
            orderType = 1;

        int sortType = SortSelectionFrame.comboBox.getSelectedIndex();

        ArrayList<DownloadPanel> toSort = null;
        int isQueue = 0;
        if (MainGui.processingScrollPane.isVisible())
            toSort = MainGui.processingDownloadsPanelList;
        else if (MainGui.completedScrollPane.isVisible())
            toSort = MainGui.completedDownloadsPanelList;
        else if (MainGui.queueScrollPane.isVisible()) {
            isQueue = 1;
        }

        DownloadQueue selectedQueue = null;
        if (isQueue == 1)
            for (DownloadQueue downloadQueue : DownloadCollection.queues)
                if (downloadQueue.isShown()) {
                    toSort = downloadQueue.getItems();
                    break;
                }

        doSort(toSort, sortType, orderType);
    }

    private void doSort(ArrayList<DownloadPanel> toSort, int sortType, int orderType) {
        switch (orderType) {
            case 0:
                switch (sortType) {
                    case 0:
                        for (int i = 0; i < toSort.size() - 1; i++) {
                            for (int j = i + 1; j < toSort.size(); j++) {
                                if (toSort.get(i).getDownload().getFileName().compareTo(toSort.get(j).getDownload().getFileName()) >= 0) {
                                    DownloadPanel temp = toSort.get(i);
                                    toSort.set(i, toSort.get(j));
                                    toSort.set(j, temp);
                                }
                            }
                        }
                        break;
                    case 1:

                        for (int i = 0; i < toSort.size() - 1; i++) {
                            for (int j = i + 1; j < toSort.size(); j++) {
                                if (toSort.get(i).getDownload().getFullFileSize() > toSort.get(j).getDownload().getFullFileSize()) {
                                    DownloadPanel temp = toSort.get(i);
                                    toSort.set(i, toSort.get(j));
                                    toSort.set(j, temp);
                                }
                            }
                        }
                        break;

                    case 2:
                        for (int i = 0; i < toSort.size() - 1; i++) {
                            for (int j = i + 1; j < toSort.size(); j++) {
                                if (toSort.get(i).getDownload().getTime().isAfter(toSort.get(j).getDownload().getTime())) {
                                    DownloadPanel temp = toSort.get(i);
                                    toSort.set(i, toSort.get(j));
                                    toSort.set(j, temp);
                                }
                            }
                        }
                        break;
                }
                break;
            case 1:
                switch (sortType) {
                    case 0:
                        for (int i = 0; i < toSort.size() - 1; i++) {
                            for (int j = i + 1; j < toSort.size(); j++) {
                                if (toSort.get(i).getDownload().getFileName().compareTo(toSort.get(j).getDownload().getFileName()) < 0) {
                                    DownloadPanel temp = toSort.get(i);
                                    toSort.set(i, toSort.get(j));
                                    toSort.set(j, temp);
                                }
                            }
                        }
                        break;

                    case 1:

                        for (int i = 0; i < toSort.size() - 1; i++) {
                            for (int j = i + 1; j < toSort.size(); j++) {
                                if (toSort.get(i).getDownload().getFullFileSize() < toSort.get(j).getDownload().getFullFileSize()) {
                                    DownloadPanel temp = toSort.get(i);
                                    toSort.set(i, toSort.get(j));
                                    toSort.set(j, temp);
                                }
                            }
                        }
                        break;

                    case 2:
                        for (int i = 0; i < toSort.size() - 1; i++) {
                            for (int j = i + 1; j < toSort.size(); j++) {
                                if (toSort.get(i).getDownload().getTime().isBefore(toSort.get(j).getDownload().getTime())) {
                                    DownloadPanel temp = toSort.get(i);
                                    toSort.set(i, toSort.get(j));
                                    toSort.set(j, temp);
                                }
                            }
                        }
                        break;
                }
                break;
        }
        readyProcessingIndexingForShow();
        readyCompletedIndexingForShow();
        try {
            readyQueueIndexingForShow(DownloadCollection.findShownQueue().getName());
        } catch (NullPointerException e) {
            System.out.println("No Queue is shown to sort.");
        }
    }

    //just save processing for the first time.
    public void saveDownloadsToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(MainGui.listDownloadFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (DownloadPanel downloadPanel : MainGui.processingDownloadsPanelList)
                MainGui.revivedDownloadList.add(downloadPanel.getDownload());

            objectOutputStream.writeObject(MainGui.revivedDownloadList);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Saving done.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MainGui.settingsFile));
            String defaultSavePath = "";
            int maxNumber = 0;
            ArrayList<String> filterSites = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[LIMIT]:"))
                    maxNumber = Integer.parseInt(line.replace("[LIMIT]:", ""));
                else if (line.startsWith("[DEFSAVEPATH]:"))
                    defaultSavePath = line.substring(14, line.length());
                else
                    filterSites.add(line.substring(13, line.length()));
            }
            reader.close();
            MainGui.defaultSavePath = defaultSavePath;
            MainGui.downloadsSimetanLimit = maxNumber;
            MainGui.settingsFrame.getDefSavePath().setText(defaultSavePath);
            MainGui.newDownloadFrame.getSavePath().setText(defaultSavePath);
            if (maxNumber == 0)
                MainGui.settingsFrame.getNumberOfDownloads().setText("0 ( no limit )");
            else
                MainGui.settingsFrame.getNumberOfDownloads().setText(maxNumber + "");

            for (String filterSite : filterSites)
                SettingsFrame.filteredCombobox.addItem(filterSite);

            filterSites.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void systemTrayTask() {

    }

}
