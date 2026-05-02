package structures;


/**
 * K: Key (Used for sorting, must implement Comparable. e.g., LocalDate, Integer, String)
 * V: Value (Data type to be stored. e.g., Integer for Movie ID)
 */
public class MyAVLTree<K extends Comparable<? super K>, V> {

    // Inner class for Node definition
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

    
    //  Height and Balance Methods (Height and Balance)

    // Returns the height of the node, 0 if null
    private int height(Node N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    // Calculates the balance factor of the node
    private int getBalance(Node N) {
        if (N == null) {
            return 0;
        }
        return height(N.left) - height(N.right);
    }

   
    //  Rotation Logic
   
    
    // Right rotate for balancing
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights (Children first, then parent)
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate for balancing
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

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
        // BST insertion
        if (node == null) {
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        } 
        else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        } 
        else {
            // If key already exists, add value to the existing list
            node.values.add(value);
            return node;
        }

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor
        int balance = getBalance(node);

        // Handle 4 cases of imbalance using rotations
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

    
    // Range Functionality For getAllIDsReleasedInRange Function
    
    /*
     * Collects all values between start and end (exclusive of both boundaries).
     */
    public MyArrayList<V> getValuesInRange(K start, K end) {
        MyArrayList<V> result = new MyArrayList<>();
        getValuesInRange(root, start, end, result);
        return result;
    }

    private void getValuesInRange(Node node, K start, K end, MyArrayList<V> result) {
        if (node == null) {
            return;
        }

        int cmpStart = node.key.compareTo(start);
        int cmpEnd = node.key.compareTo(end);

        // If current key is greater than start, explore the left subtree
        if (cmpStart > 0) {
            getValuesInRange(node.left, start, end, result);
        }

        // If current key is within the range (start < key < end), add values to result
        if (cmpStart > 0 && cmpEnd < 0) {
            for (int i = 0; i < node.values.size(); i++) {
                result.add(node.values.get(i));
            }
        }

        // If current key is less than end, explore the right subtree
        if (cmpEnd < 0) {
            getValuesInRange(node.right, start, end, result);
        }
    }

    // ==========================================
    // Specific Value Deletion
    // ==========================================

    /**
     * 특정 키(key) 내에서 특정 값(value) 하나만 찾아 삭제합니다.
     * 값이 삭제된 후 해당 키에 남은 값이 없다면 노드 자체를 트리에서 제거합니다.
     */
    public void remove(K key, V value) {
        root = remove(root, key, value);
    }

    private Node remove(Node root, K key, V value) {
        if (root == null) {
            return null; // 삭제할 키를 찾지 못함
        }

        int cmp = key.compareTo(root.key);

        if (cmp < 0) {
            root.left = remove(root.left, key, value);
        } 
        else if (cmp > 0) {
            root.right = remove(root.right, key, value);
        } 
        else {
            // 1. 삭제할 키(날짜)를 찾은 경우
            // 해당 노드의 리스트에서 특정 영화 ID(value)를 삭제
            root.values.remove(value); 

            // 2. 만약 리스트에 아직 영화가 남아있다면? 
            // 노드를 삭제할 필요가 없으므로 그대로 반환 (트리 구조 유지)
            if (root.values.size() > 0) {
                return root;
            }

            // 3. 리스트가 텅 비었다면? (해당 날짜에 영화가 더 이상 없음)
            // 여기서부터 실제 트리 노드 삭제 로직 시작
            if ((root.left == null) || (root.right == null)) {
                Node temp;
                if (root.left != null) {
                    temp = root.left;  // 왼쪽 자식이 있으면 temp는 왼쪽 자식
                } 
                else {
                    temp = root.right; // 왼쪽이 없으면 temp는 오른쪽 자식 (오른쪽도 없으면 null이 들어감)
                }


                if (temp == null) { // 자식이 없는 경우
                    root = null;
                } 
                else { // 자식이 하나인 경우
                    root = temp; 
                }
            } 
            else {
                // 자식이 둘인 경우: 오른쪽 서브트리의 최소값(Successor)을 찾음
                Node temp = minValueNode(root.right);

                // Successor의 데이터를 현재 노드로 복사
                root.key = temp.key;
                root.values = temp.values;

                // 오른쪽 서브트리에서 복사해온 노드를 삭제
                // (이때는 전체 노드 삭제이므로 리스트가 비어있다고 가정하고 처리)
                root.right = removeNode(root.right, temp.key);
            }
        }

        // 노드가 삭제되어 null이 된 경우
        if (root == null) {
            return null;
        }

        // 4. 높이 갱신 및 균형 유지 (구조가 변경되었을 때만 의미가 있음)
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        int balance = getBalance(root);

        // LL Case
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }
        // LR Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        // RR Case
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }
        // RL Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    /**
     * 노드 구조 자체를 무조건 삭제할 때 사용하는 내부 헬퍼 메서드
     */
    private Node removeNode(Node root, K key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = removeNode(root.left, key);
        }
        else if (cmp > 0) {
            root.right = removeNode(root.right, key);
        }
        else {
            if ((root.left == null) || (root.right == null)) {
                root = (root.left != null) ? root.left : root.right;
            } 
            else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.values = temp.values;
                root.right = removeNode(root.right, temp.key);
            }
        }
        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }
}