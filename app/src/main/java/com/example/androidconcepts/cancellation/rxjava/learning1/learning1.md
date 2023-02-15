This is the observed logs of Learning1 :

1. debug repeat : 0 isDisposed : false
2. debug repeat : 1 isDisposed : false
3. // press home, disposable gets disposed.
4. debug repeat : 2 isDisposed : true
5. debug repeat : 3 isDisposed : true
6. debug repeat : 4 isDisposed : true
7. network call result received : Success
8. saving result started
9. saved result
10. second map result : transaction done successfully!

From the above observation of logs, we can see that in rxJava execution gets cancelled only when it
comes to onNext or onError (subscribe callbacks), It keeps on running even in the next map operator.