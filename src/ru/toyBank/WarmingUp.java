package ru.toyBank;

import java.util.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class WarmingUp implements Callable<Integer> {

    CountDownLatch LATCH = new CountDownLatch(3);
    Random random = new Random();

    @Override
    public Integer call() throws Exception {
        List<DataBaseSystem> systems = Arrays.asList(new DataBaseSystem(2000), new DataBaseSystem(2500), new DataBaseSystem(3000));
        ExecutorService systemsExecutorPool = Executors.newFixedThreadPool(3);
        List<Future<Integer>> systemResults = systemsExecutorPool.invokeAll(systems);
        int result = 0;
        for(Future<Integer> system: systemResults){
            result += system.get();
        }
        systemsExecutorPool.shutdownNow();
        return result;
    }

    class DataBaseSystem implements Callable<Integer> {

        int time;

        DataBaseSystem(int time){
            this.time = time;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(time);
            LATCH.countDown();
            LATCH.await();
            return random.nextInt(100_000) + 1;
        }
    }
}
