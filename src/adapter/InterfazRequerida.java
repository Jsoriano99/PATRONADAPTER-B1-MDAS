package adapter;

import java.util.List;

// Interfaz que define los métodos requeridos para la funcionalidad avanzada de maquetado
public interface InterfazRequerida {
    void unir(String archivo1, String archivo2, String archivoSalida);
    void combinar(String archivo1, String archivo2, String archivoSalida);
    void separar(String archivoEntrada, List<Integer> puntosCorte, String prefijoSalida);
}
