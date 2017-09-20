package ru.otus.l141;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 *
 */
public class DumbParallelSortTest {
    @Test
    public void parallelSort() throws Exception {
        testParallelSortForSize(10);
        testParallelSortForSize(100);
        testParallelSortForSize(100000);
    }

    protected void testParallelSortForSize(int size){
        Random rnd = new Random();

        long[] array = rnd.longs(size).toArray();

        long[] expected = Arrays.copyOf(array,array.length);

        Arrays.sort(expected);

        DumbParallelSort.parallelSort(array);

        assertArrayEquals(
                expected,
                array
        );
    }

    @Test
    public void getChunksSize() throws Exception {
        assertArrayEquals(
                new int[]{3,3,3},
                DumbParallelSort.getChunkSize(9,3)
                );

        assertArrayEquals(
                new int[]{3,3,4},
                DumbParallelSort.getChunkSize(10,3)
        );

        assertArrayEquals(
                new int[]{3,3,5},
                DumbParallelSort.getChunkSize(11,3)
        );

        assertArrayEquals(
                new int[]{2,2,2,5},
                DumbParallelSort.getChunkSize(11,4)
        );

        assertArrayEquals(
                new int[]{0,0,0,3},
                DumbParallelSort.getChunkSize(3,4)
        );
    }

    @Test
    public void mergerSortedChunksWithTwoArrays() throws Exception {
        mergerSortedChunksTestHelper(new long[]{1,4,5,8},new long[]{2,3,6,7});
        mergerSortedChunksTestHelper(new long[]{1,4,5,8,8,8},new long[]{2,3,6,7});
        mergerSortedChunksTestHelper(new long[]{1,4,4,5,5,8},new long[]{2,3,6,7});
        mergerSortedChunksTestHelper(new long[]{1,4,4,5,5,8},new long[]{2,3,6,7,9});
        mergerSortedChunksTestHelper(new long[]{1,4,5,8},new long[]{2,3,3,4,6,7});
        mergerSortedChunksTestHelper(new long[]{1,4,5,8},new long[]{2,3,3,4,6,7,9});

        //with fill offset
        mergerSortedChunksTestHelper(new long[]{1,4,5,8},new long[]{2,3,6,7},1);
        mergerSortedChunksTestHelper(new long[]{1,4,5,8},new long[]{2,3,6,7},2);
    }

    protected void mergerSortedChunksTestHelper(long[] firstArray, long[] secondArray){
        mergerSortedChunksTestHelper(firstArray, secondArray,0);
    }

    protected void mergerSortedChunksTestHelper(long[] firstArray, long[] secondArray, int fillFrom){
        long[] result = new long[fillFrom + firstArray.length + secondArray.length];

        Arrays.sort(firstArray);
        Arrays.sort(secondArray);

        long[] expected = new long[fillFrom + firstArray.length + secondArray.length];

        if(fillFrom > 0) {
            long[] fill = new long[fillFrom];
            Arrays.fill(fill,-1);
            System.arraycopy(fill, 0, expected, 0, fillFrom);
            System.arraycopy(fill, 0, result, 0, fillFrom);

        }
        System.arraycopy(firstArray, 0, expected, fillFrom, firstArray.length);
        System.arraycopy(secondArray, 0, expected, fillFrom + firstArray.length, secondArray.length);

        Arrays.sort(expected);

        DumbParallelSort.mergeSortedChunks(result,fillFrom,firstArray,secondArray);
        assertArrayEquals(
                expected,
                result
        );
    }


}