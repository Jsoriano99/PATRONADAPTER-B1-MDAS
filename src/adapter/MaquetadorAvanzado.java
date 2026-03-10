package adapter;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MaquetadorAvanzado implements InterfazRequerida { // Clase que implementa la funcionalidad avanzada de maquetado utilizando MaquetadorBasico como adaptador
    
    private MaquetadorBasico maquetadorBasico;

    // Constructor que inicializa el MaquetadorBasico
    public MaquetadorAvanzado() {
        this.maquetadorBasico = new MaquetadorBasico();
    }

    // Implementación de los métodos de la InterfazRequerida utilizando MaquetadorBasico
    @Override
    public void unir(String archivo1, String archivo2, String archivoSalida) {
        try {
            Files.deleteIfExists(Paths.get(archivoSalida));
            List<String> lineas1 = Files.readAllLines(Paths.get(archivo1));
            List<String> lineas2 = Files.readAllLines(Paths.get(archivo2));
            
            List<String> resultado = new ArrayList<>();
            resultado.addAll(lineas1);
            resultado.addAll(lineas2);
            
            Files.write(Paths.get(archivoSalida), resultado);
            System.out.println("Archivos unidos correctamente en: " + archivoSalida);
        } catch (IOException e) {
            System.err.println("Error al unir archivos: " + e.getMessage());
        }
    }

    // Método para combinar dos archivos intercalando sus líneas
    @Override
    public void combinar(String archivo1, String archivo2, String archivoSalida) {
        try {
            Files.deleteIfExists(Paths.get(archivoSalida));
            List<String> lineas1 = Files.readAllLines(Paths.get(archivo1));
            List<String> lineas2 = Files.readAllLines(Paths.get(archivo2));
            
            List<String> resultado = new ArrayList<>();
            int max = Math.max(lineas1.size(), lineas2.size());
            
            for (int i = 0; i < max; i++) {
                if (i < lineas1.size()) {
                    resultado.add(lineas1.get(i));
                }
                if (i < lineas2.size()) {
                    resultado.add(lineas2.get(i));
                }
            }
            
            Files.write(Paths.get(archivoSalida), resultado);
            System.out.println("Archivos combinados correctamente en: " + archivoSalida);
        } catch (IOException e) {
            System.err.println("Error al combinar archivos: " + e.getMessage());
        }
    }

    // Método para separar un archivo en varias partes a partir de una lista de puntos de corte
    @Override
    public void separar(String archivoEntrada, List<Integer> puntosCorte, String prefijoSalida) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivoEntrada));
            List<Integer> puntos = new ArrayList<>(puntosCorte);
            Collections.sort(puntos);
            
            int inicio = 0;
            int numArchivo = 1;
            
            for (int punto : puntos) {
                List<String> parte = new ArrayList<>();
                for (int i = inicio; i < punto && i < lineas.size(); i++) {
                    parte.add(lineas.get(i));
                }
                
                if (!parte.isEmpty()) {
                    String nombreArchivo = prefijoSalida + "_" + numArchivo + ".txt";
                    Files.write(Paths.get(nombreArchivo), parte);
                    System.out.println("Creado: " + nombreArchivo + " (" + parte.size() + " líneas)");
                    numArchivo++;
                }
                
                inicio = punto;
            }
            
            List<String> parteFinal = new ArrayList<>();
            for (int i = inicio; i < lineas.size(); i++) {
                parteFinal.add(lineas.get(i));
            }
            
            if (!parteFinal.isEmpty()) {
                String nombreArchivo = prefijoSalida + "_" + numArchivo + ".txt";
                Files.write(Paths.get(nombreArchivo), parteFinal);
                System.out.println("Creado: " + nombreArchivo + " (" + parteFinal.size() + " líneas)");
            }
            
        } catch (IOException e) {
            System.err.println("Error al separar archivo: " + e.getMessage());
        }
    }
}
