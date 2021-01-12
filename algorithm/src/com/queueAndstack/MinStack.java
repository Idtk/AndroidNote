package com.queueAndstack;

import java.util.Stack;

/**
 * Leetcode155
 */
public class MinStack {

    Stack<Integer> dataStack = new Stack<>();
    Stack<Integer> minStack = new Stack<>();

    /** initialize your data structure here. */
    public MinStack() {

    }
    
    public void push(int x) {
        dataStack.push(x);
        if(minStack.isEmpty()){
            minStack.push(x);
            return;
        }

        int min = minStack.peek();
        if(min>x){
            minStack.push(x);
        }
    }
    
    public void pop() {
        int pop = dataStack.pop();
        int min = minStack.peek();
        if(pop == min){
            minStack.pop();
        }
    }
    
    public int top() {
        return dataStack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}
