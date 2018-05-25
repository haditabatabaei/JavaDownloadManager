package gui.Panels;

import Collection.DownloadQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class QueuePanel extends JFrame {
    private JComboBox<String> comboBox;
    private JButton[] queueOperationButtons;
    private JPanel buttonsPanel;
    private JPanel largerButtonsPanel;

    public QueuePanel() {
        super("Queue Settings");
        setLocation(120, 120);
        setSize(400, 240);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        buttonsPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        queueOperationButtons = new JButton[4];
        largerButtonsPanel = new JPanel(new BorderLayout());

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
            buttonsPanel.add(queueOperationButtons[i]);
        }
        comboBox = new JComboBox<>();
        comboBox.setEditable(false);


        largerButtonsPanel.add(buttonsPanel, BorderLayout.NORTH);

        add(comboBox, BorderLayout.NORTH);
        add(largerButtonsPanel, BorderLayout.CENTER);


    }

    public void updateQueueList(ArrayList<DownloadQueue> downloadQueueList) {
        comboBox.removeAllItems();
        for (DownloadQueue downloadQueue : downloadQueueList) {
            comboBox.addItem(downloadQueue.getName());
        }
        revalidate();
    }

    public void makeVisible() {
        setVisible(true);
    }

    public void addQueue(String name, ArrayList<DownloadQueue> downloadQueueArrayList) {
        downloadQueueArrayList.add(new DownloadQueue(name));
        comboBox.addItem(name);
        revalidate();
    }

    public void removeQueue(ArrayList<DownloadQueue> downloadQueueArrayList) {
        Iterator it = downloadQueueArrayList.iterator();
        String name = (String) comboBox.getSelectedItem();
        while (it.hasNext()) {
            DownloadQueue tmp = (DownloadQueue) it.next();
            if (tmp.getName().equals(name)) {
                it.remove();
                comboBox.removeItem(name);
                break;
            }
        }
        revalidate();
    }

    public DownloadQueue selectQueue(ArrayList<DownloadQueue> downloadQueueArrayList) {
        String tmp = (String) comboBox.getSelectedItem();
        DownloadQueue toReturn = null;
        for (DownloadQueue downloadQueue : downloadQueueArrayList) {
            if (downloadQueue.getName().equals(tmp)) {
                toReturn = downloadQueue;
            }
        }
        setVisible(false);
        return toReturn;
    }

    public JButton[] getQueueOperationButtons() {
        return queueOperationButtons;
    }
}