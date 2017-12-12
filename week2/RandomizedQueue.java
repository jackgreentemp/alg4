import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;

    private int N;

    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {

        validateInput(item);

        if(N == s.length)
            resize(2 * s.length);
        s[N++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for(int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    public Item dequeue() {

        validateEmpty();

        int index = N>1?StdRandom.uniform(N):(N-1);
        Item switchTemp = s[--N];//缓存数组最后一个元素
        Item item = s[index];//获取随机的元素
        s[index] = switchTemp;//将数组最后的元素交换到随机元素所在的位置
        s[N] = null;
        if(N > 0 && N == s.length/4)
            resize(s.length/2);
        return item;
    }

    public Item sample() {

        validateEmpty();

        return s[N>1?StdRandom.uniform(N):(N-1)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] count = new int[N];

        private int t = 0;
        
        public RandomizedQueueIterator() {
            for(int i = 0; i < N; i++)
                count[i] = i;
        }

        public boolean hasNext(){
            return t != N;
        }

        public void remove(){
            throw new UnsupportedOperationException("remove() is not supported");
        }

        public Item next() {

            if(!hasNext()) {
                throw new NoSuchElementException("no more items to return");
            }

            //int tempIndex = StdRandom.uniform(N);
            //StdOut.println("tempIndex = " + tempIndex);
            //while(isInArray(tempIndex)) {
                //tempIndex = StdRandom.uniform(N);
            //}
            //count[t++] = tempIndex;
            
            int tempIndex = StdRandom.uniform(N-t);
            
            int res = count[tempIndex];
            
            count[tempIndex] = count[N-1-t];
            
            count[N-1-t] = res;
            
            t++;
            
            return s[res];
        }

        private boolean isInArray(int tempIndex){
            for(int i = 0; i < t; i++) {
                if(tempIndex == count[i])
                    return true;
            }
            return false;
        }
    }

    private void validateInput(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("input is null");
        }
    }

    private void validateEmpty() {
        if(isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> testRandomizedQueue= new RandomizedQueue<String>();
        StdOut.println("after init, size = " + testRandomizedQueue.size());
        StdOut.println("after init, isEmpty() = " + testRandomizedQueue.isEmpty());

        for(int i = 0; i < 10; i++) {
            testRandomizedQueue.enqueue("" + i);
        }

        StdOut.println("after init, size = " + testRandomizedQueue.size());
        StdOut.println("after init, isEmpty() = " + testRandomizedQueue.isEmpty());

        StdOut.println("=====iterator begin=====");
        Iterator<String> mIterator = testRandomizedQueue.iterator();
        while (mIterator.hasNext()) {
            StdOut.print(" " + mIterator.next());
        }
        StdOut.println("\n=====iterator end=====");

        StdOut.println("=====sample() begin=====");
        for(int i = 0; i < 10; i++) {
            StdOut.print(" " + testRandomizedQueue.sample());
        }
        StdOut.println("\n =====sample() end=====");

        StdOut.println("=====dequeue() begin=====");
        for(int i = 0; i < 10; i++) {
            StdOut.print(" " + testRandomizedQueue.dequeue());
        }
        StdOut.println("\n size = " + testRandomizedQueue.size());
        StdOut.println("\n isEmpty = " + testRandomizedQueue.isEmpty());
        StdOut.println("\n =====dequeue() end=====");

//        StdOut.println("=====iterator begin=====");
//        Iterator<String> mIterator2 = testRandomizedQueue.iterator();
//        while (mIterator2.hasNext()) {
//            StdOut.print(" " + mIterator2.next());
//        }
//        StdOut.println("\n=====iterator end=====");
    }

}
