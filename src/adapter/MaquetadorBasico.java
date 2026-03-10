package adapter;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class MaquetadorBasico { // Clase que implementa la funcionalidad básica de maquetado

    // Método para crear un nuevo archivo con el contenido dado
    public void anadirTexto(String archivo, String texto) {
        try (FileWriter fw = new FileWriter(archivo, true)) {
            fw.write(texto);
        } catch (IOException e) {
            System.err.println("Error al añadir texto: " + e.getMessage());
        }
    }

    // Método para extraer un párrafo específico de un archivo
    public String extraerParrafo(String archivo, int inicio, int fin) {
        StringBuilder resultado = new StringBuilder();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            for (int i = inicio - 1; i < fin && i < lineas.size(); i++) {
                resultado.append(lineas.get(i)).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al extraer párrafo: " + e.getMessage());
        }
        return resultado.toString();
    }

    // Método para dividir un archivo en dos partes a partir de una línea específica
    public String[] dividir(String archivo, int numLinea) {
        String[] resultado = new String[2];
        StringBuilder parte1 = new StringBuilder();
        StringBuilder parte2 = new StringBuilder();
        
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            for (int i = 0; i < lineas.size(); i++) {
                if (i < numLinea) {
                    parte1.append(lineas.get(i)).append("\n");
                } else {
                    parte2.append(lineas.get(i)).append("\n");
                }
            }
            resultado[0] = parte1.toString();
            resultado[1] = parte2.toString();
        } catch (IOException e) {
            System.err.println("Error al dividir archivo: " + e.getMessage());
        }
        return resultado;
    }
}
