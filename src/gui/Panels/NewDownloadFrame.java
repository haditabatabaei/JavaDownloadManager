package gui.Panels;

import ActionHandlers.NewFrameButtonHoverHandler;
import Collection.DownloadCollection;
import Collection.DownloadQueue;
import gui.Colors;
import gui.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class represents new Download window.
 */
public class NewDownloadFrame extends JFrame {

    private JPanel fileInfoPanel;
    private JPanel textFieldPanel;
    private JPanel iconsPanel;
    private JPanel saveDirPanel;
    private JPanel insideDirPanel;
    private JTextField savePath;
    private JPanel optionsPanel;
    private JPanel insideOptionsPanel;

    private JCheckBox launchCheckBox;

    private JRadioButton automatic;
    private JRadioButton manual;
    private JRadioButton queue;

    private JLabel folderIcon;
    private JLabel addressIcon;
    private JLabel fileIcon;
    private JLabel fullSize;
    private JLabel fullSizeTooltip;


    private JTextField addressTextField;
    private JTextField nameTextField;
    private Icons icons;
    private JButton addDownload;
    private JButton cancelDownload;

    private JFileChooser fileChooser;

    private QueuePanel queuePanel;

    private DownloadCollection collection;

    public NewDownloadFrame(DownloadCollection collection) {
        super("New Download");
        setSize(480, 350);
        setLocation(100, 100);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        this.collection = collection;
        queuePanel = new QueuePanel();
        icons = new Icons();

        savePath = new JTextField("Save Path Here.");
        saveDirPanel = new JPanel(new BorderLayout());
        insideDirPanel = new JPanel(new BorderLayout());
        saveDirPanel.add(insideDirPanel, BorderLayout.NORTH);
        saveDirPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        addressTextField = new JTextField();
        nameTextField = new JTextField();

        folderIcon = new JLabel();
        folderIcon.setIcon(icons.getFolderIcon());

        insideDirPanel.add(folderIcon, BorderLayout.EAST);
        insideDirPanel.add(savePath, BorderLayout.CENTER);

        fullSize = new JLabel((new Random().nextInt(300) + 10) + "MB");
        fullSizeTooltip = new JLabel("Size : ");
        fullSizeTooltip.setIcon(icons.getMicroSdIcon());
        final ButtonGroup btnGp = new ButtonGroup();
        automatic = new JRadioButton("Automatically", true);
        manual = new JRadioButton("Manually");
        queue = new JRadioButton("Queues");
        launchCheckBox = new JCheckBox("Launch Download when it is Completed.");

        automatic.setFocusPainted(false);
        manual.setFocusPainted(false);
        queue.setFocusPainted(false);
        launchCheckBox.setFocusPainted(false);

        btnGp.add(automatic);
        btnGp.add(manual);
        btnGp.add(queue);


        optionsPanel = new JPanel(new BorderLayout());
        insideOptionsPanel = new JPanel(new GridLayout(6, 1, 0, 0));
        JLabel startLabel = new JLabel("Start with : ");

        insideOptionsPanel.add(launchCheckBox);
        insideOptionsPanel.add(startLabel);
        insideOptionsPanel.add(automatic);
        insideOptionsPanel.add(manual);
        insideOptionsPanel.add(queue);

        optionsPanel.add(insideOptionsPanel, BorderLayout.NORTH);

        addressIcon = new JLabel("URL : ");
        addressIcon.setIcon(icons.getLinkIcon());

        fileIcon = new JLabel("Name : ");
        fileIcon.setIcon(icons.getDocumentFileIcon());

        textFieldPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        iconsPanel = new JPanel(new GridLayout(3, 1, 0, 5));

        fileInfoPanel = new JPanel(new BorderLayout());

        iconsPanel.add(addressIcon);
        iconsPanel.add(fileIcon);
        iconsPanel.add(fullSizeTooltip);

        textFieldPanel.add(addressTextField);
        textFieldPanel.add(nameTextField);
        textFieldPanel.add(fullSize);

        NewFrameButtonHoverHandler nfbh = new NewFrameButtonHoverHandler();

        EmptyBorder btnBottomBorder = new EmptyBorder(10, 20, 10, 20);
        addDownload = new JButton("OK");
        cancelDownload = new JButton("Cancel");

        addDownload.setBorder(btnBottomBorder);
        cancelDownload.setBorder(btnBottomBorder);

        addDownload.setForeground(Color.WHITE);
        cancelDownload.setForeground(Color.WHITE);

        addDownload.setBackground(Colors.btnGreen);
        cancelDownload.setBackground(Colors.btnGreen);

        addDownload.addMouseListener(nfbh);
        cancelDownload.addMouseListener(nfbh);

        JPanel larger = new JPanel(new BorderLayout());
        JPanel btns = new JPanel(new GridLayout(1, 2, 15, 5));

        larger.add(btns, BorderLayout.EAST);

        btns.add(cancelDownload);
        btns.add(addDownload);
        add(larger, BorderLayout.SOUTH);

        folderIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setDialogTitle("Choose a directory to save your file: ");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (fileChooser.getSelectedFile().isDirectory())
                        savePath.setText(fileChooser.getSelectedFile() + "");
                }
            }
        });

        queue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queuePanel.updateQueueList(collection.getQueues());
                queuePanel.makeVisible();
                Random randomGen = new Random();
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0:
                            queuePanel.getQueueOperationButtons()[i].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    queuePanel.addQueue("Test" + randomGen.nextInt(20), collection.getQueues());
                                }
                            });
                            break;
                        case 1:
                            queuePanel.getQueueOperationButtons()[i].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("Queue Selected : " + queuePanel.selectQueue(collection.getQueues()).getName());

                                }
                            });
                            break;
                        case 2:
                            queuePanel.getQueueOperationButtons()[i].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    queuePanel.removeQueue(collection.getQueues());
                                }
                            });
                            break;
                        case 3:
                            queuePanel.getQueueOperationButtons()[i].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("Settings Button Clicked.");
                                }
                            });
                            break;
                    }
                }
            }
        });

        fileInfoPanel.add(textFieldPanel, BorderLayout.CENTER);
        fileInfoPanel.add(iconsPanel, BorderLayout.WEST);
        add(fileInfoPanel, BorderLayout.NORTH);
        add(saveDirPanel, BorderLayout.CENTER);

        saveDirPanel.add(optionsPanel, BorderLayout.CENTER);
    }

    /**
     * This method retrieves ok/add button
     *
     * @return ok button
     */
    public JButton getAddDownload() {
        return addDownload;
    }

    /**
     * This method retrieves text field which hosts file's url address
     *
     * @return file url address textfield
     */
    public JTextField getAddressTextField() {
        return addressTextField;
    }

    /**
     * This method retrieves text field which hosts file's name
     *
     * @return file's name
     */
    public JTextField getNameTextField() {
        return nameTextField;
    }

    /**
     * This method retrieves cancel Download
     *
     * @return cancel Download
     */
    public JButton getCancelDownload() {
        return cancelDownload;
    }

    /**
     * This method retrieves full size of file
     *
     * @return full size
     */
    public int getFullSize() {
        String justNumber = fullSize.getText().replace("MB", "");
        return Integer.parseInt(justNumber);
    }

    /**
     * This method retrieves check box which determines whether file must opened after Download or not
     *
     * @return true if file must open after Download. flase otherwise
     */
    public JCheckBox getLaunchCheckBox() {
        return launchCheckBox;
    }

    /**
     * This method retrieves 'Automatic' Download option in radio buttons
     *
     * @return automatic Download options
     */
    public JRadioButton getAutomatic() {
        return automatic;
    }

    /**
     * This method returns 'Manual' Download options in radio button
     *
     * @return manual Download options
     */
    public JRadioButton getManual() {
        return manual;
    }

    /**
     * This class represents 'Queue' Download options in radio button
     *
     * @return Queue Download options
     */
    public JRadioButton getQueue() {
        return queue;
    }

    /**
     * This method retrieves text field which hosts file's save path
     *
     * @return save path text field
     */
    public JTextField getSavePath() {
        return savePath;
    }

    public void setQueueRadioAction(ArrayList<DownloadQueue> downloadQueueArrayList) {

    }
}