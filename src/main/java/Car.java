import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private final CyclicBarrier cb;
    private static boolean isWin = false;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed,CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cb = cb;
    }
    @Override
    public void run() {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int) (Math.random() * 800));
                System.out.println(this.name + " готов");
                cb.await();
                cb.await();

            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            checkWin(this);
        try {
            cb.await();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
        private static synchronized void checkWin(Car c){
        if(!isWin){
            System.out.println(c.name + " is Winner");
            isWin = true;
        }


         }
    }

