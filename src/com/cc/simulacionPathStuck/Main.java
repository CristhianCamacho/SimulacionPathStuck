package com.cc.simulacionPathStuck;

import com.cc.simulacionPathStuck.panel.SimulationStuckFrame;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

      SimulationStuckFrame ssp = new SimulationStuckFrame(dim.width-400, dim.height-200);

	  ssp.setVisible(true);
      //ssp.run();
    }
}
