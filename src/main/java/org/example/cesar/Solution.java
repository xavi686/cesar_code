package org.example.cesar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

/*
Sin repeticiones
*/

public class Solution {

    public static void main(String[] args) {
        var words = new ArrayList<String>();
        Collections.addAll(words, "To become", "a", "programmer,", "you", "need", "to code",
                "and", "to code", "you", "need", "to study");

        words.stream().distinct().forEach(System.out::println);
    }


}
