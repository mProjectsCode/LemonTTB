package LemonTTB.commands.commands.gui;

import javax.swing.*;

public class Gui {
    public Gui() {
        JFrame frame = new JFrame("LemonTTB gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        frame.getContentPane().add(button1);
        frame.getContentPane().add(button2);
        frame.setVisible(true);
    }
}