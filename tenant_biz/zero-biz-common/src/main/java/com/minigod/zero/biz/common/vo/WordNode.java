package com.minigod.zero.biz.common.vo;

import java.util.HashMap;

public class WordNode {
    public HashMap<Character, WordNode> children;
    public boolean isEndWord;

    public WordNode() {
        this.children = new HashMap<>();
        this.isEndWord = false;
    }

    public void insert(String word) {
        WordNode node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.children.put(c, new WordNode());
            }
            node = node.children.get(c);
        }
        node.isEndWord = true;
    }

    /**
     * 用于搜索指定的单词是否存在于敏感词库中
     *
     * @param word
     * @return
     */
    public boolean search(String word) {
        WordNode node = searchPrefix(word);
        return node != null && node.isEndWord;
    }

    /**
     * 用于检查是否存在以指定前缀开头的敏感词
     *
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    /**
     * 用于搜索以指定前缀开头的敏感词的节点
     *
     * @param prefix
     * @return
     */
    private WordNode searchPrefix(String prefix) {
        WordNode node = this;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }
        return node;
    }
}
