package gui;

import ActionHandlers.ButtonHoverHandler;
import ActionHandlers.CentralCommand;
import ActionHandlers.LeftButtonHoverHandler;


import download.Download;
import gui.Panels.*;

import Collection.DownloadCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


import java.util.ArrayList;


/**
 * This is main Java Download Manager Gui
 *
 * @author Seyed Mohammad Hadi Tabatabaei
 * @version 1.0
 */
public class MainGui {
    public static JFrame frame;
    public static String defaultSavePath;
    public static int downloadsSimetanLimit;
    public static SettingsFrame settingsFrame;
    public static SortSelectionFrame sortSelectionFrame;
    public static NewDownloadFrame newDownloadFrame;
    public static QueueSelectionFrame queueSelectionFrame;

    private ArrayList<JButton> toolBarButtons;
    private ArrayList<JMenuItem> topMenuItems;
    public static ArrayList<DownloadPanel> processingDownloadsPanelList;
    public static ArrayList<DownloadPanel> completedDownloadsPanelList;
    public static ArrayList<DownloadPanel> searchResultDownloadsPanelList;
    public static ArrayList<DownloadPanel> removedDownloadsPanelList;
    public static ArrayList<Download> revivedDownloadList;
    public static ArrayList<String> filterSiteList;
    public static DownloadCollection downloadCollection;

    public static GridLayout gProcess;
    public static GridLayout gCompleted;
    public static GridLayout gQueue;
    public static GridLayout gSearch;
    //single components
    public static JButton processingBtn;
    public static JButton completedBtn;
    public static JButton queuesBtn;
    public static JButton searchBtn;
    public static JButton sortBtn;

    private JPanel processingPanel;
    private JPanel completedPanel;
    private JPanel queuePanel;
    private JPanel searchResultPanel;

    public static JPanel processingIndexingPanel;
    public static JPanel completedIndexingPanel;
    public static JPanel queueIndexingPanel;
    public static JPanel searchResultIndexingPanel;

    private JPanel rightPanel;
    private JPanel leftPanel;

    public static JScrollPane processingScrollPane;
    public static JScrollPane completedScrollPane;
    public static JScrollPane queueScrollPane;
    public static JScrollPane searchResultScrollPane;

    public static File listDownloadFile;
    public static File listQueueFile;
    public static File listRemovedFile;
    public static File filterFile;
    public static File settingsFile;
    public static File saveddirectory;
    public static String SAVE_PATH = "saveddata//";
    private ButtonHoverHandler buttonHoverHandler;
    private LeftButtonHoverHandler leftButtonHoverHandler;

    private Dimension myPrefDlDim;

    public static CentralCommand centralCommand;

    private JMenuBar topMenuBar;
    private JToolBar toolBar;

    public static SystemTray systemTray;

    public static Toolkit toolkit;

    private void headStart() {
        //frame stuff
        frame = new JFrame("Java Download Manager");
        frame.setMinimumSize(new Dimension(900, 550));
        frame.setSize(900, 680);
        frame.setLocation(200, 200);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setBackground(Colors.DarkBlue);
        //system tray task for frame
        centralCommand = new CentralCommand();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                centralCommand.systemTrayTask();
            }
        });
        toolBarButtons = new ArrayList<>();
        topMenuItems = new ArrayList<>();
        processingDownloadsPanelList = new ArrayList<>();
        completedDownloadsPanelList = new ArrayList<>();
        searchResultDownloadsPanelList = new ArrayList<>();
        removedDownloadsPanelList = new ArrayList<>();
        revivedDownloadList = new ArrayList<>();
        filterSiteList = new ArrayList<>();

        downloadsSimetanLimit = 0;

        downloadCollection = new DownloadCollection();

        myPrefDlDim = new Dimension(0, 80);
        saveddirectory = new File(SAVE_PATH);
        listDownloadFile = new File(SAVE_PATH + "list.jdm");
        listQueueFile = new File(SAVE_PATH + "queue.jdm");
        listRemovedFile = new File(SAVE_PATH + "removed.jdm");
        filterFile = new File(SAVE_PATH + "filter.jdm");
        settingsFile = new File(SAVE_PATH + "settings.jdm");

        buttonHoverHandler = new ButtonHoverHandler();
        leftButtonHoverHandler = new LeftButtonHoverHandler();

        settingsFrame = new SettingsFrame();
        sortSelectionFrame = new SortSelectionFrame();
        newDownloadFrame = new NewDownloadFrame();
        queueSelectionFrame = new QueueSelectionFrame();
    }

    private void createJmenu() {
        UIManager uim = new UIManager();
        //creating jmenuabr
        topMenuBar = new JMenuBar();

        //creating jmenu
        JMenu downloadMenu = new JMenu("Download");
        JMenu helpMenu = new JMenu("Help");
        JMenu exitOption = new JMenu("Exit");
        JMenu themesMenu = new JMenu("Themes");


        //creating jmenu items
        JMenuItem newDl = new JMenuItem("New Download");
        JMenuItem pauseDl = new JMenuItem("Pause");
        JMenuItem resumeDl = new JMenuItem("Resume");
        JMenuItem cancelDl = new JMenuItem("Cancel");
        JMenuItem removeDl = new JMenuItem("Remove");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem aboutOption = new JMenuItem("About");
        JMenuItem registerOption = new JMenuItem("Register");
        JMenuItem defaultTheme = new JMenuItem("Default");
        JMenuItem windowsTheme = new JMenuItem("Windows 7");
        JMenuItem nimbusTheme = new JMenuItem("Nimbus");


        defaultTheme.addActionListener(uim);
        windowsTheme.addActionListener(uim);
        nimbusTheme.addActionListener(uim);

        downloadMenu.setMnemonic(KeyEvent.VK_D);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        themesMenu.setMnemonic(KeyEvent.VK_T);
        exitOption.setMnemonic(KeyEvent.VK_X);

        //adding items to menu item array list
        topMenuItems.add(newDl);
        topMenuItems.add(pauseDl);
        topMenuItems.add(resumeDl);
        topMenuItems.add(cancelDl);
        topMenuItems.add(removeDl);
        topMenuItems.add(settings);
        topMenuItems.add(aboutOption);
        topMenuItems.add(registerOption);
        topMenuItems.add(exitOption);
        topMenuItems.add(defaultTheme);
        topMenuItems.add(windowsTheme);
        topMenuItems.add(nimbusTheme);

        exitOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                centralCommand.saveDownloadsToFile();
                centralCommand.saveQueuesToFile();
                System.exit(0);
            }
        });
        //adding action commands
        for (int i = 0; i < 11; i++) {
            JMenuItem selectedItem = topMenuItems.get(i);
            selectedItem.addActionListener(centralCommand);
            switch (i) {
                //new download
                case 0:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_SHOW_NEW_DOWNLOAD);
                    selectedItem.setMnemonic(KeyEvent.VK_N);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
                    break;
                //pause download
                case 1:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_PAUSE_DOWNLOAD);
                    selectedItem.setMnemonic(KeyEvent.VK_P);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
                    break;
                //resume download
                case 2:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_RESUME_DOWNLOAD);
                    selectedItem.setMnemonic(KeyEvent.VK_R);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
                    break;
                //cancel download
                case 3:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_CANCEL_DOWNLOAD);
                    selectedItem.setMnemonic(KeyEvent.VK_C);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
                    break;
                //remove download
                case 4:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_REMOVE_DOWNLOAD);
                    selectedItem.setMnemonic(KeyEvent.VK_O);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
                    break;
                //settings frame
                case 5:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_SHOW_SETTINGS);
                    selectedItem.setMnemonic(KeyEvent.VK_S);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
                    break;
                //about option
                case 6:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_ABOUT);
                    selectedItem.setMnemonic(KeyEvent.VK_A);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
                    break;
                //register option
                case 7:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_REGISTER);
                    selectedItem.setMnemonic(KeyEvent.VK_G);
                    selectedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
                    break;
                case 8:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_EXIT);
                case 9:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_CHANGE_TO_DEFAULT_THEME);
                    selectedItem.setMnemonic(KeyEvent.VK_E);
                    break;
                case 10:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_CHANGE_TO_WINDOWS_THEME);
                    selectedItem.setMnemonic(KeyEvent.VK_W);
                    break;
                case 11:
                    selectedItem.setActionCommand(CentralCommand.COMMAND_CHANGE_TO_NIMBUS_THEME);
                    selectedItem.setMnemonic(KeyEvent.VK_U);
                    break;
            }

        }

        topMenuBar.add(downloadMenu);
        topMenuBar.add(helpMenu);
        topMenuBar.add(exitOption);

        downloadMenu.add(newDl);
        downloadMenu.add(pauseDl);
        downloadMenu.add(resumeDl);
        downloadMenu.add(cancelDl);
        downloadMenu.add(removeDl);
        downloadMenu.add(settings);

        themesMenu.add(defaultTheme);
        themesMenu.add(windowsTheme);
        themesMenu.add(nimbusTheme);

        helpMenu.add(aboutOption);
        helpMenu.add(registerOption);
        helpMenu.add(themesMenu);
    }

    private void createTopToolbarObjects() {

        toolBar = new JToolBar("Tool Bar");
        toolBarButtons.add(new JButton("New Download"));//
        toolBarButtons.add(new JButton("Pause"));//
        toolBarButtons.add(new JButton("Resume"));//
        toolBarButtons.add(new JButton("Cancel"));
        toolBarButtons.add(new JButton("Remove"));//
        toolBarButtons.add(new JButton("Settings"));//

        toolBar.setBackground(Colors.LightBlue);
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(false);
        toolBar.setBorder(new EmptyBorder(0, 0, 0, 0));
        EmptyBorder toolbarBtnEBorder = new EmptyBorder(10, 5, 10, 5);

        for (int i = 0; i < 6; i++) {
            JButton tmpBtn = toolBarButtons.get(i);
            switch (i) {
                case 0:
                    tmpBtn.setActionCommand(CentralCommand.COMMAND_SHOW_NEW_DOWNLOAD);
                    tmpBtn.setIcon(Icons.ICON_NEW_DOWNLOAD_GREEN);
                    break;
                case 1:
                    tmpBtn.setActionCommand(CentralCommand.COMMAND_PAUSE_DOWNLOAD);
                    tmpBtn.setIcon(Icons.ICON_PAUSE_COLOR);
                    break;
                case 2:
                    tmpBtn.setActionCommand(CentralCommand.COMMAND_RESUME_DOWNLOAD);
                    tmpBtn.setIcon(Icons.ICON_START_COLOR);
                    break;
                case 3:
                    tmpBtn.setActionCommand(CentralCommand.COMMAND_CANCEL_DOWNLOAD);
                    tmpBtn.setIcon(Icons.ICON_CANCEL_COLOR);
                    break;
                case 4:
                    tmpBtn.setActionCommand(CentralCommand.COMMAND_REMOVE_DOWNLOAD);
                    tmpBtn.setIcon(Icons.ICON_DELETE_PURPLE);
                    break;
                case 5:
                    tmpBtn.setActionCommand(CentralCommand.COMMAND_SHOW_SETTINGS);
                    tmpBtn.setIcon(Icons.ICON_SETTINGS_COLOR);
                    break;
            }
            tmpBtn.setBackground(Colors.LightBlue);
            tmpBtn.setForeground(Colors.DarkGray);
            tmpBtn.addMouseListener(buttonHoverHandler);
            tmpBtn.setFocusPainted(false);
            tmpBtn.setBorderPainted(false);
            tmpBtn.setBorder(toolbarBtnEBorder);
            tmpBtn.addActionListener(centralCommand);
            toolBar.add(tmpBtn);
            toolBar.addSeparator();
        }
    }

    private void createLeft() {
        leftPanel = new JPanel();
        leftPanel.setBackground(Colors.DarkGray);
        leftPanel.setPreferredSize(new Dimension(200, 0));

        leftPanel.setLayout(new BorderLayout());
        JPanel leftButtonsPanel = new JPanel(new BorderLayout());
        leftButtonsPanel.setBackground(Colors.DarkGray);
        leftButtonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel logoLable = new JLabel();
        logoLable.setIcon(Icons.LOGO);
        JPanel leftButtonsExactPanel = new JPanel(new GridLayout(5, 1, 0, 0));
        leftPanel.add(logoLable, BorderLayout.NORTH);
        leftPanel.add(leftButtonsPanel, BorderLayout.CENTER);
        leftButtonsPanel.add(leftButtonsExactPanel, BorderLayout.NORTH);

        ArrayList<JButton> leftButtonsList = new ArrayList<>();

        processingBtn = new JButton("Processing" + " (" + processingDownloadsPanelList.size() + ")");
        completedBtn = new JButton("Completed" + " (" + completedDownloadsPanelList.size() + ")");
        queuesBtn = new JButton("Queues");
        searchBtn = new JButton("Search" + " (" + searchResultDownloadsPanelList.size() + ")");
        sortBtn = new JButton("Sort");

        processingBtn.setActionCommand(CentralCommand.COMMAND_SHOW_PROCESSING_DOWNLOADS);
        completedBtn.setActionCommand(CentralCommand.COMMAND_SHOW_COMPLETED_DOWNLOADS);
        queuesBtn.setActionCommand(CentralCommand.COMMAND_SHOW_QUEUE);
        searchBtn.setActionCommand(CentralCommand.COMMAND_SHOW_SEARCH);
        sortBtn.setActionCommand(CentralCommand.COMMAND_SHOW_SORT);

        processingBtn.setIcon(Icons.ICON_PROCESSING_COLOR);
        completedBtn.setIcon(Icons.ICON_COMPLETED_COLOR);
        queuesBtn.setIcon(Icons.ICON_QUEUE_COLOR);
        searchBtn.setIcon(Icons.ICON_SEARCH_COLOR);
        sortBtn.setIcon(Icons.ICON_SORT_COLOR);

        leftButtonsList.add(processingBtn);
        leftButtonsList.add(completedBtn);
        leftButtonsList.add(queuesBtn);
        leftButtonsList.add(searchBtn);
        leftButtonsList.add(sortBtn);


        EmptyBorder leftBtnEmpBorder = new EmptyBorder(15, 0, 15, 0);

        for (JButton leftButton : leftButtonsList) {
            leftButton.setHorizontalAlignment(SwingConstants.CENTER);
            leftButton.setBackground(Colors.DarkGray);
            leftButton.setForeground(Color.WHITE);
            leftButton.setBorderPainted(false);
            leftButton.setBorder(leftBtnEmpBorder);
            leftButton.setFocusPainted(false);
            leftButtonsExactPanel.add(leftButton);
            leftButton.addMouseListener(leftButtonHoverHandler);
            leftButton.addActionListener(centralCommand);
        }

    }

    private void createRight() {

        rightPanel = new JPanel();
        rightPanel.setLayout(new OverlayLayout(rightPanel));

        processingPanel = new JPanel(new BorderLayout());
        completedPanel = new JPanel(new BorderLayout());
        queuePanel = new JPanel(new BorderLayout());
        searchResultPanel = new JPanel(new BorderLayout());

        gProcess = new GridLayout(0, 1, 0, 0);
        gCompleted = new GridLayout(0, 1, 0, 0);
        gQueue = new GridLayout(0, 1, 0, 0);
        gSearch = new GridLayout(0, 1, 0, 0);

        processingIndexingPanel = new JPanel(gProcess);
        completedIndexingPanel = new JPanel(gCompleted);
        queueIndexingPanel = new JPanel(gQueue);
        searchResultIndexingPanel = new JPanel(gSearch);

        processingScrollPane = new JScrollPane(processingPanel);
        completedScrollPane = new JScrollPane(completedPanel);
        queueScrollPane = new JScrollPane(queuePanel);
        searchResultScrollPane = new JScrollPane(searchResultPanel);

        processingScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        completedScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        queueScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        processingPanel.add(processingIndexingPanel, BorderLayout.NORTH);
        completedPanel.add(completedIndexingPanel, BorderLayout.NORTH);
        queuePanel.add(queueIndexingPanel, BorderLayout.NORTH);
        searchResultPanel.add(searchResultIndexingPanel, BorderLayout.NORTH);

        rightPanel.add(completedScrollPane);
        rightPanel.add(processingScrollPane);
        rightPanel.add(queueScrollPane);
        rightPanel.add(searchResultScrollPane);

        processingScrollPane.setVisible(true);
        completedScrollPane.setVisible(false);
        queueScrollPane.setVisible(false);
        searchResultScrollPane.setVisible(false);
    }

    private void addingStuff() {
        completedPanel.add(completedIndexingPanel, BorderLayout.NORTH);
        processingPanel.add(processingIndexingPanel, BorderLayout.NORTH);
        queuePanel.add(queueIndexingPanel, BorderLayout.NORTH);
        searchResultPanel.add(searchResultIndexingPanel, BorderLayout.NORTH);
        frame.add(rightPanel, BorderLayout.CENTER);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.setJMenuBar(topMenuBar);
        frame.setVisible(true);
    }

    private void createFiles() {
        try {
            if (saveddirectory.mkdir())
                System.out.println("Directory created.");
            else
                System.out.println("Directory exists");

            if (listDownloadFile.createNewFile()) {
                System.out.println("download list file created.");
            } else
                System.out.println("download list file already existed.");
            if (listQueueFile.createNewFile())
                System.out.println("queue list file created.");
            else
                System.out.println("queue list file already exist.");

            if (settingsFile.createNewFile())
                System.out.println("setting created.");
            else
                System.out.println("settings existed.");

            if (settingsFile.createNewFile())
                System.out.println("filter file created.");
            else
                System.out.println("filter file existed.");

            if (listRemovedFile.createNewFile())
                System.out.println("removed downloads file created.");
            else
                System.out.println("removed downloads file exist.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainGui() {
        headStart();
        createJmenu();
        createTopToolbarObjects();
        createLeft();
        createRight();
        addingStuff();
        createFiles();
        centralCommand.loadSettings();
        centralCommand.reloadDownloadsFromFile();
        System.out.println("Started. No Errors.");
    }

    /**
     * This inner class handles UIManager and look and feel
     */
    class UIManager implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tmp = ((JMenuItem) e.getSource()).getText();
            if (tmp.equals("Default")) {
                try {

                    javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception ignored) {
                }
            } else if (tmp.equals("Windows 7")) {
                try {

                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

                } catch (Exception ignored) {
                }
            } else {
                try {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

                } catch (Exception ignored) {
                }
            }
            SwingUtilities.updateComponentTreeUI(frame.getRootPane());
        }
    }


    public void saveQueueDownloadsToFile() {
        try {
            String toWrite = "";
            FileWriter fileWriter = new FileWriter(listQueueFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //  for (DownloadQueue downloadQueue : downloadCollection.getQueues()) {
            //     toWrite += "{QUEUENAME}:" + downloadQueue.getName() + "\n";
            //     for (Download download : downloadQueue.getItems()) {
            //     toWrite += download.toStringForSave();
            //     }
            // }
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Queue downloads saved to queue.jdm");
    }


    public static boolean isFilterSite(String urlAddress) {
        for (int i = 0; i < SettingsFrame.filteredCombobox.getItemCount(); i++) {
            if (SettingsFrame.filteredCombobox.getItemAt(i).contains(urlAddress)) {
                return true;
            }
        }
        return false;
    }
}