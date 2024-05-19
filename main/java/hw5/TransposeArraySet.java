package hw5;

/**
 * Set implemented using plain Java arrays and transpose-sequential-search heuristic.
 *
 * @param <T> Element type.
 */
public class TransposeArraySet<T> extends ArraySet<T> {


  /**
   * See if set contains an element.
   * @param t the element to search for
   * @return true if present, false if not
   **/

  @Override
  public boolean has(T t) {
    int index = find(t);
    if (index == -1) {
      return false;
    } else if (index == 0) { // already at the front; has no preceding element & need not do anything anyway
      return true;
    }
    // it's not absent from array, and it's not at the front. Implement swap with preceding element.
    T prev = data[index - 1];
    T queried = data[index];
    data[index - 1] = queried;
    data[index] = prev;

    return true;
  }

  /**
   * main function testing various functionalities of the set.
   * @param args the commandline arguments
   **/

  public static void main(String[] args) {
    TransposeArraySet<String> m = new TransposeArraySet<>();


    m.insert("Z"); //removed
    m.insert("X");
    m.insert("Y");//removed
    m.insert("A?");

    boolean has7 = m.has("Z");
    boolean has1 = m.has("A?");
    boolean has4 = m.has("A?"); // check that doesn't move if already at front
    boolean has2 = m.has("X");
    boolean has3 = m.has("D"); // moving element at the back to the front
    boolean has5 = m.has("w"); // test trying to access elements not present does nothing
    boolean has6 = m.has("z");
    //

    m.remove("Z");
    m.remove("A?"); // remove element at end - works fine
    m.insert("q");
    m.insert("e");
    m.remove("e");


  }

}
