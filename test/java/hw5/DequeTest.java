package hw5;

import exceptions.EmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DequeTest {

  private Deque<String> deque;

  @BeforeEach
  public void setUp() {
    this.deque = createDeque();
  }

  protected abstract Deque<String> createDeque();

  @Test
  @DisplayName("Deque is empty after construction.")
  public void testConstructor() {
    assertTrue(deque.empty());
    assertEquals(0, deque.length());
  }


  // TEST LENGTH AND INSERT FRONT AND INSERT BACK

  // test length only with addition of elements, simple
  @Test
  @DisplayName("Length increments after adding to the front")
  public void lengthInsertFront() {
    deque.insertFront("Front1");
    deque.insertBack("Back1"); // first back insertion fails, length is still 2, not 3
    assertEquals(15, deque.length());
  }

  @Test
  @DisplayName("Length increments after adding to the back")
  public void lengthInsertBack() {
    deque.insertBack("String1");
    assertEquals(1, deque.length());
  }

  // test length with only addition of elements, complex

  @Test // fails
  @DisplayName("Length increments doubly when adding to front then back")
  public void lengthInsertFrontThenBack() {
    deque.insertFront("Front1");
    deque.insertBack("Back1"); // fails. Length is 1 cuz this wasn't added.
    assertEquals(2, deque.length());
  }

  @Test
  @DisplayName("Length increments doubly when adding to back then front")
  public void lengthInsertBackThenFront() {
    deque.insertBack("Back1");
    deque.insertFront("Front1");
    assertEquals(2, deque.length()); // works when length = 1
  }

  // LENGTH TESTS ADDING AND REMOVING FROM FRONT AND BACK

  // simpler cases, only going one direction for both addition and removal

  @Test
  @DisplayName("Length decrements after adding to front and removing from front")
  public void lengthAddFrontRemoveFront() {
    deque.insertFront("String1");
    deque.insertFront("String2");
    deque.removeFront();
    assertEquals(1, deque.length());
  }

  @Test
  @DisplayName("Length decrements after adding to back and removing from back")
  public void lengthAddBackRemoveBack() {
    deque.insertBack("Back1");
    deque.insertBack("Back2"); // fails to add
    deque.removeBack();
    assertEquals(1, deque.length());
  }

  // more complex - add one way, remove from the other

  @Test
  @DisplayName("Length decrements after adding to back and removing from front")
  public void lengthAddBackRemoveFront() {
    deque.insertBack("Back1");
   // deque.insertBack("Back2"); // uncommenting this produces error
    deque.removeFront();
    assertEquals(0, deque.length());
  }

  @Test
  @DisplayName("Length decrements after adding to front and removing from back")
  public void lengthAddFrontRemoveBack() {
    deque.insertFront("Front1");
    deque.insertFront("Front2");
    deque.removeBack();
    assertEquals(1, deque.length());
  }

  // yet more complex - add in both directions, remove from both

  @Test
  @DisplayName("Length decrements after adding to front and back and removing from front")
  public void lengthInsertFrontAndBackRemoveFront() {
    deque.insertFront("Front1");
    deque.insertBack("Back2"); // this fails because it simply wasn't added.
    deque.insertFront("Front2");
    deque.removeFront();
    deque.removeBack();
    assertEquals(1, deque.length());
  }

  @Test
  @DisplayName("Length decrements after adding to front and back and removing from back")
  public void lengthInsertFrontAndBackRemoveBack() {
    deque.insertFront("Front1");
    deque.insertBack("Back2"); // when this is run, it produces an error
    deque.insertFront("Front2");
    deque.removeBack(); // don't think error is here
    deque.removeFront(); // or here
    assertEquals(1, deque.length());
  }

  // TEST FRONT()

  // exceptions

  @Test
  @DisplayName("Front throws exception when deque empty")
  public void frontThrowsEmptyException() {
    try {
      String a = deque.front();
      fail("Front didn't throw empty exception");
    } catch (EmptyException e) {
      // success
    }
  }

  // simple: access of front from element added to front

  @Test
  @DisplayName("Front returns value added to front")
  public void frontAccessFromFront() {
    deque.insertFront("Front1");
    assertEquals("Front1", deque.front());
  }

  // simple: front used to access element added to back, when it's the only element present

  @Test
  @DisplayName("Front returns value added to back when it's the only value present")
  public void frontOneElement() {
    deque.insertBack("Back1");
    assertEquals("Back1", deque.front());
  }

  // more complex: multiple added, from front or back, and front returns appropriate value
  // some tests are just adding multiple to front, some just multiple to back, some both

  @Test
  @DisplayName("Front returns most recently added to front after multiple front insertions")
  public void frontInsertionsOnlyProperReturn() {
    deque.insertFront("Front1");
    deque.insertFront("Front2");
    assertEquals("Front2", deque.front());
  }

  @Test
  @DisplayName("Front returns least recently added to back after multiple back insertions")
  public void frontLeastRecentlyAddedBack() {
    deque.insertBack("Back1");
    deque.insertBack("Back2");
    assertEquals("Back1", deque.front());
  }

  @Test
  @DisplayName("Front returns frontmost after insertion to front then back")
  public void frontThenBackFrontReturn() {
    deque.insertFront("Front1");
    deque.insertBack("Back1");
    assertEquals("Front1", deque.front());
  }

  @Test
  @DisplayName("Front returns frontmost after insertion to back then front")
  public void backThenFrontFrontReturn() {
    deque.insertBack("Back1");
    deque.insertFront("Front1");
    assertEquals("Front1", deque.front());
  }

  // complex case of inserting multiple to front and back in 'complex' order, testing front

  @Test
  @DisplayName("Front returns frontmost after 'complex' insertion to front and back")
  public void frontComplex1() {
    deque.insertBack("Back1");
    deque.insertFront("Front1");
    deque.insertBack("Back2");
    deque.insertBack("Back3");
    deque.insertFront("Front2");
    deque.insertBack("Back4");
    assertEquals("Front2", deque.front());
  }

  // TESTING BACK()

  // exceptions

  @Test // fails
  @DisplayName("Back() throws exception when deque empty")
  public void BackThrowsEmptyException() {
    try {
      String a = deque.back();
      fail("Back didn't throw empty exception");
    } catch (EmptyException e) {
      // success
    }
  }

  // simple: access of back from element added to back

  @Test
  @DisplayName("Back returns value added to back")
  public void backAccessFromBack() {
    deque.insertFront("Back1");
    assertEquals("Back1", deque.back());
  }

  // simple: front used to access element added to back, when it's the only element present

  @Test
  @DisplayName("Back returns value added to front when it's the only value present")
  public void backOneElement() {
    deque.insertFront("Front1");
    assertEquals("Front1", deque.back());
  }

  // more complex: multiple added, from front or back, and front returns appropriate value
  // some tests are just adding multiple to front, some just multiple to back, some both

  @Test
  @DisplayName("Back returns most recently added to back after multiple back insertions")
  public void backInsertionsOnlyProperReturn() {
    deque.insertBack("Back1");
    deque.insertBack("Back2"); // fail - Back1
    deque.insertBack("Back3");
    deque.insertBack("Back4"); // fail - Back3
    assertEquals("Back4", deque.back());
  }

  @Test // this works, but above fails. Why? Back() is fine. insertBack() has the problem.
  @DisplayName("Back returns least recently added to front after multiple front insertions")
  public void backLeastRecentlyAddedFront() {
    deque.insertFront("Front1");
    deque.insertFront("Front2");
    assertEquals("Front1", deque.back());
  }

  @Test
  @DisplayName("Back returns backmost after insertion to front then back")
  public void frontThenBackBackReturn() { // fails! But below succeeds. Why?
    deque.insertFront("Front1"); // because insertBack() fails to properly expand array.
    deque.insertBack("Back1"); // this proves that the second element is simply not added.
    assertEquals("Back1", deque.back());
  }

  @Test
  @DisplayName("Back returns backmost after insertion to back then front")
  public void backThenFrontBackReturn() {
    deque.insertBack("Back1");
    deque.insertFront("Front1");
    assertEquals("Back1", deque.back());
  }

  // complex case of inserting multiple to front and back in 'complex' order, testing front

  @Test // fails
  @DisplayName("Back returns backmost after 'complex' insertion to front and back")
  public void backComplex1() {
    deque.insertBack("Back1");
    deque.insertFront("Front1");
    deque.insertBack("Back2");
    deque.insertBack("Back3");
    deque.insertFront("Front2");
    deque.insertBack("Back4");
    assertEquals("Back4", deque.back());
  }

  // maybe try adding one to front, then removing from back? If only one element, front and back
  // are the same. And do the converse, too

  // exceptions for removeFront() and removeBack()

  @Test
  @DisplayName("removeFront throws exception when deque empty")
  public void removeFrontThrowsEmptyException() {
    try {
      deque.removeFront();
      fail("removeFront() didn't throw empty exception");
    } catch (EmptyException e) {
      // success
    }
  }

  @Test
  @DisplayName("removeBack throws exception when deque empty")
  public void removeBackThrowsEmptyException() {
    try {
      deque.removeBack();
      fail("removeBack() didn't throw empty exception");
    } catch (EmptyException e) {
      // success
    }
  }

  // TEST BEHAVES LIKE A STACK
  @Test
  @DisplayName("Back() exhibits last in, first out behavior")
  public void backStackBehavior() {
    deque.insertBack("b1");
    deque.insertBack("b2");
    deque.insertBack("b3");

    deque.removeBack();
    deque.removeBack();
    assertEquals("b1", deque.back());
  }

  @Test
  @DisplayName("Front() exhibits last in, first out behavior")
  public void frontStackBehavior() {
    deque.insertFront("f1");
    deque.insertFront("f2");
    deque.insertFront("f3");

    deque.removeFront();
    deque.removeFront();
    assertEquals("f1", deque.back());
  }

  // TEST BEHAVES LIKE A QUEUE

  @Test
  @DisplayName("Back() exhibits first in, first out behavior with insertFront()")
  public void backQueueBehavior() {
    deque.insertFront("f1");
    deque.insertFront("f2");
    deque.insertFront("f3");
    deque.insertFront("f4");

    assertEquals("b1", deque.back());
  }

  @Test
  @DisplayName("Front() exhibits first in, first out behavior with insertBack()")
  public void frontQueueBehavior() {
    deque.insertBack("b1");
    deque.insertBack("b2");

    assertEquals("b1", deque.front());
  }



}
