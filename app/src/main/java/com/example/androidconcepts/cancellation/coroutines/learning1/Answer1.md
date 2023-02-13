Answers for Exercises given in Learning1 :

After removing withContext from `performMoneyTransferNetworkCalls`, suspend modifier in this become redundant.

1. Output : 
2. debug 0
3. debug 1
4. // press home, child coroutine gets cancelled.
5. debug 2
6. debug 3
7. debug 4
8. network calls done, returning result
9. network call result received : done success
10. isActive : true
11. saving result started
12. isActive : true
13. isActive : false
14. saved result started

->
`performMoneyTransferNetworkCalls` method doesn't handle any cancellations now as it is not even suspend effectively.
Notice very interesting things happened here, now `saveResultInDb` gets executed even when the scope is cancelled way before.
isActive() is true even when the scope was cancelled already, after blocking delay of 2 secs it gets set to false.
When all the code is executed in `saveResultInDb` then internally withContext throws CancellationException. 
"debug : saved in db line 12" doesn't executes as `saveResultInDb` threw CancellationException.

->
To fix this problem of suspend method running even when its scope was cancelled, we need to add a withContext to duplicated `performMoneyTransaction` and name it as `performMoneyTransactionFixed`. This runs the method in coroutine scope and after doing that isActive becomes false at the right time (reason is unclear).
For debugging purpose lets add isActive in logs of `performMoneyTransferNetworkCalls` too like this : 
    repeat(5) {
        Log.d("debug", "$it isActive : ${coroutineContext.isActive}")
        blockingDelay(1000)
    }

suspend fun performMoneyTransactionFixed(): String = withContext(Dispatcher.IO) {
    val result = performMoneyTransferNetworkCalls()
    Log.d("debug", "network call result received : $result")
    Log.d("debug", "isActive : ${coroutineContext.isActive}")
    saveResultInDb(result)
    Log.d("debug", "saved in db line 12")
    return "transaction done successfully!"
}

1. Output :
2. debug 0 isActive : true
3. debug 1 isActive : true
4. // press home, child coroutine gets cancelled.
5. debug 2 isActive : false
6. debug 3 isActive : false
7. debug 4 isActive : false
8. network calls done, returning result
9. network call result received : done success
10. isActive : false