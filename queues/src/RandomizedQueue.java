import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] list;
    private int size;
    private int cap;
    private static final int INIT_CAP = 8;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] idx;
        private int current;

        public RandomizedQueueIterator() {
            idx = new int[size];

            for (int i = 0; i < size; i++) {
                int r = StdRandom.uniform(i + 1);
                if (r < i) {
                    idx[i] = idx[r];
                }
                idx[r] = i;
            }
            current = 0;
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int i = idx[current];
            current++;
            return list[i];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        list = (Item[]) new Object[INIT_CAP];
        size = 0;
        cap = INIT_CAP;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size >= cap) {
            renewCap(cap * 2);
        }
        list[size] = item;
        size++;
    }

    private void renewCap(int n) {
        cap = n;
        Item[] newList = (Item[]) new Object[cap];
        for (int i = 0; i < size; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size <= cap / 4) {
            renewCap(cap / 2);
        }
        int r = StdRandom.uniform(size);
        Item item = list[r];
        list[r] = list[size - 1];
        list[size - 1] = null;
        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int r = StdRandom.uniform(size);
        return list[r];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        assert (queue.size() == 2);
        queue.dequeue();
        assert queue.size() == 1;

        RandomizedQueue<Integer> qu = new RandomizedQueue<Integer>();

        int[] lst = new int[50];
        for (int i = 0; i < 50; i++) {
            if (qu.isEmpty()) {
                qu.enqueue(i);
            } else {
                int r = StdRandom.uniform(2);
                if (r == 0) {
                    qu.enqueue(i);
                } else {
                    int x = qu.dequeue();
                    assert lst[x] == 0;
                    lst[x] = 1;
                }
            }
        }

        RandomizedQueue<Integer> qIter = new RandomizedQueue<Integer>();
        int[] lstIter = new int[50];
        for (int i = 0; i < 50; i++) {
            qIter.enqueue(i);
        }
        for (int x : qIter) {
            assert 0 <= x;
            assert x < 50;
            assert lstIter[x] == 0;
            lstIter[x] = 1;
        }
    }
}