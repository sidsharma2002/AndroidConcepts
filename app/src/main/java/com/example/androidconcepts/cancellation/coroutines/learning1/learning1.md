This is the observed logs of Learning1 :
1. debug 0
2. debug 1
3. // press home, child coroutine gets cancelled.
4. debug 2
5. debug 3
6. debug 4
7. network calls done, returning result

-> 
Even though both the suspend methods 'performMoneyTransfer' and 'saveResultInDb' doesn't support cooperative cancellations,
When the parent scope is cancelled second method saveResultInDb doesn't executes. In fact, even Log.d("debug", "network call result received : $result") doesn't executes.

->
Also notice that in this block of code in activity :
        line 1 `val result = useCase.performMoneyTransfer()`
        line 2 `Log.d("debug", "result from usecase : $result")`
After cancelling the scope, line 2 doesn't gets executed.

->
This means that for a coroutine of format :
        line 1 : suspend_fun1()
        line 2 : // the nature of code doesn't matter here...
Irrespective of suspend fun1 supports cooperative cancellations or not, if the parent scope is cancelled then line just after fun1 will not execute. 