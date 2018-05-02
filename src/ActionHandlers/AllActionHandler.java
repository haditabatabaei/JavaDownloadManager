package ActionHandlers;

import gui.Colors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllActionHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){
            System.out.println(((JButton)e.getSource()).getText());

        }
        else if(e.getSource() instanceof JMenuItem){
            System.out.println(((JMenuItem)e.getSource()).getText());
        }
    }
}