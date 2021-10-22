package com.cc.simulacionPathStuck.listeners;

import com.cc.simulacionPathStuck.panel.SimulationStuckFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseListener implements ActionListener {

    SimulationStuckFrame simulationStuckFrame;

    public PauseListener(SimulationStuckFrame simulationStuckFrame) {
        this.simulationStuckFrame = simulationStuckFrame;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            this.simulationStuckFrame.hilo.preWait();
            this.simulationStuckFrame.hilo.wait();
        }
        catch (Exception ex) {

        }
    }
}