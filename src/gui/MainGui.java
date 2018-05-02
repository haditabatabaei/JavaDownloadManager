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

    public MainGui() {
        super("Java Download Manager");
        toolBarButtons = new ArrayList<>();
        topMenuItems = new ArrayList<>();
        setSize(1070, 680);
        setLocation(200, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Colors.DarkBlue);


        AllActionHandler allActionHandler = new AllActionHandler();
        ButtonHoverHandler bhh = new ButtonHoverHandler();
        LeftButtonHoverHandler lbhh = new LeftButtonHoverHandler();

        JToolBar toolBar = new JToolBar("Tool Bar");
        toolBarButtons.add(new JButton("New Download"));
        toolBarButtons.add(new JButton("Pause"));
        toolBarButtons.add(new JButton("Resume"));
        toolBarButtons.add(new JButton("Cancel"));
        toolBarButtons.add(new JButton("Remove"));
        toolBarButtons.add(new JButton("Settings"));

        toolBar.setBackground(Colors.LightBlue);
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(false);
        toolBar.setBorder(new EmptyBorder(0,0,0,0));

        EmptyBorder toolbarBtnEBorder = new EmptyBorder(10,5,10,5);
        for (JButton button : toolBarButtons) {
            button.setBackground(Colors.LightBlue);
            button.setForeground(Colors.DarkGray);
            button.setFocusPainted(false);
            button.addMouseListener(bhh);
            button.addActionListener(allActionHandler);
            button.setBorderPainted(false);
            button.setBorder(toolbarBtnEBorder);
            toolBar.add(button);
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
        leftPanel.setLayout(new BorderLayout(0, 0));
        JPanel leftButtonsPanel = new JPanel(new GridLayout(4, 1, 0, 5));
        leftButtonsPanel.setBackground(Colors.DarkGray);
        leftButtonsPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
        leftPanel.add(leftButtonsPanel, BorderLayout.NORTH);

        ArrayList<JButton> leftButtonsList = new ArrayList<>();

        JButton processingBtn = new JButton("Processing (0/3)");
        JButton completedBtn = new JButton("Completed (0/3)");
        JButton queuesBtn = new JButton("Queues");
        JButton defaultBtn = new JButton("Default");

        leftButtonsList.add(processingBtn);
        leftButtonsList.add(completedBtn);
        leftButtonsList.add(queuesBtn);
        leftButtonsList.add(defaultBtn);
        EmptyBorder leftBtnEmpBorder = new EmptyBorder(10, 0, 10, 0);

        for (JButton button : leftButtonsList) {
            button.setBackground(Colors.DarkGray);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setBorder(leftBtnEmpBorder);
            button.setFocusPainted(false);
            leftButtonsPanel.add(button);
            button.addMouseListener(lbhh);
            button.addActionListener(allActionHandler);
        }

        JPanel mainDownloadsPanel = new JPanel();
        mainDownloadsPanel.setBackground(Colors.VeryLightBlue);

        add(mainDownloadsPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        setJMenuBar(topMenuBar);
        add(toolBar, BorderLayout.NORTH);
        setVisible(true);
    }
}
