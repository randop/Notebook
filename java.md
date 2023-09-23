# JAVA

## run multiple tasks in parallel
```java
try (var service = Executors.newVirtualThreadPerTaskExecutor()) {
   Future<T1> f1 = service.submit(callable1);
   Future<T2> f2 = service.submit(callable2);
   result = combine(f1.get(), f2.get());
}
```
