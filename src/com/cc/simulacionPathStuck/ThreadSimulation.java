package com.cc.simulacionPathStuck;

import com.cc.simulacionPathStuck.panel.SimulationStuckFrame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ThreadSimulation extends Thread {

    SimulationStuckFrame simulationStuckFrame;

    int step = 5;
    Graphics2D g2d;

    ArrayList pointsList = new ArrayList<Point>();

    ArrayList directionList = new ArrayList<Integer>();

    // model
    int pasosHastaAhora = 0;

    Point pointNow;

    static int simulacionNumero = 0;
    static int simularNvecesMAX = 5000;

    Graphics2D g2dPanelResultados;

     public ThreadSimulation(SimulationStuckFrame simulationStuckFrame) {
         this.simulationStuckFrame = simulationStuckFrame;
     }

     boolean isRunning = true;
     final Object lock = new Object();

    @Override
    public void run() {
        super.run();

        Dimension pSimulationSize = this.simulationStuckFrame.pSimulation.getSize();
        int xCenter = (int)(pSimulationSize.width / 2);
        int yCenter = (int)(pSimulationSize.height / 2);

        pointNow = new Point(xCenter, yCenter);
        pointsList.add(pointNow.clone());

        Stroke stroke = new BasicStroke(2, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND, 2,
                new float[] { step, 1 }, step);

        g2d = ((Graphics2D)(this.simulationStuckFrame.pSimulation.getGraphics() ));
        g2dPanelResultados = ((Graphics2D)(this.simulationStuckFrame.pResults.getGraphics() ));

        g2d.setStroke(stroke);
        g2dPanelResultados.setStroke(stroke);
        g2d.setPaint(Color.RED);
        g2dPanelResultados.setPaint(Color.BLUE);

simulacionNumero++;

        while(isRunning) {


            synchronized (lock) {
                runAlgo(/*pointNowRunning*/);
            }
/*
try {
this.sleep(100);
}
catch (InterruptedException e) {
System.out.println(e);
}
*/
        }
    }

    public void preWait() {
        synchronized (lock) {
            isRunning = false;
        }
    }

    public void runAlgo(/*Point pointNow*/)
    {
        int direction = (int) ( Math.random()*4 );

        Point pointNowToTry = new Point(pointNow.x, pointNow.y);

        Point oldPoint = new Point(pointNowToTry.x, pointNowToTry.y);
/*
System.out.println(String.format("oldPoint (%1$s, %2$s)",
pointNowToTry.x, pointNowToTry.y
));
*/
        if (direction == 0 && !containsDirection(direction) ) {
            // up
            pointNowToTry.y = pointNowToTry.y + step;
        }
        else
        if (direction == 1 && !containsDirection(direction) ) {
            // right
            pointNowToTry.x = pointNowToTry.x + step;
        }
        else
        if (direction == 2 && !containsDirection(direction) ) {
            // down
            pointNowToTry.y = pointNowToTry.y - step;
        }
        else
        if (direction == 3 && !containsDirection(direction) ) {
            // down
            pointNowToTry.x = pointNowToTry.x - step;
        }



        if ( !containsPoint(pointNowToTry) ) {

            pointNow = new Point (pointNowToTry.x , pointNowToTry.y);

/*
System.out.println(String.format("drawLine (%1$s, %2$s)--(%3$s, %4$s)",
oldPoint.x, oldPoint.y, pointNow.x , pointNow.y
));
*/

            g2d.drawLine(oldPoint.x, oldPoint.y, pointNow.x , pointNow.y  );
            pointsList.add(pointNow.clone());
            directionList.clear();

            pasosHastaAhora++;

            FontMetrics fm=g2d.getFontMetrics();
            String text = "Pasos: " + pasosHastaAhora;

            g2d.setFont(new Font("Calibri", Font.BOLD, 20));
            int textWidth = fm.stringWidth(text);

            g2d.clearRect(0,0, 2*textWidth, 60);
            g2d.drawString(text,20, 20);

            String text1 = "Simulacion: " + simulacionNumero;
            g2d.drawString(text1,20, 40);
        }
        else {
            // CASES
            if ( !containsDirection ( (new Integer(direction) ) ) ) {
                directionList.add(new Integer(direction));
            }
/*
System.out.println(String.format("directionList.size() %1$s",
        directionList.size()
));
*/
            if (directionList.size() >= 4) {
/*
try {
this.sleep(2000);
}
catch (InterruptedException e) {
System.out.println(e);
}
*/
                if (simulacionNumero <=  simularNvecesMAX ) {

                    // Actualizar List
                    this.simulationStuckFrame.agregarAListaDeResultados(pasosHastaAhora);
                    // Actualizar UI de panel de Resultados
                    actualzarUIPanelResultados();

                    // reiniciar hilo para siguiente simulacion
                    this.simulationStuckFrame.startSimulationX();
                }

                this.stop();
            }
        }
    }

    public boolean containsDirection (Integer i) {
        boolean result = false;

        for (int k = 0; k< directionList.size(); k++) {
            if( ((Integer)directionList.get(k)).intValue() == i ) {
                result = true;

                break;
            }
        }

        return result;
    }


    public boolean containsPoint (Point p)
    {
        boolean result = false;

        for (int i = 0; i< pointsList.size(); i++) {
            Point p_i = (Point)pointsList.get(i);

            if ( p_i.x == p.x &&
                 p_i.y == p.y ) {
                result = true;

                break;
            }
        }

        return result;
    }

    public void clearPantalla() {
        g2d.clearRect(0,0,
                this.simulationStuckFrame.pSimulation.getWidth(),
                this.simulationStuckFrame.pSimulation.getHeight()
        );
    }

    public void actualzarUIPanelResultados() {

        Dimension pResultSize = this.simulationStuckFrame.pResults.getSize();

        g2dPanelResultados.clearRect(0,0,pResultSize.width, pResultSize.height);

        // Ejes
        g2dPanelResultados.drawLine(20, 20,
                20,
                pResultSize.height - 40 );

        g2dPanelResultados.drawLine(20, pResultSize.height - 40,
                pResultSize.width - 40,
                pResultSize.height - 40 );
        // Ejes texto
        g2dPanelResultados.drawString(
                "Frecuencia"
                ,20, 20);
        g2dPanelResultados.drawString(
                "Pasos"
                ,pResultSize.width - 40,
                pResultSize.height - 40);


        int xSize = pResultSize.width - 60;
        int ySize = pResultSize.height - 60;

        if (this.simulationStuckFrame.resultados.size() != 0) {

            int x0 = 20;
            int y0 = 20;

            g2dPanelResultados.setPaint(Color.RED);
            g2dPanelResultados.setFont(new Font("Calibri", Font.BOLD, 10));

            for (int i=0; i<this.simulationStuckFrame.resultados.size()-1 ; i++) {

                SimulationStuckFrame.Pair p = ((SimulationStuckFrame.Pair)this.simulationStuckFrame.resultados.get(i));
                Shape s = get_punto_de_control(p , 2) ;
                g2dPanelResultados.fill(s);

                g2dPanelResultados.drawString(String.format("(%1$s, %2$s)",p.valor,p.frecuencia),
                        s.getBounds().x, s.getBounds().y);

/*
                Point p1 = get_punto(((SimulationStuckFrame.Pair)this.simulationStuckFrame.resultados.get(i)));
                Point p2 = get_punto(((SimulationStuckFrame.Pair)this.simulationStuckFrame.resultados.get(i+1)));

                g2dPanelResultados.drawLine(
                        p1.x + x0,
                        p1.y - y0,
                        p2.x + x0,
                        p2.y - y0
                );
*/
            }



            /*
            int stepX = (int) (xSize / (this.simulationStuckFrame.resultados.size() + 1) );

            int count = 0;
            for (int x=20; x<pResultSize.width - 40; x+=stepX) {

                if (this.simulationStuckFrame.resultados.size() <= count )
                {
                    break;
                }

                g2d.setFont(new Font("Calibri", Font.BOLD, 10));
                g2dPanelResultados.drawString(
                        "" + ((SimulationStuckFrame.Pair)this.simulationStuckFrame.resultados.get(count)).valor
                        , x, pResultSize.height - 20);
                count++;
            }
            */
        }



    }

    int scalaY = 10;
    public Shape get_punto_de_control(SimulationStuckFrame.Pair p, int tam)
    {
        int x0 = 22;
        int y0 = 42;

        Dimension pResultSize = this.simulationStuckFrame.pResults.getSize();

        return new Rectangle2D.Double( p.valor-tam/2 + x0 , pResultSize.height - scalaY*(p.frecuencia-tam/2) - y0 , tam , tam );
    }

    /*
    public Point get_punto(SimulationStuckFrame.Pair p)
    {
        int x0 = 22;
        int y0 = 42;

        Dimension pResultSize = this.simulationStuckFrame.pResults.getSize();

        return new Point( p.valor + x0, pResultSize .height - scalaY*(p.frecuencia) - y0 );
    }
    */
}
