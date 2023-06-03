package com.mprzenzak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AutomatonFrame extends JFrame {
    private CellularAutomaton automaton;
    private JButton[][] buttons;

    private JButton loadButton;

    private JButton unloadButton;

    public AutomatonFrame(CellularAutomaton automaton) {
        this.automaton = automaton;
        int size = automaton.getState().length;
        buttons = new JButton[size][size];

        unloadButton = new JButton("Unload Script");
        unloadButton.addActionListener(e -> {
            automaton.unloadScript();
            JOptionPane.showMessageDialog(null, "Script unloaded!");
        });

        loadButton = new JButton("Load Script");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    automaton.loadScript(file.getAbsolutePath());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Script loaded!");
            }
        });

        JPanel automatonPanel = new JPanel(new GridLayout(size, size));
        JPanel buttonPanel = new JPanel();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton button = new JButton();
                button.addActionListener(new CellButtonListener(i, j));
                buttons[i][j] = button;
                automatonPanel.add(button);
            }
        }

        buttonPanel.add(unloadButton);
        buttonPanel.add(loadButton);

        add(automatonPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateState();
    }

    public void updateState() {
        boolean[][] state = automaton.getState();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                buttons[i][j].setBackground(state[i][j] ? Color.BLACK : Color.WHITE);
            }
        }
    }

    private class CellButtonListener implements ActionListener {
        private final int i;
        private final int j;

        public CellButtonListener(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[][] state = automaton.getState();
            state[i][j] = !state[i][j];
            automaton.setState(state);
            updateState();
        }
    }
}

