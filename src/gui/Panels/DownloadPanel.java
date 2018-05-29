package gui.Panels;

import ActionHandlers.PanelSelectionHandlers;
import Download.Download;
import gui.Colors;
import gui.Icons;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private Icons icons;
    private JPanel insidePanel;
    private JPanel infoPanel;
    private Download download;

    private boolean isSelect;
    private Border selectedBorder;
    private Border defaultBorder;

    private PanelSelectionHandlers psh;

    public DownloadPanel() {
        super(new BorderLayout());
        psh = new PanelSelectionHandlers();
        defaultBorder = BorderFactory.createMatteBorder(0, 0, 5, 0, Colors.LowerDarkBlue2);
        selectedBorder = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.YELLOW);
        setBorder(defaultBorder);
        downloadName = new JLabel();
        fileSize = new JLabel();
        dateAndTime = new JLabel();
        icons = new Icons();


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

        dlSmallButtonsAsLabels[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                download.openFolder();
            }
        });
        dlSmallButtonsAsLabels[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                download.cancel();
            }
        });

        dlSmallButtonsAsLabels[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                download.resume();
            }
        });

        addMouseListener(psh);
    }

    /**
     * This method adds a Download to this panel
     *
     * @param download
     */
    public void addDownload(Download download) {
        this.download = download;
        downloadName.setText(download.getFileName());
        fileSize.setText(download.getDownloadedSize() + " / " + download.getFullFileSize() + "MB");
        dateAndTime.setText(download.getDate() + " " + download.getTime());
        insidePanel.add(download.getDownloadProgressBar());
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
}
