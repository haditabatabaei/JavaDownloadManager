package gui.Panels;

import ActionHandlers.CentralCommand;
import gui.MainGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueSelectionFrame extends JFrame {
    public static JComboBox<String> selectableComboBox;
    private JButton selectButton;
    private JButton cancelButton;

    public QueueSelectionFrame() {
        super("Select Queue");
        setSize(350, 200);
        setResizable(false);
        setLocation(200, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        selectableComboBox = new JComboBox<>(QueuePanel.comboBox.getModel());
        QueuePanel.updateQueueList();
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JPanel insideButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        selectButton = new JButton("Select");
        cancelButton = new JButton("Cancel");
        insideButtonPanel.add(selectButton);
        insideButtonPanel.add(cancelButton);
        buttonsPanel.add(insideButtonPanel, BorderLayout.NORTH);
        if (selectableComboBox != null) {
            add(selectableComboBox, BorderLayout.NORTH);
        } else {
            System.out.println("Inside null combobox");
            add(new JLabel("No Queue.First add in new download window."), BorderLayout.NORTH);
        }
        add(buttonsPanel, BorderLayout.CENTER);

        selectButton.setActionCommand(CentralCommand.COMMAND_SELECT_QUEUE_FOR_SHOW);
        selectButton.addActionListener(MainGui.centralCommand);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeHidden();
            }
        });

    }

    public void makeVisible() {
        setVisible(true);
    }

    public void makeHidden() {
        setVisible(false);
    }
}
