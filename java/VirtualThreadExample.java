import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VirtualThreadExample {

    // Example types for demonstration
    static class T1 {
        private final String name;

        public T1(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "T1{name='" + name + "'}";
        }
    }

    static class T2 {
        private final int value;

        public T2(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "T2{value=" + value + "}";
        }
    }

    // Example method to combine T1 and T2
    static String combine(T1 t1, T2 t2) {
        return t1.toString() + " combined with " + t2.toString();
    }

    public static void main(String[] args) {
        Callable<T1> callable1 = () -> {
            Thread.sleep(3000); // Simulate some processing time
            return new T1("Example2");
        };

        Callable<T2> callable2 = () -> {
            Thread.sleep(10000); // Simulate some processing time
            return new T2(31);
        };

        String result;
        try (var service = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<T1> f1 = service.submit(callable1);
            Future<T2> f2 = service.submit(callable2);
            result = combine(f1.get(), f2.get());
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions that could occur during execution or retrieval of results
            Thread.currentThread().interrupt(); // Re-interrupt if it was an InterruptedException
            throw new RuntimeException("Task execution failed", e);
        }

        System.out.println("Result: " + result);
    }
}
