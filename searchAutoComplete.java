import java.util.*;

class AutoComplete {
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;
    }

    private final TrieNode root;

    public AutoComplete() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, k -> new TrieNode());
        }
        node.isEndOfWord = true;
    }

    public List<String> search(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            if (!node.children.containsKey(ch)) return results;
            node = node.children.get(ch);
        }
        dfs(node, new StringBuilder(prefix), results);
        return results;
    }

    private void dfs(TrieNode node, StringBuilder path, List<String> results) {
        if (node.isEndOfWord) results.add(path.toString());
        for (char ch : node.children.keySet()) {
            path.append(ch);
            dfs(node.children.get(ch), path, results);
            path.deleteCharAt(path.length() - 1);
        }
    }

    public static void main(String[] args) {
        AutoComplete ac = new AutoComplete();
        ac.insert("hello");
        ac.insert("hell");
        ac.insert("helicopter");
        ac.insert("hero");
        ac.insert("hermit");

        System.out.println(ac.search("hel")); // ["hello", "hell", "helicopter"]
        System.out.println(ac.search("her")); // ["hero", "hermit"]
        System.out.println(ac.search("hi"));  // []
    }
}
