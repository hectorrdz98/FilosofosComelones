/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filosofos;

import processing.core.*;

/**
 *
 * @author sasuk
 */
public class Interface extends PApplet{
    
    String cronometer = "";
    int quantum;
    int actQuantum = 0;
    
    public Filosofo filosofos[];
    public Tenedor tenedores[];
    
    int r = 200;
    int[] center = {500,300+50};
    
    public Interface(int nFilosofos, int quantum) { 
        filosofos = new Filosofo[nFilosofos]; 
        tenedores = new Tenedor[nFilosofos]; 
        this.quantum = quantum;
    }
    
    public void startFilo() {
        ControlFilosofos cFilo = new ControlFilosofos(this.filosofos, this.quantum, this);
        cFilo.start();
    }
    
    @Override
    public void settings() { 
        size(1000, 750);
    }
    
    @Override
    public void setup() {
        for (int i = 0; i < this.filosofos.length; i++) {
            int x = this.center[0] + (int)(this.r * Math.sin((360/(this.filosofos.length)) * (i*2 + 1) * Math.PI/180 / 2)) - 25;
            int y = this.center[1] + (int)(this.r * Math.cos((360/(this.filosofos.length)) * (i*2 + 1) * Math.PI/180 / 2)) - 25;
            this.tenedores[i] = new Tenedor(new int[] { x, y });
            
            x = this.center[0] + (int)(this.r * Math.sin((360/this.filosofos.length) * i * Math.PI/180));
            y = this.center[1] + (int)(this.r * Math.cos((360/this.filosofos.length) * i * Math.PI/180));
            this.filosofos[i] =  new Filosofo(
                new int[] { x, y },
                String.valueOf(i+1),
                this.quantum
            );
        }
        
        for (int i = 0; i < this.filosofos.length; i++) {
            if (i == 0) {
                this.filosofos[i].addTenedores(new Tenedor[] {
                    this.tenedores[0], this.tenedores[this.filosofos.length-1]
                });
            } else {
                this.filosofos[i].addTenedores(new Tenedor[] {
                    this.tenedores[i-1], this.tenedores[i]
                });
            }
            
            //this.filosofos[i].start();
        }
    }
    
    @Override
    public void draw() {
        clear();
        
        // Draw the background
        background(0);
        noStroke();
        
        // Superior
        fill(71, 71, 107);
        rect(0, 0, width, height - 100);
        
        // Inferior
        fill(41, 41, 61);
        rect(0, height - 100, width, height);
        
        
        // Cronometer and Quantum
        fill(255);
        textSize(25);
        textAlign(CENTER, CENTER);
        text("Quantum: " + this.actQuantum, 120, height - 50); 
        //text(this.cronometer, width - 100, height - 50); 
        fill(255);
        textSize(20);
        text("Pensando: ", width - 175, height - 50); 
        fill(128, 0, 128);
        square(width - 100 - 20, height - 50 - 20, 40);
        fill(255);
        textSize(20);
        text("Esperando: ", width - 175 - 175, height - 50); 
        fill(51, 153, 102);
        square(width - 100 - 20 - 175, height - 50 - 20, 40);
        fill(255);
        textSize(20);
        text("Comiendo: ", width - 175 - 175 - 175, height - 50); 
        fill(51, 102, 153);
        square(width - 100 - 20 - 175 - 175, height - 50 - 20, 40);
        
        
        // Title
        fill(255);
        textSize(40);
        text("Filósofos comelones", width/2, 50); 
        
        textAlign(LEFT);
        noStroke();
        
        noFill();
        stroke(150,150,150);
        strokeWeight(4);
        circle(this.center[0], this.center[1], this.r * 2);
        noStroke();
        
        // Draw Filosofos
        for (int i = 0; i<this.filosofos.length; i++) {
            if (this.filosofos[i].pensando()) fill(128, 0, 128);
            else if (this.filosofos[i].esperando()) fill(51, 153, 102);
            else if (this.filosofos[i].comiendo()) fill(51, 102, 153);
            else fill(255,255,255);
            circle(this.filosofos[i].pos[0], this.filosofos[i].pos[1], 100);
            textSize(40);
            fill(255,255,255);
            text(this.filosofos[i].name, this.filosofos[i].pos[0] - 10, this.filosofos[i].pos[1] + 15);
        }
        
        // Draw Tenedores
        for (int i = 0; i<this.tenedores.length; i++) {
            if (this.tenedores[i].disponible()) fill(102, 102, 153);
            else fill(255,0,0);
            square(this.tenedores[i].pos[0], this.tenedores[i].pos[1], 50);
        }
    }
}
