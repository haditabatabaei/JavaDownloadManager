package gui;

import javax.swing.*;

public class Icons {
    private final ImageIcon settings = new ImageIcon(getClass().getResource("..//icons//settings.png"));
    private final ImageIcon settingsColor = new ImageIcon(getClass().getResource("..//icons//icons8_Settings_32px.png"));
    private final ImageIcon pause = new ImageIcon(getClass().getResource("..//icons//button_pause.png"));
    private final ImageIcon pauseColor = new ImageIcon(getClass().getResource("..//icons//icons8_Pause_32px.png"));
    private final ImageIcon start = new ImageIcon(getClass().getResource("..//icons//button_play.png"));
    private final ImageIcon startColor = new ImageIcon(getClass().getResource("..//icons//icons8_Circled_Play_32px.png"));
    private final ImageIcon startColorRed = new ImageIcon(getClass().getResource("..//icons//icons8_Start_32px.png"));
    private final ImageIcon newDl = new ImageIcon(getClass().getResource("..//icons//plus_alt.png"));
    private final ImageIcon newDlGreen = new ImageIcon(getClass().getResource("..//icons//icons8_Plus_32px_1.png"));
    private final ImageIcon newDlOrange = new ImageIcon(getClass().getResource("..//icons//icons8_Positive_32px.png"));
    private final ImageIcon delete = new ImageIcon(getClass().getResource("..//icons//trash_empty.png"));
    private final ImageIcon deletePurple = new ImageIcon(getClass().getResource("..//icons//icons8_Trash_32px.png"));
    private final ImageIcon cancelColor = new ImageIcon(getClass().getResource("..//icons//icons8_Stop_32px.png"));
    private final ImageIcon queueColor = new ImageIcon(getClass().getResource("..//icons//icons8_Future_32px.png"));
    private final ImageIcon completedColor = new ImageIcon(getClass().getResource("..//icons//icons8_Ok_32px.png"));
    private final ImageIcon processingColor = new ImageIcon(getClass().getResource("..//icons//icons8_Process_32px.png"));

    public ImageIcon getSettings() {
        return settings;
    }

    public ImageIcon getDelete() {
        return delete;
    }

    public ImageIcon getNewDl() {
        return newDl;
    }

    public ImageIcon getPause() {
        return pause;
    }

    public ImageIcon getStart() {
        return start;
    }

    public ImageIcon getDeletePurple() {
        return deletePurple;
    }

    public ImageIcon getNewDlGreen() {
        return newDlGreen;
    }

    public ImageIcon getNewDlOrange() {
        return newDlOrange;
    }

    public ImageIcon getPauseColor() {
        return pauseColor;
    }

    public ImageIcon getSettingsColor() {
        return settingsColor;
    }

    public ImageIcon getStartColor() {
        return startColor;
    }

    public ImageIcon getStartColorRed() {
        return startColorRed;
    }

    public ImageIcon getCancelColor() {
        return cancelColor;
    }

    public ImageIcon getCompletedColor() {
        return completedColor;
    }

    public ImageIcon getProcessingColor() {
        return processingColor;
    }

    public ImageIcon getQueueColor() {
        return queueColor;
    }
}
