import util.Color;

import java.util.Random;

public class Student implements Runnable {
    private final int id;
    private float arrival;
    private float ttc;
    private float startTtc;
    private int score;
    private long profOrAs;

    public Student(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {

            this.arrival = new Random().nextFloat(0F, 1F);
            Thread.sleep((long) (arrival * 1000));
            Main.queue.put(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void setTtc(float ttc) {
        this.ttc = ttc;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void setProfOrAs(long profOrAs) {
        this.profOrAs = profOrAs;
    }

    public void setStartTtc(float startTtc) {
        this.startTtc = startTtc;
    }

    @Override
    public String toString() {
        return "<" + Color.PEACH.getCode() + "Thread: " + Color.RESET.getCode() +id +
                Color.RESET.getCode() + "> " + "<" + Color.SKY_BLUE.getCode() + "Arrival: " + Color.RESET.getCode() + arrival +
                "> <" + Color.YELLOW.getCode() + "Prof: " + Color.RESET.getCode() + profOrAs +
                "> <" + Color.BLUE.getCode() + "TTC: " + Color.RESET.getCode() + ttc + Color.BLUE.getCode() +":" + Color.RESET.getCode() + startTtc +
                "> <" + Color.GREEN.getCode() + "Score: " + Color.RESET.getCode() + score + ">";
    }
}
