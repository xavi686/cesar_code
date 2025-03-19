package org.example.cesar;

import java.io.*;
import java.nio.file.*;

import static org.example.cesar.Cipher.*;
import static org.example.cesar.Menu.*;
import static org.example.cesar.Validator.*;

/**
 * Clase para validar archivos, al momento de abrir y crear.
 */
public class FileManager {

    /**
     * Método para lectura del archivo a encriptar/descencriptar
     * @param filePath contiene la ruta del archivo a leer
     * @param key contiene el valor de la clave utilizado para encriptar y descencriptar
     * @return el mensaje el resultado del mensaje cifrado o descifrado del archivo leído
     * @throws IOException en caso de error en lectura del archivo se manejará la excepción, cargando el menú principal
     * reiniciando el programa.
     */
    public static StringBuilder readFile(String filePath, int key) throws IOException {
    Path inputPath = Paths.get(filePath);
    StringBuilder cifrado = new StringBuilder();
    //Verifica si existe el archivo
    try (InputStream inputStream = Files.newInputStream(inputPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
        String processedLine;
        while ((processedLine = reader.readLine()) != null) {
            cifrado.append(processedLine);
            cifrado.append(System.lineSeparator()); //salto de línea
        }
    } catch (IOException | NullPointerException e) {
        //e.printStackTrace(); //prueba pila excepción
        //LOGGER.log(Level.SEVERE, "Error leyendo el archivo", e); //Prueba para manejo de errores severos
        System.out.println("Error al escribir en el archivo, \n no se escribió el nombre del archivo \n no existe o no se tiene acceso. \n" + e);
        menuPrincipal();
    }
    return cifrado;
}

public static void writeFile(String content, String filePath) {
    esRutaValida(filePath);
    Path outputPath = Paths.get(filePath); // Archivo de salida
    //!Files.exists(outputPath) es redundante porque Files.newBufferedWriter:
    //- Verifica internamente si el archivo existe.
    //- Lo crea si es necesario.
    try{
        if (!Files.exists(outputPath)) {
            Files.createFile(outputPath); // Crea el archivo si no existe
        }
    } catch (IOException | InvalidPathException e) {
        //throw new RuntimeException(e);
        System.out.println("Error al escribir en archivo");
        errorEnArchivo();
    }
    try (OutputStream outputStream = Files.newOutputStream(outputPath);
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
        writer.write(content);
        writer.newLine(); // Agrega una nueva línea para cada línea procesada
    } catch (IOException | FileWriteException e) {//se generó un error al escribir la ruta y presionar espacio
        //throw new RuntimeException("Error al escribir en el archivo", e);
        System.out.println("Error al escribir en archivo");
        errorEnArchivo();
    }
}

/////////OPCIÓN PARA MANEJAR LA EXCEPCIÓN COMPROBADA AL ESCRIBIR EL ARCHIVO
    public static class FileWriteException extends RuntimeException {
        public FileWriteException(String mensaje, Throwable causa) {
            super(mensaje, causa);
        }
    }

}