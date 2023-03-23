package com.mprzenzak.processors;

import com.mprzenzak.processing.Processor;
import com.mprzenzak.processing.StatusListener;

public class Coder implements Processor {
    private final String text;
    private final int key;

    public Coder(String text, int key) {
        this.text = text;
        this.key = key;
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
