package com.mprzenzak;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CellularAutomaton automaton = new CellularAutomaton("automaton");
            automaton.initialize(50);
            AutomatonFrame frame = new AutomatonFrame(automaton);
            frame.setSize(800, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    automaton.step();
                    frame.updateState();
                }
            });
            timer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
