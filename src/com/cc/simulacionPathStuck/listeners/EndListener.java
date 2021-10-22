package com.cc.simulacionPathStuck.listeners;

import com.cc.simulacionPathStuck.panel.SimulationStuckFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndListener implements ActionListener {

    SimulationStuckFrame simulationStuckFrame;

    public EndListener(SimulationStuckFrame simulationStuckFrame) {
        this.simulationStuckFrame = simulationStuckFrame;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.simulationStuckFrame.hilo.stop();

        this.simulationStuckFrame.hilo.clearPantalla();
    }
}