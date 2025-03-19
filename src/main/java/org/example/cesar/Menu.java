package org.example.cesar;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

import static org.example.cesar.BruteForce.decryptByBruteForce;
import static org.example.cesar.Cipher.ALFABETO;
import static org.example.cesar.Cipher.procesar;
import static org.example.cesar.FileManager.readFile;
import static org.example.cesar.FileManager.writeFile;
import static org.example.cesar.Validator.*;

//pasos para trabajar con git en intellij
//https://www.youtube.com/watch?v=G5tvUmPe7ok

/**
 * Clase principal que muestra el menú de opciones para cifrado y descifrado mediante código César.
 * **/
public class Menu {
    /**
     * Ruta del archivo que tiene el mensaje a encriptar
     **/
    private static Scanner entrada;

    public static Scanner getEntrada() {
        if (entrada == null) {
            entrada = new Scanner(System.in);
        }
        return entrada;
    }

    /**
     * Menú de opciones
     * 1. Cifrado de mensaje contenido en un archivo de texto, digitando un número entero para encriptar el mensaje.
     * 2. Descifrado de un archivo de texto, a partir de un número entero conocido, para desencriptar el mensaje real.
     * 3. Descifrado mediante método de Fuerza Bruta, mediante iteración de números del 1 al 26, hasta descubrir
     * el mensaje real, mediante comparación de palabras contenidos en el mensaje, con palabras
     * almacenadas en un diccionario de datos.
     */
    public static void menuPrincipal() {
        System.out.println("Menú código César: ");
        System.out.println("1. Cifrar");
        System.out.println("2. Descifrar");
        System.out.println("3. Descifrar por método Brute Force");
        System.out.println("4. Salir");
        System.out.println("Seleccione una opción del menú: ");
    }

    /**
     * Entrada al menu del programa.
     * Muestra el menú de opciones y captura las entradas del usuario para ejecutar las acciones correspondientes.
     */
    public static void iniciar() {
        menuPrincipal();
        int opcion = esNumero();
        int key;
        StringBuilder mensaje;
        String modo;
        do {
            switch (opcion) {
                case 1: //Cifrar el mensaje contenido en un archivo a partir de la clave de desplazamiento proporcionada
                    modo = "Cifrar";
                    validToOpen(modo);
                    key = toValid(modo);
                    try {
                        mensaje = readFile(getRutaArchivo(), key);
                        if(!mensaje.isEmpty()){
                            while(true){
                                System.out.println("Ingresa la ruta donde quiere escribir el archivo: ");
                                setRutaArchivo(getEntrada().nextLine().trim());
                                if (esRutaValida(getRutaArchivo()) && callValidExtension()) break;
                            }
                            String codificado = procesar(mensaje, key);
                            writeFile(procesar(mensaje, key), getRutaArchivo());
                            System.out.println("\n" + "Mensaje: " + "\n" + codificado + "\n");
                            opcion = 0;
                        } else{
                            System.out.println("No hay información contenida en el archivo.\n");
                        }
                    } catch (IOException | InvalidPathException e){//| FileManager.FileWriteException e) {
                        System.out.println("Error al leer o escribir en el archivo");
                        System.exit(0);
                    }
                    break;
                case 2: //Decifrar el mensaje contenido en un archivo a partir de una clave de desplazamiento conocida
                    modo = "Descifrar";
                    validToOpen(modo);
                    key = toValid(modo);
                    try {//try de descifrar con clave conocida
                        mensaje = readFile(getRutaArchivo().trim(), (ALFABETO.length-1) - (key%(ALFABETO.length-1))); //decifrar
                        String codificado = procesar(mensaje, (ALFABETO.length-1) - (key%(ALFABETO.length-1)));
                        if(!mensaje.isEmpty()){
                            System.out.println("\n" + "Mensaje: " + "\n" + codificado);
                        } else {
                            System.out.println("No hay información contenida en el archivo.\n");
                        }
                    } catch (IOException | InvalidPathException | NullPointerException e) {
                        System.out.println("Error al leer o escribir en el archivo");
                    }
                    opcion = 0;
                    break;
                case 3:
                    //Ejecuta el contenido en la clase BruteForce.java, para descifrar el mensaje contenido en una
                    //ubicación específica del archivo
                    validToOpen("Descifrar por método Fuerza Bruta");
                    decryptByBruteForce(getRutaArchivo().trim());
                    opcion = 0;
                    break;
                default: //Mostrar el menú si el número ingresado no está entre el intervalo de selección
                    menuPrincipal();
                    opcion = esNumero();
            }
        } while(!(opcion==4)); //Termina el programa al seleccionar la opción del menú correspondiente
        getEntrada().close();
        System.out.println("Fin del programa.");
    }
}