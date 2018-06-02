package gui.Panels;

import ActionHandlers.QueueOperationHandlers;
import Collection.DownloadQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class QueuePanel extends JFrame {
    public static JFrame frame = new JFrame("Queue Settings");
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

        QueueOperationHandlers qoh = new QueueOperationHandlers();
        for (int i = 0; i < 4; i++) {
            queueOperationButtons[i] = new JButton();
            switch (i) {
                case 0:
                    queueOperationButtons[i].setText("Add");
                    break;
                case 1:
                    queueOperationButtons[i].setText("Select");
                    break;
                case 2:
                    queueOperationButtons[i].setText("Remove");
                    break;
                case 3:
                    queueOperationButtons[i].setText("Settings");
                    break;
            }
            queueOperationButtons[i].addActionListener(qoh);
            buttonsPanel.add(queueOperationButtons[i]);
        }
        comboBox.setEditable(false);

        largerButtonsPanel.add(buttonsPanel, BorderLayout.NORTH);

        frame.add(comboBox, BorderLayout.NORTH);
        frame.add(largerButtonsPanel, BorderLayout.CENTER);
    }

    public static void updateQueueList(ArrayList<DownloadQueue> downloadQueueList) {
        if (!(comboBox == null)) {
            comboBox.removeAllItems();

            for (DownloadQueue downloadQueue : downloadQueueList) {
                comboBox.addItem(downloadQueue.getName());
            }
            frame.revalidate();
        }
    }

    public void makeVisible() {
        frame.setVisible(true);
    }

    public JButton[] getQueueOperationButtons() {
        return queueOperationButtons;
    }

    public static boolean checkRepetiveInCombobox(String queueName) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String toCompare = comboBox.getItemAt(i);
            if (toCompare.equals(queueName))
                return true;
        }
        return false;
    }
}