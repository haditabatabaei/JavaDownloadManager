package ActionHandlers;

import gui.Icons;
import gui.Panels.SettingsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FilterSiteButtonHandler implements ActionListener {
    //private Icons icons = new Icons();
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        switch (clickedButton.getText()) {
            case "Add":
                String inputUrl = JOptionPane.showInputDialog(null, "Enter site url : ", "Enter site url", JOptionPane.QUESTION_MESSAGE);
                if (!SettingsFrame.hasDuplicateInComboBox(inputUrl)) {
                    SettingsFrame.filteredCombobox.addItem(inputUrl);
                    SettingsFrame.filteredCombobox.revalidate();
                } else
                    JOptionPane.showMessageDialog(null, "Duplicate site url.\nEnter another.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case "Remove":
                String selected = (String) SettingsFrame.filteredCombobox.getSelectedItem();
                SettingsFrame.filteredCombobox.removeItem(selected);
                SettingsFrame.filteredCombobox.revalidate();
                break;
        }
    }
}
