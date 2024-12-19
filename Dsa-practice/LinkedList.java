public class LinkedList {
    
    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    Node head;
    Node tail;
    int size = 0;

    public LinkedList(Node head, Node tail) {
        this.head = head;
        head.next = tail;
        this.tail = tail;
        this.size = 2;
    }

    // 1. add first 
    // 2. add last 

    public void addFirst(Node node) {
        node.next = head;
        this.head = node;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList(new Node(1), null);
        list.addFirst(new Node(2));
        list.addFirst(new Node(3));

        Node curr = list.head;
        while(curr != null) {
            System.out.println("Data is : "+ curr.data);
            curr = curr.next;
        }
        System.out.println("hello world");
    }
}
