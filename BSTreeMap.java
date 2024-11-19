import java.util.Iterator;
import java.util.Stack;

/**
 * Class that implements a binary search tree which implements the MyMap
 * interface.
 * @author Brian S. Borowski
 * @version 1.1.1 February 27, 2024
 */
public class BSTreeMap<K extends Comparable<K>, V> implements MyMap<K, V>
{
    public static final int PREORDER = 1, INORDER = 2, POSTORDER = 3;
    protected Node<K, V> root;
    protected int size;

    /**
     * Creates an empty binary search tree map.
     */
    public BSTreeMap() { }

    /**
     * Creates a binary search tree map of the given key-value pairs.
     * @param elements an array of key-value pairs
     */
    public BSTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a binary search tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created. If sorted is false,
     * the pairs will be inserted in the order they are received.
     * @param elements an array of key-value pairs
     */
    public BSTreeMap(Pair<K, V>[] elements, boolean sorted)
    {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high)
    {
        if (low > high)
        {
            return null;
        }

        int mid = low + (high - low) / 2;
        Node<K, V> current = new Node<>(pairs[mid].key, pairs[mid].value);

        Node<K, V> leftChild = createBST(pairs, low, mid - 1);
        Node<K, V> rightChild = createBST(pairs, mid + 1, high);

        if (leftChild != null)
        {
            leftChild.setParent(current);
        }
        if (rightChild != null){
            rightChild.setParent(current);
        }

        current.setLeft(leftChild);
        current.setRight(rightChild);

        size++;
        return current;
    }

    /**
     * Inserts the pairs into the tree in the order they appear in the given
     * array.
     * @param pairs the array of <K, V> pairs to insert
     */
    protected void insertElements(Pair<K, V>[] pairs)
    {
        for (Pair<K, V> pair : pairs) {
            put(pair);
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map
     */
    public int size()
    {
        return size;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns a String of the key-value pairs visited with a preorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with a preorder
     *         traversal
     */
    public String preorder()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        preorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in a preorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int preorder(Node<K, V> n, StringBuilder builder, int nodesVisited)
    {
        if (n != null)
        {
            builder.append(n).append(", ");

            int count = 1 + preorder(n.getLeft(), builder, nodesVisited) + preorder(n.getRight(), builder, nodesVisited);

            if (count == size)
            {
                int lastCommaIndex = builder.lastIndexOf(", ");
                try { builder.delete(lastCommaIndex, lastCommaIndex + 2); } catch (Exception e) {}
            }

            return count;
        }

        return 0;
    }

    /**
     * Returns a String of the key-value pairs visited with an inorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with an inorder
     *         traversal
     */
    public String inorder()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        inorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in an inorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int inorder(Node<K, V> n, StringBuilder builder, int nodesVisited)
    {
        if (n != null)
        {
            int count = 1 + inorder(n.getLeft(), builder, nodesVisited);

            builder.append(n).append(", ");

            count += inorder(n.getRight(), builder, nodesVisited);

            if (count == size)
            {
                int lastCommaIndex = builder.lastIndexOf(", ");
                try { builder.delete(lastCommaIndex, lastCommaIndex + 2); } catch (Exception e) {}
            }

            return count;
        }

        return 0;
    }

    /**
     * Returns a String of the key-value pairs visited with a postorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with a postorder
     *         traversal
     */
    public String postorder()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        postorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in a postorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int postorder(Node<K, V> n, StringBuilder builder, int nodesVisited)
    {
        if (n != null)
        {
            int count = 1 + postorder(n.getLeft(), builder, nodesVisited) + postorder(n.getRight(), builder, nodesVisited);

            builder.append(n).append(", ");

            if (count == size)
            {
                int lastCommaIndex = builder.lastIndexOf(", ");
                try { builder.delete(lastCommaIndex, lastCommaIndex + 2); } catch (Exception e) {}
            }

            return count;
        }

        return 0;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * @param  key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this
     *         map contains no mapping for the key
     */
    public V get(K key)
    {
        Node<K, V> x = iterativeSearch(key);
        return x != null ? x.value : null;
    }

    /**
     * Determines if the supplied key is found in the tree. If so, it returns a
     * reference to the Node containing the key. Otherwise, null is returned.
     * @param key key whose mapping is to be removed from the map
     * @return a reference to the Node containing the specified key
     */
    private Node<K, V> iterativeSearch(K key)
    {
        Node<K, V> current = root;
        while (current != null && !current.key.equals(key))
        {
            if (key.compareTo(current.key) < 0)
            {
                current = current.getLeft();
            }
            else if (key.compareTo(current.key) > 0)
            {
                current = current.getRight();
            }
        }
        return current;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param pair  the key-value mapping to insert into the tree
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V put(Pair<K, V> pair)
    {
        return put(pair.key, pair.value);
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V put(K key, V value)
    {
        if (root == null)
        {
            root = new Node<>(key, value);
        }
        else
        {
            Node<K, V> current = root;
            Node<K, V> previous = null;
            int childDirection = 0;

            while (current != null)
            {
                if (key.compareTo(current.key) == 0)
                {
                    V oldValue = current.value;
                    current.value = value;
                    return oldValue;
                }

                previous = current;
                if (key.compareTo(current.key) < 0)
                {
                    current = current.getLeft();
                    childDirection = -1;
                }
                else if (key.compareTo(current.key) > 0)
                {
                    current = current.getRight();
                    childDirection = 1;
                }
            }

            if (childDirection == -1)
            {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.setParent(previous);
                previous.setLeft(newNode);
            }
            if (childDirection == 1)
            {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.setParent(previous);
                previous.setRight(newNode);
            }
        }

        size++;
        return null;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key)
    {
        Node<K, V> current = iterativeSearch(key);

        if (current == null)
        {
            return null;
        }
        else if (current.getLeft() == null)
        {
            if (current.getParent() == null)
            {
                root = current.getRight();
            }
            else if (current.equals(current.getParent().getLeft()))
            {
                current.getParent().setLeft(current.getRight());
            }
            else
            {
                current.getParent().setRight(current.getRight());
            }

            if (current.getRight() != null)
            {
                current.getRight().setParent(current.getParent());
            }
        }
        else if (current.getRight() == null)
        {
            if (current.getParent() == null)
            {
                root = current.getLeft();
            }
            else if (current.equals(current.getParent().getLeft()))
            {
                current.getParent().setLeft(current.getLeft());
            }
            else
            {
                current.getParent().setRight(current.getLeft());
            }

            if (current.getLeft() != null)
            {
                current.getLeft().setParent(current.getParent());
            }
        }
        else
        {
            Node<K, V> rightMin = current.getRight();
            while (rightMin.getLeft() != null)
            {
                rightMin = rightMin.getLeft();
            }

            if (!rightMin.getParent().equals(current))
            {
                rightMin.getParent().setLeft(rightMin.getRight());
                if (rightMin.getRight() != null)
                {
                    rightMin.getRight().setParent(rightMin.getParent());
                }
                rightMin.setRight(current.getRight());
                rightMin.getRight().setParent(rightMin);
            }

            if (current.getParent() == null)
            {
                root = rightMin;
            }
            else if (current.equals(current.getParent().getLeft()))
            {
                current.getParent().setLeft(rightMin);
            }
            else
            {
                current.getParent().setRight(rightMin);
            }
            rightMin.setParent(current.getParent());
            rightMin.setLeft(current.getLeft());
            rightMin.getLeft().setParent(rightMin);
        }
        size--;
        return current.value;
    }

    /**
     * Returns a reference to the Node whose key value is the minimum key in the
     * tree.
     * @param x the Node at which to start the traversal
     * @return a reference to the Node whose key value is the minimum key in the
     *         tree
     */
    protected Node<K, V> treeMinimum(Node<K, V> x)
    {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    /**
     * Returns a String representation of the tree, where the Nodes are visited
     * with an inorder traversal.
     * @return a String representation of the tree
     */
    public String toString()
    {
        return inorder();
    }

    /**
     * Returns an ASCII drawing of the tree.
     * @return an ASCII drawing of the tree
     */
    public String toAsciiDrawing()
    {
        BinarySearchTreePrinter<K, V> printer =
                new BinarySearchTreePrinter<>();
        printer.createAsciiTree(root);
        return printer.toString();
    }

    public void printTraversal(int type)
    {
        switch (type) {
            case PREORDER -> {
                System.out.print("Preorder traversal:       ");
                System.out.println(preorder());
            }
            case INORDER -> {
                System.out.print("Inorder traversal:        ");
                System.out.println(inorder());
            }
            case POSTORDER -> {
                System.out.print("Postorder traversal:      ");
                System.out.println(postorder());
            }
        }
    }

    /**
     * Returns an iterator over the Entries in this map in the order
     * in which they appear.
     * @return an iterator over the Entries in this map
     */
    public Iterator<Entry<K, V>> iterator()
    {
        return new BinaryTreeItr();
    }

    private class BinaryTreeItr implements Iterator<Entry<K, V>>
    {
        private Node<K, V> current;
        private final Stack<Node<K, V>> parentStack = new Stack<>();

        BinaryTreeItr() {
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !parentStack.isEmpty() || current != null;
        }

        @Override
        public Entry<K, V> next() {
            while (hasNext()) {
                if (current != null) {
                    parentStack.push(current);
                    current = current.getLeft();
                } else {
                    Node<K, V> toReturn = parentStack.pop();
                    current = toReturn.getRight();
                    return toReturn;
                }
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the height of the tree. If the tree is null, the height is -1.
     * @return the height of the tree
     */
    public int height()
    {
        return height(root) ;
    }

    protected int height(Node<K, V> node)
    {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    /**
     * Returns the number of null references in the tree. Uses a recursive
     * helper method to count the null references.
     * @return the number of null references in the tree
     */
    public int nullCount() {
        return nullCount(root);
    }

    private int nullCount(Node<K, V> node)
    {
        if (node == null) {
            return 1;
        }
        return nullCount(node.getLeft()) + nullCount(node.getRight());
    }

    /**
     * Returns the sum of the levels of each non-null node in the tree starting
     * at the root.
     * For example, the tree
     *   5 <- level 0
     *  / \
     * 2   8 <- level 1
     *      \
     *       10 <- level 2
     * has sum 0 + 2(1) + 2 = 4.
     * @return the sum of the levels of each non-null node in the tree starting
     *         at the root
     */
    public int sumLevels() {
        return sumLevels(root, 0);
    }

    private int sumLevels(Node<K, V> node, int level)
    {
        if (node == null)
        {
            return 0;
        }
        return level + sumLevels(node.getLeft(), level + 1) + sumLevels(node.getRight(), level + 1);
    }

    /**
     * Returns the sum of the levels of each null node in the tree starting at
     * the root.
     * For example, the tree
     *    5 <- level 0
     *   / \
     *  2   8 <- level 1
     * / \ / \
     * * * * * 10 <- level 2
     *        / \
     *        * * <- level 3
     * has sum 3(2) + 2(3) = 12.
     * @return the sum of the levels of each null node in the tree starting at
     *         the root
     */
    public int sumNullLevels() {
        return sumNullLevels(root, 0);
    }

    private int sumNullLevels(Node<K, V> node, int level)
    {
        if (node == null)
        {
            return level;
        }
        return sumNullLevels(node.getLeft(), level + 1) + sumNullLevels(node.getRight(), level + 1);
    }

    public double successfulSearchCost() {
        return size == 0 ? 0 : 1 + (double)sumLevels() / size;
    }

    public double unsuccessfulSearchCost() {
        return (double)sumNullLevels() / nullCount();
    }
}
