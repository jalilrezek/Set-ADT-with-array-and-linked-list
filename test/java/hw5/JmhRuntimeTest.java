package hw5;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class JmhRuntimeTest {

  private List<Integer> data;
  // You may add more private fields in here.

  @Setup(Level.Invocation)
  public void setUp() {
    // You may update this method.
    data = new ArrayList<>();
    for (int i = 0; i < 900; i++) {
      data.add(i);
    }
    Collections.shuffle(data);
    //for (int i = 900; i < 1000; i++) {
     // data.add(1001); // something that hasn't yet been added.
   // }
  }

  // Experiment: perform a sequence of operations on an implementation of Set ADT.
  private void experiment(Set<Integer> set) {
    // You may update this method.
    for (Integer num : data) {
      set.insert(num); // unoptimized: will insert 1001 to end. Then, asked to insert it again.
    }
    int target = data.get(data.size() - 1);
    for (Integer num : data) {
      set.insert(target);
    }
    //boolean has = false;
   // for (Integer num : data) {
     // has = set.has(num);
  //  }
   // for (int i = 0; i < 10000; i++) {
     // set.insert(1001);
    //}
    // it will go all the way to the end to find it. Optimized will stop at the front.
  } // array: You only move it forward 100 times, so by the time you're done '1001' is at position
  // 899 or so instead of 999, but it's really not that much faster.
  // the linked list, meanwhile, moves it all the way to the front. Faster


  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void linkedSet() {
    // Do not change this method.
    Set<Integer> set = new LinkedSet<>();
    experiment(set);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void moveToFront() {
    // Do not change this method.
    Set<Integer> set = new MoveToFrontLinkedSet<>();
    experiment(set);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void arraySet() {
    // Do not change this method.
    Set<Integer> set = new ArraySet<>();
    experiment(set);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void transposeSequence() {
    // Do not change this method.
    Set<Integer> set = new TransposeArraySet<>();
    experiment(set);
  }
}
