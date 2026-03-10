package adapter;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    
    private static final String CARPETA_PRUEBA = "prueba/";
    
    public static void main(String[] args) {
        new File(CARPETA_PRUEBA).mkdirs();
        
        System.out.println("=== DEMOSTRACIÓN DEL PATRÓN ADAPTER ===\n");
        
        MaquetadorAvanzado maquetadorAvanzado = new MaquetadorAvanzado();
        
        System.out.println("--- 1. Maquetador Básico: añadirTexto ---");
        MaquetadorBasico basico = new MaquetadorBasico();
        basico.anadirTexto(CARPETA_PRUEBA + "texto1.txt", "Línea 1\nLínea 2\nLínea 3\n");
        basico.anadirTexto(CARPETA_PRUEBA + "texto1.txt", "Línea 4\nLínea 5\n");
        System.out.println(leerArchivo(CARPETA_PRUEBA + "texto1.txt"));
        
        System.out.println("--- 2. Maquetador Básico: extraerParrafo ---");
        String parrafo = basico.extraerParrafo(CARPETA_PRUEBA + "texto1.txt", 2, 4);
        System.out.println("Párrafo extraído (líneas 2-4):\n" + parrafo);
        
        System.out.println("--- 3. Maquetador Básico: dividir ---");
        String[] partes = basico.dividir(CARPETA_PRUEBA + "texto1.txt", 3);
        System.out.println("Parte 1:\n" + partes[0]);
        System.out.println("Parte 2:\n" + partes[1]);
        
        System.out.println("\n=== OPERACIONES DEL MAQUETADOR AVANZADO (ADAPTER) ===\n");
        
        System.out.println("--- 4. Unir dos archivos ---");
        crearArchivo(CARPETA_PRUEBA + "archivo1.txt", "Contenido del archivo 1\nLínea extra\n");
        crearArchivo(CARPETA_PRUEBA + "archivo2.txt", "Contenido del archivo 2\nOtra línea\n");
        maquetadorAvanzado.unir(CARPETA_PRUEBA + "archivo1.txt", 
                               CARPETA_PRUEBA + "archivo2.txt", 
                               CARPETA_PRUEBA + "unido.txt");
        System.out.println(leerArchivo(CARPETA_PRUEBA + "unido.txt"));
        
        System.out.println("--- 5. Combinar archivos (intercalando) ---");
        crearArchivo(CARPETA_PRUEBA + "combinado1.txt", "A\nB\nC\n");
        crearArchivo(CARPETA_PRUEBA + "combinado2.txt", "1\n2\n3\n");
        maquetadorAvanzado.combinar(CARPETA_PRUEBA + "combinado1.txt", 
                                   CARPETA_PRUEBA + "combinado2.txt", 
                                   CARPETA_PRUEBA + "combinado.txt");
        System.out.println(leerArchivo(CARPETA_PRUEBA + "combinado.txt"));
        
        System.out.println("--- 6. Separar archivo en varios ---");
        System.out.println("Archivo original (6 líneas):");
        crearArchivo(CARPETA_PRUEBA + "separar.txt", "Línea 1\nLínea 2\nLínea 3\nLínea 4\nLínea 5\nLínea 6\n");
        System.out.println(leerArchivo(CARPETA_PRUEBA + "separar.txt"));
        System.out.println("Separando en puntos 2 y 4 (corte exclusivo):");
        List<Integer> puntos = Arrays.asList(2, 4);
        maquetadorAvanzado.separar(CARPETA_PRUEBA + "separar.txt", puntos, CARPETA_PRUEBA + "parte");
        System.out.println("\nContenido de los archivos generados:");
        System.out.println("parte_1.txt:\n" + leerArchivo(CARPETA_PRUEBA + "parte_1.txt"));
        System.out.println("parte_2.txt:\n" + leerArchivo(CARPETA_PRUEBA + "parte_2.txt"));
        System.out.println("parte_3.txt:\n" + leerArchivo(CARPETA_PRUEBA + "parte_3.txt"));
        
        System.out.println("\n=== FIN DE LA DEMOSTRACIÓN ===");
    }
    
    private static void crearArchivo(String nombre, String contenido) {
        try {
            Files.write(Paths.get(nombre), contenido.getBytes());
        } catch (IOException e) {
            System.err.println("Error al crear archivo: " + e.getMessage());
        }
    }
    
    private static String leerArchivo(String nombre) {
        try {
            return Files.readString(Paths.get(nombre));
        } catch (IOException e) {
            return "Error al leer archivo: " + e.getMessage();
        }
    }
}
