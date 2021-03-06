package gui.Panels;

import ActionHandlers.CentralCommand;
import Collection.DownloadQueue;
import gui.MainGui;

import javax.swing.*;
import java.awt.*;

public class QueueSettings {
    private JFrame frame;
    private JLabel nameLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private JTextField nameTextField;
    private JTextField dateTextField;
    private JTextField timeTextField;
    private String oldName;
    private String oldDate;
    private String oldTime;
    private JButton okButton;
    private JButton cancelButton;

    private JPanel infoPanel;
    private JPanel lowerPanel;
    private JPanel buttonsPanel;

    public QueueSettings() {
        frame = new JFrame("Queue Settings");
        frame.setSize(350, 200);
        frame.setResizable(false);
        frame.setLocation(170, 170);
        frame.setLayout(new BorderLayout());
        infoPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        lowerPanel = new JPanel(new BorderLayout());
        buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        JPanel detailed1 = new JPanel(new BorderLayout());
        JPanel detailed2 = new JPanel(new BorderLayout());
        JPanel detailed3 = new JPanel(new BorderLayout());
        nameLabel = new JLabel("Name : ");
        dateLabel = new JLabel("Date : ");
        timeLabel = new JLabel("Time : ");

        nameTextField = new JTextField();
        dateTextField = new JTextField();
        timeTextField = new JTextField();

        detailed1.add(nameLabel, BorderLayout.WEST);
        detailed1.add(nameTextField, BorderLayout.CENTER);

        detailed2.add(dateLabel, BorderLayout.WEST);
        detailed2.add(dateTextField, BorderLayout.CENTER);

        detailed3.add(timeLabel, BorderLayout.WEST);
        detailed3.add(timeTextField, BorderLayout.CENTER);

        infoPanel.add(detailed1);
        infoPanel.add(detailed2);
        infoPanel.add(detailed3);

        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(lowerPanel, BorderLayout.CENTER);
        lowerPanel.add(buttonsPanel, BorderLayout.NORTH);

        okButton.setActionCommand(CentralCommand.COMMAND_APPLY_QUEUE_SETTINGS);
        okButton.addActionListener(MainGui.centralCommand);
        cancelButton.setActionCommand(CentralCommand.COMMAND_CANCEL_QUEUE_SETTINGS);
        cancelButton.addActionListener(MainGui.centralCommand);

    }

    public void makeVisible() {
        frame.setVisible(true);
    }

    public void makeHidden() {
        frame.setVisible(false);
    }

    public void fillSettingsWithMyStats(DownloadQueue queue) {
        nameTextField.setText(queue.getName());
        timeTextField.setText(queue.getStartTime());
        dateTextField.setText(queue.getStartDate());
        oldName = queue.getName();
        oldDate = queue.getStartDate();
        oldTime = queue.getStartTime();
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getDateTextField() {
        return dateTextField;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getTimeTextField() {
        return timeTextField;
    }
}

