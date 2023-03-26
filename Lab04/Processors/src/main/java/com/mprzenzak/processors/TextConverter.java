package com.mprzenzak.processors;

import com.mprzenzak.processing.Processor;
import com.mprzenzak.processing.Status;
import com.mprzenzak.processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TextConverter implements Processor {
    private final String text;
    private static int taskId = 0;
    private String result;

    public TextConverter(String text) {
        this.text = text;
    }

    public String convert() {
        return text.toUpperCase();
    }

    @Override
    public boolean submitTask(String s, StatusListener statusListener) {
        taskId++;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(
                ()->{
                    System.out.println("running");
                    atomicInteger.incrementAndGet();
                    statusListener.statusChanged(new Status(taskId,atomicInteger.get()));
                },
                1, 10, TimeUnit.MILLISECONDS);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        //result = convert();
        executor.submit(() -> {
            System.out.println("executor started");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (atomicInteger.get() >= 100) {
                    result = convert();
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
        return "Zamiana liter na wielkie";
    }

    @Override
    public String getResult() {
        return result;
    }
}
