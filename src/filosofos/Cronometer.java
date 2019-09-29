/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filosofos;

/**
 *
 * @author sasuk
 */
public class Cronometer extends Thread {
    boolean activeCronometer = true;
    Interface inter;
    int quantum;
    
    int lastSeg = 0;
    
    public Cronometer(Interface inter, int quantum) {
        this.inter = inter;
        this.quantum = quantum;
    }
    
    @Override
    public void run() {
        Integer minutos = 0 , segundos = 0, milesimas = 0;
        String min="", seg="", mil="";
        try
        {
            while( activeCronometer )
            {
                Thread.sleep( 4 );
                milesimas += 4;

                if( milesimas == 1000 )
                {
                    milesimas = 0;
                    segundos += 1;
                    if( segundos == 60 )
                    {
                        segundos = 0;
                        minutos++;
                    }
                }
                
                if( minutos < 10 ) min = "0" + minutos;
                else min = minutos.toString();
                if( segundos < 10 ) seg = "0" + segundos;
                else seg = segundos.toString();

                if( milesimas < 10 ) mil = "00" + milesimas;
                else if( milesimas < 100 ) mil = "0" + milesimas;
                else mil = milesimas.toString();
                
                // Change Quantum
                if (Integer.parseInt(seg) > 0 && this.lastSeg != Integer.parseInt(seg) && Integer.parseInt(seg) % this.quantum == 0) {
                    inter.actQuantum++;
                    this.lastSeg = Integer.parseInt(seg);
                }

                inter.cronometer =  min + ":" + seg + ":" + mil ;
            }
        }catch(Exception e){}
        
    }

    public void pararCronometro(){
        activeCronometer = false;
    }
}