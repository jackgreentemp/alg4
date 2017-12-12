import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {

        RandomizedQueue<String> mRandomizedQueue = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);
        
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            //StdOut.println("s = " + s);
            mRandomizedQueue.enqueue(s);
        }

        int n = mRandomizedQueue.size();

        if(k<0 || k>n){
            throw new IllegalArgumentException("k should between 0 and n");
        }

        for(int i = 0; i < k; i++)
            StdOut.println(mRandomizedQueue.dequeue());
    }
}
