import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.*;
/**
 * @author zhe tan
 * @start-date 04/21/2017
 * @version 1.0
 * 
 * This class creates a data type Percolation with the following API:
 * 
 * public class Deque<Item> implements Iterable<Item> {
 *     public Deque()                           // construct an empty deque
 *     public boolean isEmpty()                 // is the deque empty?
 *     public int size()                        // return the number of items on the deque
 *     public void addFirst(Item item)          // add the item to the front
 *     public void addLast(Item item)           // add the item to the end
 *     public Item removeFirst()                // remove and return the item from the front
 *     public Item removeLast()                 // remove and return the item from the end
 *     public Iterator<Item> iterator()         // return an iterator over items in order from front to end
 *  
 *     public static void main(String[] args)   // unit testing (optional)
 * }
 */

//implement the data structure using doubly linked list
public class Deque<Item> implements Iterable<Item> {
    //helper data type for doubly linked list
    private class Node{
        private Item element; 
        private Node next;
        private Node prev;
        
    }
    
    private int size;// number of element in the deque
    private Node pre ; // sentinel node befor first
    private Node post; // sentinel node after last

    public Deque() {
        size = 0;
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if(item == null) {
            throw new NullPointerException();
        }
        Node next = pre.next; // the current first node
        Node temp = new Node();
        temp.element = item;
        temp.next = next;
        temp.prev = pre;
        pre.next = temp;
        next.prev = temp;
        size++;

    }
    
    public void addLast(Item item) {
        if(item == null) {
            throw new NullPointerException();
        }
        Node before = post.prev;
        Node temp = new Node();
        temp.element = item;
        temp.next = post;
        temp.prev = before;
        before.next = temp;
        post.prev = temp;
        size++;
    }
    
    public Item removeFirst() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        Node temp = pre.next;
        Node tempN = temp.next;
        pre.next = tempN;
        tempN.prev = pre;
        temp.next = null;
        temp.prev = null;
        size--;
        return temp.element;
        
    }
    
    public Item removeLast() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        Node temp = post.prev;
        Node tempP = temp.prev;
        tempP.next = post;
        post.prev = tempP;
        temp.next = null;
        temp.prev = null;
        size--;
        return temp.element;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = pre.next; // Node that is returned by next();
        private int index = 0;
        public boolean hasNext() {
            return index < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item item = current.element;
            current = current.next;
            index ++;
            return item;
        }

    }
      
    public static void main(String[] args) {
        Deque<Integer> queue = new Deque<Integer>();
        //queue.addFirst(null);
        for (int i = 0; i<10; i++) {
            queue.addFirst(i);
        }        
        Iterator<Integer> iterator = queue.iterator();
        while(iterator.hasNext()) {
            int x = iterator.next();
            StdOut.println(x);
        }
    }  
}