package hw5;

/**
 * Set implemented using a doubly linked list and move-to-front heuristic.
 *
 * @param <T> Element type.
 */
public class MoveToFrontLinkedSet<T> extends LinkedSet<T> {


  /**
   * Handle relinking if the node is not the head or tail, and the list is not empty.
   * @param n the node queried
   * @param prev the tail's previous node
   * @param next the node's next node
   **/

  private void remainingCases(Node<T> n, Node<T> prev, Node<T> next) {
    n.next = head;
    n.prev = null;

    head.prev = n;
    head = n;

    prev.next = next;
    next.prev = prev;
  }

  /**
   * Handle relinking if the node queried is the tail.
   * @param n the node queried (which is the tail if this method is reached)
   * @param prev the tail's previous node
   **/

  private void tailCase(Node<T> n, Node<T> prev) {
    n.next = head;
    n.prev = null;

    head.prev = n;
    head = n;

    prev.next = null;
    tail = prev;
  }


  /**
   * See if set contains an element.
   * @param t the element to search for
   * @return true if present, false if not
   **/

  @Override
  public boolean has(T t) {
    Node<T> n = find(t);
    if (n == null) {
      return false;
    } else if (n.prev == null) { // it's at the front; prev does not exist, and need not do anything anyway
      return true;
    }

    // node is not absent from LL, and it's not at the front (prev exists). Move to front
    Node<T> prev = n.prev;
    Node<T> next = n.next;
    if (next == null) { // it's the tail
      tailCase(n, prev);
    } else {
      remainingCases(n, prev, next);
    }
    return true;
  }

  /**
   * Main method. Testing the functionalities of the list.
   * @param args commandline arguments
   **/

  public static void main(String[] args) {
    MoveToFrontLinkedSet<String> m = new MoveToFrontLinkedSet<>();


    m.insert("Z"); //removed
    m.insert("X");
    m.insert("Y");//removed
    m.insert("A?");
    m.insert("D");//removed

    boolean has1 = m.has("A?");
    boolean has4 = m.has("A?"); // check that doesn't move if already at front
    boolean has2 = m.has("X");
    boolean has3 = m.has("D"); // moving element at the back to the front
    boolean has5 = m.has("w");


    m.remove("Z");
    m.remove("A?"); // remove element at end - works fine
    m.insert("q");
    m.insert("e");
    m.insert("h");
    m.insert("F!");


  }


}
