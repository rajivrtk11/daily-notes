import java.util.HashMap;

public class Jan08Y2025 {
    public int search(int[] nums, int target) {
        int si = 0, ei = nums.length-1;
        
        while (si < ei) {
            int mid = (si + ei)/2;
            if(nums[mid] < nums[ei]) {
                if(target >= nums[mid] && target <= nums[ei]) si = mid;
                else ei = mid;
            }
            else {
                if(target >= nums[si] && target <= nums[mid]) ei = mid;
                else si = mid;
            }
        }

        return nums[ei];
    }

    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}

class LRUCache {
    class DoublyLinkedNode {
        int key;
        int value;

        DoublyLinkedNode prev, next;

        public DoublyLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    DoublyLinkedNode head = null, tail = null;
    int size = 0;
    int capacity = 0;
    HashMap<Integer, DoublyLinkedNode> cache;

    // add last method
    private void addLast(DoublyLinkedNode node) {
        if(this.size == 0) {
            this.head = node;
            this.tail = node;
        }
        else {
            this.tail.next = node;
            node.prev = this.tail;
            this.tail = node;
        }
        this.size++;
    }

    // remove node
    private void removeNode(DoublyLinkedNode node) {
        if(this.size == 1) {
            this.head = null;
            this.tail = null;
        }
        else if(node.prev == null) { // head node
            this.head = node.next; // move head 
            this.head.prev = null; // remove backlink

            node.next = null; 
        }
        else if(node.next == null) { // tail node
            this.tail = node.prev;
            this.tail.next = null;

            node.prev = null
        }
        else {
            DoublyLinkedNode prev = node.prev;
            DoublyLinkedNode next = node.next;

            prev.next = next;
            next.prev = prev;

            // remove node links
            node.prev = null;
            node.next = null;
        }
        this.size--;
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;  
        this.cache = new HashMap<>();  
    }
    
    public int get(int key) {
        if(!cache.containsKey(key)) {
            return -1;
        }

        DoublyLinkedNode node = cache.get(key);
        int removeValue = node.value;

        removeNode(node);
        addLast(node);

        return removeValue;
    }
    
    public void put(int key, int value) {
        if(!cache.containsKey(key)) {
            DoublyLinkedNode node = new DoublyLinkedNode(key, value);
            cache.put(key, node);

            addLast(node);
            if(this.size > this.capacity) {
                node = this.head;
                removeNode(node);
                cache.remove(node.key);
            }
        }   
        else {
            int val = get(key);
            if(val != value) {
                DoublyLinkedNode node = cache.get(key);
                node.value = value;
            }
        }
    }
}
