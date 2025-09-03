# JAVA

## run multiple tasks in parallel
```java
try (var service = Executors.newVirtualThreadPerTaskExecutor()) {
   Future<T1> f1 = service.submit(callable1);
   Future<T2> f2 = service.submit(callable2);
   result = combine(f1.get(), f2.get());
}
```

## run virtual threads in Java 25
```java
        // Create and start a virtual thread directly
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("Running in virtual thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // Simulate I/O-bound work
                System.out.println("Task completed in virtual thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.err.println("Virtual thread interrupted: " + e.getMessage());
            }
        });

        // Wait for the virtual thread to complete
        try {
            virtualThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }

```
