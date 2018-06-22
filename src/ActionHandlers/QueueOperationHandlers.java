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

            case "Settings":

                break;
        }
    }
}