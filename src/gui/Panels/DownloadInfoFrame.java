package gui.Panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Download.Download;
import gui.Icons;

import java.awt.*;

public class DownloadInfoFrame extends JFrame {
    /**
     * This is my Download info panel.
     * This class has almost every information in it
     */
    private Icons icons;
    private JPanel[] infoSmallPanels;


    public DownloadInfoFrame(Download download) {
        super(download.getFileName() + " Info");
        setSize(480, 350);
        setLocation(100, 100);
        setMinimumSize(new Dimension(480, 350));
        setLayout(new BorderLayout());
        icons = new Icons();
        JPanel infoPanel = new JPanel(new GridLayout(6, 1, 0, 5));

        /* File Name Info */
        JLabel fileInfo = new JLabel();
        fileInfo.setText("File Name : " + download.getFileName());
        fileInfo.setIcon(icons.getFileIcon());

        /*File Address Info */
        JLabel fileAddress = new JLabel("Download URL : ");
        fileAddress.setIcon(icons.getLinkIcon());
        JPanel pnl2 = new JPanel(new BorderLayout());
        JTextField fileAddressField = new JTextField();
        fileAddressField.setText(download.getUrlAddress());
        fileAddressField.setEditable(false);
        fileAddressField.setBorder(new EmptyBorder(0, 0, 0, 0));
        pnl2.add(fileAddress, BorderLayout.WEST);
        pnl2.add(fileAddressField, BorderLayout.CENTER);


        /*File Time and Date Started*/
        JLabel fileDateAndTime = new JLabel();
        fileDateAndTime.setText("Date & Time : " + download.getDate() + " | " + download.getTime());
        fileDateAndTime.setIcon(icons.getDateIcon());

        /*File Save Directory*/
        JPanel pnl1 = new JPanel(new BorderLayout());
        JTextField fileSavedirectoryField = new JTextField();
        JLabel fileSavedirectory = new JLabel("Save to : ");
        fileSavedirectory.setText("Save to : ");
        fileSavedirectory.setIcon(icons.getFolderIcon());
        fileSavedirectoryField.setText(download.getSavePath());
        fileSavedirectoryField.setEditable(false);
        fileSavedirectoryField.setBorder(new EmptyBorder(0, 0, 0, 0));
        pnl1.add(fileSavedirectory, BorderLayout.WEST);
        pnl1.add(fileSavedirectoryField, BorderLayout.CENTER);

        /*File Size*/
        JLabel fileSize = new JLabel();
        fileSize.setText("Downloaded : " + download.getDownloadedSize() + " / " + download.getFullFileSize() + " MB");
        fileSize.setIcon(icons.getMicroSdIcon());

        /*Launch After*/
        JLabel launchAfter = new JLabel();
        if (download.isLaunchedAfter())
            launchAfter.setText("File will launch after Download finished.");
        else
            launchAfter.setText("File wont launch after Download finished. ");
        launchAfter.setIcon(icons.getRunIcon());

        infoSmallPanels = new JPanel[6];

        for (int i = 0; i < 6; i++) {
            infoSmallPanels[i] = new JPanel(new BorderLayout());
            infoPanel.add(infoSmallPanels[i]);
            switch (i) {
                case 0:
                    infoSmallPanels[i].add(fileInfo, BorderLayout.CENTER);
                    break;
                case 1:
                    infoSmallPanels[i].add(pnl2, BorderLayout.CENTER);
                    break;
                case 2:
                    infoSmallPanels[i].add(fileDateAndTime, BorderLayout.CENTER);
                    break;
                case 3:
                    infoSmallPanels[i].add(pnl1, BorderLayout.CENTER);
                    break;
                case 4:
                    infoSmallPanels[i].add(fileSize, BorderLayout.CENTER);
                    break;
                case 5:
                    infoSmallPanels[i].add(launchAfter, BorderLayout.CENTER);
            }
        }

        add(infoPanel, BorderLayout.NORTH);

        setVisible(true);
    }

}