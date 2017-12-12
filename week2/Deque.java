import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;

    private int count;

    private class Node {
        Item item;
        Node last;
        Node next;
    }

    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return count;
    }

    public void addFirst(Item item) {

        validateInput(item);

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        
        if(isEmpty())
            last = first;
        else
            oldFirst.last = first;
        
        count++;
    }

    public void addLast(Item item) {

        validateInput(item);

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.last = oldLast;
        last.next = null;
        
        if(isEmpty())
            first = last;
        else
            oldLast.next = last;
        
        count++;
    }

    public Item removeFirst() {

        validateEmpty();

        Item item = first.item;
        first = first.next;
        
        if(first != null)
            first.last = null;
        else
            last = null;
        
        count--;
        return item;
    }

    public Item removeLast() {

        validateEmpty();

        Item item = last.item;
        last = last.last;
        
        if(last != null)
            last.next = null;
        else
            first = null;
        
        count--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }

        public Item next() {

            if(current == null) {
                throw new NoSuchElementException("Deque is empty");
            }

            Item item = current.item;
            current = current.next;
            return item;
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
        Deque<String> testDeque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            
            if(s.equals("-h"))
                StdOut.println(testDeque.removeFirst());
            else if(s.equals("-l"))
                StdOut.println(testDeque.removeLast());
            else
                testDeque.addLast(s);
            
            StdOut.println("\n size = " + testDeque.size());
            
            Iterator<String> i = testDeque.iterator();
            while(i.hasNext()) {
                StdOut.print(" " + i.next());
            }
        }

    }

}
