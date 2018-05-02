package ActionHandlers;

import gui.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonHoverHandler extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e){
        super.mouseEntered(e);
        JButton button = (JButton)e.getSource();
        button.setBackground(Colors.LowerDarkBlue2);
        button.setForeground(Color.WHITE);
    }
    @Override
    public void mouseExited(MouseEvent e){
        super.mouseExited(e);
        JButton button = (JButton)e.getSource();
        button.setBackground(Colors.LightBlue);
        button.setForeground(Colors.DarkGray);
    }
}
