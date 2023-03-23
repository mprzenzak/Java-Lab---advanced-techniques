package com.mprzenzak.processors;

import com.mprzenzak.processing.Processor;
import com.mprzenzak.processing.StatusListener;

public class TextConverter implements Processor {
    private final String text;

    public TextConverter(String text) {
        this.text = text;
    }

    public String convert() {
        return text.toUpperCase();
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
