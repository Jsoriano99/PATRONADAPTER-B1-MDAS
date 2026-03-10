package adapter;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MaquetadorAvanzado implements InterfazRequerida {
    
    private MaquetadorBasico maquetadorBasico;

    public MaquetadorAvanzado() {
        this.maquetadorBasico = new MaquetadorBasico();
    }

    @Override
    public void unir(String archivo1, String archivo2, String archivoSalida) {
        try {
            new File(archivoSalida).delete();
            
            int total1 = contarLineas(archivo1);
            int total2 = contarLineas(archivo2);
            
            String contenido1 = maquetadorBasico.extraerParrafo(archivo1, 1, total1);
            maquetadorBasico.anadirTexto(archivoSalida, contenido1);
            
            String contenido2 = maquetadorBasico.extraerParrafo(archivo2, 1, total2);
            maquetadorBasico.anadirTexto(archivoSalida, contenido2);
            
            System.out.println("Archivos unidos correctamente en: " + archivoSalida);
        } catch (Exception e) {
            System.err.println("Error al unir archivos: " + e.getMessage());
        }
    }

    @Override
    public void combinar(String archivo1, String archivo2, String archivoSalida) {
        try {
            new File(archivoSalida).delete();
            
            int total1 = contarLineas(archivo1);
            int total2 = contarLineas(archivo2);
            
            int max = Math.max(total1, total2);
            
            for (int i = 1; i <= max; i++) {
                if (i <= total1) {
                    String linea = maquetadorBasico.extraerParrafo(archivo1, i, i);
                    maquetadorBasico.anadirTexto(archivoSalida, linea);
                }
                if (i <= total2) {
                    String linea = maquetadorBasico.extraerParrafo(archivo2, i, i);
                    maquetadorBasico.anadirTexto(archivoSalida, linea);
                }
            }
            
            System.out.println("Archivos combinados correctamente en: " + archivoSalida);
        } catch (Exception e) {
            System.err.println("Error al combinar archivos: " + e.getMessage());
        }
    }

    @Override
    public void separar(String archivoEntrada, List<Integer> puntosCorte, String prefijoSalida) {
        try {
            List<Integer> puntos = new ArrayList<>(puntosCorte);
            Collections.sort(puntos);
            
            String archivoActual = archivoEntrada;
            int numArchivo = 1;
            
            for (int punto : puntos) {
                String[] partes = maquetadorBasico.dividir(archivoActual, punto);
                
                String nombreArchivo = prefijoSalida + "_" + numArchivo + ".txt";
                new File(nombreArchivo).delete();
                maquetadorBasico.anadirTexto(nombreArchivo, partes[0]);
                System.out.println("Creado: " + nombreArchivo);
                numArchivo++;
                
                archivoActual = prefijoSalida + "_temp.txt";
                new File(archivoActual).delete();
                maquetadorBasico.anadirTexto(archivoActual, partes[1]);
            }
            
            String nombreArchivo = prefijoSalida + "_" + numArchivo + ".txt";
            new File(nombreArchivo).delete();
            
            String resto = maquetadorBasico.extraerParrafo(archivoActual, 1, contarLineas(archivoActual));
            maquetadorBasico.anadirTexto(nombreArchivo, resto);
            System.out.println("Creado: " + nombreArchivo);
            
            new File(archivoActual).delete();
            
        } catch (Exception e) {
            System.err.println("Error al separar archivo: " + e.getMessage());
        }
    }
    
    private int contarLineas(String archivo) throws IOException {
        List<String> lineas = Files.readAllLines(Paths.get(archivo));
        return lineas.size();
    }
}
