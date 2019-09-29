/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filosofos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sasuk
 */
public class ControlFilosofos extends Thread {
    
    Filosofo [] filosofos;
    boolean continuar = true;
    int quantum;
    Interface inter;
    
    public ControlFilosofos(Filosofo [] filosofos, int quantum, Interface inter) {
        this.filosofos = filosofos;
        this.quantum = quantum;
        this.inter = inter;
    }
    
    @Override
    public void run() {
        ArrayList <Filosofo> omitted = new ArrayList<>();
        LinkedList <Filosofo> waiting = new LinkedList<>();
        while(continuar) {
            
            try {
                Thread.sleep(this.quantum * 1000);
                this.inter.actQuantum++;
            } catch (InterruptedException ex) {
                Logger.getLogger(Filosofo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            omitted.clear();
            for (int i = 0; i<this.filosofos.length; i++) {
                if (this.filosofos[i].comiendo()) {
                    System.out.println(this.filosofos[i].name + " dejó de comer");
                    this.filosofos[i].tenedores[0].desocupar();
                    this.filosofos[i].tenedores[1].desocupar();
                    this.filosofos[i].pensar();
                    omitted.add(this.filosofos[i]);
                }
            }
            
            System.out.println("\nWaiting");
            for(Filosofo f : waiting) { 
                System.out.println(f.name + " waiting");
            }
            
            System.out.println("\nOmitted");
            for (int i = 0; i<omitted.size(); i++) {
                System.out.println(omitted.get(i).name + " omitting");
            }
            System.out.println();

            int cont = 0;
            while (cont < waiting.size()) {
                Filosofo f = waiting.get(cont);
                if (!omitted.contains(f)) {
                    System.out.println(f.name + " waiting");
                    if (f.tenedores[0].disponible() && f.tenedores[1].disponible()) {
                        System.out.println(f.name + " puede comer");
                        f.tenedores[0].ocupar();
                        f.tenedores[1].ocupar();
                        f.comer();
                        omitted.add(f);
                        waiting.remove(f);
                        cont--;
                    } 
                }
                cont++;
               
            }
            /*for (int i = 0; i<temp; i++) {
                System.out.println(waiting.get(i).name + " waiting");
                if (waiting.get(i).tenedores[0].disponible() && waiting.get(i).tenedores[1].disponible()) {
                    System.out.println(waiting.get(i).name + " puede comer");
                    waiting.get(i).tenedores[0].ocupar();
                    waiting.get(i).tenedores[1].ocupar();
                    waiting.get(i).comer();
                    omitted.add(waiting.get(i));
                    waiting.remove(i);
                }
            }*/
            
            for (int i = 0; i<this.filosofos.length; i++) {
                System.out.println(this.filosofos[i].name + " estaba " + this.filosofos[i].state);
                if (!this.filosofos[i].comiendo()) {
                    if (this.filosofos[i].pensando()) {
                        if (!omitted.contains(this.filosofos[i])) this.filosofos[i].esperar();
                        if (!waiting.contains(this.filosofos[i])) waiting.addLast(this.filosofos[i]);
                    } else {
                        if (!this.filosofos[i].esperando())
                            this.filosofos[i].pensar();
                    }
                }
                System.out.println(this.filosofos[i].name + " ahora está " + this.filosofos[i].state);
            }
            
            
            
        }
        
    }
    
    public void parar(){
        this.continuar = false;
    }
    
}
