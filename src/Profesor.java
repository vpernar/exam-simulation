import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Profesor implements Runnable {
    private final CyclicBarrier cyclicBarrier;

    public Profesor(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

//    public Profesor() {
//    }

    @Override
    public void run() {

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            Student s1 = null;
            Student s2 = null;
            try {
                s1 = Main.queue.take();
                s2 = Main.queue.take();

//                cyclicBarrier.await();
                doGrade(s1);

                doGrade(s2);

            } catch (InterruptedException e) {
                if (s1 != null) {
                    s1.setScore(5);
                    s1.setProfOrAs(Thread.currentThread().getId());
                    System.err.println(s1);
                }

                if (s2 != null) {
                    s2.setProfOrAs(Thread.currentThread().getId());
                    s2.setScore(5);
                    System.err.println(s2);
                }

                return;
            } //catch (BrokenBarrierException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    private void doGrade(Student s) throws InterruptedException {
        float ttc = new Random().nextFloat(0.5F, 1);
        s.setTtc(ttc);
        s.setStartTtc((float) System.currentTimeMillis());

        Thread.sleep((long) (ttc * 1000));

        s.setProfOrAs(Thread.currentThread().getId());
        s.setScore(new Random().nextInt(5, 11));

        Main.finished.add(s);
        System.out.println("<" + Main.i + "> " + s);
        Main.i.getAndIncrement();
    }
}
