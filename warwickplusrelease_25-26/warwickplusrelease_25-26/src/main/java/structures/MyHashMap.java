package structures;

public class MyHashMap<K, V> {

    // (Key,Value) pair
    private class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                Node<?, ?> other = (Node<?, ?>) obj;
                return this.key.equals(other.key);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (key == null) ? 0 : key.hashCode();
        }
    }

    // array that have arraylist as element (seperate chaining)
    private MyArrayList<Node<K, V>>[] table;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.table = new MyArrayList[capacity];
        this.size = 0;
    }

    // hash code + compression function (hash function)
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        if (this.size >= this.capacity * 0.75) {  // checking load factor
            resize();
        }

        int index = hash(key);

        // make new arraylist if the bucket is null
        if (table[index] == null) {
            table[index] = new MyArrayList<>();
        }

        MyArrayList<Node<K, V>> bucket = table[index];
        Node<K, V> newNode = new Node<>(key, value);

        // check if there is same key
        for (int i = 0; i < bucket.size(); i++) {
            Node<K, V> existingNode = bucket.get(i);
            if (existingNode.key.equals(key)) {
                // ignore new value
                return;
            }
        }

        bucket.add(newNode);
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        MyArrayList<Node<K, V>> bucket = table[index];

        if (bucket == null) {
            return null;
        }
        for (int i = 0; i < bucket.size(); i++) { //checking bucket
            Node<K, V> node = bucket.get(i);
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    // preventing overloding
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = this.capacity * 2;
        MyArrayList<Node<K, V>>[] newTable = new MyArrayList[newCapacity];

        // going through current table
        for (int i = 0; i < this.capacity; i++) {
            MyArrayList<Node<K, V>> bucket = this.table[i];
            if (bucket != null) { // moving current element to new table
                for (int j = 0; j < bucket.size(); j++) {
                    Node<K, V> node = bucket.get(j);
                    int newIndex = Math.abs(node.key.hashCode()) % newCapacity;

                    if (newTable[newIndex] == null) {
                        newTable[newIndex] = new MyArrayList<>();
                    }
                    newTable[newIndex].add(node);
                }
            }
        }

        this.table = newTable;
        this.capacity = newCapacity;
    }

    public V remove(K key) {
        int index = hash(key);
        MyArrayList<Node<K, V>> bucket = table[index];

        if (bucket == null) {
            return null;
        }

        // finding if there is same key
        for (int i = 0; i < bucket.size(); i++) {
            Node<K, V> node = bucket.get(i);
        
            // if there is 
            if (node.key.equals(key)) {
                // for showing which value is removed
                V removedValue = node.value;
            
                // deleting node
                bucket.remove(node); 
        
                size--;
    
                return removedValue; 
            }
        }

    // if there is no element that  have same key
        return null;
    }

    
    // returns all keys in HashMap
    public MyArrayList<K> keySet() {
        MyArrayList<K> keys = new MyArrayList<>();

        // going through all bucket
        for (int i = 0; i < capacity; i++) {
            MyArrayList<Node<K, V>> bucket = table[i];
            
            // going through each bucket
            if (bucket != null) {
                for (int j = 0; j < bucket.size(); j++) {
                    Node<K, V> node = bucket.get(j);
                    keys.add(node.key); // Add the key to our result list
                }
            }
        }
        
        return keys;
    }

    // returns all values in HashMap
    public MyArrayList<V> valueSet() {
        MyArrayList<V> values = new MyArrayList<>();

        // going through all bucket
        for (int i = 0; i < capacity; i++) {
            MyArrayList<Node<K, V>> bucket = table[i];

            // going through each bucket
            if (bucket != null) {
                for (int j = 0; j < bucket.size(); j++) {
                    Node<K, V> node = bucket.get(j);
                    values.add(node.value); // Add the value to our result list
                }
            }
        }

        return values;
    }

    public int size() {
        return size;
    }
}