package com.mprzenzak.processors;

import com.mprzenzak.processing.Processor;
import com.mprzenzak.processing.Status;
import com.mprzenzak.processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Adder implements Processor {
    private String result;
    private int a;
    private int b;
    private static int taskId = 0;

    public Adder(String equation) {
        String[] parts = equation.split("\\+");

        this.a = Integer.parseInt(parts[0]);
        this.b = Integer.parseInt(parts[1]);
    }

    public String add() {
        return String.valueOf(a + b);
    }

    @Override
    public boolean submitTask(String s, StatusListener statusListener) {
        taskId++;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(
                () -> {
                    atomicInteger.incrementAndGet();
                    statusListener.statusChanged(new Status(taskId, atomicInteger.get()));
                },
                1, 10, TimeUnit.MILLISECONDS);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            System.out.println("executor started");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (atomicInteger.get() >= 100) {
                    result = add();
                    executorService.shutdown();
                    executor.shutdown();
                    break;
                }
            }
        });
        return true;
    }

    @Override
    public String getInfo() {
        return "Dodawanie dwóch liczb";
    }

    @Override
    public String getResult() {
        return result;
    }
}
