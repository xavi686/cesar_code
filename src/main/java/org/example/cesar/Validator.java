package org.example.cesar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;

import static org.example.cesar.Cipher.ALFABETO;
import static org.example.cesar.Menu.*;

/**
 * Clase para validar el número de clave de desplazamiento de las letras del mensaje dentro de un rango de numeración
 * válida, acorde con el alfabeto en Español, y transformar el mensaje tanto para ocultarlo como para descifrarlo.
 **/
public class Validator {

    /**
     * Extensiones con las que se desea trabajar.
     */
    private static final String[] validExtensions = {"txt", "doc"};

    /**
     * Variable que contendrá la ruta del archivo a leer y/o escribir
     */
    public static File archivoAbrir(String path) {
        return new File(path);
    }

    private static String rutaArchivo;

    public static String getRutaArchivo() {
        return rutaArchivo;
    }

    public static void setRutaArchivo(String rutaArchivo) {
        Validator.rutaArchivo = rutaArchivo;
    }

    /**
     * El metodo esNumero valida que se ingrese un número entero, procesando datos no deseados hasta que se ingrese
     * una opción del menú entre 1 y 4.
     * getEntrada() variable de tipo Scanner para capturar la entrada de datos del número de opción ql usuario.
     * @return opcion regresa un número entero válido, correspondiente a una de las opciones del menú.
     */
//    public static int esNumero(@org.jetbrains.annotations.NotNull Scanner entrada) {
    public static int esNumero() {
        int opcion;
        while (true) {
            try {
                // Verifica si la entrada es un número entero
                String option = getEntrada().nextLine().trim();
                opcion = checkNumber(option);
                if (opcion > 0 && opcion < 5) {
                    break;  // Sale del bucle si el número es válido
                }
                else{
                    System.out.println("Ingrese un número válido.");
                    menuPrincipal();
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Debes ingresar un número entero.");
                getEntrada().next(); // Descarta el dato no valido de la entrada
                menuPrincipal();
            }
        }
        return opcion;
    }

    /**
     * Verifica si la entrada ingresada por el usuario es un número entero
     * @param valor variable que contiene un dato ingresado por el usario
     * @return num retorna un número entero válido luego de su verificación
     */
    public static int checkNumber(@NotNull String valor){
        int num = 0;
        try{
            if(valor.matches("\\d+")) {
                num = Integer.parseInt(valor);
            }
        } catch (NumberFormatException e){
            System.out.println("Se excede el límite del valor numérico definido.");
        }
        return num;
    }

    /**
     * Valida que el número a ingresar esté dentro de un rango de numeración valida, acorde a la posición de las
     * letras del alfabeto en Español, que incluye la letra Ñ.
     * @return clave retorna un número entero si cumple las condiciones
     */

    public static int toValid(String modo){
        int clave;
        while(true){
            System.out.println("Digite un número válido para la clave César para " + modo +":");
            try{
                // Verifica si la entrada es un número entero
                String dato = getEntrada().nextLine().trim();
                clave = checkNumber(dato);
                if(isAValidKey(clave)) {
                    break;  // Sale del bucle si el número es válido
                }
            } catch(InputMismatchException e){
                System.out.println("Entrada no válida. Debes ingresar un número entero, no negativo.");
                getEntrada().next(); // Descarta la entrada no válida
            }
        }
        return clave;
    }

    /**
     *
     * @param key variable tipo entero que contiene el número de clave de desplazamiento de las letras en el mensaje
     * @return true/false retorna verdadero o falso si el número de la clave está o no dentro de un rango específico
     */
    public static boolean isAValidKey(int key) {
        int limite = Integer.MAX_VALUE;
        if(key > 0 && key < limite && (key % (ALFABETO.length-1) != 0)){
            return true;
        } else{
            System.out.println("Digite un número de clave válido, dentro del valor límite de 1 a " + limite +".");
            return false;
        }
    }

    /**
     * Valida que la extensión del archivo que se crea, exista o sea valido dentro del listado de excepciones
     * en el arreglo validExtensions
     * @param nombreArchivo variable que contiene la ruta con el nombre del archivo con la extensión creada
     * @return false si la extensión no es válida o no existe
     */
    private static boolean validExtension(String nombreArchivo) {
        int indicePunto = nombreArchivo.lastIndexOf(".");
        if (indicePunto == -1 || indicePunto == nombreArchivo.length() - 1){
            return false; // No tiene extensión válida
        }
        String extension = nombreArchivo.substring(indicePunto + 1).toLowerCase();
        for (String ext : validExtensions) {
            if (extension.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    public static boolean callValidExtension(){
        return validExtension(rutaArchivo);
    }

    /**
     * Método para validar ruta y abrir un archivo para leer el mensaje, ya sea para cifrado o descifrado
     */
    public static void validToOpen(String modo){
        System.out.println("Ingresa una ruta válida para abrir el archivo para " + modo +":");
        //Ejemplo de rutas:
        //../cesar/src/entrada.txt = dentro de la carpeta src
        //../entrada.txt =  dentro de la carpeta Proyecto Modulo 1
        setRutaArchivo(getEntrada().nextLine().trim());
        while (!callValidExtension()){
            System.out.println("Archivo sin extensión o extensión no valida");
            System.out.println("Ingresa la ruta donde quiere escribir el archivo: ");
            setRutaArchivo(getEntrada().nextLine().trim());
        }
        while(!archivoAbrir(getRutaArchivo()).exists()) {
            errorEnArchivo();
        }
    }

    /**
     * Método que muestra un mensaje si la ruta del archivo no es válida
     */
    public static void errorEnArchivo(){
        System.out.println("El archivo no existe en la ruta proporcionada.");
        System.out.println("Ingresa una ruta válida para abrir el archivo: ");
        setRutaArchivo(getEntrada().nextLine().trim());
    }

    /**
     *
     * @param filePath contiene la ruta para ser validada
     * @return true si la ruta existe o se creó, o false si no es posible crearla
     */
    public static boolean esRutaValida(String filePath) {
        try {
            Path path = Paths.get(filePath);
            // Verifica si el directorio padre existe o puede crearse
            Path parentDir = path.getParent();
            if (parentDir != null && Files.notExists(parentDir)) {
                System.out.println("La carpeta no existe. Creándola...");
                Files.createDirectories(parentDir);
            }
            return true;
        } catch (InvalidPathException e) {
            System.out.println("Ruta inválida: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("No se pudo crear la carpeta: " + e.getMessage());
        }
        return false;
    }
}
