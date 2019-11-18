
public class BinaryTree {

	private BinaryTreeNode root;

	public BinaryTree() {
		this.root = null;
	}


	public void insert(String word) {
		root = insert(word, root);
	}

	public BinaryTreeNode getRoot() {
		return root;
	}

	public void setRoot(BinaryTreeNode root) {
		this.root = root;
	}

	private BinaryTreeNode insert(String word, BinaryTreeNode node) {

		if (node == null) {
			return new BinaryTreeNode(word);
		}
		if (word.compareTo(node.word) > 0) {
			node.right = insert(word, node.right);
		} else if (word.compareTo(node.word) < 0) {
			node.left = insert(word, node.left);
		}
		return node;

	}

	public boolean contains(String word) {
		return contains(word, root);
	}

	private boolean contains(String word, BinaryTreeNode node) {
		if (node == null) {
			return false;
		}
		if (word.compareTo(node.word) < 0) {
			return contains(word, node.left);
		} else if (word.compareTo(node.word) > 0) {
			return contains(word, node.right);
		} else {
			return true;
		}
	}
	
	public boolean search(BinaryTreeNode root, String word){
		if(root==null){
			return false;
		}
		if(word.compareTo(root.word) < 0){
			return search(root.left,word);
		}
		else if(word.compareTo(root.word) > 0){
			return search(root.right,word);
		}
		else{
			return true;
		}
		
	}

	class BinaryTreeNode {

		private String word;

		private BinaryTreeNode left;

		private BinaryTreeNode right;

		public BinaryTreeNode(String word, BinaryTreeNode left, BinaryTreeNode right) {
			this.word = word;
			this.left = left;
			this.right = right;
		}

		public BinaryTreeNode(String word) {
			this(word, null, null);
		}

		public boolean contains(String inputWord) {
			if (inputWord.equals(word))
				return true;
			BinaryTreeNode next;
			if (inputWord.compareTo(word) < 0) {
				next = left;
			} else {
				next = right;
			}
			return next != null && next.contains(inputWord);
		}

	}

}
