This is the observed logs of Learning1 :
1. debug 0
2. debug 1
3. // press home, child coroutine gets cancelled.
4. debug 2
5. debug 3
6. debug 4
7. network calls done, returning result

->
Given that `performMoneyTransferNetworkCalls` doesn't support cooperative cancellation i.e for eg: isActive check in repeat loop, it declares withContext explicitly. 
This withContext has a major role in our following observations.

-> 
Even though both the suspend methods 'performMoneyTransferNetworkCalls' and 'saveResultInDb' doesn't support cooperative cancellations explicitly (use of ensureActive/isActive),
When the parent scope is cancelled second method saveResultInDb doesn't executes. In fact even Log.d("debug", "network call result received : $result") doesn't executes.

->
Give that `performMoneyTransaction` doesn't use a withContext but has suspend methods inside it, notice that in this block of code in activity :
        line 1 `val result = useCase.performMoneyTransfer()`
        line 2 `Log.d("debug", "result from usecase : $result")`
After cancelling the scope, line 2 doesn't executes.

->
This means that for a coroutine of format :
        line 1 : suspend_fun1()
        line 2 : // the nature of code doesn't matter here...
If suspend fun1 uses withContext with blocking and non cooperative code inside it, if the parent scope is cancelled then the line just after fun1 will not execute. 

->
There are few questions that arises in mind :
        1. What happens when we remove withContext from `performMoneyTransferNetworkCalls`
        2. If CancellationException is not thrown by us in `performMoneyTransferNetworkCalls`, then how coroutine got cancelled and who throws it
        3. What happens if we add a suspend method inside `performMoneyTransferNetworkCalls` inside the repeat loop
        4. What happens if we add a suspend method inside `performMoneyTransferNetworkCalls` after the repeat loop
        5. What happens when we replace blockingDelay by suspending delay
        6. Why not just write all this code in one single method
        7. Why not use withContext in only `performMoneyTransaction` and not in the child suspend methods