import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class FactorialTask extends RecursiveTask<Long> {
    private final int start;
    private final int end;

    public FactorialTask(int n) {
        this(1, n);
    }

    private FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end <= start) {
            return (long) Math.max(1, start);
        }
        if (end - start == 1) {
            return (long) start * end;
        }

        int mid = start + (end - start) / 2;
        FactorialTask leftTask = new FactorialTask(start, mid);
        FactorialTask rightTask = new FactorialTask(mid + 1, end);

        leftTask.fork();

        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();

        return leftResult * rightResult;
    }
}

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the number for factorial: ");
            int n = sc.nextInt();

            ForkJoinPool forkJoinPool = new ForkJoinPool();
            FactorialTask factorialTask = new FactorialTask(n);

            long result = forkJoinPool.invoke(factorialTask);

            System.out.println("Factorial " + n + "! = " + result);
        }
    }
}