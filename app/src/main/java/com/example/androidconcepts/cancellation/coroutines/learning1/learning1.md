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
Give that `performMoneyTransaction` doesn't use a withContext but has suspend methods (which throws CancellationException) inside it, notice that in this block of code in activity :
        line 1 `val result = useCase.performMoneyTransfer()`
        line 2 `Log.d("debug", "result from usecase : $result")`
After cancelling the scope, line 2 doesn't executes. 

->
1. CancellationExceptions : These are the main reason for cancellations of the suspend functions execution and hence of coroutines.
2. The main reason in the code that even without using isActive when only withContext is present then coroutines still cancels after execution of blocking code is that withContext internally checks for isActive,
   But that only happens when while loop is finished.

->
This means that for a coroutine of format :
        line 1 : suspend_fun1()
        line 2 : // the nature of code doesn't matter here...
Even if suspend fun1 uses only withContext (or any inbuilt cancellation checking function) with blocking and non cooperative code inside it, if the parent scope is cancelled then the line just after fun1 will not execute. 
Same apply for the functions which checks for isActive() inside them.

-> 
The fact that one cannot predict the nature of suspend methods (throws cancellation or not, if yes then immediately or after executing blocking code) by just seeing their name makes coroutines very complex.

If one catches the cancellation exception of some suspend method then its not predictable that the
exception will be thrown immediately after cancelling the coroutine or after 5 seconds (suppose you do view binding stuff inside the catch then you encounter typical binding is null exception).

->
Few must to perform exercises and questions to think on :
        1. What happens when we remove withContext from `performMoneyTransferNetworkCalls`.
        2. What happens if we add a suspend method inside `performMoneyTransferNetworkCalls` inside the repeat loop.
        3. What happens if we add a suspend method inside `performMoneyTransferNetworkCalls` after the repeat loop.
        4. Why not use withContext in only `performMoneyTransaction` and not in the child suspend methods.
        5. Remove all withContexts.