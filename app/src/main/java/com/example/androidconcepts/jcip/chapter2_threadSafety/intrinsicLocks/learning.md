Objects in java work as intrinsic locks. Intrinsic Locks are reentrant in nature. If a thread is
holding a lock A and it again request it to hold, then in reentrant nature they will get a hold of
it. Reentrant locks are held pr thread.

In the case of HomeWidget the lock is "this" which is reentrant in nature, hence the performTask
method doesn't freeze. If it wasn't so then the thread would go in deadlock. 