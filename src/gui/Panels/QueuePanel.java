package gui.Panels;

import ActionHandlers.CentralCommand;
import Collection.DownloadCollection;
import Collection.DownloadQueue;
import gui.MainGui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QueuePanel {
    public static JFrame frame = new JFrame("Queue Settings/Manager");
    public static JComboBox<String> comboBox = new JComboBox<>();
    private JButton[] queueOperationButtons;
    private JPanel buttonsPanel;
    private JPanel largerButtonsPanel;

    public QueuePanel() {
        frame.setLocation(120, 120);
        frame.setSize(400, 240);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        buttonsPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        queueOperationButtons = new JButton[4];
        largerButtonsPanel = new JPanel(new BorderLayout());

        for (int i = 0; i < 4; i++) {
            queueOperationButtons[i] = new JButton();
            switch (i) {
                case 0:
                    queueOperationButtons[i].setText("Add");
                    queueOperationButtons[i].setActionCommand(CentralCommand.COMMAND_ADD_NEW_QUEUE);
                    break;
                case 1:
                    queueOperationButtons[i].setText("Select");
                    queueOperationButtons[i].setActionCommand(CentralCommand.COMMAND_SELECT_QUEUE);
                    break;
                case 2:
                    queueOperationButtons[i].setText("Remove");
                    queueOperationButtons[i].setActionCommand(CentralCommand.COMMAND_REMOVE_SELECTED_QUEUE);
                    break;
                case 3:
                    queueOperationButtons[i].setText("Settings");
                    queueOperationButtons[i].setActionCommand(CentralCommand.COMMAND_SHOW_QUEUE_SETTINGS_FRAME);
                    break;
            }
            queueOperationButtons[i].addActionListener(MainGui.centralCommand);
            buttonsPanel.add(queueOperationButtons[i]);
        }
        comboBox.setEditable(false);

        largerButtonsPanel.add(buttonsPanel, BorderLayout.NORTH);

        frame.add(comboBox, BorderLayout.NORTH);
        frame.add(largerButtonsPanel, BorderLayout.CENTER);

        updateQueueList();
    }

    public static void updateQueueList() {
        if (!(comboBox == null)) {
            comboBox.removeAllItems();

            for (DownloadQueue downloadQueue : DownloadCollection.queues) {
                comboBox.addItem(downloadQueue.getName());
            }
            frame.revalidate();
        }
    }

    public void makeVisible() {
        frame.setVisible(true);
    }


    public static boolean hasRepetiveInCombobox(String queueName) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String toCompare = comboBox.getItemAt(i);
            if (toCompare.equals(queueName))
                return true;
        }
        return false;
    }
}