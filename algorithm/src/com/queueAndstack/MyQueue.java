package com.queueAndstack;

import java.util.Stack;

/**
 * Leetcode232
 */
public class MyQueue {

    Stack<Integer> in = new Stack<>();
    Stack<Integer> out = new Stack<>();

    /** Initialize your data structure here. */
    public MyQueue() {

    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        in.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        in2Out();
        return out.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        in2Out();
        int val = out.pop();
        out.push(val);
        return val;
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return out.isEmpty() && in.isEmpty();
    }

    private void in2Out(){
        if(out.isEmpty()){
            while(!in.isEmpty()){
                out.push(in.pop());
            }
        }
    }
}
