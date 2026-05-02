package structures;

public class MyHashMap<K, V> {

    // Key와 Value를 한 쌍으로 묶어주는 내부 클래스
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

    // MyArrayList를 담는 배열 (버킷)
    private MyArrayList<Node<K, V>>[] table;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.table = new MyArrayList[capacity];
        this.size = 0;
    }

    // 해시 함수: 키를 배열의 인덱스로 변환
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        if (this.size >= this.capacity * 0.75) {
            resize();
        }

        int index = hash(key);

        // 해당 칸이 비어있으면 MyArrayList를 새로 생성 (제공된 코드 활용)
        if (table[index] == null) {
            table[index] = new MyArrayList<>();
        }

        MyArrayList<Node<K, V>> bucket = table[index];
        Node<K, V> newNode = new Node<>(key, value);

        // 이미 같은 키가 있는지 확인
        for (int i = 0; i < bucket.size(); i++) {
            Node<K, V> existingNode = bucket.get(i);
            if (existingNode.key.equals(key)) {
                //existingNode.value = value; // 값 업데이트
                return;
            }
        }

        // 중복이 없으면 추가
        bucket.add(newNode);
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        MyArrayList<Node<K, V>> bucket = table[index];

        if (bucket == null) {
            return null;
        }
        for (int i = 0; i < bucket.size(); i++) {
            Node<K, V> node = bucket.get(i);
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    // 데이터를 2배 큰 배열로 재배치하는 함수
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = this.capacity * 2;
        MyArrayList<Node<K, V>>[] newTable = new MyArrayList[newCapacity];

        // 기존 테이블의 모든 버킷을 순회
        for (int i = 0; i < this.capacity; i++) {
            MyArrayList<Node<K, V>> bucket = this.table[i];
            if (bucket != null) {
                // 버킷 안의 모든 노드를 순회하며 새로운 위치를 계산
                for (int j = 0; j < bucket.size(); j++) {
                    Node<K, V> node = bucket.get(j);
                    int newIndex = Math.abs(node.key.hashCode()) % newCapacity;

                    // 새 테이블의 해당 위치가 비어있으면 초기화
                    if (newTable[newIndex] == null) {
                        newTable[newIndex] = new MyArrayList<>();
                    }
                    newTable[newIndex].add(node);
                }
            }
        }

        // 테이블과 용량 정보를 새 것으로 교체
        this.table = newTable;
        this.capacity = newCapacity;
    }

    public V remove(K key) {
        // 1. 키를 해시 함수에 통과시켜 어느 버킷(배열 인덱스)에 있는지 찾습니다.
        int index = hash(key);
        MyArrayList<Node<K, V>> bucket = table[index];

        // 2. 해당 버킷이 비어있으면 지울 데이터가 아예 없는 것이므로 null을 반환합니다.
        if (bucket == null) {
            return null;
        }

        // 3. 버킷(MyArrayList) 안을 순회하며 똑같은 키를 가진 노드를 찾습니다.
        for (int i = 0; i < bucket.size(); i++) {
            Node<K, V> node = bucket.get(i);
        
            // 4. 지우려는 키와 일치하는 노드를 찾았다면!
            if (node.key.equals(key)) {
                // 삭제하기 전에, 사용자에게 "이 값을 지웠어"라고 알려주기 위해 값을 백업합니다.
                V removedValue = node.value;
            
                // 5. MyArrayList에서 해당 인덱스의 노드를 삭제합니다.
                bucket.remove(node); 
            
                // 6. 해시맵 전체의 데이터 개수(size)를 1 감소시킵니다.
                size--;
            
                // 7. 지워진 원래 값을 반환합니다.
                return removedValue; 
            }
        }

    // 탐색을 다 했는데도 일치하는 키가 없었다면 지울 게 없으니 null 반환
        return null;
    }

    /**
     * Returns a list of all keys present in the HashMap.
     * Use this to get all Movie IDs from the movieDB.
     */
    public MyArrayList<K> keySet() {
        MyArrayList<K> keys = new MyArrayList<>();

        // 1. Traverse all buckets in the table
        for (int i = 0; i < capacity; i++) {
            MyArrayList<Node<K, V>> bucket = table[i];
            
            // 2. If the bucket is not empty, collect all keys in it
            if (bucket != null) {
                for (int j = 0; j < bucket.size(); j++) {
                    Node<K, V> node = bucket.get(j);
                    keys.add(node.key); // Add the key to our result list
                }
            }
        }
        
        return keys;
    }

    public MyArrayList<V> valueSet() {
        MyArrayList<V> values = new MyArrayList<>();

        for (int i = 0; i < capacity; i++) {
            MyArrayList<Node<K, V>> bucket = table[i];

            if (bucket != null) {
                for (int j = 0; j < bucket.size(); j++) {
                    Node<K, V> node = bucket.get(j);
                    values.add(node.value); // Add the key to our result list
                }
            }
        }

        return values;
    }

    public int size() {
        return size;
    }
}