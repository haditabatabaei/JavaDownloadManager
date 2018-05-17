package gui;

import ActionHandlers.AllActionHandler;
import ActionHandlers.ButtonHoverHandler;
import ActionHandlers.LeftButtonHoverHandler;

import download.Download;
import gui.Panels.DownloadPanel;
import gui.Panels.NewDownloadFrame;

import Collection.DownloadCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class MainGui extends JFrame {
    private ArrayList<JButton> toolBarButtons;
    private ArrayList<JMenuItem> topMenuItems;
    private NewDownloadFrame newDownloadFrame;
    private Icons icons;
    private DownloadCollection downloadCollection;

    public MainGui() {
        super("Java Download Manager");
        toolBarButtons = new ArrayList<>();
        topMenuItems = new ArrayList<>();
        icons = new Icons();
        downloadCollection = new DownloadCollection();
        setMinimumSize(new Dimension(900, 500));
        setSize(900, 680);
        setLocation(200, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(Colors.DarkBlue);

        AllActionHandler allActionHandler = new AllActionHandler();
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
                    System.out.println("Hello");
                    tmpBtn.setIcon(icons.getSettingsColor());
                    break;
                case "New Download":
                    tmpBtn.setIcon(icons.getNewDlGreen());
                    break;
                case "Pause":
                    tmpBtn.setIcon(icons.getPauseColor());
                    break;
                case "Remove":
                    tmpBtn.setIcon(icons.getDeletePurple());
                    break;
                case "Resume":
                    tmpBtn.setIcon(icons.getStartColor());
                    break;
                case "Cancel":
                    tmpBtn.setIcon(icons.getCancelColor());
                    break;
            }
            if (tmpBtn.getText().equals("New Download")) {
                tmpBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        newDownloadFrame = new NewDownloadFrame();
                        newDownloadFrame.setVisible(true);
                    }
                });
            }
            tmpBtn.setBackground(Colors.LightBlue);
            tmpBtn.setForeground(Colors.DarkGray);
            tmpBtn.setFocusPainted(false);
            tmpBtn.addMouseListener(bhh);
            tmpBtn.addActionListener(allActionHandler);
            tmpBtn.setBorderPainted(false);
            tmpBtn.setBorder(toolbarBtnEBorder);
            toolBar.add(tmpBtn);
            toolBar.addSeparator();
        }


        JMenuBar topMenuBar = new JMenuBar();
        JMenu downloadMenu = new JMenu("Download");
        JMenu helpMenu = new JMenu("Help");
        JMenu exitOption = new JMenu("Exit");
        exitOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        JMenuItem newDl = new JMenuItem("New Download");
        JMenuItem pauseDl = new JMenuItem("Pause");
        JMenuItem resumeDl = new JMenuItem("Resume");
        JMenuItem cancelDl = new JMenuItem("Cancel");
        JMenuItem removeDl = new JMenuItem("Remove");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem aboutOption = new JMenuItem("About");
        JMenuItem registerOption = new JMenuItem("Register");

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
            if (topMenuItems.indexOf(item) == topMenuItems.size() - 1)
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
            else if (item.getText().equals("About")) {
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String title = "About Us";
                        String message = "Java Download Manager.\nSeyed Mohammad Hadi Tabatabaei\nMidterm Project Starts : Tuesday, Ordibehesht 11th";
                        JOptionPane.showMessageDialog(getContentPane(), message, title, JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            } else {
                item.addActionListener(allActionHandler);
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
        leftPanel.setPreferredSize(new Dimension(200, 0)


        );
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

        JButton processingBtn = new JButton("Processing");
        JButton completedBtn = new JButton("Completed");
        JButton queuesBtn = new JButton("Queues");
        JButton defaultBtn = new JButton("Default");

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
            button.addActionListener(allActionHandler);
        }
        Random randGen = new Random();

        /*
         RIGHT PANEL
         */

        ArrayList<DownloadPanel> processingDownloadsList = new ArrayList<>();
        ArrayList<DownloadPanel> queueDownloadsList = new ArrayList<>();
        ArrayList<DownloadPanel> completedDownloadsList = new ArrayList<>();

        processingPanel.setLayout(new BorderLayout());
        completedPanel.setLayout(new BorderLayout());
        queuePanel.setLayout(new BorderLayout());

        GridLayout g1 = new GridLayout(0, 1, 0, 0);
        GridLayout g2 = new GridLayout(0, 1, 0, 0);
        GridLayout g3 = new GridLayout(0, 1, 0, 0);

        JPanel processingListPanel = new JPanel(g1);
        JPanel completedListPanel = new JPanel(g2);
        JPanel queueListPanel = new JPanel(g3);

        processingScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        completedScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        queueScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        Dimension myPrefDlDim = new Dimension(0, 80);

        /*

        ADDING PROCESSING DOWNLOADS

         */
        for (int i = 0; i < 10; i++) {
            downloadCollection.addProccessingDownload(new Download(0));
            downloadCollection.getProcessingDownloads().get(i).setDownloadedSize(10 * i);
            processingDownloadsList.add(new DownloadPanel());
            processingDownloadsList.get(i).setPreferredSize(myPrefDlDim);
            processingDownloadsList.get(i).addDownload(downloadCollection.getProcessingDownloads().get(i));
            processingDownloadsList.get(i).setBackground(new Color(randGen.nextInt(255), randGen.nextInt(255), randGen.nextInt(255)));
            g1.setRows(g1.getRows() + 1);
            processingListPanel.add(processingDownloadsList.get(i));
            downloadCollection.getProcessingDownloads().get(i).print();
        }
        System.out.println("Processing Download Finished ");
        for (int i = 0; i < 5; i++) {
            downloadCollection.addCompletedDownload(new Download(0));
            downloadCollection.getCompletedDownloads().get(i).setDownloadedSize(5 * i);
            completedDownloadsList.add(new DownloadPanel());
            completedDownloadsList.get(i).setPreferredSize(myPrefDlDim);
            completedDownloadsList.get(i).addDownload(downloadCollection.getCompletedDownloads().get(i));
            completedDownloadsList.get(i).setBackground(new Color(randGen.nextInt(255), randGen.nextInt(255), randGen.nextInt(255)));
            g2.setRows(g2.getRows() + 1);
            completedListPanel.add(completedDownloadsList.get(i));
            downloadCollection.getCompletedDownloads().get(i).print();
        }
        System.out.println("Completed Download finished");
/*        Queue<Download> nightDownload = null;

        for (int i = 0; i < 6; i++) {
            downloadCollection.addQueueDownload(new Download(0), nightDownload);
            downloadCollection.getQueueDownloads().get(i).setDownloadedSize(10 * i);
            queueDownloadsList.add(new DownloadPanel());
            queueDownloadsList.get(i).setPreferredSize(myPrefDlDim);
            queueDownloadsList.get(i).addDownload(downloadCollection.getQueueDownloads().get(i));
            queueDownloadsList.get(i).setBackground(new Color(randGen.nextInt(255), randGen.nextInt(255), randGen.nextInt(255)));
            g1.setRows(g1.getRows() + 1);
            queueListPanel.add(queueDownloadsList.get(i));
            downloadCollection.getQueueDownloads().get(i).print();
        }*/


        processingBtn.setText(processingBtn.getText() + " (" + downloadCollection.getProcessingDownloads().size() + ")");
        completedBtn.setText(completedBtn.getText() + " (" + downloadCollection.getCompletedDownloads().size() + ")");
        completedPanel.add(completedListPanel, BorderLayout.NORTH);
        processingPanel.add(processingListPanel, BorderLayout.NORTH);
        queuePanel.add(queueListPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        setJMenuBar(topMenuBar);
        add(toolBar, BorderLayout.NORTH);
        setVisible(true);
    }
}