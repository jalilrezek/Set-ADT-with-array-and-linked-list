# Discussion

## Flawed Deque

I wrote a lot, but here is a summary first.

SUMMARY OF MISTAKES

insertBack() sometimes fails to add requested elements.

I think this relates to expanding the deque's capacity when needed. Whenever an expansion is required,
it seems that insertBack() simply fails to add the requested element, resulting in the symptoms above.

back() throws LengthException rather than EmptyException.

removeFront() and removeBack() fail to throw any exception at all.

Below I go in depth and explain possible causes.


MISTAKE 1: 

My unit test backInsertionsOnlyProperReturn() reveals that the
back() method sometimes returns the wrong value. For instance, adding one string, "Back1", worked
fine in that back() returned "Back1". Adding "Back2" did not work, however; back() yielded "Back1"
still. 

Then, upon adding "Back3", back() worked again. But it failed upon adding a fourth element. Then,
it worked up until the seventh added element, where it failed, reporting "Back6" instead of "Back7".

This pattern continued. After each failure, it took more and more entries to cause another failure.

I speculate that this is an error with the expansion of the deque. It might be using an array 
implementation, and seek to expand the size of the array by a factor of 2 or something similar
each time capacity is reached. But it is not doing this properly. Maybe the deque
checks if it is at capacity only after trying to add a new element. In this case, the addition fails
but maybe numElements is still incremented, so the capacity is doubled, but in a faulty way.

MISTAKE 2: 

When adding to the front, the lengths update fine. But once the first element is added
to the back, it is not counted towards the length. The next element added, to the front or back,
does count towards length. So now, length is lagging by 1. 

The next time the pattern of inserting front followed by inserting back occurs, another addition
(presumably the addition to the back) fails to be counted to the length. So now, length lags by 2.

The lag by 2 persists even when repeating the pattern of insert front followed by insert back
many times. But eventually, another insertion to the back is not counted, and length lags by 3.

I think it may always be possible, continuing this pattern enough, to get more missed additions,
increasing the lag between recorded and proper length. But, for each subsequent missed addition,
it takes many more "insert front followed by insert back" events. This is similar to Mistake 1,
where back() yields the wrong value at increasingly distant intervals as the number of elements
added to the back grows.

I edited my test to have many insertFront() calls before the first insertBack() call. My thinking
was that this issue might be related to the grow() functionality of insertBack(), as with Mistake 1.
insertFront() seemed to work fine, so, if insertFront() was called many times, the capacity would
be expanded and many more slots would be available to fill. Then, the first call of insertBack()
would not have to expand to a new space, and there would be no lag. By contrast, we have only one or two
insertFront() calls followed by an insertBack() call, insertBack() may have to try to expand the
capacity, resulting in an error situation.

Sure enough, when I had many insertFront() calls before my first insertBack() call, the first 
insertBack() call did not fail to be counted towards the length as it had before.

Therefore, I think both Mistake 1 and Mistake 2 are related to insertBack() having an erroneous
grow() functionality; or the grow() functionality shared by both insertBack() and insertFront()
does not properly handle the case when growth is required because of an insertion to the back.


MISTAKE #3:

back() throws LengthException() instead of EmptyException(). removeFront() and removeBack() do not
throw exceptions at all. This is when these three methods are called upon empty deques.

Maybe back() simply had the wrong exception written in its code, i.e. LengthException() instead of
EmptyException().

My BackThrowsEmptyException() test showed this.

removeFront() and removeBack() may fail because they do not handle the exception properly.
They might simply catch it and proceed when they should actually be throwing it.

My removeFrontThrowsEmptyException() and removeBackThrowsEmptyException() tests showed this.

Another possibility is that the implementations of these methods could be flawed. Maybe they always 
assume that at least one element is present and access the deque without first checking that it's
empty.


## Hacking Linear Search

Linked list: The removal logic works perfectly well. It removes the node and re-links its two 
neighbors to each other, if both neighbors exist, and deals with the edge cases of head and tail
appropriately. The insert logic inserts at the end of the list, which works fine.

Transpose array: The removal logic actually runs contrary to the heuristic, but I won't be changing
it since the instructions say not to. Removal switches out the last element with the target, and 
erases the target by decrementing numElements. According to the heuristic, the least-queried items
will be at the end of the array. But this removal mechanism puts that least-searched item at the 
front, in front of far more frequently-queried items. So, it runs contrary to the logic and goals
of the heuristic.
Insertion inserts elements at the very end, growing if necessary. It works fine with the heuristic.



## Profiling

I used the original ArrayList, shuffled its elements, and added them all to the set, as in the
default program given to us. But then, I also searched, a number of times equal to the number
of elements in the set (1000), for the very last element, by trying to insert that element.

I'm going to take the time to explain my findings based on the way that the heuristics work,
which requires some effort so please don't subtract points for being too long to read.

I think that this could model some realistic scenarios like the following. Maybe we are running
a dataset that collects input from users across the world, like a collaborative science project
drawing help from observations and data submited by the general populace. And, the most noteworthy
submission would be an extreme case located at the very end of our dataset. We have many people
all repeatedly submitting the same extreme response. But, it's already in our dataset, so we 
don't need to add it again. The heuristics would help in this case.

Here are the results: 

Benchmark                         Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arraySet           avgt    2  0.600          ms/op
JmhRuntimeTest.linkedSet          avgt    2  2.159          ms/op
JmhRuntimeTest.moveToFront        avgt    2  0.755          ms/op
JmhRuntimeTest.transposeSequence  avgt    2  0.422          ms/op

moveToFront performed much better than linkedSet, and transposeSequence performed slightly
better than arraySet. This aligns with expectations.

My dataset was large enough to observe some noticeable differences. I tried a much larger task
of entering the last entry 10,000 times in an experiment detailed in my "initialProfilinREADME"
file in the test/java/hw5 folder where I saw even more pronounced differences. As the dataset
gets larger, the moveToFront linked list outperforms the regular array, and begins to catch up
to the transpose sequence array.

The linked list showed a much greater benefit from the heuristic than the array. Upon reflection,
this is not too surprising. The array does not move a queried value to the front - it just swaps it 
with its neighbor, one place closer to the front. The most commonly-searched element only slowly
comes to the front. Therefore, The extent to which the transpose sequence array is faster grows
with each iteration. With a small number, say 100, of repeated queries for the last element, by the
time we've processed the last request, the last number is only 50 places out of 1000 closer to
the front. With many more searches, however, it will eventually reach the front and we will 
increasingly see benefits from the transpose sequence optimization.

The optimized linked list, however, instantly moves the queried element to the front, so we can
expect immediate benefits from the optimization, explaining why the moveToFront linked list was
so much faster than the unoptimized one.

What's noteworthy is that the linked lists, even the optimized one, took longer than both
of the array implementations.

This must be because of the extra relinking operations that come with the linked list, even though
it is theoretically better in that it moves elements right to the very front immediately with the
first query.

Based on this, I infer that a linked list implementation is preferable for enormous data sets
potentially orders of magnitude larger than the array of 1000 elements that I tested here. But,
for smaller datasets, the array implementation is more time-efficient.





