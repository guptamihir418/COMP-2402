package comp2402a3;

import java.util.LinkedList;
import java.util.Stack;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Queue;
import java.io.PrintWriter;
import java.util.Random;

/**
 * An implementation of binary trees
 * 
 * @author morin
 *
 * @param <Node>
 */
public class BinaryTree<Node extends BinaryTree.BTNode<Node>> {

	public static class BTNode<Node extends BTNode<Node>> {
		public Node left;
		public Node right;
		public Node parent;
	}

	/**
	 * An extension of BTNode that you can actually instantiate.
	 */
	protected static class EndNode extends BTNode<EndNode> {
		public EndNode() {
			this.parent = this.left = this.right = null;
		}
	}

	/**
	 * Used to make a mini-factory
	 */
	protected Node sampleNode;

	/**
	 * The root of this tree
	 */
	protected Node r;

	/**
	 * This tree's "null" node
	 */
	protected Node nil;

	/**
	 * Create a new instance of this class
	 * 
	 * @param sampleNode - a sample of a node that can be used to create a new node
	 *                   in newNode()
	 * @param nil        - a node that will be used in place of null
	 */
	public BinaryTree(Node sampleNode, Node nil) {
		this.sampleNode = sampleNode;
		this.nil = nil;
		r = nil;
	}

	/**
	 * Create a new instance of this class
	 * 
	 * @param sampleNode - a sample of a node that can be used to create a new node
	 *                   in newNode()
	 */
	public BinaryTree(Node sampleNode) {
		this.sampleNode = sampleNode;
	}

	/**
	 * Allocate a new node for use in this tree
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	protected Node newNode() {
		try {
			Node u = (Node) sampleNode.getClass().getDeclaredConstructor().newInstance();
			u.parent = u.left = u.right = nil;
			return u;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Compute the depth (distance to the root) of u
	 * 
	 * @param u
	 * @return the distanct between u and the root, r
	 */
	public int depth(Node u) {
		int d = 0;
		while (u != r) {
			u = u.parent;
			d++;
		}
		return d;
	}

	/**
	 * Compute the size (number of nodes) of this tree
	 * 
	 * @warning uses recursion so could cause a stack overflow
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return size(r);
	}

	/**
	 * @return the size of the subtree rooted at u
	 */
	protected int size(Node u) {
		if (u == nil)
			return 0;
		return 1 + size(u.left) + size(u.right);
	}

	/**
	 * Compute the number of nodes in this tree without recursion
	 * 
	 * @return
	 */
	public int size2() {
		Node u = r, prev = nil, next;
		int n = 0;
		while (u != nil) {
			if (prev == u.parent) {
				n++;
				if (u.left != nil)
					next = u.left;
				else if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} else if (prev == u.left) {
				if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
		return n;
	}

	/**
	 * Compute the maximum depth of any node in this tree
	 * 
	 * @return the maximum depth of any node in this tree
	 */
	public int height() {
		return height(r);
	}

	/**
	 * @return the height of the subtree rooted at u
	 */
	protected int height(Node u) {
		if (u == nil)
			return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		toStringHelper(sb, r);
		return sb.toString();
	}

	protected void toStringHelper(StringBuilder sb, Node u) {
		if (u == null) {
			return;
		}
		sb.append('(');
		toStringHelper(sb, u.left);
		toStringHelper(sb, u.right);
		sb.append(')');
	}

	/**
	 * @ return an n-node BinaryTree that has the shape of a random binary search
	 * tree.
	 */
	public static BinaryTree<EndNode> randomBST(int n) {
		Random rand = new Random();
		EndNode sample = new EndNode();
		BinaryTree<EndNode> t = new BinaryTree<EndNode>(sample);
		t.r = randomBSTHelper(n, rand);
		return t;
	}

	protected static EndNode randomBSTHelper(int n, Random rand) {
		if (n == 0) {
			return null;
		}
		EndNode r = new EndNode();
		int ml = rand.nextInt(n);
		int mr = n - ml - 1;
		if (ml > 0) {
			r.left = randomBSTHelper(ml, rand);
			r.left.parent = r;
		}
		if (mr > 0) {
			r.right = randomBSTHelper(mr, rand);
			r.right.parent = r;
		}
		return r;
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		return r == nil;
	}

	/**
	 * Make this tree into the empty tree
	 */
	public void clear() {
		r = nil;
	}

	/**
	 * Demonstration of a recursive traversal
	 * 
	 * @param u
	 */
	public void traverse(Node u) {
		if (u == nil)
			return;
		traverse(u.left);
		traverse(u.right);
	}

	/**
	 * Demonstration of a non-recursive traversal
	 */
	public void traverse2() {
		Node u = r, prev = nil, next;
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					next = u.left;
				} else if (u.right != nil) {
					next = u.right;
				} else {
					next = u.parent;
				}
			} else if (prev == u.left) {
				if (u.right != nil) {
					next = u.right;
				} else {
					next = u.parent;
				}
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
	}

	/**
	 * Demonstration of a breadth-first traversal
	 */
	public void bfTraverse() {
		Queue<Node> q = new LinkedList<Node>();
		if (r != nil)
			q.add(r);
		while (!q.isEmpty()) {
			Node u = q.remove();
			if (u.left != nil)
				q.add(u.left);
			if (u.right != nil)
				q.add(u.right);
		}
	}

	/**
	 * Find the first node in an in-order traversal
	 * 
	 * @return the first node reported in an in-order traversal
	 */
	public Node firstNode() {
		Node w = r;
		if (w == nil)
			return nil;
		while (w.left != nil)
			w = w.left;
		return w;
	}

	/**
	 * Find the node that follows w in an in-order traversal
	 * 
	 * @param w
	 * @return the node that follows w in an in-order traversal
	 */
	public Node nextNode(Node w) {
		if (w.right != nil) {
			w = w.right;
			while (w.left != nil)
				w = w.left;
		} else {
			while (w.parent != nil && w.parent.left != w)
				w = w.parent;
			w = w.parent;
		}
		return w;
	}

	public int totalDepth() {
		// TODO: Your code goes here

		Node u = r, prev = nil, next;

		int d = 0;
		int td = 0;

		if (u == null) {
			return -1;
		}

		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					d++;
					td += d;
					next = u.left;
				} else if (u.right != nil) {
					d++;
					td += d;
					next = u.right;
				} else {
					d--;
					next = u.parent;
				}
			} else if (prev == u.left) {
				if (u.right != nil) {
					d++;
					td += d;
					next = u.right;
				} else {
					d--;
					next = u.parent;
				}
			} else {
				d--;
				next = u.parent;
			}
			prev = u;
			u = next;
		}
		return td;
	}

	public int totalLeafDepth() {
		// TODO: Your code goes here

		Node u = r, prev = nil, next;

		int d = 0;
		int td = 0;

		if (u == null) {
			return -1;
		}

		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					d++;
					next = u.left;
				} else if (u.right != nil) {
					d++;
					next = u.right;
				} else {
					td += d;
					d--;
					next = u.parent;
				}
			} else if (prev == u.left) {
				if (u.right != nil) {
					d++;
					next = u.right;
				} else {
					d--;
					next = u.parent;
				}
			} else {
				d--;
				next = u.parent;
			}
			prev = u;
			u = next;
		}
		return td;
	}

	public String bracketSequence() {
		StringBuilder sb = new StringBuilder();
		Node u = r, prev = nil, next;

		if (u == nil) {
			sb.append(".");
			return sb.toString();
		}

		while (u != nil) {
			if (prev == u.parent) {
				sb.append("(");
				if (u.left != nil) {
					next = u.left;
				} else if (u.right != nil) {
					sb.append(".");
					next = u.right;
				} else {
					sb.append("..)");
					next = u.parent;
				}
			} else if (prev == u.left) {
				if (u.right != nil) {
					next = u.right;
				} else {
					sb.append(".)");
					next = u.parent;
				}
			} else {
				sb.append(")");
				next = u.parent;
			}

			prev = u;
			u = next;
		}

		return sb.toString();
	}

	public void prettyPrint(PrintWriter w) {
		Node u = r, prev = nil, next;
		ArrayList<StringBuilder> alsb = new ArrayList<StringBuilder>();
		int row = 0;
		int col = 0;
		int maxRow = 0;
		int maxCol = 0;

		String space = " ";
		String star = "*";
		String hyphen = "-";
		String pipe = "|";

		if (r == nil) return;

		alsb.add(new StringBuilder());

		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					if (alsb.get(row).length() < col) {
						alsb.get(row).append(space.repeat(col - alsb.get(row).length()));
					}

					alsb.get(row).insert(col, star);
					row++;

					if (row > maxRow) {
						alsb.add(new StringBuilder());
						maxRow++;
					}

					if (alsb.get(row).length() < col) {
						alsb.get(row).append(space.repeat(col - alsb.get(row).length()));
					}

					alsb.get(row).insert(col, pipe);
					row++;

					if (row > maxRow) {
						alsb.add(new StringBuilder());
						maxRow++;
					}
					next = u.left;

				} else if (u.right != nil) {
					if (alsb.get(row).length() < col) {
						alsb.get(row).append(space.repeat(col - alsb.get(row).length()));
					}

					alsb.get(row).insert(col, star);
					alsb.get(row).append(hyphen);
					col++;
					col++;

					if (col > maxCol) maxCol = col;

					next = u.right;
				} else {
					if (alsb.get(row).length() < col) {
						alsb.get(row).append(space.repeat(col - alsb.get(row).length()));
					}

					alsb.get(row).insert(col, star);

					if (u.equals(u.parent.left)) {
						row--;
						row--;
					} else if (u.equals(u.parent.right)) {
						while (alsb.get(row).charAt(col - 1) != star.charAt(0)) {
							col--;
						}
						col--;
					}
					next = u.parent;
				}
			} else if (prev == u.left) {
				if (u.right != nil) {
					while (col <= maxCol) {
						alsb.get(row).append(hyphen);
						col++;
					}
					col++;

					if (col > maxCol) maxCol = col;

					next = u.right;
				} else {
					if (u.equals(u.parent.left)) {
						row--;
						row--;
					} else if (u.equals(u.parent.right)) {
						while (alsb.get(row).charAt(col - 1) != star.charAt(0)) {
							col--;
						}
						col--;
					}
					next = u.parent;
				}
			} else {
				if (u.parent != nil || u.parent != null) {
					if (u.equals(u.parent.left)) {
						row--;
						row--;
					} else if (u.equals(u.parent.right)) {
						while (alsb.get(row).charAt(col - 1) != star.charAt(0)) {
							col--;
						}
						col--;
					}
				}
				next = u.parent;
			}
			prev = u;
			u = next;
		}

		for (StringBuilder printMe : alsb) {
			w.println(printMe.toString());
		}
	}

	public static void main(String[] args) {
		// System.out.println(randomBST(30));

		BinaryTree<EndNode> bt = randomBST(10);
		System.out.println(bt);
		// System.out.println(bt.totalDepth());
		System.out.println(bt.bracketSequence());

		PrintWriter w = new PrintWriter(System.out);
		bt.prettyPrint(w);
		w.flush();

		// System.out.println(bt.prettyPrint(w));
		// System.out.println(bt.totalLeafDepth());
		// System.out.println(bt.bracketSequence());

	}

}
