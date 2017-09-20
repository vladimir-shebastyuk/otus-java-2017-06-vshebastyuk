package ru.otus.l141;


import java.util.Random;

/**
 *
 */
public class Main {
    public static final int SIZE = 15;

    public static void main(String... args) throws Exception{
        Random rnd = new Random();

        long[] array = rnd.longs(SIZE,0,10).toArray();

        System.out.print("Before sorting:\t");

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }

        System.out.println();

        DumbParallelSort.parallelSort(array);

        System.out.print("After sorting:\t");

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }

        System.out.println();
    }
}
