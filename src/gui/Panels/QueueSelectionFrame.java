package gui.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueSelectionFrame extends JFrame {
    public static JComboBox comboBox;
    private JButton selectButton;
    private JButton cancelButton;

    public QueueSelectionFrame() {
        super("Select Queue");
        setSize(350, 200);
        setResizable(false);
        setLocation(200, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        comboBox = QueuePanel.comboBox;
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JPanel insideButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        selectButton = new JButton("Select");
        cancelButton = new JButton("Cancel");
        insideButtonPanel.add(selectButton);
        insideButtonPanel.add(cancelButton);
        buttonsPanel.add(insideButtonPanel, BorderLayout.NORTH);

        add(comboBox, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);


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

    public JButton getSelectButton() {
        return selectButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}