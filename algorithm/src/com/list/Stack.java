package com.list;

import java.util.ArrayList;

public class Stack<T>{
    
    public ArrayList<T> list;

    public Stack(){
        list = new ArrayList<T>();
    }

    public void push(T value){
        list.add(0,value);
    }

    public T pop(){
        T item = list.listIterator().next();
        list.remove(item);
        return item;
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }
}
