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
public class Tenedor {
    
    int[] pos;
    boolean ocupado;
    
    public Tenedor(int[] pos) {
        this.pos = pos;
        this.ocupado = false;
    }
    
    public void ocupar() {
        this.ocupado = true;
    }
    
    public void desocupar() {
        this.ocupado = false;
    }
    
    public boolean disponible() {
        return !this.ocupado;
    }
    
}
