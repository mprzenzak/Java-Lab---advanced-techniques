package com.mprzenzak;
import org.graalvm.polyglot.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CellularAutomaton {
    private Context context;
    private Value getState;
    private Value setState;
    private Value initialize;
    private Value step;

    public CellularAutomaton(String automatonName) throws IOException {
        loadScript(automatonName);
    }

    public void loadScript(String automatonName) throws IOException {
        context = Context.create();
        String jsCode = new String(Files.readAllBytes(Paths.get("scripts/" + automatonName + ".js")));
        context.eval("js", jsCode);
        getState = context.getBindings("js").getMember("getState");
        setState = context.getBindings("js").getMember("setState");
        initialize = context.getBindings("js").getMember("initialize");
        step = context.getBindings("js").getMember("step");
    }

    public void unloadScript() {
        context.close();
        context = null;
        getState = null;
        setState = null;
        initialize = null;
        step = null;
    }

    public boolean[][] getState() {
        Value result = getState.execute();
        boolean[][] state = new boolean[(int) result.getArraySize()][];
        for (int i = 0; i < state.length; i++) {
            Value row = result.getArrayElement(i);
            state[i] = new boolean[(int) row.getArraySize()];
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = row.getArrayElement(j).asBoolean();
            }
        }
        return state;
    }

    public void setState(boolean[][] state) {
        Value jsArray = context.eval("js", "[]");
        for (boolean[] row : state) {
            Value jsRow = context.eval("js", "[]");
            for (boolean cell : row) {
                jsRow.executeVoid("push", cell);
            }
            jsArray.executeVoid("push", jsRow);
        }
        setState.execute(jsArray);
    }

    public void initialize(int size) {
        if (initialize.canExecute()) {
            initialize.execute(size);
        }
    }

    public void step() {
        if (step.canExecute()) {
            step.execute();
        }
    }
}
