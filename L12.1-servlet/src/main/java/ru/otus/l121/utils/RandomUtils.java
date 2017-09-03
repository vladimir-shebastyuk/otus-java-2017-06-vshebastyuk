package ru.otus.l121.utils;

import java.util.Random;

/**
 *
 */
public class RandomUtils {
    private final static String CHARACTERS = "ABCDEFGHIJKLMOPQRSTUVWXYZ";

    public static String generateString(Random rng,  int length, String characters)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public static String generateString(Random rng,  int length){
        return generateString(rng,length,CHARACTERS);
    }
}
