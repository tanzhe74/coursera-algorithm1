import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * @author zhe tan
 * @start-date 04/21/2017
 * @version 1.0
 * 
 * This class creates a data type Percolation with the following API:
 * 
 * public class RandomizedQueue<Item> implements Iterable<Item> {
 *     public RandomizedQueue()                 // construct an empty randomized queue
 *     public boolean isEmpty()                 // is the queue empty?
 *     public int size()                        // return the number of items on the queue
 *     public void enqueue(Item item)           // add the item
 *     public Item dequeue()                    // remove and return a random item
 *     public Item sample()                     // return (but do not remove) a random item
 *     public Iterator<Item> iterator()         // return an independent iterator over items in random order
 *     public static void main(String[] args)   // unit testing (optional)
 * }

 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int n;
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        n = 0;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    private void resize(int newSize) {
        Item[] temp = (Item[])new Object[newSize];
        
        for(int i = 0; i < n; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }
    
    private void exchange(Item[] queue, int index1, int index2) {
        if(index1 == index2) return;
        Item temp = queue[index1];
        queue[index1] = queue[index2];
        queue[index2] = temp;
    }
    
    public void enqueue(Item item) {
        if(item == null) throw new NullPointerException();
        
        if(n == queue.length) resize(2*queue.length);
        queue[n++] = item;
    }
    
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        Item item = queue[index];
        queue[index] = null;
        exchange(queue, index, n-1);
        n--;
        if(n > 0 && n == queue.length / 4) resize(queue.length / 2);
        return item;
    }
    
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        
        int index = StdRandom.uniform(n);
        return queue[index];
    }
    
    public Iterator<Item> iterator(){
        return new RandomizedIterator();
    }
    
    private class RandomizedIterator implements Iterator<Item> {
        private Item[] randomQueue;;
        private int currentIndex;
        public RandomizedIterator() {
            randomQueue = (Item[]) new Object[n];
            for(int i = 0; i<n; i++) {
                randomQueue[i] = queue[i];
            }
            shuffle(randomQueue);
            currentIndex = 0;
        }
        
        public boolean hasNext() {
            return currentIndex < n;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item item = randomQueue[currentIndex];
            currentIndex++;
            return item;
        }
        
        private void shuffle(Item[] a) {
            for (int i = 0; i<a.length; i++) {
                int random = StdRandom.uniform(i+1);
                exchange(a, i, random);
            }
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomq = new RandomizedQueue<Integer>();
        for(int i = 0; i<10; i++) {
            randomq.enqueue(i);
        }
        for(int i = 0; i<10; i++) {
            StdOut.println(randomq.dequeue());
        }
    }
}