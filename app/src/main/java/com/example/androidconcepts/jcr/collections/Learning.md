ArrayList and LinkedList :

(Time of adding elements at an index i) is directly proportional to (the closeness of i from index
0). For eg : Adding at last index takes shortest time and adding at 0th index takes longest time.
The same opposite in LinkedList i.e adding at the first index is shortest and at the last index is
longest. Though around the middle index arrayList (80ms) takes much less time than linkedList (1sec)

a. Adding 10^6 elements : ArrayList (20ms) LinkedList (120ms)
b. After that adding 10^3 elements at middle index : ArrayList (80ms) LinkedList (1sec)
c. After that adding 10^3 elements at 0 index : ArrayList (300ms) LinkedList (10ms)

HashSet :

Unsorted unique elements. Uses hashmap internally. 