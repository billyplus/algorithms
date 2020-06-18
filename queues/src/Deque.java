import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private class Node {
    Item item;
    Node next;
    Node last;
  }

  private Node head;
  private Node end;
  private int size;

  private class NodeIterator implements Iterator<Item> {
    private Node current;

    public NodeIterator() {
      current = head;
    }

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item i = current.item;
      current = current.next;
      return i;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // construct an empty deque
  public Deque() {
    size = 0;
    head = null;
    end = head;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size <= 0;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null)
      throw new IllegalArgumentException();
    size++;
    Node n = new Node();
    n.item = item;
    if (head == null) {
      head = n;
      end = n;
    } else {
      n.next = head;
      head.last = n;
      head = n;
    }
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null)
      throw new IllegalArgumentException();
    size++;
    Node n = new Node();
    n.item = item;
    if (end == null) {
      head = n;
      end = n;
    } else {
      n.last = end;
      end.next = n;
      end = n;
    }
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }

    size--;
    Item ret = head.item;
    if (size == 0) {
      head = null;
      end = null;
    } else {
      head = head.next;
      head.last = null;
    }
    return ret;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    size--;
    Item ret = end.item;
    if (size == 0) {
      head = null;
      end = null;
    } else {
      end = end.last;
      end.next = null;
    }
    return ret;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new NodeIterator();
  }

  // unit testing (required)
  public static void main(String[] args) {
  }
}