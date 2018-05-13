package gui;

import ActionHandlers.AllActionHandler;
import ActionHandlers.ButtonHoverHandler;
import ActionHandlers.LeftButtonHoverHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainGui extends JFrame {
    private ArrayList<JButton> toolBarButtons;
    private ArrayList<JMenuItem> topMenuItems;
    private Icons icons;
    private NewDownloadFrame newDownloadFrame;

    public MainGui() {
        super("Java Download Manager");
        toolBarButtons = new ArrayList<>();
        topMenuItems = new ArrayList<>();
        icons = new Icons();
        newDownloadFrame = new NewDownloadFrame();
        setMinimumSize(new Dimension(1000,500));
        setSize(1070, 680);
        setLocation(200, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        JMenuItem exitOption = new JMenuItem("Exit");
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
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setLayout(new BorderLayout());
        JPanel leftButtonsPanel = new JPanel(new GridLayout(4, 1, 0, 0));
        leftButtonsPanel.setBackground(Colors.DarkGray);
        leftButtonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("..//images//missile-logo.jpg"));
            JLabel logoLable = new JLabel();
            logoLable.setIcon(logo);
            leftPanel.add(logoLable, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.println("Exception Error.");
        }

        leftPanel.add(leftButtonsPanel, BorderLayout.CENTER);

        ArrayList<JButton> leftButtonsList = new ArrayList<>();

        JButton processingBtn = new JButton("Processing (0/3)");
        JButton completedBtn = new JButton("Completed (0/3)");
        JButton queuesBtn = new JButton("Queues");
        JButton defaultBtn = new JButton("Default");

        leftButtonsList.add(processingBtn);
        leftButtonsList.add(completedBtn);
        leftButtonsList.add(queuesBtn);
        leftButtonsList.add(defaultBtn);
        EmptyBorder leftBtnEmpBorder = new EmptyBorder(0, 0, 10, 0);
        JPanel rightpanel = new JPanel();

        for (JButton button : leftButtonsList) {
            switch (leftButtonsList.indexOf(button)) {
                case 0:
                    button.setIcon(icons.getProcessingColor());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (rightpanel.isVisible())
                                rightpanel.setVisible(false);
                            else
                                rightpanel.setVisible(true);
                        }
                    });
                    break;
                case 1:
                    button.setIcon(icons.getCompletedColor());
                    break;
                case 2:
                    button.setIcon(icons.getQueueColor());
                    break;
            }
            button.setBackground(Colors.DarkGray);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setBorder(leftBtnEmpBorder);
            button.setFocusPainted(false);
            leftButtonsPanel.add(button);
            button.addMouseListener(lbhh);
            button.addActionListener(allActionHandler);
        }


        rightpanel.setBackground(Color.BLACK);
        rightpanel.setVisible(true);
        add(rightpanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        setJMenuBar(topMenuBar);
        add(toolBar, BorderLayout.NORTH);
        setVisible(true);
    }
}
