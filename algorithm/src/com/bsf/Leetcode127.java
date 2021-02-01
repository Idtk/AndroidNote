package com.bsf;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Leetcode127 {
    public static void exec(){
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<String>
        (Arrays.asList("hot","dot","dog","lot","log","cog"));
        int result = ladderLength(beginWord, endWord, wordList);
        System.out.println("result: "+result);
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if(!wordSet.contains(endWord)){
            return 0;
        }
        // 因首尾会加入队列，所以可删除
        wordSet.remove(beginWord);
        wordSet.remove(endWord);

        // begin
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.add(beginWord);
        visited.add(beginWord);

        // end
        Queue<String> queueEnd = new ArrayDeque<>();
        Set<String> visitedEnd = new HashSet<>();
        queueEnd.add(endWord);
        visitedEnd.add(endWord);

        int count = 0;
        while(!queue.isEmpty() && !queueEnd.isEmpty()){
            count++;

            // 比较end与begin，取较短的遍历
            if(queueEnd.size() < queue.size()){
                Queue<String> queueTmp = queue;
                queue = queueEnd;
                queueEnd = queueTmp;
                Set<String> visitedTmp = visited;
                visited = visitedEnd;
                visitedEnd = visitedTmp;
            }

            int size = queue.size();
            while(size-->0){
                String curr = queue.poll();
                char[] chars = curr.toCharArray();
                for(int i = 0; i<chars.length;i++){// 单词字符遍历
                    char record = chars[i]; // 保存
                    for(char j = 'a'; j<='z';j++){ // 转换的新单词的可能遍历
                        if(j == record){
                            continue;
                        }
                        chars[i] = j;
                        String newWord = new String(chars);
                        if(visited.contains(newWord)){
                            continue;
                        }
                        if(visitedEnd.contains(newWord)){
                            return count+1;
                        }
                        if(wordSet.contains(newWord)){
                            queue.add(newWord);
                            visited.add(newWord);
                        }
                    }
                    chars[i] = record; // 还原
                }
            }
        }
        return 0;
    }

    public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        int end = wordList.indexOf(endWord);
        if (end == -1) {
            return 0;
        }
        wordList.add(beginWord);
        
        // 从两端 BFS 遍历要用的队列
        Queue<String> queue1 = new LinkedList<>();
        Queue<String> queue2 = new LinkedList<>();
        // 两端已经遍历过的节点
        Set<String> visited1 = new HashSet<>();
        Set<String> visited2 = new HashSet<>();
        queue1.offer(beginWord);
        queue2.offer(endWord);
        visited1.add(beginWord);
        visited2.add(endWord);
        
        int count = 0;
        Set<String> allWordSet = new HashSet<>(wordList);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            count++;
            if (queue1.size() > queue2.size()) {
                Queue<String> tmp = queue1;
                queue1 = queue2;
                queue2 = tmp;
                Set<String> t = visited1;
                visited1 = visited2;
                visited2 = t;
            }
            int size1 = queue1.size();
            while (size1-- > 0) {
                String s = queue1.poll();
                char[] chars = s.toCharArray();
                for (int j = 0; j < s.length(); ++j) {
                    // 保存第j位的原始字符
                    char c0 = chars[j];
                    for (char c = 'a'; c <= 'z'; ++c) {
                        chars[j] = c;
                        String newString = new String(chars);
                        // 已经访问过了，跳过
                        if (visited1.contains(newString)) {
                            continue;
                        }
                        // 两端遍历相遇，结束遍历，返回 count
                        if (visited2.contains(newString)) {
                            return count + 1;
                        }
                        // 如果单词在列表中存在，将其添加到队列，并标记为已访问
                        if (allWordSet.contains(newString)) {
                            queue1.offer(newString);
                            visited1.add(newString);
                        }
                    }
                    // 恢复第j位的原始字符
                    chars[j] = c0;
                }
            }
        }
        return 0;
    }
}
