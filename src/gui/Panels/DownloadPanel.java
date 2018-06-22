package gui.Panels;

import ActionHandlers.PanelSelectionHandlers;
import download.Download;
import gui.Colors;
import gui.Icons;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

/**
 * This class represents a Download panel as a list panel
 */
public class DownloadPanel extends JPanel {
    private JLabel downloadName;
    private JLabel fileSize;
    private JLabel infoIcon;
    private JLabel dateAndTime;
    private JLabel fileIcon;
    private JLabel[] dlSmallButtonsAsLabels;

    private JPanel insidePanel;
    private JPanel infoPanel;

    private Download download;

    private boolean isSelect;
    private Border selectedBorder;
    private Border defaultBorder;

    private transient PanelSelectionHandlers psh;

    private void headStart() {
        psh = new PanelSelectionHandlers();
        defaultBorder = BorderFactory.createMatteBorder(0, 0, 5, 0, Colors.LowerDarkBlue2);
        selectedBorder = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.YELLOW);
        setBorder(defaultBorder);
        downloadName = new JLabel();
        fileSize = new JLabel();
        dateAndTime = new JLabel();
        insidePanel = new JPanel(new GridLayout(4, 1, 5, 0));
        infoPanel = new JPanel(new GridLayout(1, 3, 0, 5));
        insidePanel.add(downloadName);
        insidePanel.add(infoPanel);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 0, 5));

        buttonsPanel.setMaximumSize(new Dimension(50, 50));

        infoPanel.add(buttonsPanel);
        infoPanel.add(dateAndTime);
        infoPanel.add(fileSize);

        dlSmallButtonsAsLabels = new JLabel[3];

        infoIcon = new JLabel();
        infoIcon.setIcon(Icons.ICON_INFO);
        infoIcon.setBorder(new EmptyBorder(0, 0, 0, 10));


        fileIcon = new JLabel();
        fileIcon.setIcon(Icons.ICON_FILE);
        fileIcon.setBorder(new EmptyBorder(0, 10, 0, 10));

        EmptyBorder btnEmpBorder = new EmptyBorder(0, 0, 0, 0);

        for (int i = 0; i < 3; i++) {
            dlSmallButtonsAsLabels[i] = new JLabel();
            dlSmallButtonsAsLabels[i].setBorder(btnEmpBorder);
            switch (i) {
                case 0:
                    dlSmallButtonsAsLabels[i].setIcon(Icons.ICON_LITTLE_CANCEL);
                    break;
                case 1:
                    dlSmallButtonsAsLabels[i].setIcon(Icons.ICON_LITTLE_FOLDER);
                    break;
                case 2:
                    dlSmallButtonsAsLabels[i].setIcon(Icons.ICON_LITTLE_PLAY);
                    break;
            }
            buttonsPanel.add(dlSmallButtonsAsLabels[i]);
        }
    }

    private void addingStuff() {
        add(fileIcon, BorderLayout.WEST);
        add(infoIcon, BorderLayout.EAST);
        add(insidePanel);
    }

    private void smallButtonsMouseHandler() {
        dlSmallButtonsAsLabels[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                download.cancel();
            }
        });

        dlSmallButtonsAsLabels[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                download.openFolder();
            }
        });

        dlSmallButtonsAsLabels[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //download.resume();
            }
        });
    }

    public DownloadPanel() {
        super(new BorderLayout());
        headStart();
        addingStuff();
        smallButtonsMouseHandler();
        addMouseListener(psh);
    }

    public void setMouseListener() {
        addMouseListener(new PanelSelectionHandlers());
    }

    /**
     * This method adds a Download to this panel
     *
     * @param download
     */
    public void addDownload(Download download) {
        this.download = download;
        download.createProgressbar();
        downloadName.setText(download.getFileName());
        fileSize.setText(download.getDownloadedSize() + " / " + download.getFullFileSize() + "Byte");
        dateAndTime.setText(download.getDate() + " " + download.getTime());
        insidePanel.add(download.getDownloadProgressBar());
    }

    public void update() {
        double downloadedKB = download.getDownloadedSize() / 1024;
        double downloadedMB = downloadedKB / 1024;
        double downloadedGB = downloadedMB / 1024;
        double fullKB = download.getFullFileSize() / 1024;
        double fullMB = fullKB / 1024;
        double fullGB = fullMB / 1024;
        int percent = download.getPercent();
        DecimalFormat df = new DecimalFormat("#.##");

        String downloaded = "";
        if (downloadedGB >= 1)
            downloaded = df.format(downloadedGB) + " GB ";
        else if (downloadedMB < 1024 && downloadedMB >= 1)
            downloaded = df.format(downloadedMB) + " MB ";
        else
            downloaded = df.format(downloadedKB) + " KB ";

        String full = "";
        if (fullGB >= 1)
            full = df.format(fullGB) + " GB ";
        else if (fullMB < 1024 && fullMB >= 1)
            full = df.format(fullMB) + " MB ";
        else
            full = df.format(fullKB) + " KB ";

        fileSize.setText(downloaded + "/ " + full);

        download.getDownloadProgressBar().setValue(percent);
        validate();
    }

    /**
     * This method retrieves panel's Download
     *
     * @return panel's Download
     */
    public Download getDownload() {
        return download;
    }

    public void changeDownload(Download newDownload) {
        download = newDownload;
        downloadName.setText(newDownload.getFileName());
        fileSize.setText(newDownload.getDownloadedSize() + " / " + newDownload.getFullFileSize() + "MB");
        dateAndTime.setText(newDownload.getDate() + " " + newDownload.getTime());
        insidePanel.remove(download.getDownloadProgressBar());
        insidePanel.add(newDownload.getDownloadProgressBar());
    }

    /**
     * This method will select this panel and it's Download.
     */


    public void select() {
        if (!isSelected()) {
            download.select();
            setBackground(Color.YELLOW);
            setBorder(selectedBorder);
        } else {
            setBackground(Colors.defaultGray);
            setBorder(defaultBorder);
        }
        isSelect = !isSelect;
    }

    /**
     * This method checks whether this panel is selected or not
     *
     * @return true if selected. false otherwise
     */
    public boolean isSelected() {
        return isSelect;
    }

    /**
     * This method retrieves an array of JLabels for handling actions.
     *
     * @return array of JLabel
     */
    public JLabel[] getDlSmallButtonsAsLabels() {
        return dlSmallButtonsAsLabels;
    }


    public void print() {
        System.out.println(toString());
    }

    public String toString() {
        return getDownload().toString();
    }

    public JLabel getFileSize() {
        return fileSize;
    }


}

