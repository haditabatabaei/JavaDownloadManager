package ActionHandlers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueOperationHandlers implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        switch (clicked.getActionCommand()) {
            case "Add":

            case "Remove":
            case "Select":
            case "Settings":
        }
    }
}