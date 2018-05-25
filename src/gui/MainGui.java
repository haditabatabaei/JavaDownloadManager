package gui;

import ActionHandlers.ButtonHoverHandler;
import ActionHandlers.LeftButtonHoverHandler;

import Collection.DownloadQueue;
import Download.Download;
import gui.Panels.DownloadInfoFrame;
import gui.Panels.DownloadPanel;
import gui.Panels.NewDownloadFrame;

import Collection.DownloadCollection;
import gui.Panels.SettingsFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is main Java Download Manager Gui
 *
 * @author Seyed Mohammad Hadi Tabatabaei
 * @versino 1.0
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

    private JButton processingBtn;
    private JButton completedBtn;
    private JButton queuesBtn;
    private JButton defaultBtn;

    private JPanel processingListPanel;
    private JPanel completedListPanel;
    private JPanel queueListPanel;

    private DownloadQueue testDownloadQueue;

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
        g1 = new GridLayout(0, 1, 0, 0);
        g2 = new GridLayout(0, 1, 0, 0);
        g3 = new GridLayout(0, 1, 0, 0);
        processingBtn = new JButton("Processing");
        completedBtn = new JButton("Completed");
        queuesBtn = new JButton("Queues");
        defaultBtn = new JButton("Default");
        testDownloadQueue = new DownloadQueue("Night Download");

        processingListPanel = new JPanel(g1);
        completedListPanel = new JPanel(g2);
        queueListPanel = new JPanel(g3);

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
                            settingsFrame = new SettingsFrame();
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
                            System.exit(0);
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
        leftButtonsList.add(defaultBtn);
        EmptyBorder leftBtnEmpBorder = new EmptyBorder(15, 0, 15, 0);

        JPanel processingPanel = new JPanel();
        JPanel completedPanel = new JPanel();
        JPanel queuePanel = new JPanel();


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new OverlayLayout(rightPanel));

        JScrollPane processingScrollPane = new JScrollPane(processingPanel);
        JScrollPane completedScrollPane = new JScrollPane(completedPanel);
        JScrollPane queueScrollPane = new JScrollPane(queuePanel);

        rightPanel.add(completedScrollPane);
        rightPanel.add(processingScrollPane);
        rightPanel.add(queueScrollPane);

        processingScrollPane.setVisible(true);
        completedScrollPane.setVisible(false);
        queueScrollPane.setVisible(false);

        for (JButton button : leftButtonsList) {
            switch (leftButtonsList.indexOf(button)) {
                case 0:
                    button.setIcon(icons.getProcessingColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!(processingScrollPane.isVisible())) {
                                processingScrollPane.setVisible(true);
                                completedScrollPane.setVisible(false);
                                queueScrollPane.setVisible(false);
                                System.out.println("I'm in first if");
                            }
                        }
                    });
                    break;
                case 1:
                    button.setIcon(icons.getCompletedColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!(completedScrollPane.isVisible())) {
                                processingScrollPane.setVisible(false);
                                completedScrollPane.setVisible(true);
                                queueScrollPane.setVisible(false);
                                System.out.println("I'm in second if");
                            }
                        }
                    });
                    break;
                case 2:
                    button.setIcon(icons.getQueueColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!(queueScrollPane.isVisible())) {
                                processingScrollPane.setVisible(false);
                                completedScrollPane.setVisible(false);
                                queueScrollPane.setVisible(true);
                                System.out.println("I'm in third if");
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
                settingsFrame = new SettingsFrame();
                settingsTask(settingsFrame);
            }
        });


        completedPanel.add(completedListPanel, BorderLayout.NORTH);
        processingPanel.add(processingListPanel, BorderLayout.NORTH);
        queuePanel.add(queueListPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        setJMenuBar(topMenuBar);
        add(toolBar, BorderLayout.NORTH);
        setVisible(true);
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
                        processingDownloadsList.add(tmpDlPanel);
                        processingListPanel.add(processingDownloadsList.get(processingDownloadsList.size() - 1));
                        g1.setRows(g1.getRows() + 1);
                        break;
                    case 2:
                        downloadCollection.addProccessingDownload(tmpDl);
                        downloadCollection.getProcessingDownloads().get(downloadCollection.getProcessingDownloads().size() - 1).setDownloadedSize(newDownloadFrame.getFullSize());
                        processingDownloadsList.add(tmpDlPanel);
                        g1.setRows(g1.getRows() + 1);
                        processingListPanel.add(processingDownloadsList.get(downloadCollection.getProcessingDownloads().size() - 1));
                        break;
                    case 3:
                        downloadCollection.addQueueDownload(tmpDl, testDownloadQueue);
                        queueDownloadsList.add(tmpDlPanel);
                        g1.setRows(g1.getRows() + 1);
                        downloadCollection.getQueueDownloads().get(downloadCollection.getQueueDownloads().size() - 1).setDownloadedSize(newDownloadFrame.getFullSize());
                        queueDownloadsList.get(downloadCollection.getQueueDownloads().size() - 1).addDownload(downloadCollection.getQueueDownloads().get(downloadCollection.getQueueDownloads().size() - 1));
                        queueListPanel.add(queueDownloadsList.get(downloadCollection.getQueueDownloads().size() - 1));
                        break;
                }

                revalidate();
                updateDownloadNumbers(processingBtn, completedBtn, queuesBtn);
                tmpDlPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
                            DownloadInfoFrame downloadInfoFrame = new DownloadInfoFrame(tmpDlPanel.getDownload());
                        } else if (e.getClickCount() == 1) {
                            if (tmpDlPanel.isSelected()) {
                                tmpDlPanel.select();
                            } else
                                tmpDlPanel.select();
                        } else if (e.getClickCount() == 2) {
                            tmpDlPanel.getDownload().open();
                        }
                    }
                });
                tmpDlPanel.getDlSmallButtonsAsLabels()[1].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        tmpDlPanel.getDownload().openFolder();
                    }
                });
                tmpDlPanel.getDlSmallButtonsAsLabels()[0].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        tmpDlPanel.getDownload().cancel();
                    }
                });

                tmpDlPanel.getDlSmallButtonsAsLabels()[2].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        tmpDlPanel.getDownload().resume();
                    }
                });
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
                if (!tmp.equals("default is 0 ( infinite )")) {
                    numberOfMaximumDownloads = Integer.parseInt(tmp);
                    settingsFrame.getNumberOfDownloads().setText(numberOfMaximumDownloads + "");
                    setNumberOfMaximumDownloads();
                }

            }
        });
        settingsFrame.getOk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultSavePath = settingsFrame.getDefSavePath().getText();
                String tmp = settingsFrame.getNumberOfDownloads().getText();
                if (!tmp.equals("0")) {
                    numberOfMaximumDownloads = Integer.parseInt(tmp);
                    settingsFrame.getNumberOfDownloads().setText(numberOfMaximumDownloads + "");
                    setNumberOfMaximumDownloads();
                }

                settingsFrame.setVisible(false);
            }
        });

        settingsFrame.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.setVisible(false);
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
                System.exit(0);
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
                        it1.remove();
                        processingListPanel.remove(tmp);
                        downloadCollection.removeProcessingDownload(tmp.getDownload());
                        g1.setRows(g1.getRows() - 1);
                    }
                }

                Iterator it2 = queueDownloadsList.iterator();
                while (it2.hasNext()) {
                    DownloadPanel tmp = (DownloadPanel) it2.next();
                    if (tmp.isSelected()) {
                        tmp.getDownload().remove();
                        it2.remove();
                        queueListPanel.remove(tmp);
                        downloadCollection.removeQueueDownload(tmp.getDownload(), testDownloadQueue);
                        g2.setRows(g2.getRows() - 1);
                    }
                }

                Iterator it3 = completedDownloadsList.iterator();
                while (it3.hasNext()) {
                    DownloadPanel tmp = (DownloadPanel) it3.next();
                    if (tmp.isSelected()) {
                        tmp.getDownload().remove();
                        it3.remove();
                        completedListPanel.remove(tmp);
                        downloadCollection.removeCompletedDownload(tmp.getDownload());
                        g3.setRows(g3.getRows() - 1);
                    }
                }
                break;
        }
        updateDownloadNumbers(processingBtn, completedBtn, queuesBtn);
        revalidate();
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
}