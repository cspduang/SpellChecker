import java.util.ArrayList;
import java.util.List;

public class Trie {

	public char nodeValue;

	public Trie childs[] = null; 

	public boolean isEnd = false;

	private static final int SIZE = 53;

	public Trie(char nodeValue) {
		this.nodeValue = nodeValue;
		childs = new Trie[SIZE];
	}

	public void addWord(String word, Trie root) {

		if (word == null || word.isEmpty()) {
			return;
		}
		Trie node = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (Character.isUpperCase(c)) {
				if (node.childs[c - 'A' + 26] == null) {
					node.childs[c - 'A' + 26] = new Trie(c);
				}
				node = node.childs[c - 'A' + 26];
			} else if (Character.isLowerCase(c)) {
				if (node.childs[c - 'a'] == null) {
					node.childs[c - 'a'] = new Trie(c);
				}
				node = node.childs[c - 'a'];
			} else {
				if (node.childs[52] == null) {
					node.childs[52] = new Trie(c);
				}
				node = node.childs[52];
			}

		}
		node.isEnd = true;
	}

	public boolean search(String word, Trie root) {
		if (word == null || word.isEmpty()) {
			return false;
		}
		Trie node = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (Character.isUpperCase(c)) {
				if (node.childs[c - 'A' + 26] != null) {
					node = node.childs[c - 'A' + 26];
				} else {
					return false;
				}
			} else if (Character.isLowerCase(c)) {
				if (node.childs[c - 'a'] != null) {
					node = node.childs[c - 'a'];
				} else {
					return false;
				}
			} else {
				if (node.childs[52] != null) {
					node = node.childs[52];
				} else {
					return false;
				}
			}
		}
		return node.isEnd;
	}


	
	public void preTraverse(Trie node)
    {
        if(node!=null)
        {
            System.out.print(node.nodeValue+"-");
            for(Trie child:node.childs)
            {
                preTraverse(child);
            }
        }
    }

}
