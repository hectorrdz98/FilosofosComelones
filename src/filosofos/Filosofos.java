/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filosofos;

import processing.core.PApplet;

/**
 *
 * @author sasuk
 */
public class Filosofos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int nFilosofos = 5;
        int quantum = 5;
        
        Interface inter = new Interface(nFilosofos, quantum);
        String[] processingArgs = { "Filósofos" };
        PApplet.runSketch(processingArgs, inter);
        
        //Cronometer cron = new Cronometer(inter, quantum);
        //cron.start();
        
        inter.startFilo();
    }
    
}
