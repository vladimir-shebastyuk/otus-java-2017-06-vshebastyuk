package ru.otus.l31;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Тестирование работы MyArrayListmvn  с функциями
 *      addAll(Collection<? super T> c, T... elements)
 *      static <T> void copy(List<? super T> dest, List<? extends T> src)
 *      static <T> void sort(List<T> list, Comparator<? super T> c)
 */
public class MyArrayListWithJavaUtilsCollectionsTest {

    @Test
    public void addAll() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();

        /* Add to empty list */
        Collections.addAll(myArrayList, 1,2,3,4,5);

        Assert.assertArrayEquals(myArrayList.toArray(),new Integer[]{1,2,3,4,5});

        /* Add to non-empty list */
        Collections.addAll(myArrayList, 6,7,8);

        Assert.assertArrayEquals(myArrayList.toArray(),new Integer[]{1,2,3,4,5,6,7,8});
    }

    @Test
    public void copySrcSameLengthAsDst(){
        MyArrayList<Integer> srcMyArrayList = new MyArrayList<>();
        MyArrayList<Integer> dstMyArrayList = new MyArrayList<>();

        final int ARRAY_SIZE = 10;

        srcMyArrayList.addAll(
                Stream
                        .iterate(1, n -> n + 1)
                        .limit(ARRAY_SIZE)
                        .collect(Collectors.toList())
        );
        dstMyArrayList.addAll(
                Stream
                        .iterate(ARRAY_SIZE, n -> n - 1)
                        .limit(ARRAY_SIZE)
                        .collect(Collectors.toList())
        );

        Collections.copy(dstMyArrayList,srcMyArrayList);

        Assert.assertArrayEquals(
                srcMyArrayList.toArray(),
                dstMyArrayList.toArray()
        );
    }

    @Test
    public void copyDestLengthBiggerThanSrc(){
        MyArrayList<Integer> srcMyArrayList = new MyArrayList<>();
        MyArrayList<Integer> dstMyArrayList = new MyArrayList<>();

        final int SOURCE_ARRAY_SIZE = 10;
        final int DESTINATION_ARRAY_SIZE = 15;

        srcMyArrayList.addAll(
                Stream
                        .iterate(1, n -> n + 1)
                        .limit(SOURCE_ARRAY_SIZE)
                        .collect(Collectors.toList())
        );
        dstMyArrayList.addAll(
                Stream
                        .generate(() -> 0)
                        .limit(DESTINATION_ARRAY_SIZE)
                        .collect(Collectors.toList())
        );

        Collections.copy(dstMyArrayList,srcMyArrayList);

        Object[] expectedArray = Stream
                                        .iterate(1, n -> n + 1)
                                        .map((n) -> (n <= SOURCE_ARRAY_SIZE) ? n : 0 )
                                        .limit(DESTINATION_ARRAY_SIZE)
                                        .toArray();

        Assert.assertArrayEquals(
                expectedArray,
                dstMyArrayList.toArray()
        );
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void copySrcLengthBiggerThanDst(){
        MyArrayList<Integer> srcMyArrayList = new MyArrayList<>();
        MyArrayList<Integer> dstMyArrayList = new MyArrayList<>();

        final int SOURCE_ARRAY_SIZE = 15;
        final int DESTINATION_ARRAY_SIZE = 10;

        srcMyArrayList.addAll(
                Stream
                        .iterate(1, n -> n + 1)
                        .limit(SOURCE_ARRAY_SIZE)
                        .collect(Collectors.toList())
        );
        dstMyArrayList.addAll(
                Stream
                        .generate(() -> 0)
                        .limit(DESTINATION_ARRAY_SIZE)
                        .collect(Collectors.toList())
        );

        Collections.copy(dstMyArrayList,srcMyArrayList); //expect exception
    }

    @Test
    public void sortDescOrderedValues(){
        MyArrayList<Integer> srcMyArrayList = new MyArrayList<>();
        MyArrayList<Integer> sortedMyArrayList = new MyArrayList<>();

        final int ARRAY_SIZE = 10;

        srcMyArrayList.addAll(
                Stream
                        .iterate(ARRAY_SIZE, n -> n - 1)
                        .limit(ARRAY_SIZE)
                        .collect(Collectors.toList())
        );
        sortedMyArrayList.addAll(
                Stream
                        .iterate(1, n -> n + 1)
                        .limit(ARRAY_SIZE)
                        .collect(Collectors.toList())
        );

        Collections.sort(srcMyArrayList);

        Assert.assertArrayEquals(
                srcMyArrayList.toArray()
                ,sortedMyArrayList.toArray()
        );
    }

    @Test
    public void sortRandomValues(){
        MyArrayList<Integer> srcMyArrayList = new MyArrayList<>();

        final int ARRAY_SIZE = 10;

        srcMyArrayList.addAll(
                Stream
                    .generate(()->(int)(Math.random() * Integer.MAX_VALUE))
                    .limit(ARRAY_SIZE)
                    .collect(Collectors.toList())
        );

        Collections.sort(srcMyArrayList);

        int prev = -1;
        for (Integer i: srcMyArrayList) {
            if(i > prev){
                prev = i;
            }else{
                Assert.fail("Non sorted values");
            }
        }
    }

}
