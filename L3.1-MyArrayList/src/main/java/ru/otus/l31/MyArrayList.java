package ru.otus.l31;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

@SuppressWarnings("unchecked")
public class MyArrayList<E> implements List<E> {
    final int INIT_CAPACITY = 16;
    final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8; //the most safe max storage size

    Object[] storage;
    int size;


    public MyArrayList(){
        this.storage = new Object[INIT_CAPACITY];
        this.size = 0;
    }

    private boolean increaseCapacity(){
        if(this.storage.length < this.MAX_ARRAY_SIZE) {
            int newCapacity = Math.min(this.MAX_ARRAY_SIZE, this.storage.length * 2);

            this.storage = Arrays.copyOf(this.storage, newCapacity);

            return true;
        }else{
            return false;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator<E>(this);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.storage, this.size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(this.size > a.length) {
            return (T[]) Arrays.copyOf(this.storage, this.size, a.getClass());
        }else{
            System.arraycopy(this.storage,0,a,0, this.size);

            /*
            If the list fits in the specified array with room to spare (i.e., the array has more elements than the list),
            the element in the array immediately following the end of the list is set to null.
            (This is useful in determining the length of the list only if the caller knows that the list does not contain any null elements.)
             */
            if(this.size < a.length){
                a[this.size] = null;
            }

            return a;
        }
    }

    @Override
    public boolean add(E e) {
        ensureCapacity(this.size + 1);

        this.storage[this.size++] = e;

        return true;
    }

    private void ensureCapacity(int newCapacity) {
        if( newCapacity > this.storage.length){
            if(!this.increaseCapacity()){
                throw new OutOfMemoryError();
            }
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e: c) {
            this.add(e);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public E get(int index) {
        if(index >= 0 && index < this.size) {
            return (E)this.storage[index];
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E set(int index, E element) {
        if(index < this.size){
            E old = (E)this.storage[index];
            this.storage[index] = element;

            return old;
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(int index, E element) {
        ensureCapacity(this.size + 1);
        System.arraycopy(this.storage,index,this.storage, index + 1,this.size - index);
        this.storage[index] = element;
        this.size++;
    }

    @Override
    public E remove(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < this.storage.length; i++){
            if(o==null ? this.storage[i]==null : o.equals(this.storage[i])){
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>(this);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>(this, index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    private class ListIterator<E> implements java.util.ListIterator<E>{
        private MyArrayList<E> myArrayList;
        int currentPosition = -1;


        ListIterator(MyArrayList myArrayList){
            this.myArrayList = myArrayList;
        }

        ListIterator(MyArrayList myArrayList, int startPosition){
            if(startPosition >= 0 && startPosition < myArrayList.size()) {
                this.myArrayList = myArrayList;
                this.currentPosition = startPosition - 1;
            }else{
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public boolean hasNext() {
            return this.currentPosition + 1 < this.myArrayList.size();
        }

        @Override
        public E next() {
            if(this.currentPosition < this.myArrayList.size()) {
                return (E)this.myArrayList.storage[++this.currentPosition];
            }else{
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasPrevious() {
            return this.currentPosition > 0;
        }

        @Override
        public E previous() {
            if(this.currentPosition > 0) {
                return (E)this.myArrayList.storage[this.currentPosition--];
            }else{
                throw new NoSuchElementException();
            }
        }

        @Override
        public int nextIndex() {
            return this.currentPosition + 1;
        }

        @Override
        public int previousIndex() {
            return this.currentPosition - 1;
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }

        @Override
        public void set(E e) {
            this.myArrayList.set(this.currentPosition, e);
        }

        @Override
        public void add(E e) {
            throw new NotImplementedException();
        }
    }
}
