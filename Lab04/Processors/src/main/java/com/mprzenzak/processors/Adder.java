package com.mprzenzak.processors;
import com.mprzenzak.processing.Processor;
import com.mprzenzak.processing.StatusListener;


public class Adder implements Processor {
    private final int a;
    private final int b;

    public Adder(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int add() {
        return a + b;
    }

    @Override
    public boolean submitTask(String s, StatusListener statusListener) {
        return false;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getResult() {
        return null;
    }
}
