/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filosofos;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sasuk
 */
public class Filosofo extends Thread {
    
    int[] pos;
    String name;
    String state;
    boolean continuar = true;
    Tenedor[] tenedores;
    int quantum;
    
    public Filosofo(int[] pos, String name, int quantum) {
        this.pos = pos;
        this.name = name;
        this.state = "pensando";
        this.quantum = quantum;
    }
    
    public void addTenedores(Tenedor[] tenedores) {
        this.tenedores = tenedores;;
    }
    
    public void comer() {
        this.state = "comiendo";
    }
    
    public boolean comiendo() {
        if (this.state.equals("comiendo")) return true;
        return false;
    }
    
    public void pensar() {
        this.state = "pensando";
    }
    
    public boolean pensando() {
        if (this.state.equals("pensando")) return true;
        return false;
    }
    
    public void esperar() {
        this.state = "esperando";
    }
    
    public boolean esperando() {
        if (this.state.equals("esperando")) return true;
        return false;
    }
    
    @Override
    public void run() {
        while(continuar) {
            System.out.println(this.name + " saluda!");
            
            if (this.esperando()) {
                if (this.tenedores[0].disponible() && this.tenedores[1].disponible()) {
                    this.tenedores[0].ocupar();
                    this.tenedores[1].ocupar();
                    this.comer();
                } else {
                    this.esperar();
                }
            } else {
                if (this.pensando()) {
                    if (this.tenedores[0].disponible() && this.tenedores[1].disponible()) {
                        this.tenedores[0].ocupar();
                        this.tenedores[1].ocupar();
                        this.comer();
                    } else {
                        this.esperar();
                    }
                } else {
                    if (this.comiendo()) {
                        this.tenedores[0].desocupar();
                        this.tenedores[1].desocupar();
                    }
                    this.pensar();
                }
            }
            
            try {
                Thread.sleep(this.quantum * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Filosofo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void parar(){
        this.continuar = false;
    }
    
}
