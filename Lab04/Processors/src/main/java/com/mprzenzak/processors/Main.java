package com.mprzenzak.processors;

import com.mprzenzak.processing.Processor;
import com.mprzenzak.processing.StatusListener;
import com.mprzenzak.application.MyStatusListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
//        TextConverter textConverter = new TextConverter("ttest");
//        textConverter.submitTask("test", null);
//        Thread.sleep(20000);
//        var a = textConverter.getResult();
//        System.out.println(a);
        Class<?> p = Adder.class;
        try {
            Constructor<?> cp = p.getDeclaredConstructor(int.class, int.class);

            Object o = cp.newInstance(2,3);

            Method getInfoMethod = p.getDeclaredMethod("getInfo");
            System.out.println((String)getInfoMethod.invoke(o));

            Method submitTaskMethod = p.getDeclaredMethod("submitTask", String.class, StatusListener.class);

            boolean b = (boolean) submitTaskMethod.invoke(o, "Tekst na wejœcie", new MyStatusListener());

            if (b)
                System.out.println("Processing started correctly");
            else
                System.out.println("Processing ended with an error");

            Method getResultMethod = p.getDeclaredMethod("getResult");

            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.submit(() -> {
                String result = null;
                while (true) {
                    try {
                        Thread.sleep(800);
                        result = (String) getResultMethod.invoke(o);
                    } catch (InterruptedException | IllegalAccessException | IllegalArgumentException |
                             InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (result != null) {
                        System.out.println("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
                        System.out.println("Result: " + result);
                        executor.shutdown();
                        break;
                    }
                }
            });

            System.out.println("main FINISHED");
            var result = (String) getResultMethod.invoke(o);
            System.out.println(result);

        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                 | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}