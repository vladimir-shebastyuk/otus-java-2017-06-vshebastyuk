package ru.otus.l141;

import java.util.Arrays;

/**
 *
 */
public class DumbParallelSort {
    public static final int THREAD_COUNT = 4; // SHOULD BE POWER OF TWO

    public static void parallelSort(long[] array){
        Thread[] threads = new Thread[THREAD_COUNT];

        final long[][] chunks = splitOnChunks(array,THREAD_COUNT);

        for (int i=0;i<THREAD_COUNT;i++){
            final long[] chunk = chunks[i];

            threads[i] = new Thread(()->{
               Arrays.sort(chunk);
            });

            threads[i].start();
        }

        //wait until all threads complete work
        for (int i=0;i<THREAD_COUNT;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mergerSortedChunks(array,chunks);
    }

    protected static void mergerSortedChunks(long[] resultArray, long[][] chunks){
        if(!checkNumberIsPowerOfTwo(chunks.length)){
            throw new IllegalArgumentException("Number of chunks should be power of two");
        }

        long[][] newChunks;

        while(chunks.length > 2){
            newChunks = new long[chunks.length / 2][];

            for(int i=0;(i+1)<chunks.length;i+=2){
                newChunks[i / 2] = new long[chunks[i].length + chunks[i+1].length];

                mergeSortedChunks(newChunks[i / 2],0,chunks[i],chunks[i+1]);
            }

            chunks = newChunks;
        }

        mergeSortedChunks(resultArray,0,chunks[0],chunks[1]);
    }

    protected static void mergeSortedChunks(long[] resultArray, int fillFrom, long[] firstChunk, long[] secondChunk){
        if(resultArray.length < (fillFrom + firstChunk.length + secondChunk.length)){
            throw new IllegalArgumentException("Cannot place result into resultArray");
        }

        int firstIndex = 0;
        int secondIndex = 0;
        int resultIndex = fillFrom;

        //iterate until finish both source arrays
        while(((firstIndex) < firstChunk.length) || ((secondIndex ) < secondChunk.length)){
            //copy from the first array if: there is no element in second array
            // OR there is an element, that left in the first array,  which is smaller than current element in the second array
            if((secondIndex >= secondChunk.length) || ( (firstIndex < firstChunk.length) && (firstChunk[firstIndex] < secondChunk[secondIndex]))){
                resultArray[resultIndex++] = firstChunk[firstIndex++];
            }else{
                resultArray[resultIndex++] = secondChunk[secondIndex++];
            }
        }
    }

    protected static int[] getChunkSize(int length, int chunksCount){
        int step = length / chunksCount;
        int[] sizes = new int[chunksCount];

        for(int i=0;i<chunksCount -1;i++){
            sizes[i] = step;
        }

        sizes[chunksCount -1] = step + length % chunksCount;

        return sizes;
    }

    protected static long[][] splitOnChunks(long[] array, int chunkCount){
        int[] sizes = getChunkSize(array.length,chunkCount);

        long[][] chunks = new long[chunkCount][];

        int start = 0;

        for (int i=0;i<chunkCount;i++) {
            int end = start + sizes[i];

            chunks[i] = Arrays.copyOfRange(array,start,end);

            start = end;
        }

        return chunks;
    }

    protected static boolean checkNumberIsPowerOfTwo(int number){
        return ((number & (number -1)) == 0);
    }
}
