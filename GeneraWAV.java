package mx.unam.fi.poo.g1.P3;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class GeneraWAV
 * Class to generate a sound file WAV.
 * @author Aldo Axel Estrada Zacarias
 * @version November-21-2024
 */

public class GeneraWAV {
    /**
     * Escribe Method
     * @param nomArchivo -> The name of file to generate.
     * @param tiempo -> Time of sound.
     * @param frecuenciaMuestreo -> Signal Frequency.
     * @param armonico -> Number of samples.
     */
    public void Escribe(String nomArchivo, int tiempo, int frecuenciaMuestreo, int armonico) {
        if(nomArchivo == null) throw new NullPointerException("No hay nombre de archivo...");
        
        if(!nomArchivo.endsWith(".wav") || tiempo <= 0 || frecuenciaMuestreo <= 0 || armonico <=0) {
            throw new IllegalArgumentException("Argumentos no validos...");
        }
        
        try (FileOutputStream wav = new FileOutputStream(nomArchivo);) {
            int muestras = tiempo * frecuenciaMuestreo;
            
            // cabecera
            wav.write("RIFF".getBytes());
            wav.write(convertEndiannessInt(36 + tiempo * frecuenciaMuestreo * 2)); // tamanio de archivo
            wav.write("WAVE".getBytes());
            wav.write("fmt ".getBytes());
            
            // datos
            wav.write(convertEndiannessInt(16)); // numero de bits
            wav.write(convertEndiannessShort((short) 1));
            wav.write(convertEndiannessShort((short) 1)); //  canales (mono)
            wav.write(convertEndiannessInt(frecuenciaMuestreo));
            wav.write(convertEndiannessInt(frecuenciaMuestreo * 2)); // bytes por segundo
            wav.write(convertEndiannessShort((short) 2)); // bytes por muestra
            wav.write(convertEndiannessShort((short) 16)); // bits por muestra
            wav.write("data".getBytes());
            wav.write(convertEndiannessInt(muestras * 2)); // bytes quee ocupan las muestras
            
            for(int m = 0; m < muestras; m++) {
                double tiempoMuestra = (double) m / frecuenciaMuestreo;
                short muestra = (short) (Math.sin(2 * Math.PI * tiempoMuestra * armonico) * 32000);
                wav.write(convertEndiannessShort(muestra));
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    /**
      * convertEndiannessShort Method
      * Method that converts a short value to endian.
      * @param valor -> Value to be transformed.
      * @return resultado -> Transformed value.
      */
    private static byte[] convertEndiannessShort(short valor) {
        byte[] resultado;
        byte b1 = (byte)(( valor >> 8 ) & 0xFF) ;
        byte b0 = (byte)( valor  & 0xFF) ;
        resultado = new byte[ ]{ b0 ,  b1 };
        return resultado;
    }
    
    /**
     * convertEndiannessInt Method
     * Method that converts a int value to endian.
     * @param valor -> Value to be transformed.
     * @return resultado -> Transformed value.
     */
    private static byte[] convertEndiannessInt(int valor) {
        byte[] resultado;
        byte b3 = (byte)(( valor >> 24) & 0xFF) ;
        byte b2 = (byte)(( valor >> 16) & 0xFF) ;
        byte b1 = (byte)(( valor >> 8 ) & 0xFF) ;
        byte b0 = (byte)( valor  & 0xFF) ;
        resultado = new byte[ ]{ b0 ,  b1 , b2 , b3 };
        return resultado;
    }
}
