import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Asistent implements Runnable {

    public Semaphore semaphore;
    public CyclicBarrier cyclicBarrier;

    public Asistent(Semaphore semaphore, CyclicBarrier cyclicBarrier) {
        this.semaphore = semaphore;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            Student s = null;
            try {
                semaphore.acquire();

                s = Main.queue.take();

                float ttc = new Random().nextFloat(0.5F, 1F);
                s.setTtc(ttc);
                s.setStartTtc((float) System.currentTimeMillis());

                Thread.sleep((long) (ttc * 1000));

                s.setProfOrAs(Thread.currentThread().getId());
                s.setScore(new Random().nextInt(5, 10));

                Main.finished.add(s);
                System.out.println("<" + Main.i + "> " + s);
                Main.i.getAndIncrement();

                semaphore.release();

            } catch (InterruptedException e) {
                if (s != null) {
                    s.setScore(5);
                    s.setProfOrAs(Thread.currentThread().getId());
                    System.err.println(s);
                }
                return;
            }
        }
    }

}
