package gui;

import ActionHandlers.ButtonHoverHandler;
import ActionHandlers.LeftButtonHoverHandler;

import Collection.DownloadQueue;
import Download.Download;
import gui.Panels.*;

import Collection.DownloadCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is main Java Download Manager Gui
 *
 * @author Seyed Mohammad Hadi Tabatabaei
 * @version 1.0
 */
public class MainGui extends JFrame {
    private ArrayList<JButton> toolBarButtons;
    private ArrayList<JMenuItem> topMenuItems;
    private NewDownloadFrame newDownloadFrame;
    private SettingsFrame settingsFrame;
    private Icons icons;
    private String defaultSavePath;
    private int numberOfMaximumDownloads;

    public static DownloadCollection downloadCollection;
    public static ArrayList<DownloadPanel> processingDownloadsList;
    public static ArrayList<DownloadPanel> queueDownloadsList;
    public static ArrayList<DownloadPanel> completedDownloadsList;

    private GridLayout g1;
    private GridLayout g2;
    private GridLayout g3;
    private GridLayout g4;

    private JButton processingBtn;
    private JButton completedBtn;
    private JButton queuesBtn;
    private JButton searchBtn;

    private JPanel processingListPanel;
    private JPanel completedListPanel;
    private JPanel queueListPanel;
    private JPanel searchResultListPanel;

    private File listDownloadFile;
    private File listQueueFile;
    private File listRemovedFile;
    private File filterFile;
    private File settingsFile;

    private Dimension myPrefDlDim;

    public MainGui() {
        super("Java Download Manager");
        toolBarButtons = new ArrayList<>();
        topMenuItems = new ArrayList<>();
        icons = new Icons();
        processingDownloadsList = new ArrayList<>();
        queueDownloadsList = new ArrayList<>();
        completedDownloadsList = new ArrayList<>();
        numberOfMaximumDownloads = 0;
        downloadCollection = new DownloadCollection();
        myPrefDlDim = new Dimension(0, 80);
        listDownloadFile = new File("list.jdm");
        listQueueFile = new File("queue.jdm");
        listRemovedFile = new File("removed.jdm");
        filterFile = new File("filter.jdm");
        settingsFile = new File("settings.jdm");
        settingsFrame = new SettingsFrame();
        createFiles();

        g1 = new GridLayout(0, 1, 0, 0);
        g2 = new GridLayout(0, 1, 0, 0);
        g3 = new GridLayout(0, 1, 0, 0);
        g4 = new GridLayout(0, 1, 0, 0);
        processingBtn = new JButton("Processing");
        completedBtn = new JButton("Completed");
        queuesBtn = new JButton("Queues");
        searchBtn = new JButton("Search");

        processingListPanel = new JPanel(g1);
        completedListPanel = new JPanel(g2);
        queueListPanel = new JPanel(g3);
        searchResultListPanel = new JPanel(g4);

        setMinimumSize(new Dimension(900, 500));
        setSize(900, 680);
        setLocation(200, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                systemTrayTask();
            }
        });

        setBackground(Colors.DarkBlue);

        ButtonHoverHandler bhh = new ButtonHoverHandler();
        LeftButtonHoverHandler lbhh = new LeftButtonHoverHandler();

        JToolBar toolBar = new JToolBar("Tool Bar");
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
            switch (tmpBtn.getText()) {
                case "Settings":
                    tmpBtn.setIcon(icons.getSettingsColor());
                    break;
                case "New Download":
                    tmpBtn.setIcon(icons.getNewDlGreen());
                    break;
                case "Pause":
                    tmpBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            doTheJob(1);
                        }
                    });
                    tmpBtn.setIcon(icons.getPauseColor());
                    break;
                case "Remove":
                    tmpBtn.setIcon(icons.getDeletePurple());
                    tmpBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            doTheJob(4);
                        }
                    });
                    break;
                case "Resume":
                    tmpBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            doTheJob(2);
                        }
                    });
                    tmpBtn.setIcon(icons.getStartColor());
                    break;
                case "Cancel":
                    tmpBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            doTheJob(3);
                        }
                    });
                    tmpBtn.setIcon(icons.getCancelColor());
                    break;
            }
            tmpBtn.setBackground(Colors.LightBlue);
            tmpBtn.setForeground(Colors.DarkGray);
            tmpBtn.setFocusPainted(false);
            tmpBtn.addMouseListener(bhh);
            tmpBtn.setBorderPainted(false);
            tmpBtn.setBorder(toolbarBtnEBorder);
            toolBar.add(tmpBtn);
            toolBar.addSeparator();
        }


        JMenuBar topMenuBar = new JMenuBar();
        JMenu downloadMenu = new JMenu("Download");
        JMenu helpMenu = new JMenu("Help");
        JMenu exitOption = new JMenu("Exit");

        JMenu themesMenu = new JMenu("Themes");

        UIManager uim = new UIManager();

        JMenuItem defaultTheme = new JMenuItem("Default");
        JMenuItem windowsTheme = new JMenuItem("Windows 7");
        JMenuItem nimbusTheme = new JMenuItem("Nimbus");

        themesMenu.setMnemonic(KeyEvent.VK_T);
        defaultTheme.setMnemonic(KeyEvent.VK_E);
        windowsTheme.setMnemonic(KeyEvent.VK_W);
        nimbusTheme.setMnemonic(KeyEvent.VK_U);

        defaultTheme.addActionListener(uim);
        windowsTheme.addActionListener(uim);
        nimbusTheme.addActionListener(uim);

        themesMenu.add(defaultTheme);
        themesMenu.add(windowsTheme);
        themesMenu.add(nimbusTheme);
        topMenuBar.add(themesMenu);

        JMenuItem newDl = new JMenuItem("New Download");
        JMenuItem pauseDl = new JMenuItem("Pause");
        JMenuItem resumeDl = new JMenuItem("Resume");
        JMenuItem cancelDl = new JMenuItem("Cancel");
        JMenuItem removeDl = new JMenuItem("Remove");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem aboutOption = new JMenuItem("About");
        JMenuItem registerOption = new JMenuItem("Register");
        Dimension myPrefDlDim = new Dimension(0, 80);

        newDl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        pauseDl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        resumeDl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        cancelDl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        removeDl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        aboutOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        registerOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));

        downloadMenu.setMnemonic(KeyEvent.VK_D);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        exitOption.setMnemonic(KeyEvent.VK_X);

        newDl.setMnemonic(KeyEvent.VK_N);
        pauseDl.setMnemonic(KeyEvent.VK_P);
        resumeDl.setMnemonic(KeyEvent.VK_R);
        cancelDl.setMnemonic(KeyEvent.VK_C);
        removeDl.setMnemonic(KeyEvent.VK_O);
        settings.setMnemonic(KeyEvent.VK_S);
        aboutOption.setMnemonic(KeyEvent.VK_A);
        registerOption.setMnemonic(KeyEvent.VK_G);

        topMenuItems.add(newDl);
        topMenuItems.add(pauseDl);
        topMenuItems.add(resumeDl);
        topMenuItems.add(cancelDl);
        topMenuItems.add(removeDl);
        topMenuItems.add(settings);
        topMenuItems.add(aboutOption);
        topMenuItems.add(registerOption);
        topMenuItems.add(exitOption);


        for (JMenuItem item : topMenuItems) {
            switch (topMenuItems.indexOf(item)) {
                case 0:
                    item.addActionListener(e -> {
                        newDownloadFrame = new NewDownloadFrame();
                        newDownloadFrame.setVisible(true);
                        newDownloadFrame.getSavePath().setText(defaultSavePath);
                        newDownloadTask(newDownloadFrame, myPrefDlDim);
                    });
                    break;
                case 1:
                    doTheJob(1);
                    break;
                case 2:
                    doTheJob(2);
                    break;
                case 3:
                    doTheJob(3);
                    break;
                case 4:
                    doTheJob(4);
                    break;
                case 5:
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            settingsFrame.makeVisisble();
                            settingsTask(settingsFrame);
                        }
                    });
                    break;
                case 6:
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showAboutMessage();
                        }
                    });
                    break;
                case 7:
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Register");
                        }
                    });
                    break;
                case 8:
                    item.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            closeOperation();
                        }
                    });
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

        helpMenu.add(aboutOption);
        helpMenu.add(registerOption);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Colors.DarkGray);
        leftPanel.setPreferredSize(new Dimension(200, 0));

        leftPanel.setLayout(new BorderLayout());
        JPanel leftButtonsPanel = new JPanel(new BorderLayout());
        leftButtonsPanel.setBackground(Colors.DarkGray);
        leftButtonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        ImageIcon logo = new ImageIcon(getClass().getResource("..//images//missile-logo.jpg"));
        JLabel logoLable = new JLabel();
        logoLable.setIcon(logo);
        JPanel leftButtonsExactPanel = new JPanel(new GridLayout(4, 1, 0, 0));
        leftPanel.add(logoLable, BorderLayout.NORTH);
        leftPanel.add(leftButtonsPanel, BorderLayout.CENTER);
        leftButtonsPanel.add(leftButtonsExactPanel, BorderLayout.NORTH);

        ArrayList<JButton> leftButtonsList = new ArrayList<>();

        leftButtonsList.add(processingBtn);
        leftButtonsList.add(completedBtn);
        leftButtonsList.add(queuesBtn);
        leftButtonsList.add(searchBtn);
        EmptyBorder leftBtnEmpBorder = new EmptyBorder(15, 0, 15, 0);

        JPanel processingPanel = new JPanel();
        JPanel completedPanel = new JPanel();
        JPanel queuePanel = new JPanel();
        JPanel searchResultPanel = new JPanel();

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new OverlayLayout(rightPanel));

        JScrollPane processingScrollPane = new JScrollPane(processingPanel);
        JScrollPane completedScrollPane = new JScrollPane(completedPanel);
        JScrollPane queueScrollPane = new JScrollPane(queuePanel);
        JScrollPane searchResultScrollPane = new JScrollPane(searchResultPanel);

        rightPanel.add(completedScrollPane);
        rightPanel.add(processingScrollPane);
        rightPanel.add(queueScrollPane);
        rightPanel.add(searchResultScrollPane);

        processingScrollPane.setVisible(true);
        completedScrollPane.setVisible(false);
        queueScrollPane.setVisible(false);
        searchResultScrollPane.setVisible(false);

        for (JButton button : leftButtonsList) {
            switch (leftButtonsList.indexOf(button)) {
                case 0:
                    button.setHorizontalAlignment(SwingConstants.CENTER);
                    button.setIcon(icons.getProcessingColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!(processingScrollPane.isVisible())) {
                                processingScrollPane.setVisible(true);
                                completedScrollPane.setVisible(false);
                                queueScrollPane.setVisible(false);
                                searchResultScrollPane.setVisible(false);
                                System.out.println("I'm in first if");
                            }
                        }
                    });
                    break;
                case 1:
                    button.setHorizontalAlignment(SwingConstants.CENTER);
                    button.setIcon(icons.getCompletedColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!(completedScrollPane.isVisible())) {
                                completedScrollPane.setVisible(true);
                                processingScrollPane.setVisible(false);
                                queueScrollPane.setVisible(false);
                                searchResultScrollPane.setVisible(false);
                                System.out.println("I'm in second if");
                            }
                        }
                    });
                    break;
                case 2:
                    button.setHorizontalAlignment(SwingConstants.CENTER);
                    button.setIcon(icons.getQueueColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            QueueSelectionFrame queueSelectionFrame = new QueueSelectionFrame();
                            queueSelectionFrame.makeVisible();
                            queueSelectionFrame.getSelectButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String selectedQueueName = QueueSelectionFrame.comboBox.getSelectedItem() + "";
                                    if (selectedQueueName.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Create a queue first. list is empty.", "Error", JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        DownloadQueue selectedQueue = MainGui.downloadCollection.getQueueByName(selectedQueueName);
                                        updatequeueListPanel(selectedQueue);
                                    }
                                    if (!(queueScrollPane.isVisible())) {
                                        processingScrollPane.setVisible(false);
                                        completedScrollPane.setVisible(false);
                                        searchResultScrollPane.setVisible(false);
                                        queueScrollPane.setVisible(true);
                                        System.out.println("I'm in third if");
                                    }
                                }
                            });
                        }
                    });
                    revalidate();
                    repaint();
                    break;
                case 3:
                    button.setHorizontalAlignment(SwingConstants.CENTER);
                    button.setIcon(icons.getSearchIconColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            try {
                                String inputSearch = JOptionPane.showInputDialog(null, "Enter part of name or url address:", "Search...", JOptionPane.QUESTION_MESSAGE);
                                searchAndShowResult(inputSearch);
                            } catch (NullPointerException e1) {
                                System.out.println("Null entered in search box.exception handled");
                            }
                            if (!(searchResultScrollPane.isVisible())) {
                                searchResultScrollPane.setVisible(true);
                                processingScrollPane.setVisible(false);
                                completedScrollPane.setVisible(false);
                                queueScrollPane.setVisible(false);
                            }
                        }
                    });
                    break;
            }
            button.setBackground(Colors.DarkGray);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setBorder(leftBtnEmpBorder);
            button.setFocusPainted(false);
            leftButtonsExactPanel.add(button);
            button.addMouseListener(lbhh);
        }
        /*
         RIGHT PANEL
         */


        processingPanel.setLayout(new BorderLayout());
        completedPanel.setLayout(new BorderLayout());
        queuePanel.setLayout(new BorderLayout());


        processingScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        completedScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        queueScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        /*
        New Download
         */
        toolBarButtons.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDownloadFrame = new NewDownloadFrame();
                newDownloadFrame.setVisible(true);
                newDownloadFrame.getSavePath().setText(defaultSavePath);
                newDownloadTask(newDownloadFrame, myPrefDlDim);
            }
        });
        /*
        Setting
         */
        toolBarButtons.get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.makeVisisble();
                settingsTask(settingsFrame);
            }
        });


        completedPanel.add(completedListPanel, BorderLayout.NORTH);
        processingPanel.add(processingListPanel, BorderLayout.NORTH);
        queuePanel.add(queueListPanel, BorderLayout.NORTH);
        searchResultPanel.add(searchResultListPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        setJMenuBar(topMenuBar);
        add(toolBar, BorderLayout.NORTH);
        setVisible(true);

        loadProcessingDownloads();
        loadQueueDonwloads();
        loadSettingsFromFile();
        System.out.println("Started. No Errors.");
    }

    /**
     * This method updates number of downloads.
     *
     * @param proc processing button
     * @param cmp  completed button
     * @param que  queue button
     */
    private void updateDownloadNumbers(JButton proc, JButton cmp, JButton que) {
        proc.setText("Processing ");
        cmp.setText("Completed ");
        que.setText("Queue ");
        proc.setText(proc.getText() + "(" + downloadCollection.getProcessingDownloads().size() + ")");
        cmp.setText(cmp.getText() + "(" + downloadCollection.getCompletedDownloads().size() + ")");
        que.setText(que.getText() + "(" + downloadCollection.getQueueDownloads().size() + ")");
    }

    /**
     * This method handles adding a new Download task
     *
     * @param newDownloadFrame new Download jfram
     * @param myPrefDlDim      preferred Download dimension
     */
    private void newDownloadTask(NewDownloadFrame newDownloadFrame, Dimension myPrefDlDim) {
        JButton tmpOk = newDownloadFrame.getAddDownload();
        tmpOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {
                int downloadStatus = 1;
                int launchAfter = 0;

                if (newDownloadFrame.getManual().isSelected())
                    downloadStatus = 2;
                else if (newDownloadFrame.getQueue().isSelected())
                    downloadStatus = 3;

                if (newDownloadFrame.getLaunchCheckBox().isSelected())
                    launchAfter = 1;

                String tmpUrlAddress = newDownloadFrame.getAddressTextField().getText();
                String tmpName = newDownloadFrame.getNameTextField().getText();

                if (tmpName.isEmpty())
                    tmpName = "No Name";

                int fullSize = newDownloadFrame.getFullSize();

                Download tmpDl = new Download(tmpUrlAddress, tmpName, fullSize);

                tmpDl.setSavePath(newDownloadFrame.getSavePath().getText());
                tmpDl.setUrlAddress(newDownloadFrame.getAddressTextField().getText());

                switch (launchAfter) {
                    case 1:
                        tmpDl.setLaunchedAfter(true);
                        break;
                    case 0:
                        tmpDl.setLaunchedAfter(false);
                        break;
                }

                DownloadPanel tmpDlPanel = new DownloadPanel();

                tmpDl.setDownloading(false);
                tmpDl.setFinished(false);
                tmpDl.setInQueue(false);

                tmpDlPanel.addDownload(tmpDl);
                tmpDlPanel.setPreferredSize(myPrefDlDim);
                switch (downloadStatus) {
                    case 1:
                        downloadCollection.addProccessingDownload(tmpDl);
                        tmpDl.setDownloading(true);
                        processingDownloadsList.add(tmpDlPanel);
                        processingListPanel.add(processingDownloadsList.get(processingDownloadsList.size() - 1));
                        break;
                    case 2:
                        downloadCollection.addProccessingDownload(tmpDl);
                        downloadCollection.getProcessingDownloads().get(downloadCollection.getProcessingDownloads().size() - 1).setDownloadedSize(newDownloadFrame.getFullSize());
                        processingDownloadsList.add(tmpDlPanel);
                        g2.setRows(g2.getRows() + 1);
                        processingListPanel.add(processingDownloadsList.get(downloadCollection.getProcessingDownloads().size() - 1));
                        break;
                    case 3:
                        String queueName = NewDownloadFrame.selectedQueueName.getText().replace("Selected Queue : ", "");
                        if (!queueName.isEmpty()) {
                            DownloadQueue selectedQueue = downloadCollection.getQueueByName(queueName);
                            tmpDl.setInQueue(true);
                            tmpDl.setQueueName(selectedQueue.getName());
                            downloadCollection.addQueueDownload(tmpDl, selectedQueue);
                            queueDownloadsList.add(tmpDlPanel);
                            queueListPanel.add(tmpDlPanel);
                            g3.setRows(g3.getRows() + 1);
                            System.out.println("Adding download completed.(Queue download added)");
                        } else {
                            System.out.println("No Queue.");
                        }
                        break;
                }
                g1.setRows(processingListPanel.getComponentCount());
                //     g2.setRows(processingListPanel.getComponentCount());
                //    g3.setRows(processingListPanel.getComponentCount());
                System.out.println("-------------------------AFTER ADD------------------------");
                downloadCollection.printAllQueuesAndTheirDownload();
                System.out.println("----------------------------------------------------------");

                revalidate();
                updateDownloadNumbers(processingBtn, completedBtn, queuesBtn);
                newDownloadFrame.setVisible(false);
                tmpDlPanel.getDownload().start();
                checkForFinishedDownloads();
            }
        });
        newDownloadFrame.getCancelDownload().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDownloadFrame.setVisible(false);
            }
        });
    }

    /**
     * This method handles number of maximum available downloads
     */
    private void setNumberOfMaximumDownloads() {
        if (numberOfMaximumDownloads > 0) {
            downloadCollection.setMaximumSizeOfProcessingDl(numberOfMaximumDownloads);
        }
    }

    /**
     * This method handles settings for the Download manager
     *
     * @param settingsFrame settings frame
     */
    private void settingsTask(SettingsFrame settingsFrame) {
        settingsFrame.getNumberOfDownloads().setText(numberOfMaximumDownloads + "");
        settingsFrame.getApply().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultSavePath = settingsFrame.getDefSavePath().getText();
                String tmp = settingsFrame.getNumberOfDownloads().getText();
                int maxNumber = Integer.parseInt(tmp);
                if (maxNumber > 0) {
                    numberOfMaximumDownloads = maxNumber;
                    settingsFrame.getNumberOfDownloads().setText(numberOfMaximumDownloads + "");
                    setNumberOfMaximumDownloads();
                }
                saveSettingsToFile();
            }
        });
        settingsFrame.getOk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultSavePath = settingsFrame.getDefSavePath().getText();
                String tmp = settingsFrame.getNumberOfDownloads().getText();
                int maxNumber = Integer.parseInt(tmp);
                if (maxNumber > 0) {
                    numberOfMaximumDownloads = maxNumber;
                    settingsFrame.getNumberOfDownloads().setText(numberOfMaximumDownloads + "");
                    setNumberOfMaximumDownloads();
                }
                saveSettingsToFile();
                settingsFrame.makeHidden();
            }
        });

        settingsFrame.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.makeHidden();
            }
        });
        settingsFrame.getDefSavePath().setText(defaultSavePath);
    }

    /**
     * This method handles system tray icon and actions
     */
    private void systemTrayTask() {
        SystemTray systemTray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(getClass().getResource("..//icons//icons8_Java_32px.png"));
        PopupMenu popupMenu = new PopupMenu();
        MenuItem exitpopItem = new MenuItem("Exit");
        MenuItem openItem = new MenuItem("Open");
        MenuItem aboutItem = new MenuItem("About");
        TrayIcon icon = new TrayIcon(image, "Java Download Manager", popupMenu);
        setVisible(false);
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                SystemTray.getSystemTray().remove(icon);
            }
        });
        exitpopItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SystemTray.getSystemTray().remove(icon);
                closeOperation();
            }
        });
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutMessage();
            }
        });
        popupMenu.add(openItem);
        popupMenu.add(aboutItem);
        popupMenu.add(exitpopItem);

        icon.setImageAutoSize(true);
        try {
            systemTray.add(icon);

        } catch (Exception em) {
            em.printStackTrace();
        }
    }

    /**
     * This method prints about dialog
     */
    private void showAboutMessage() {
        String title = "About Us";
        String message = "Java Download Manager.\nSeyed Mohammad Hadi Tabatabaei\nMidterm Project Starts : Tuesday, Ordibehesht 11th";
        JOptionPane.showMessageDialog(getContentPane(), message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method checks wheter some downloads are finished or not.
     */
    private void checkForFinishedDownloads() {
        for (DownloadPanel downloadPanel : processingDownloadsList) {
            if (downloadPanel.getDownload().isFinished()) {
                processingDownloadsList.remove(downloadPanel);
                downloadCollection.getProcessingDownloads().remove(downloadPanel.getDownload());
                processingListPanel.remove(downloadPanel);

                completedDownloadsList.add(downloadPanel);
                downloadCollection.getCompletedDownloads().add(downloadPanel.getDownload());
                completedListPanel.add(downloadPanel);
                revalidate();

            }
        }
        for (DownloadPanel downloadPanel : queueDownloadsList) {
            if (downloadPanel.getDownload().isFinished()) {
                queueDownloadsList.remove(downloadPanel);
                downloadCollection.getQueueDownloads().remove(downloadPanel.getDownload());
                queueListPanel.remove(downloadPanel);

                completedDownloadsList.add(downloadPanel);
                downloadCollection.getCompletedDownloads().add(downloadPanel.getDownload());
                completedListPanel.add(downloadPanel);
                revalidate();
            }
        }
        updateDownloadNumbers(processingBtn, completedBtn, queuesBtn);
    }

    /**
     * This method handles functions for downloads such as pause, resume , remove ,cancel
     *
     * @param job jobs as integer number
     */
    private void doTheJob(int job) {
        switch (job) {
            case 1:
                for (DownloadPanel downloadPanel : processingDownloadsList) {
                    if (downloadPanel.isSelected()) {
                        downloadPanel.getDownload().pause();
                    }
                }
                for (DownloadPanel downloadPanel : queueDownloadsList)
                    if (downloadPanel.isSelected()) {
                        downloadPanel.getDownload().pause();
                    }
                break;
            case 2:
                for (DownloadPanel downloadPanel : processingDownloadsList) {
                    if (downloadPanel.isSelected()) {
                        downloadPanel.getDownload().resume();
                    }
                }
                for (DownloadPanel downloadPanel : queueDownloadsList)
                    if (downloadPanel.isSelected())
                        downloadPanel.getDownload().resume();

                break;
            case 3:
                for (DownloadPanel downloadPanel : processingDownloadsList) {
                    if (downloadPanel.isSelected()) {
                        downloadPanel.getDownload().cancel();
                    }
                }
                for (DownloadPanel downloadPanel : queueDownloadsList)
                    if (downloadPanel.isSelected()) {
                        downloadPanel.getDownload().cancel();
                    }
                break;
            case 4:
                Iterator it1 = processingDownloadsList.iterator();
                while (it1.hasNext()) {
                    DownloadPanel tmp = (DownloadPanel) it1.next();
                    if (tmp.isSelected()) {
                        tmp.getDownload().remove();
                        downloadCollection.addRemovedDownload(tmp.getDownload());
                        processingListPanel.remove(tmp);
                        downloadCollection.removeProcessingDownload(tmp.getDownload());
                        it1.remove();
                        g1.setRows(g1.getRows() - 1);
                    }
                }


                Iterator it2 = queueDownloadsList.iterator();
                while (it2.hasNext()) {
                    DownloadPanel tmpPanel = (DownloadPanel) it2.next();
                    if (tmpPanel.isSelected()) {
                        Download selectedDownload = tmpPanel.getDownload();
                        DownloadQueue selectedDownloadsQueue = downloadCollection.findSpecialDownloadQueue(selectedDownload);
                        tmpPanel.getDownload().remove();
                        downloadCollection.addRemovedDownload(selectedDownload);
                        queueListPanel.remove(tmpPanel);
                        downloadCollection.removeDownloadFromQueue(selectedDownload, selectedDownloadsQueue);
                        it2.remove();
                        g3.setRows(g3.getRows() - 1);
                    }
                }


                Iterator it3 = completedDownloadsList.iterator();
                while (it3.hasNext()) {
                    DownloadPanel tmp = (DownloadPanel) it3.next();
                    if (tmp.isSelected()) {
                        tmp.getDownload().remove();
                        completedListPanel.remove(tmp);
                        downloadCollection.removeCompletedDownload(tmp.getDownload());
                        downloadCollection.addRemovedDownload(tmp.getDownload());
                        it3.remove();
                        g2.setRows(g2.getRows() - 1);
                    }
                }
                break;
        }
        updateDownloadNumbers(processingBtn, completedBtn, queuesBtn);
        revalidate();
    }

    public void updatequeueListPanel(DownloadQueue downloadQueue) {
        queueListPanel.removeAll();
        Iterator it = queueDownloadsList.iterator();
        g3.setRows(0);
        while (it.hasNext()) {
            DownloadPanel tempDlPanel = (DownloadPanel) it.next();
            if (downloadQueue == downloadCollection.findSpecialDownloadQueue(tempDlPanel.getDownload())) {
                queueListPanel.add(tempDlPanel);
            }
        }
        g3.setRows(queueListPanel.getComponentCount());
        validate();
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
            SwingUtilities.updateComponentTreeUI(getRootPane());
        }
    }

    public void saveOnExitOperation() {
        try {
            FileWriter listDownloadFileWriter = new FileWriter(listDownloadFile, false);
            BufferedWriter bufferedWriter = new BufferedWriter(listDownloadFileWriter);
            for (Download download : downloadCollection.getProcessingDownloads()) {
                bufferedWriter.write(download.toStringForSave());
            }
            System.out.println("Download Processing list saved in list.jdm");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProcessingDownloads() {
        System.out.println("Loading Downloads From list.jdm");
        try {
            String line;
            FileReader fileReader = new FileReader(listDownloadFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String fileName = "";
            String fileUrlAddress = "";
            String queueName = "";
            String date = "";
            String time = "";
            String savePath = "";
            boolean isInQueue = true;
            boolean downloading = true;
            boolean launchedAfter = true;
            int fullSize = 0;
            int downloadedSize = 0;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("{")) {

                } else if (line.startsWith("}")) {
                    Download tmpDownload = new Download(fileUrlAddress, fileName, fullSize);
                    tmpDownload.setDownloading(downloading);
                    tmpDownload.setInQueue(isInQueue);
                    tmpDownload.setSavePath(savePath);
                    tmpDownload.setQueueName(queueName);
                    tmpDownload.setDownloadedSize(downloadedSize);
                    tmpDownload.setDate(date);
                    tmpDownload.setTime(time);
                    tmpDownload.setLaunchedAfter(launchedAfter);
                    int downloadStatus = 1;

                    if (tmpDownload.isInQueue())
                        downloadStatus = 3;

                    DownloadPanel tmpDlPanel = new DownloadPanel();

                    tmpDlPanel.addDownload(tmpDownload);
                    tmpDlPanel.setPreferredSize(myPrefDlDim);
                    switch (downloadStatus) {
                        case 1:
                            downloadCollection.addProccessingDownload(tmpDownload);
                            tmpDownload.setDownloading(true);
                            processingDownloadsList.add(tmpDlPanel);
                            processingListPanel.add(processingDownloadsList.get(processingDownloadsList.size() - 1));
                            break;
/*                        case 2:
                            downloadCollection.addProccessingDownload(tmpDownload);
                            downloadCollection.getProcessingDownloads().get(downloadCollection.getProcessingDownloads().size() - 1).setDownloadedSize(newDownloadFrame.getFullSize());
                            processingDownloadsList.add(tmpDlPanel);
                            g2.setRows(g2.getRows() + 1);
                            processingListPanel.add(processingDownloadsList.get(downloadCollection.getProcessingDownloads().size() - 1));
                            break;*/
                        case 3:
                            if (!queueName.isEmpty()) {
                                DownloadQueue selectedQueue = downloadCollection.getQueueByName(queueName);
                                downloadCollection.addQueueDownload(tmpDownload, selectedQueue);
                                queueDownloadsList.add(tmpDlPanel);
                                queueListPanel.add(tmpDlPanel);
                                g3.setRows(g3.getRows() + 1);
                                System.out.println("Adding download completed.(Queue download added)");
                            } else {
                                System.out.println("No Queue.");
                            }
                            break;
                    }
                    g1.setRows(processingListPanel.getComponentCount());
                    //     g2.setRows(processingListPanel.getComponentCount());
                    //    g3.setRows(processingListPanel.getComponentCount());

                    // System.out.println("-------------------------AFTER ADD------------------------");
                    // downloadCollection.printAllQueuesAndTheirDownload();
                    //System.out.println("----------------------------------------------------------");

                    revalidate();
                    updateDownloadNumbers(processingBtn, completedBtn, queuesBtn);
                    tmpDlPanel.getDownload().start();

                } else {
                    if (line.contains("[FILENAME]")) {
                        fileName = line.replace("[FILENAME]:", "");
                    } else if (line.contains("[DOWNLOADURL]")) {
                        fileUrlAddress = line.replace("[DOWNLOADURL]:", "");
                    } else if (line.contains("[FULLSIZE]")) {
                        fullSize = Integer.parseInt(line.replace("[FULLSIZE]:", ""));
                    } else if (line.contains("[DOWNLOADED]")) {
                        downloadedSize = Integer.parseInt(line.replace("[DOWNLOADED]:", ""));
                    } else if (line.contains("[QUEUENAME]")) {
                        String tmp = line.replace("[QUEUENAME]:", "");
                        if (tmp.isEmpty())
                            isInQueue = false;
                        else {
                            isInQueue = true;
                            queueName = tmp;
                        }
                    } else if (line.contains("[DATE]")) {
                        date = line.replace("[DATE]:", "");
                    } else if (line.contains("[TIME]")) {
                        time = line.replace("[TIME]:", "");
                    } else if (line.contains("[SAVEPATH]")) {
                        savePath = line.replace("[SAVEPATH]:", "");
                    } else if (line.contains("[DOWNLOADING]")) {
                        String tmp = line.replace("[DOWNLOADING]:", "");
                        switch (tmp) {
                            case "true":
                                downloading = true;
                                break;
                            case "false":
                                downloading = false;
                                break;
                        }
                    } else if (line.contains("[LAUNCHAFTER]")) {
                        String tmp = line.replace("[LAUNCHAFTER]:", "");
                        switch (tmp) {
                            case "true":
                                launchedAfter = true;
                                break;
                            case "false":
                                launchedAfter = false;
                                break;
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFiles() {
        try {
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

    public void loadSettingsFromFile() {
        try {
            FileReader fileReader = new FileReader(settingsFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileReader fileReader2 = new FileReader(filterFile);
            BufferedReader bufferedReader2 = new BufferedReader(fileReader2);

            String line;
            String maxNumber = "";
            String defaultSavePath = "";

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("[MAXNUMBER]")) {
                    maxNumber = line.replace("[MAXNUMBER]:", "");
                } else if (line.startsWith("[DEFAULTSAVEPATH]")) {
                    defaultSavePath = line.replace("[DEFAULTSAVEPATH]:", "");
                }
            }
            bufferedReader.close();
            settingsFrame.getNumberOfDownloads().setText(maxNumber);
            settingsFrame.getDefSavePath().setText(defaultSavePath);
            this.defaultSavePath = defaultSavePath;
            numberOfMaximumDownloads = Integer.parseInt(maxNumber);
            setNumberOfMaximumDownloads();

            line = "";
            while ((line = bufferedReader2.readLine()) != null) {
                line = line.replace("[FILTERSITE]:", "");
                SettingsFrame.filteredCombobox.addItem(line);
            }
            SettingsFrame.filteredCombobox.revalidate();
            bufferedReader2.close();

            System.out.println("Settings from loaded from settings.jdm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettingsToFile() {
        try {
            FileWriter fileWriter = new FileWriter(settingsFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            FileWriter fileWriter2 = new FileWriter(filterFile);
            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);

            String defaultSavePath = settingsFrame.getDefSavePath().getText();
            String maxNumber = settingsFrame.getNumberOfDownloads().getText();
            String defaultInfo = "{\n[MAXNUMBER]:" + maxNumber + "\n[DEFAULTSAVEPATH]:" + defaultSavePath + "\n}";
            String filterInfo = "";
            for (int i = 0; i < SettingsFrame.filteredCombobox.getItemCount(); i++) {
                filterInfo += "[FILTERSITE]:" + SettingsFrame.filteredCombobox.getItemAt(i) + "\n";
            }
            bufferedWriter.write(defaultInfo);
            bufferedWriter2.write(filterInfo);
            bufferedWriter.close();
            bufferedWriter2.close();


            System.out.println("Settings saved to settings.jdm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRemovedToFile() {
        try {
            FileWriter fileWriter = new FileWriter(listRemovedFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Download removedDownload : downloadCollection.getRemovedDownloads()) {
                bufferedWriter.write(removedDownload.toRemovedString());
            }
            bufferedWriter.close();
            System.out.println("Removed downloads saved in removed.jdm");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveRemovedToFile(Download removedDownload) {

    }

    public void loadQueueDonwloads() {
        try {
            FileReader fileReader = new FileReader(listQueueFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String fileName = "";
            String fileUrlAddress = "";
            String queueName = "";
            String date = "";
            String time = "";
            String savePath = "";
            boolean isInQueue = true;
            boolean downloading = true;
            boolean launchedAfter = true;
            int fullSize = 0;
            int downloadedSize = 0;
            DownloadQueue selectedQueue;
            Download selectedDownload;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("{QUEUENAME}")) {
                    queueName = line.replace("{QUEUENAME}:", "");
                    downloadCollection.addQueue(queueName, LocalTime.now().toString(), LocalDate.now().toString());
                    //  QueuePanel.comboBox.addItem(queueName);
                    //  QueueSelectionFrame.comboBox.addItem(queueName);
                } else if (line.equals("}")) {
                    /* ADD DOWNLOAD */
                    Download tmpDownload = new Download(fileUrlAddress, fileName, fullSize);
                    tmpDownload.setDownloading(downloading);
                    tmpDownload.setInQueue(isInQueue);
                    tmpDownload.setSavePath(savePath);
                    tmpDownload.setQueueName(queueName);
                    tmpDownload.setDownloadedSize(downloadedSize);
                    tmpDownload.setDate(date);
                    tmpDownload.setTime(time);
                    tmpDownload.setLaunchedAfter(launchedAfter);

                    DownloadPanel tmpDlPanel = new DownloadPanel();

                    tmpDlPanel.addDownload(tmpDownload);
                    tmpDlPanel.setPreferredSize(myPrefDlDim);


                    selectedQueue = downloadCollection.getQueueByName(queueName);
                    downloadCollection.addQueueDownload(tmpDownload, selectedQueue);
                    queueDownloadsList.add(tmpDlPanel);
                    queueListPanel.add(tmpDlPanel);
                    g3.setRows(g3.getRows() + 1);
                    System.out.println("Adding download completed.(Queue download added)");

                    g1.setRows(processingListPanel.getComponentCount());
                    //     g2.setRows(processingListPanel.getComponentCount());
                    //    g3.setRows(processingListPanel.getComponentCount());

                    revalidate();
                    updateDownloadNumbers(processingBtn, completedBtn, queuesBtn);
                    tmpDlPanel.getDownload().start();

                } else if (line.equals("{")) {

                } else {
                    if (line.contains("[FILENAME]")) {
                        fileName = line.replace("[FILENAME]:", "");
                    } else if (line.contains("[DOWNLOADURL]")) {
                        fileUrlAddress = line.replace("[DOWNLOADURL]:", "");
                    } else if (line.contains("[FULLSIZE]")) {
                        fullSize = Integer.parseInt(line.replace("[FULLSIZE]:", ""));
                    } else if (line.contains("[DOWNLOADED]")) {
                        downloadedSize = Integer.parseInt(line.replace("[DOWNLOADED]:", ""));
                    } else if (line.contains("[QUEUENAME]")) {
                        String tmp = line.replace("[QUEUENAME]:", "");
                        isInQueue = true;
                        queueName = tmp;
                    } else if (line.contains("[DATE]")) {
                        date = line.replace("[DATE]:", "");
                    } else if (line.contains("[TIME]")) {
                        time = line.replace("[TIME]:", "");
                    } else if (line.contains("[SAVEPATH]")) {
                        savePath = line.replace("[SAVEPATH]:", "");
                    } else if (line.contains("[DOWNLOADING]")) {
                        String tmp = line.replace("[DOWNLOADING]:", "");
                        switch (tmp) {
                            case "true":
                                downloading = true;
                                break;
                            case "false":
                                downloading = false;
                                break;
                        }
                    } else if (line.contains("[LAUNCHAFTER]")) {
                        String tmp = line.replace("[LAUNCHAFTER]:", "");
                        switch (tmp) {
                            case "true":
                                launchedAfter = true;
                                break;
                            case "false":
                                launchedAfter = false;
                                break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e)

        {
            e.printStackTrace();
        }
        System.out.println("Queue Downloads loaded from queue.jdm.");
    }

    public void saveQueueDownloadsToFile() {
        try {
            String toWrite = "";
            FileWriter fileWriter = new FileWriter(listQueueFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (DownloadQueue downloadQueue : downloadCollection.getQueues()) {
                toWrite += "{QUEUENAME}:" + downloadQueue.getName() + "\n";
                for (Download download : downloadQueue.getItems()) {
                    toWrite += download.toStringForSave();
                }
            }
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Queue downloads saved to queue.jdm");
    }

    public void saveToQueueFile() {

    }

    public void closeOperation() {
        saveRemovedToFile();
        saveOnExitOperation();
        saveQueueDownloadsToFile();
        System.exit(0);
    }

    public void searchAndShowResult(String inputString) {
        ArrayList<DownloadPanel> resultDownloadPanels = new ArrayList<>();
        searchResultListPanel.removeAll();
        for (DownloadPanel downloadPanel : processingDownloadsList)
            if (downloadPanel.getDownload().getFileName().contains(inputString) || downloadPanel.getDownload().getUrlAddress().contains(inputString))
                resultDownloadPanels.add(downloadPanel);

        for (DownloadQueue queue : downloadCollection.getQueues())
            for (Download download : queue.getItems()) {
                if (download.getUrlAddress().contains(inputString) || download.getFileName().contains(inputString)) {
                    DownloadPanel tmpPanel = new DownloadPanel();
                    tmpPanel.addDownload(download);
                    resultDownloadPanels.add(tmpPanel);
                }
            }
        for (DownloadPanel downloadPanel : completedDownloadsList)
            if (downloadPanel.getDownload().getFileName().contains(inputString) || downloadPanel.getDownload().getUrlAddress().contains(inputString))
                resultDownloadPanels.add(downloadPanel);

        for (DownloadPanel downloadPanel : resultDownloadPanels) {
            searchResultListPanel.add(downloadPanel);
        }
        g4.setRows(searchResultListPanel.getComponentCount());
        revalidate();
        System.out.println("Search Result showed.");
    }

}