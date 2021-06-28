package de.fhbielefeld.pmdungeon.quibble.menu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.memory.MemoryDataHandler;

public class Window {

	private JFrame frame;

	private static final int HEIGHT = 200;
	private static final int WIDTH = 400;

	/**
	 * Starts the Start Menu The main window is intended for settings. For example,
	 * whether to create a new hero
	 * 
	 * @param Title Simple Name of the Window
	 */
	public void createWindow(String Title) {
		this.frame = new JFrame();
		this.frame.setTitle("Quibble Dungeon");
		this.frame.setVisible(true);
		this.frame.setBounds(200, 200, WIDTH, HEIGHT);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new GridLayout(4, 0));
		createContent();
	}

	private void createContent() {
		this.frame.getContentPane().add(createSimpleButton("New Player", actionNewPlayer(frame)));
		this.frame.getContentPane().add(createSimpleButton("Load Game", actionLoadGame(frame)));
	}

	private JButton createSimpleButton(String displayText, ActionListener listener) {
		JButton btn = new JButton(displayText);
		btn.setSize(200, 200);
		btn.setFocusable(false);
		btn.addActionListener(listener);
		return btn;
	}

	private ActionListener actionLoadGame(JFrame frame) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				DungeonStart.startGame(true, 9);
			}
		};

		return listener;
	}

	private ActionListener actionNewPlayer(JFrame frame) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				WindowForPlayername.initiateWindowForPlayername();
			}
		};

		return listener;
	}

}
