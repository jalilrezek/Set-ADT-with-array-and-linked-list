The purpose of this project was to provide multiple implementations of the "Set" data structure. I implemented it using a linked list and using an array. 

The linked list implementation uses the "move to front" heuristic to speed up search operations. 

The array implementation uses the "transpose sequential search" heuristic to speed up search operations.

The linked list implementation is in "main/java/hw5", called "MoveToFrontLinkedSet.java".

The array implementation is in "main/java/hw5", called "TransposeArraySet.java".

I wrote unit tests for these data structures. These can be found in "test/java/hw5".

I also troubleshooted an obfuscated and buggy implementation of "Deque" data structure. For this, I wrote many tests in "test/java/hw5" in the file "DequeTest.java."

Lastly, I used JMH Runtime Test to compare the performance of the Set implementations (array and linked list) with and without optimizations and documented my findings in "test/java/hw5
/initialProfilingREADME".
