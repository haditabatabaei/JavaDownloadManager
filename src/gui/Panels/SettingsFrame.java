package gui.Panels;

import ActionHandlers.NewFrameButtonHoverHandler;
import gui.Colors;
import gui.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
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


    private JButton apply;
    private JButton ok;
    private JButton cancel;

    private JFileChooser fileChooser;

    public SettingsFrame() {
        super("Settings");
        setSize(480, 150);
        setLocation(100, 100);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        icons = new Icons();
        downloadOptions = new JPanel(new GridLayout(3, 1, 0, 5));


        downloadsNumberPanel = new JPanel(new BorderLayout());
        numberOfDownloads = new JTextField("default is 0 ( infinite )");
        JLabel labelNumberOfDownloads = new JLabel("Number of Downloads : ");
        labelNumberOfDownloads.setIcon(icons.getNewProductIcon());
        downloadsNumberPanel.add(numberOfDownloads, BorderLayout.CENTER);
        downloadsNumberPanel.add(labelNumberOfDownloads, BorderLayout.WEST);

        defaultSavePanel = new JPanel(new BorderLayout());
        defSavePath = new JTextField();
        JLabel labelDefSavePath = new JLabel("Default Save Location : ");
        JLabel fileChooserOpener = new JLabel();
        labelDefSavePath.setIcon(icons.getSaveIcon());
        fileChooserOpener.setIcon(icons.getFolderIcon());

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

        insideSouthPanel.add(apply);
        insideSouthPanel.add(ok);
        insideSouthPanel.add(cancel);


        southPanel.add(insideSouthPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        add(downloadOptions, BorderLayout.NORTH);
        setVisible(true);


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
}
