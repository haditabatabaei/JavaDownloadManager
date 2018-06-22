package gui.Panels;

import ActionHandlers.CentralCommand;
import ActionHandlers.FilterSiteButtonHandler;
import ActionHandlers.NewFrameButtonHoverHandler;
import gui.Colors;
import gui.Icons;
import gui.MainGui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class represents settings windows.
 */
public class SettingsFrame extends JFrame {
    private JTextField numberOfDownloads;
    private JTextField defSavePath;

    private Icons icons;

    private JPanel downloadsNumberPanel;
    private JPanel defaultSavePanel;
    private JPanel downloadOptions;

    private JPanel southPanel;
    private JPanel insideSouthPanel;
    private JPanel comboPanel;
    private JPanel insideComboPanel;
    private JPanel comboButtonPanel;

    public static JComboBox<String> filteredCombobox;
    private JLabel filterLabel;
    private JButton apply;
    private JButton ok;
    private JButton cancel;


    private JButton addSiteBtn;
    private JButton removeSiteBtn;
    private JFileChooser fileChooser;

    public SettingsFrame() {
        super("Settings");
        setSize(440, 300);
        setLocation(100, 100);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        downloadOptions = new JPanel(new GridLayout(3, 1, 0, 5));
        comboPanel = new JPanel(new BorderLayout());
        insideComboPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        filteredCombobox = new JComboBox<>();
        filterLabel = new JLabel("Filter Sites :");
        comboButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));

        addSiteBtn = new JButton("Add");
        removeSiteBtn = new JButton("Remove");

        comboButtonPanel.add(addSiteBtn);
        comboButtonPanel.add(removeSiteBtn);

        downloadsNumberPanel = new JPanel(new BorderLayout());
        numberOfDownloads = new JTextField("default is 0 ( infinite )");
        JLabel labelNumberOfDownloads = new JLabel("Number of Downloads : ");
        labelNumberOfDownloads.setIcon(Icons.ICON_NEW_PRODUCTION);
        downloadsNumberPanel.add(numberOfDownloads, BorderLayout.CENTER);
        downloadsNumberPanel.add(labelNumberOfDownloads, BorderLayout.WEST);

        defaultSavePanel = new JPanel(new BorderLayout());
        defSavePath = new JTextField();
        JLabel labelDefSavePath = new JLabel("Default Save Location : ");
        JLabel fileChooserOpener = new JLabel();
        labelDefSavePath.setIcon(Icons.ICON_SAVE);
        fileChooserOpener.setIcon(Icons.ICON_FOLDER);

        defaultSavePanel.add(defSavePath, BorderLayout.CENTER);
        defaultSavePanel.add(labelDefSavePath, BorderLayout.WEST);
        defaultSavePanel.add(fileChooserOpener, BorderLayout.EAST);

        downloadOptions.add(downloadsNumberPanel);
        downloadOptions.add(defaultSavePanel);

        NewFrameButtonHoverHandler nfbh = new NewFrameButtonHoverHandler();
        southPanel = new JPanel(new BorderLayout());
        insideSouthPanel = new JPanel(new GridLayout(1, 3, 10, 0));

        EmptyBorder empt = new EmptyBorder(10, 20, 10, 20);
        apply = new JButton("Apply");
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");

        apply.setBorder(empt);
        ok.setBorder(empt);
        cancel.setBorder(empt);

        apply.setForeground(Color.WHITE);
        ok.setForeground(Color.WHITE);
        cancel.setForeground(Color.WHITE);

        apply.setBackground(Colors.btnGreen);
        ok.setBackground(Colors.btnGreen);
        cancel.setBackground(Colors.btnGreen);

        apply.addMouseListener(nfbh);
        ok.addMouseListener(nfbh);
        cancel.addMouseListener(nfbh);

        apply.setFocusPainted(false);
        apply.setFocusPainted(false);
        apply.setFocusPainted(false);

        ok.setActionCommand(CentralCommand.COMMAND_APPLY_SETTINGS);
        apply.setActionCommand(CentralCommand.COMMAND_APPLY_SETTINGS);

        ok.addActionListener(MainGui.centralCommand);
        apply.addActionListener(MainGui.centralCommand);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        insideSouthPanel.add(apply);
        insideSouthPanel.add(ok);
        insideSouthPanel.add(cancel);


        southPanel.add(insideSouthPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        add(downloadOptions, BorderLayout.NORTH);
        add(comboPanel, BorderLayout.CENTER);
        comboPanel.add(insideComboPanel, BorderLayout.NORTH);
        insideComboPanel.add(filterLabel);
        insideComboPanel.add(filteredCombobox);
        insideComboPanel.add(comboButtonPanel);
        FilterSiteButtonHandler fsbh = new FilterSiteButtonHandler();
        addSiteBtn.addActionListener(fsbh);
        removeSiteBtn.addActionListener(fsbh);
        fileChooserOpener.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setDialogTitle("Choose a default directory for your downloads : ");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (fileChooser.getSelectedFile().isDirectory())
                        defSavePath.setText(fileChooser.getSelectedFile() + "");
                }
            }
        });
    }

    /**
     * This method retrieves textField which hosts default path to save files
     *
     * @return default path to save files in textfield
     */
    public JTextField getDefSavePath() {
        return defSavePath;
    }

    /**
     * This method returns number of maximum continuesly downloading in processing Download
     *
     * @return number of downloads at a time
     */
    public JTextField getNumberOfDownloads() {
        return numberOfDownloads;
    }

    /**
     * This method retrieves apply button
     *
     * @return apply button
     */
    public JButton getApply() {
        return apply;
    }

    /**
     * This method retrieves cancel button
     *
     * @return cancel button
     */
    public JButton getCancel() {
        return cancel;
    }

    /**
     * This method retrieves ok button
     *
     * @return ok button
     */
    public JButton getOk() {
        return ok;
    }

    public void makeVisisble() {
        setVisible(true);
    }

    public void makeHidden() {
        setVisible(false);
    }

    public static boolean hasDuplicateInComboBox(String inputSiteUrl) {
        for (int i = 0; i < filteredCombobox.getItemCount(); i++) {
            if (filteredCombobox.getItemAt(i).contains(inputSiteUrl))
                return true;
        }
        return false;
    }

}
