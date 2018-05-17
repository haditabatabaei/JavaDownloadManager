package gui.Panels;

import javax.swing.*;
import java.awt.*;

public class NewDownloadFrame extends JFrame {

    private JPanel fileInfoPanel;
    private JPanel fileAddressPanel;
    private JPanel fileNamePanel;
    private JLabel addressIcon;
    private JLabel fileIcon;
    private TextField addressTextField;
    private TextField nameTextField;

    public NewDownloadFrame() {
        super("New Download");
        setSize(650, 370);
        setLocation(100, 100);
        setResizable(false);
        setLayout(new BorderLayout());
        fileInfoPanel = new JPanel(new GridLayout(2, 1));
        fileAddressPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        fileNamePanel = new JPanel(new GridLayout(1, 2, 0, 0));

        addressIcon = new JLabel();
        fileIcon = new JLabel();


    }


}
