package ActionHandlers;

import gui.Colors;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class handles some hover styles for new Download window buttons
 */
public class NewFrameButtonHoverHandler extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        JButton button = (JButton)e.getSource();
        button.setBackground(Colors.btnGreenHover);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        JButton button = (JButton)e.getSource();
        button.setBackground(Colors.btnGreen);
    }
}
