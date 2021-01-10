package com.tree;

import java.util.List;

/**
 * Leetcode208
 */
class Trie {

    boolean isEnd = false;
    Trie[] next = new Trie[26];

    /** Initialize your data structure here. */
    public Trie() {

    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        Trie root = this;
        char[] w= word.toCharArray();
        for(int i=0; i < w.length; i++){
            Trie c = root.next[w[i]-'a'];
            if(c == null){
                c = new Trie();
            }
            root = c;
        }
        root.isEnd = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        char[] w= word.toCharArray();
        Trie root = this;
        for(int i=0; i < w.length; i++){
            Trie c = root.next[w[i]-'a'];
            if(c == null){
                c = new Trie();
            }
            root = c;
        }
        return root.isEnd;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        char[] w= prefix.toCharArray();
        Trie root = this;
        for(int i=0; i < w.length; i++){
            Trie c = root.next[w[i]-'a'];
            if(c == null){
                return false;
            }
            root = c;
        }
        return true;
    }
}
