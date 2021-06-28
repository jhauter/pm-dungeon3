package de.fhbielefeld.pmdungeon.quibble.input;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;

/**
 * Creates a window in which the player has to enter a name for the hero. It then checks if the entered name is accepted
 * and sets the name as the hero's name if it is accepted. The window only closes if a accepted name is entered.
 *
 * @author Adrian Koß
 */
public class WindowForPlayername{

    JTextField textField;
    private static String playerName = null;
    JFrame frame;

    /**
     * Creates a window with all the layout and labels and buttons required.
     */
    private void createWindow(){
        frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Dungeon");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        textField = new JTextField(20);
        textField.setFont(new Font(textField.getFont().getName(), Font.PLAIN, textField.getFont().getSize() * 3));
        textField.setMaximumSize(new Dimension(800, 80));

        JLabel label = new JLabel("Please enter a name for your hero", SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, (int) (label.getFont().getSize() * 1.5f)));

        JButton button = new JButton("Confirm");
        button.addActionListener(e -> checkName());

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(textField);

        pane.add(label, BorderLayout.NORTH);
        pane.add(panel, BorderLayout.CENTER);
        pane.add(button, BorderLayout.SOUTH);

        this.showNameRestrictions();
    }

    /**
     * Checks if the entered name is accepted. If so it sets the name as the hero's name and closes the window.
     */
    private void checkName(){
        if(textField.getText().matches("[a-zA-Z]{3,}(_|\\s[a-zA-Z]{3,})*")){
            LoggingHandler.logger.info("The entered name for the hero is accepted.");
            playerName = textField.getText();
            frame.dispose();
            new Thread(() -> DungeonStart.startGame(), "DungeonThread").start();
        } else {
            JOptionPane.showMessageDialog(frame, "The entered name was not accepted.");
            this.showNameRestrictions();
        }
    }

    private void showNameRestrictions(){
        String message = "The name of your hero must be at least 3 characters long and can contain only letters. " +
                "You can have multiple words as a name if the words are separated by a whitespace or an underscore";
        JOptionPane.showMessageDialog(frame, message, "A name for your hero", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void initiateWindowForPlayername(){
        EventQueue.invokeLater(() -> {
            WindowForPlayername w = new WindowForPlayername();
            w.createWindow();
        });
    }

    public static String getPlayerName() {
        return playerName;
    }
}
