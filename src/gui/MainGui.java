package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainGui extends JFrame {
    private ArrayList<JButton> toolBarButtons;

    public MainGui() {
        super("Java Download Manager");
        toolBarButtons = new ArrayList<>();
        setSize(600, 600);
        setLocation(200, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Colors.DarkBlue);


        AllActionHandler allActionHandler = new AllActionHandler();

        JToolBar toolBar = new JToolBar("Tool Bar");
        toolBarButtons.add(new JButton("New Download"));
        toolBarButtons.add(new JButton("Pause"));
        toolBarButtons.add(new JButton("Resume"));
        toolBarButtons.add(new JButton("Cancel"));
        toolBarButtons.add(new JButton("Remove"));
        toolBarButtons.add(new JButton("Setting"));

        toolBar.setBackground(Colors.DarkBlue);
        toolBar.setFloatable(false);


        for (JButton button : toolBarButtons) {
            button.setBackground(Colors.DarkBlue);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    button.setBackground(Colors.LowerDarkBlue);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    button.setBackground(Colors.DarkBlue);
                }
            });
            button.setBorderPainted(false);
            button.addActionListener(allActionHandler);
            toolBar.add(button);
            toolBar.addSeparator();
        }

        add(toolBar, BorderLayout.NORTH);
        setVisible(true);
    }
}
