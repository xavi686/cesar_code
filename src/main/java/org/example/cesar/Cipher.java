package org.example.cesar;

import java.io.*;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Cipher {

    /**
     * Arreglo de tipo char que contiene las letras del alfabeto en español incluyendo la letra ñ
     * y algunos carácteres especiales utilizados en escritura
     */
    public static final char[] ALFABETO = {'ª',
            '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','ñ','o','p','q','r','s',
            't','u','v','w','x','y','z','á','é','í',
            'ó','ú','A','B','C','D','E','F','G','H',
            'I','J','K','L','M','N','Ñ','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z','Á',
            'É','Í','Ó','Ú','Ü',' ','.',','};

    /**
     * El método convertirAHashmap se encarga de crear el arreglo tipo HashMap del alfabeto y los carácteres especiales
     * a partir del arreglo tipo char, para la identificar rápidamente los caracteres, para la codificación y
     * decodificación del mensaje.
     * @return alfabetoMap que contiene el diccionario del alfabeto asociado a un número.
     */
    public static HashMap<Character, Integer> convertirAHashMap() {
        HashMap<Character, Integer> alfabetoMap = new HashMap<>();
        for (int i = 1; i < ALFABETO.length; i++) {
            alfabetoMap.put(ALFABETO[i], i);
        }
        return alfabetoMap;
    }

    /**
     * Arreglo de tipo HashMap que contendrá las letras del alfabeto en español como clave y un entero asociado al número
     * de posición correspondiente
     */
    public static final HashMap<Character,Integer> ALPHABET = convertirAHashMap();

    /**
     * Función que toma 2 argumentos (letra y clave ingresada) y devuelve el número de la nueva
     * posición de las letras para cifrar/descifrar el mensaje, asignando las nuevas letras correspondiente
     * a la posición del arreglo creado del alfabeto
     */
    private static final BiFunction<Character, Integer, Integer> desplazarLetra =
            (letra, key) -> (ALPHABET.get(letra) + (key % (ALFABETO.length-1)) > (ALFABETO.length-1))
                    ? ALPHABET.get(letra) + (key % (ALFABETO.length-1)) - (ALFABETO.length-1)
                    : ALPHABET.get(letra) + (key % (ALFABETO.length-1));

    /**
     * Proceso que se encarga de cifrar/descifrar el mensaje
     *
     * @param key variable tipo entero que contiene el número de clave de desplazamiento de las letras en el mensaje
     */
    public static String procesar(StringBuilder cifrado, int key){
        //Verifica si existe el archivo
        StringBuilder cifrando = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new StringReader(cifrado.toString()));
            String processedLine;
            while ((processedLine = reader.readLine()) != null) {
                if(key % (ALFABETO.length-1) != 0) {//El problema está acá
                    for (char character : processedLine.toCharArray()) {
                        int nuevaLetra;
                        String permitidos = new String(ALFABETO);
                        if (permitidos.indexOf(character) == -1) {
                            //System.out.println("El carácter '" + character + "' no está dentro del arreglo permitido.");
                            //return;
                        } else {
                            // Desplazamiento para carácteres especiales
                            nuevaLetra = desplazarLetra.apply(character, key);
                            character = ALFABETO[nuevaLetra]; //Verificación
                        }
                        cifrando.append(character);
                    }
                    cifrando.append(System.lineSeparator());
                }
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            //throw new RuntimeException(e);
            System.out.println("Error al leer el archivo o clave fuera de rango.");
            System.exit(0);
        }
        if(cifrando.toString().equals(""))
            return cifrado.toString();
        else
            return cifrando.toString();
    }
}

