package ActionHandlers;

import Collection.DownloadQueue;
import gui.MainGui;
import gui.Panels.NewDownloadFrame;
import gui.Panels.QueuePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

public class QueueOperationHandlers implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        switch (clicked.getText()) {
            case "Add":
                System.out.println("Add Clicked.");
                while (true) {
                    String queueName;
                    try {
                        queueName = JOptionPane.showInputDialog("Enter Queue Name (Without Spaces) : ").replaceAll(" ", "");

                    } catch (NullPointerException em) {
                        System.out.println("cancel or close.");
                        break;
                    }
                    if (queueName.equals("")) {
                        JOptionPane.showMessageDialog(null, "Queue must have a name\nEnter Again.", "Error.", JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (!QueuePanel.checkRepetiveInCombobox(queueName)) {
                            QueuePanel.comboBox.addItem(queueName);
                            MainGui.downloadCollection.addQueue(queueName, LocalTime.now().toString(), LocalDate.now().toString());
                            QueuePanel.comboBox.revalidate();
                            JOptionPane.showMessageDialog(null, "Queue created successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Duplicate Queue Name.\nTry Again.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                break;
            case "Remove":
                System.out.println("Remove Clicked.");
                MainGui.downloadCollection.removeQueue(QueuePanel.comboBox.getSelectedItem() + "");
                QueuePanel.comboBox.removeItem(QueuePanel.comboBox.getSelectedItem());
                break;
            case "Select":
                System.out.println("Select Clicked.");
                if (QueuePanel.comboBox.getItemCount() != 0) {
                    String selectedName = QueuePanel.comboBox.getSelectedItem() + "";
                    DownloadQueue selectedQueue = MainGui.downloadCollection.getQueueByName(selectedName);
                    selectedQueue.select();
                    NewDownloadFrame.selectedQueueName.setText("Selected Queue : " + selectedQueue.getName());
                    NewDownloadFrame.selectedQueueName.revalidate();
                    JOptionPane.showMessageDialog(null, selectedName + " Selected.", "Select", JOptionPane.INFORMATION_MESSAGE);
                    QueuePanel.frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Nothing to select.Try add a queue first.", "Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "Settings":
                try {
                    String selectedQueueName = QueuePanel.comboBox.getSelectedItem() + "";
                    if (!selectedQueueName.isEmpty()) {
                        DownloadQueue selectedQueue = MainGui.downloadCollection.getQueueByName(selectedQueueName);
                        selectedQueue.showMySettings();
                    } else {
                        JOptionPane.showMessageDialog(null, "Nothing is selected.\nSelect or add a queue first.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(null, "Nothing is selected.\nSelect or add a queue first.", "Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
    }
}