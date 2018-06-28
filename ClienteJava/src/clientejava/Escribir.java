/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientejava;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author PAUL
 */
public class Escribir {

    public static void main(String[] args) {
        try {
            FileWriter es = new FileWriter("prueba1.txt");
            PrintWriter guardar = new PrintWriter(es);
            String p = "Linea";
            guardar.print(p);
            es.close();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

    }

}
