package gui.Panels;

import ActionHandlers.CentralCommand;
import gui.MainGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortSelectionFrame {
    private static JFrame frame;
    public static JComboBox<String> comboBox;
    private JLabel sortOptionLabel;
    public static JRadioButton ascendingOrder;
    public static JRadioButton descendingOrder;
    private JButton sortButton;
    private JButton cancelButton;

    private ButtonGroup radioButtonGroup;

    private JPanel northPanel;
    private JPanel radioPanel;
    private JPanel buttonsPanel;

    public SortSelectionFrame() {
        frame = new JFrame("Select sort options:");
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setLocation(250, 250);
        frame.setSize(400, 150);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        comboBox = new JComboBox<>();
        sortOptionLabel = new JLabel("Select Sort Option:");
        ascendingOrder = new JRadioButton("Ascending Order");
        descendingOrder = new JRadioButton("Descending Order");
        sortButton = new JButton("Sort");
        cancelButton = new JButton("Cancel");


        northPanel = new JPanel(new GridLayout(3, 1, 5, 0));
        radioPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        buttonsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        radioButtonGroup = new ButtonGroup();

        comboBox.addItem("Name");
        comboBox.addItem("Size");
        comboBox.addItem("Time");

        radioButtonGroup.add(descendingOrder);
        radioButtonGroup.add(ascendingOrder);

        radioPanel.add(ascendingOrder);
        radioPanel.add(descendingOrder);

        buttonsPanel.add(sortButton);
        buttonsPanel.add(cancelButton);

        northPanel.add(sortOptionLabel);
        northPanel.add(comboBox);
        northPanel.add(radioPanel);
        northPanel.add(buttonsPanel);

        frame.add(northPanel, BorderLayout.NORTH);


        sortButton.setActionCommand(CentralCommand.COMMAND_START_SORT);
        sortButton.addActionListener(MainGui.centralCommand);
        ascendingOrder.setSelected(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeHidden();
            }
        });
    }

    public JButton getSortButton() {
        return sortButton;
    }

    public JRadioButton getAscendingOrder() {
        return ascendingOrder;
    }

    public JRadioButton getDescendingOrder() {
        return descendingOrder;
    }

    public void makeHidden() {
        frame.setVisible(false);
    }

    public void makeVisibile() {
        frame.setVisible(true);
    }
}
