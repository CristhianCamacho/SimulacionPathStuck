package com.cc.simulacionPathStuck.listeners;

import com.cc.simulacionPathStuck.ThreadSimulation;
import com.cc.simulacionPathStuck.panel.SimulationStuckFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartListener implements ActionListener {

    SimulationStuckFrame simulationStuckFrame;

    public StartListener(SimulationStuckFrame simulationStuckFrame) {
        this.simulationStuckFrame = simulationStuckFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.simulationStuckFrame.startSimulationX();
    }
}
