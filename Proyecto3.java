package mx.unam.fi.poo.g1.P3;

import mx.unam.fi.poo.g1.P3.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class Proyecto 3
 * Main Class of the Final Project
 * @author Aldo Axel Estrada Zacarias
 * @version November-21-2024
 */

public class Proyecto3 {
    /**
     * *Main Method
     * It's executed the application.
     * @param args -> Default array to save the "prueba.txt" file.
     */
    public static void main(String[] args) {
        if(args.length != 1) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String nombreArchivo = br.readLine();
            int frecuenciaMuestreo = Integer.parseInt(br.readLine());
            int armonico = Integer.parseInt(br.readLine());
            int tiempo = Integer.parseInt(br.readLine());
            
            GeneraWAV wav = new GeneraWAV();
            wav.Escribe(nombreArchivo, tiempo, frecuenciaMuestreo, armonico);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Argumentos no validos...");
        } catch (IOException e) {
            System.err.println("Archivo no encontrado...");
        }
    }
}
