package ActionHandlers;

import gui.Panels.DownloadInfoFrame;
import gui.Panels.DownloadPanel;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelSelectionHandlers extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        DownloadPanel tmpDlPanel = (DownloadPanel) e.getSource();
        if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
            DownloadInfoFrame downloadInfoFrame = new DownloadInfoFrame(tmpDlPanel.getDownload());
        } else if (e.getClickCount() == 1) {
            if (tmpDlPanel.isSelected()) {
                tmpDlPanel.select();
            } else
                tmpDlPanel.select();
        } else if (e.getClickCount() == 2) {
            tmpDlPanel.getDownload().open();
        }
    }
}
