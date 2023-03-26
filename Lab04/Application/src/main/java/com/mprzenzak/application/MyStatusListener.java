package com.mprzenzak.application;

import com.mprzenzak.processing.Status;
import com.mprzenzak.processing.StatusListener;

public class MyStatusListener implements StatusListener {

    @Override
    public void statusChanged(Status s) {
        System.out.println("Progress:" + s.getProgress() + " TaskId:" + s.getTaskId());
    }
}
