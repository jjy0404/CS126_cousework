package structures;


/*
 * K: Key (Used for sorting, must implement Comparable) ex: Integer
 * V: Value (Data type to be stored) ex: Integer, Movie, CollectionInfo...
 */
public class MyAVLTree<K extends Comparable<? super K>, V> {

    //  Node definition
    private class Node {
        K key;
        MyArrayList<V> values; // List to handle multiple values for the same key
        Node left;
        Node right;
        int height;

        Node(K key, V value) {
            this.key = key;
            this.values = new MyArrayList<>();
            this.values.add(value);
            this.height = 1;
        }
    }

    private Node root;

    
    //  Height and Balance Methods 

    // Returns the height of the node, 0 if null
    private int height(Node n) {
        if (n == null) {
            return 0;
        }
        return n.height;
    }

    // Calculates the balance of the node
    private int getBalance(Node n) {
        if (n == null) {
            return 0;
        }
        return height(n.left) - height(n.right);
    }

   
    //  Rotation method
   
    
    // Right rotate for balancing
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node z = x.right;

        // rotation
        x.right = y;
        y.left = z;

        // Update heights (Children first, then parent)
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate for balancing
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node z = y.left;

        // rotation
        y.left = x;
        x.right = z;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

   
    // Insertion
   
    
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, K key, V value) {
        // smae as BST insertion
        if (node == null) {
            return new Node(key, value);
        }

        int compare = key.compareTo(node.key);

        if (compare < 0) {  // key smaller than node.key
            node.left = insert(node.left, key, value);
        } 
        else if (compare > 0) {  // key bigger than node.key
            node.right = insert(node.right, key, value);
        } 
        else {  // key = node.key
            // If key already exists, add value to the existing list
            node.values.add(value);
            return node;
        }

        // Update height of this parent node 
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // check balance
        int balance = getBalance(node);

        // 4 cases of imbalance
        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    
    // method for getAllIDsReleasedInRange() method
    
    
    // Collects all values between start and end
     
    public MyArrayList<V> getValuesInRange(K start, K end) {
        MyArrayList<V> result = new MyArrayList<>();
        getValuesInRange(root, start, end, result);  //starting recurrsion
        return result;
    }

    private void getValuesInRange(Node node, K start, K end, MyArrayList<V> result) {
        if (node == null) {  // Base case
            return;
        }

        int compareStart = node.key.compareTo(start);
        int compareEnd = node.key.compareTo(end);

        // node.key is bigger than start
        if (compareStart > 0) {
            getValuesInRange(node.left, start, end, result);  //going to left
        }

        // If node.key is in the range (start < key < end) then add values to result
        if (compareStart > 0 && compareEnd < 0) {
            for (int i = 0; i < node.values.size(); i++) {
                result.add(node.values.get(i));
            }
        }

        // If node.key is smaller than end
        if (compareEnd < 0) {
            getValuesInRange(node.right, start, end, result);  //going to right
        }
    }


    // method for getMostRatedMovies(), getMostRatedUsers(), getTopAverageRatedMovies(), getMostCastCredits()

    public MyArrayList<V> getTopN(int n) {
        MyArrayList<V> result = new MyArrayList<>();
        getTopNHelper(root, n, result);  // starting recursion
        return result;
    }   

    private void getTopNHelper(Node node, int n, MyArrayList<V> result) {
        if (node == null || result.size() >= n) { // base case
            return;
        }

        // going right to get bigger key
        getTopNHelper(node.right, n, result);

        // currently at very right node
        // add values to result
        if (result.size() < n) {
            MyArrayList<V> currentValues = node.values;
            for (int i = 0; i < currentValues.size(); i++) {
                if (result.size() < n) {
                    result.add(currentValues.get(i));
                } 
                else {
                    break;
                }
            }
        }

        // going to left child to get next largest keys
        if (result.size() < n) {
            getTopNHelper(node.left, n, result);
        }
    }

   
    // Deleting specific (key, value) pair
   
    public void remove(K key, V value) {
        root = remove(root, key, value);
    }

    private Node remove(Node node, K key, V value) {
        if (node == null) {
            return null; // can not find (key, value) pair
        }

        int compare = key.compareTo(node.key);

        if (compare < 0) {
            node.left = remove(node.left, key, value);  
        } 
        else if (compare > 0) {
            node.right = remove(node.right, key, value);
        } 
        else {
            // deleting specific value
            node.values.remove(value); 

            // if there still exist other value 
            if (node.values.size() > 0) {
                return node;
            }

            // no value exist in that node, delete the node
            if ((node.left == null) || (node.right == null)) {
                Node temp;
                if (node.left != null) {
                    temp = node.left;  
                } 
                else {
                    temp = node.right;
                }


                if (temp == null) { // when node does not have children
                    node = null;
                } 
                else { // when node have one children
                    node = temp; 
                }
            } 
            else {
                // when node have two children 
                Node temp = minValueNode(node.right);

                // change node to temp
                node.key = temp.key;
                node.values = temp.values;

                // remove temp
                node.right = removeNode(node.right, temp.key);
            }
        }

        // when node didnt had any child
        if (node == null) {
            return null;
        }

        // balancing, since structure changed
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // LeftLeft Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        // LeftRight Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RightRight Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        // RightLeft Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // method deleting total node
    private Node removeNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = removeNode(node.left, key);
        }
        else if (compare > 0) {
            node.right = removeNode(node.right, key);
        }
        else {
            if ((node.left == null) || (node.right == null)) {
                if (node.left != null) {
                    node = node.left; 
                } 
                else {
                    node = node.right;
                }
            } 
            else {
                Node temp = minValueNode(node.right);
                node.key = temp.key;
                node.values = temp.values;
                node.right = removeNode(node.right, temp.key);
            }
        }
        return node;
    }

    // method that gives very left
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }
}