package com.minigod.zero.biz.common.utils;
import com.minigod.zero.biz.common.vo.WordNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SensitiveWordFilter {
    private WordNode root;

    private final static String skipChars = " !*-+_=,，.@;:；：。、？?（）()【】[]《》<>“”\"‘’"; // 遇到这些字符就会跳过
    private final static Set<Character> skipSet = new HashSet<>(); // 遇到这些字符就会跳过

    static {
        for (char c : skipChars.toCharArray()) {
            skipSet.add(c);
        }
    }

    private static boolean skip(char c) {
        return skipSet.contains(c);
    }

    public SensitiveWordFilter() {
        root = new WordNode();
    }

    public void addSensitiveWord(String word) {
        root.insert(word);
    }

    public void addSensitiveWord(List<String> words) {
        words.forEach(word->root.insert(word));
    }

    public String filter(String text, char replaceChar) {
        StringBuilder result = new StringBuilder(text);
        int index = 0;
        while (index < result.length()) {
            char c = result.charAt(index);
            if (skip(c)) {
                index++;
                continue;
            }

            WordNode node = root;
            int start = index;
            boolean found = false;

            for (int i = index; i < result.length(); i++) {
                c = result.charAt(i);
                if (skip(c)) {
                    continue;
                }
                if (c >= 'A' && c <= 'Z') {
                    c += 32;
                }
                node=node.children.get(c);
                if (node == null) {
                    break;
                }
                if (node.isEndWord) {
                    found = true;
                    for (int j = start; j <= i; j++) {
                        result.setCharAt(j, replaceChar);
                    }
                    index = i;
                }
            }
            if (!found) {
                index++;
            }
        }
        return result.toString();
    }


	public List<String> sensitiveWordList(String text) {
		//更新修复单个字母死循环
		List<String> sensitiveWords = new ArrayList<>();
		int index = 0;
		while (index < text.length()) {
			WordNode node = root;
			int start = index;
			for (int i = index; i < text.length(); i++) {
				char c = text.charAt(i);
				if (skip(c)) {
					continue;
				}
				node = node.children.get(c);
				if (node == null) {
					break;
				}
				if (node.isEndWord) {
					sensitiveWords.add(text.substring(start, i + 1));
					index = i + 1; // 跳过已匹配的敏感词部分
					break;
				}
			}
			if (index == start) {
				index++; // 防止死循环
			}
		}
		return sensitiveWords;
	}

	public List<String> noSkipSensitiveWordList(String text) {
		//更新修复单个字母死循环
		List<String> sensitiveWords = new ArrayList<>();
		int index = 0;
		while (index < text.length()) {
			WordNode node = root;
			int start = index;
			for (int i = index; i < text.length(); i++) {
				char c = text.charAt(i);
				node = node.children.get(c);
				if (node == null) {
					break;
				}
				if (node.isEndWord) {
					sensitiveWords.add(text.substring(start, i + 1));
					index = i + 1; // 跳过已匹配的敏感词部分
					break;
				}
			}
			if (index == start) {
				index++; // 防止死循环
			}
		}
		return sensitiveWords;
	}









    public Boolean hasSensitiveWord(String text) {
        StringBuilder result = new StringBuilder(text);
        int index = 0;
        boolean found = false;
        while (index < result.length()) {
            char c = result.charAt(index);
            if (skip(c)) {
                index++;
                continue;
            }

            WordNode node = root;

            for (int i = index; i < result.length(); i++) {
                c = result.charAt(i);
                if (skip(c)) {
                    continue;
                }
                if (c >= 'A' && c <= 'Z') {
                    c += 32;
                }
                node=node.children.get(c);
                if (node == null) {
                    break;
                }
                if (node.isEndWord) {
                    found = true;
                    return found;
                }
            }
            if (!found) {
                index++;
            }
        }
        return found;
    }

//    public static void main(String[] args) {
//        SensitiveWordFilter filter = new SensitiveWordFilter();
//        filter.addSensitiveWord("dmt");
//        filter.addSensitiveWord("tmd");
//        Boolean aBoolean = filter.hasSensitiveWord("woshi");
//        System.out.println(aBoolean);
//    }

}

