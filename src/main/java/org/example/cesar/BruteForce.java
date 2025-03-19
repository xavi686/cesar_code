package org.example.cesar;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import static org.example.cesar.Cipher.ALPHABET;
import static org.example.cesar.Cipher.procesar;
import static org.example.cesar.FileManager.readFile;

public class BruteForce {
    /**
     * Método de desencriptado por BruteForce
     * Descencripta el mensaje iterando la longitud del tamaño del array del alfabeto (ALPHABET). En cada iteración
     * compara las palabras del mensaje con las que se encuentra en el arreglo keywords y en el caso
     * de que alguna palabra coincida, se da terminada la descencriptación del mensaje, indicando
     * el número de clave con la cual se encriptó.
     * @param filePath contiene la ruta del archivo que se leerá para descencriptar
     */
    public static void decryptByBruteForce(String filePath) {
        StringBuilder mensaje;
        for(int i = 0; i <= ALPHABET.size(); i++){
            try {
                mensaje = readFile(filePath, ALPHABET.size() - i); //decifrar texto
                String descifrar = procesar(mensaje, ALPHABET.size() - i);
                if(!descifrar.isEmpty()){//lee la primera vez y luego lee el mensaje vacío
                    boolean claveEncontrada = false;
                    Set<String> keywords = getStrings();
                    String[] words = descifrar.split("\\W+");
                    for (String word : words) {
                        if (keywords.contains(word)) {
                            System.out.println("CLAVE DE DESCIFRADO CÉSAR ES es: " + i + "\n");
                            claveEncontrada = true;
                            System.out.println(descifrar);
                            break;
                        }
                    }
                    if(claveEncontrada) break;
                } else {
                    System.out.println("No hay información contenida en el archivo.\n");
                    break;
                }
            } catch (IOException e) {
                System.out.println("Error de lectura o escritura de archivo");
            }
        }
    }

    /**
     * Palabras claves usadas para comparación y ayudar a determinar la clave del mensaje encriptado
     * @return el arreglo de las palabras claves utilizadas para comparar con las palabras
     * del mensaje que se va encriptando con cada iteración.
     */
    @NotNull
    private static Set<String> getStrings() {
        Set<String> keywords = new HashSet<>();
        keywords.add("con");
        keywords.add("desde");
        keywords.add("las");
        keywords.add("los");
        keywords.add("para");
        keywords.add("pero");
        keywords.add("por");
        keywords.add("que");
        keywords.add("sin");
        keywords.add("una");
        return keywords;
    }
}