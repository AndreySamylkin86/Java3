package lesson_6;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AutoRacing {
    private static final Logger log = Logger.getLogger(AutoRacing.class);
    public static final int CARS_COUNT = 4;

    public static CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
    public static CountDownLatch start = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch finish = new CountDownLatch(CARS_COUNT);
    public static Lock lockWin = new ReentrantLock();

    public static void main(String[] args) {
        log.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            start.await();
        } catch (InterruptedException e) {
           log.error("Ошибка в методе: start.await() ", e );
        }
        log.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            finish.await();
            throw new InterruptedException ();
        } catch (InterruptedException e) {

            log.error("Ошибка в методе: finish.await() ", e );
        }
        log.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

class Car implements Runnable {
    private static final Logger log = Logger.getLogger(Car.class);
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {

        try {
            log.info(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            log.info(this.name + " готов");
            AutoRacing.start.countDown();
            AutoRacing.cb.await();
        } catch (Exception e) {
            log.error("Ошибка в методе:  Thread.sleep(500 + (int) (Math.random() * 800)) ", e );
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (AutoRacing.lockWin.tryLock())
            log.info(this.name + " WIN");

        AutoRacing.finish.countDown();
    }
}

abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}

class Road extends Stage {
    private static final Logger log = Logger.getLogger(Road.class);
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";

    }

    @Override
    public void go(Car c) {
        try {
            log.info(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            log.info(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            log.error("Ошибка в методе:  Thread.sleep(length / c.getSpeed() * 1000) ", e );
        }
    }
}

class Tunnel extends Stage {
    private static final Logger log = Logger.getLogger(Tunnel.class);
    Semaphore smp = new Semaphore(2);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {

            try {
                log.info(c.getName() + " готовится к этапу(ждет): " + description);
                smp.acquire();
                log.info(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                log.error("Ошибка в методе:  Thread.sleep(length / c.getSpeed() * 1000) ", e );
            } finally {
                log.info(c.getName() + " закончил этап: " + description);
                smp.release();
            }

    }
}

class Race {


    private ArrayList<Stage> stages;

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}