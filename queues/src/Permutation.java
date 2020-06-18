import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
  public static void main(String[] args) {
    int k = 20;
    if (args.length >= 1) {
      k = Integer.parseInt(args[0]);
    }

    RandomizedQueue<String> queue = new RandomizedQueue<String>();

    int count = 0;
    while (!StdIn.isEmpty()) {
      String str = StdIn.readString();
      count++;
      if (queue.size() < k) {
        queue.enqueue(str);
      } else {
        int r = StdRandom.uniform(count);
        if (r < k) {
          queue.dequeue();
          queue.enqueue(str);
        }
      }
    }
    for (String str : queue) {
      StdOut.println(str);
    }
  }
}