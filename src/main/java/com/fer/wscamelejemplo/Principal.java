/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.wscamelejemplo;

import org.apache.camel.main.Main;

/**
 *
 * @author fercris
 */
public class Principal {
    public static void main(String[] args) {
        Main main = new Main();
        
        main.addRouteBuilder(RutaServicio.class);
        
        main.start();
    }
}
