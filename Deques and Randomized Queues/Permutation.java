import edu.princeton.cs.algs4.*;
import java.util.Iterator;

public class Permutation{
    public static void main(String[] args) {
        int k;
        k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String s;
        while (StdIn.hasNextLine() && !StdIn.isEmpty()){
          s = StdIn.readString();
          queue.enqueue(s);
        }
        
        Iterator<String> iter = queue.iterator();
        for(int i = 0; i< k; i++) {
            String item = iter.next();
            StdOut.println(item);
        }
    }
    
}