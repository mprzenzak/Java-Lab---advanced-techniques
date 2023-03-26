package com.mprzenzak.processors;

import com.mprzenzak.processing.Processor;
import com.mprzenzak.processing.Status;
import com.mprzenzak.processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Coder implements Processor {
    private final String text;
    private final int key;
    private static int taskId = 0;
    private String result;

    public Coder(String task) {
        char lastChar = task.charAt(task.length() - 1);
        key = Character.getNumericValue(lastChar);
        text = task.substring(0, task.length() - 1);
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c + key);
                if (c > 'z') {
                    c = (char) (c - 26);
                }
            } else if (c >= 'A' && c <= 'Z') {
                c = (char) (c + key);
                if (c > 'Z') {
                    c = (char) (c - 26);
                }
            }
            sb.append(c);
        }
        return sb.toString();
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
        executor.submit(() -> {
            System.out.println("executor started");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (atomicInteger.get() >= 100) {
                    result = encode();
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
        return "Szyfrowanie tekstu";
    }

    @Override
    public String getResult() {
        return result;
    }
}
