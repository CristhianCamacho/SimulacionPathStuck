package com.cc.simulacionPathStuck.panel;

import com.cc.simulacionPathStuck.ThreadSimulation;
import com.cc.simulacionPathStuck.listeners.EndListener;
import com.cc.simulacionPathStuck.listeners.PauseListener;
import com.cc.simulacionPathStuck.listeners.StartListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SimulationStuckFrame extends JFrame {

    JButton bStart, bPause, bEnd;

    JPanel pControles;

    JSplitPane pContainerSplit;
    public JPanel pSimulation;
    public JPanel pResults;

    public ThreadSimulation hilo;

    public ArrayList resultados = new ArrayList<Pair>();

    public SimulationStuckFrame(int width, int height)
    {
        this.setSize(width, height);
        //this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());

        hilo = new ThreadSimulation(this) {

        };

        pContainerSplit = new JSplitPane();

        pControles = new JPanel();
        pControles.setLayout( new GridLayout(1,3) );

        bStart = new JButton("Start");
        bStart.addActionListener(new StartListener(this));
        bPause = new JButton("Pause");
        bPause.addActionListener(new PauseListener(this));
        bEnd = new JButton("End");
        bEnd.addActionListener(new EndListener(this));

        pControles.add(bStart);
        pControles.add(bPause);
        pControles.add(bEnd);

        pSimulation = new JPanel();
        pResults = new JPanel();

        this.add(pControles, BorderLayout.NORTH);

        pContainerSplit.setLeftComponent(pSimulation);
        pContainerSplit.setRightComponent(pResults);

        pContainerSplit.setDividerLocation(this.getWidth()/2);

        this.add(pContainerSplit, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //dispose();
                System.exit(0);
            }
        });
    }

    public void startSimulationX() {

        if (this.pSimulation.getGraphics() != null ) {
            this.pSimulation.getGraphics().clearRect(
                    0,
                    0,
                    this.pSimulation.getWidth(),
                    this.pSimulation.getHeight());

        }

        this.hilo = new ThreadSimulation(this);
        this.hilo.start();
    }

    public class Pair {
        public int valor, frecuencia;
        public Pair (int valor, int frecuencia) {
            this.valor = valor;
            this.frecuencia = frecuencia;
        }
    }

    public void agregarAListaDeResultados(int pasosHastaAhora) {

        Pair pair = containsResult(pasosHastaAhora);

        if (pair == null) {
            pair = new Pair(pasosHastaAhora, 1);
            resultados.add(pair);
        } else {
            pair.frecuencia++;
        }
    }

    public Pair containsResult(int pasosHastaAhora) {
        Pair resultPair = null;

        for (int i=0; i<resultados.size(); i++) {
            if( ((Pair) resultados.get(i) ).valor == pasosHastaAhora ) {
                resultPair = (Pair) resultados.get(i);
                break;
            }
        }

        return resultPair;
    }

}
