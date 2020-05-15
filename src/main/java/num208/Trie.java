package num208;

public class Trie {

    private TrieNode trie;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        trie = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode current = trie;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (current.children[i]==null){
                current.children[i] = new TrieNode();
            }
            current = current.children[i];
        }
        current.isWord = true;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        char[] chars = word.toCharArray();
        TrieNode node = trie;
        for (char aChar : chars) {
            if (node.children[aChar - 'a'] == null) {
                return false;
            }
            node = node.children[aChar - 'a'];
        }
        return node.isWord;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        char[] chars = prefix.toCharArray();
        TrieNode node = trie;
        for (char aChar : chars) {
            if (node.children[aChar - 'a'] == null) {
                return false;
            }
            node = node.children[aChar - 'a'];
        }
        return true;
    }
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false;
    }

}
