package ru.otus.l31;

import org.junit.Assert;
import org.junit.Test;

import java.util.ListIterator;

/**
 * Тестирование работы основных функций MyArrayList
 */
public class MyArrayListTest {

    /**
     * Fills MyArrayList with numbers which start from 0 to arraySize - 1
     * @param arraySize
     * @return MyArrayList(){0,1,2, .. , arraySize -1}
     */
    private MyArrayList<Integer> createArrayFilledWithNumbers(int arraySize){
        MyArrayList<Integer> result = new MyArrayList<>();

        for(int i = 0; i < arraySize;i++){
            result.add(i);
        }

        return result;
    }

    @Test
    public void size() throws Exception {
        MyArrayList<Integer> myArrayList =
                new MyArrayList<>();

        myArrayList.add(1);
        Assert.assertEquals(1,myArrayList.size());
        myArrayList.add(1);
        Assert.assertEquals(2,myArrayList.size());
        myArrayList.add(1);
        Assert.assertEquals(3,myArrayList.size());

        myArrayList = this.createArrayFilledWithNumbers(100);
        Assert.assertEquals(100,myArrayList.size());
    }

    @Test
    public void isEmpty() throws Exception {
        MyArrayList<Integer> myArrayList =
                new MyArrayList<>();
        Assert.assertTrue(myArrayList.isEmpty());

        myArrayList.add(1);
        Assert.assertFalse(myArrayList.isEmpty());
    }

    @Test
    public void add(){
        MyArrayList<Integer> myArrayList =
                new MyArrayList<>();

        myArrayList.add(1);
        Assert.assertEquals(1, (int)myArrayList.get(0));
        myArrayList.add(3);
        Assert.assertEquals(3, (int)myArrayList.get(1));
        myArrayList.add(1,2);
        Assert.assertEquals(2, (int)myArrayList.get(1));
        Assert.assertEquals(3, (int)myArrayList.get(2));
    }

    @Test
    public void toArray(){
        MyArrayList<Integer> myArrayList =
                createArrayFilledWithNumbers(5);

        /* smaller size */
        Integer[] array = myArrayList.toArray(new Integer[0]);

        Assert.assertArrayEquals(
                new Integer[]{0,1,2,3,4},
                array
        );

        /* equals size */
        array = new Integer[5];
        myArrayList.toArray(array);

        Assert.assertArrayEquals(
                new Integer[]{0,1,2,3,4},
                array
        );

        /* dest array bigger than source */
        //specs requires to set null after last copied element
        array = new Integer[]{0,0,0,0,0,0};
        myArrayList.toArray(array);

        Assert.assertArrayEquals(
                new Integer[]{0,1,2,3,4,null},
                array
        );
    }

    @Test
    public void listIteratorNavigation() throws Exception {
        MyArrayList<Integer> myArrayList;

        final int listSize = 100;

        myArrayList = this.createArrayFilledWithNumbers(listSize);

        ListIterator<Integer> listItr = myArrayList.listIterator();

        for(int i = 0;i < listSize; i++){
            if(i == 0){
                Assert.assertFalse(listItr.hasPrevious());
            }

            if(i < listSize){
                Assert.assertTrue(listItr.hasNext());
                Assert.assertEquals((int)i, listItr.nextIndex());
            }else{
                Assert.assertFalse(listItr.hasNext());
                Assert.assertEquals(listSize, listItr.nextIndex());
            }

            Assert.assertEquals((int)i, (int)listItr.next());;

            if(i > 0) {
                Assert.assertTrue(listItr.hasPrevious());
                Assert.assertEquals((int) i - 1, listItr.previousIndex());
            }
        }
    }

}