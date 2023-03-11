import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static float sum = 0;
    public static float average;
    public static BlockingQueue<Student> queue = new LinkedBlockingQueue<>();
    public static List<Student> finished = new LinkedList<>();

    public static AtomicInteger i = new AtomicInteger(1);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesi broj studenata: ");

        int n = sc.nextInt();

        Semaphore semaphore = new Semaphore(1, true);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        ExecutorService es = Executors.newFixedThreadPool(n + 2);
        es.submit(new Asistent(semaphore, cyclicBarrier));
//        es.submit(new Profesor(cyclicBarrier)); //prosledjujem jos jednu barijeru
//        es.submit(new Profesor(cyclicBarrier));
        es.submit(new Profesor(cyclicBarrier));

        for (int i = 0; i < n; i++) {
            es.submit(new Student(i));
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        es.shutdownNow();

        for (Student s : finished) {
            sum += s.getScore();
        }

        average = sum / finished.size();
        System.out.println("Suma: " + sum);
        System.out.println("Broj studenata: " + finished.size());
        System.out.println("Prosek: " + average);
    }
}
