package gui.Panels;

import download.Download;
import gui.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DownloadPanel extends JPanel {
    private JLabel downloadName;
    private JLabel fileSize;
    private JLabel infoIcon;
    private JLabel dateAndTime;
    private JLabel fileIcon;
    private JLabel[] dlSmallButtonsAsLabels;
    private Icons icons;
    private JPanel insidePanel;
    private JPanel infoPanel;
    private JPanel smallLabelsPanel;


    public DownloadPanel() {
        super(new BorderLayout());

        downloadName = new JLabel();
        fileSize = new JLabel();
        dateAndTime = new JLabel();
        icons = new Icons();


        insidePanel = new JPanel(new GridLayout(2, 1, 5, 0));
        infoPanel = new JPanel(new GridLayout(1, 3, 0, 5));
        insidePanel.add(downloadName);
        insidePanel.add(infoPanel);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 0, 5));

        buttonsPanel.setMaximumSize(new Dimension(50,50));

        infoPanel.add(buttonsPanel);
        infoPanel.add(dateAndTime);
        infoPanel.add(fileSize);

        dlSmallButtonsAsLabels = new JLabel[3];

        infoIcon = new JLabel();
        infoIcon.setIcon(icons.getInfoIcon());
        infoIcon.setBorder(new EmptyBorder(0, 0, 0, 10));

        fileIcon = new JLabel();
        fileIcon.setIcon(icons.getFileIcon());
        fileIcon.setBorder(new EmptyBorder(0, 10, 0, 10));

        add(fileIcon, BorderLayout.WEST);
        add(infoIcon, BorderLayout.EAST);

        EmptyBorder btnEmpBorder = new EmptyBorder(0, 0, 0, 0);
        for (int i = 0; i < 3; i++) {
            dlSmallButtonsAsLabels[i] = new JLabel();
            dlSmallButtonsAsLabels[i].setBorder(btnEmpBorder);
            dlSmallButtonsAsLabels[i].setBackground(new Color(125, 125, 125));

            switch (i) {
                case 0:
                    dlSmallButtonsAsLabels[i].setIcon(icons.getLittleCancel());
                    break;
                case 1:
                    dlSmallButtonsAsLabels[i].setIcon(icons.getLittleFolder());
                    break;
                case 2:
                    dlSmallButtonsAsLabels[i].setIcon(icons.getLittlePlay());
                    break;
            }
            buttonsPanel.add(dlSmallButtonsAsLabels[i]);
        }
        add(insidePanel);
    }

    public void addDownload(Download download) {
        downloadName.setText(download.getFileName());
        fileSize.setText(download.getDownloadedSize() + " / " + download.getFullFileSize() + "MB");
        dateAndTime.setText(download.getDate() + " " + download.getTime());
    }
}
