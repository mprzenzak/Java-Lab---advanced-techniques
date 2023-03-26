package com.mprzenzak.processing;

public interface Processor {
    boolean submitTask(String task, StatusListener sl);
    String getInfo();
    String getResult();
}
