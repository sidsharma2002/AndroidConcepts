# AndroidConcepts

My personal learning topic-wise project with inDepth concepts and comments/learning summary. (See unit-tests too)
Readme is in progress.

### Topics covered [see package structure/prs/branches if not found] : 

1. ### [File Downloader : LLD + advanced concurrency](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/advancedConcurrency/fileDownloader)

The use of cache and synchronization on downloading names in this FileDownloadUseCase class is to ensure that the same file is not downloaded multiple times concurrently.The  cachedFiles HashMap is used to store previously downloaded files, and the currentlyDownloadingFiles list is used to keep track of files that are currently being downloaded. Before starting a new download, the code checks if the file is already in the cache or if it is being downloaded by another thread. If it is already in the cache, the cached file is returned; if it is being downloaded by another thread, the code waits until the download is complete before starting a new download. This is achieved by using synchronized blocks and wait() and notifyAll() methods on a monitor object.

By doing this, the code ensures that only one thread downloads a file at a time and that files are not downloaded multiple times unnecessarily, which saves time and resources.
See Unit-Tests too.

3. ### [Producer Consumer : advanced concurrency](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/advancedConcurrency/producerConsumer)

This is an example of a Producer-Consumer pattern, which is a common design pattern in concurrent programming, Demonstrates how multiple threads can work together in a synchronized manner to complete a complex task, and how the Producer-Consumer pattern can be used to manage shared resources (in this case, dishes) between these threads.

4. ### [Cancellations in Coroutines and RxJava : proper reasoning of why coroutines is hard](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/cancellation)
5. ### [General Observables](https://github.com/sidsharma2002/AndroidConcepts/blob/main/app/src/main/java/com/example/androidconcepts/common/BaseObservable.kt)
6. ### [Background thread executors : replacement for simple background work](https://github.com/sidsharma2002/AndroidConcepts/blob/main/app/src/main/java/com/example/androidconcepts/common/BgThreadPoster.kt)

The BgThreadPoster class is useful in situations where you need to perform a time-consuming task in the background, such as loading data from a remote server, without blocking the main thread. Less overengineered than coroutines.

7. ### [Coroutines : benefits + how even coroutines can block and not suspend](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/coroutines)
8. ### [Java concurrency in practice learnings : advanced concurrency, not for kids](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/jcip)
9. ### [Livedata : benefits](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/livedata/learning1)
10. ### [How to create your own livedata better than google's livedata](https://github.com/sidsharma2002/AndroidConcepts/blob/main/app/src/main/java/com/example/androidconcepts/livedata/learning2/ObservableDataHolder.kt)
11. ### [Threads doesn't interrupt immediately](https://github.com/sidsharma2002/AndroidConcepts/blob/main/app/src/main/java/com/example/androidconcepts/cancellation/threads/ThreadInterruption1UseCase.kt)
12. ### [Retro UI ,uncomment activity_main layout file](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/ui/retroDesign)
https://user-images.githubusercontent.com/53833109/233896079-566c138d-a3b4-4e39-9b30-a77326829124.mp4

#### Released : 
![WhatsApp Image 2023-04-24 at 09 21 22](https://user-images.githubusercontent.com/53833109/233896336-560662a1-2b4c-486b-916f-fe6383ab3e55.jpeg)

#### Pressed : 
![WhatsApp Image 2023-04-24 at 09 21 22 (1)](https://user-images.githubusercontent.com/53833109/233896369-ddf81603-3e82-436a-8391-94a6b8f190d6.jpeg)

#### Clicked + Shimmer Animation :
![WhatsApp Image 2023-04-24 at 09 21 41](https://user-images.githubusercontent.com/53833109/233896450-3285df31-d301-43e4-b798-1cf36322071f.jpeg)

12. ### [Manually handling config changes and not let the activity destroy, Along with strategy-pattern applied on view logic.](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/lifecycle)
13. ### [Java complete reference : data structure operations with measured time, intresting facts.](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/jcr/collections)

 It creates several large collections (ArrayList, LinkedList, HashSet, TreeSet, HashMap, TreeMap) and measures the time taken to perform various operations like adding and removing elements, iterating over elements, etc. The test is meant to compare the performance of different collections under different scenarios and help developers choose the appropriate collection type for their specific use case.

14. ### [Spamming threads : starting 100s of threads and busting myth that threads are heavy.](https://github.com/sidsharma2002/AndroidConcepts/blob/main/app/src/main/java/com/example/androidconcepts/jcip/extras/AndroidThreadSpammer.kt)
15. ### [Creating own RxJava like framework](https://github.com/sidsharma2002/AndroidConcepts/tree/main/app/src/main/java/com/example/androidconcepts/rxJava/ownImpl)
16. ### Problems with fragment and its navigation [wip]
