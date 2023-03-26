package com.mprzenzak.gui.controllers;

import com.mprzenzak.application.CustomClassLoader;
import com.mprzenzak.application.MyStatusListener;
import com.mprzenzak.processing.StatusListener;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.stage.DirectoryChooser;

public class MainController implements Initializable {

    @FXML
    private TextField taskTextField;

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private ListView<String> taskListView;

    @FXML
    private ListView<String> statusListView;

    @FXML
    private ListView<String> resultListView;

    private final ObservableList<String> tasks = FXCollections.observableArrayList();
    private final ObservableList<String> statuses = FXCollections.observableArrayList();
    private final ObservableList<String> results = FXCollections.observableArrayList();
    private final Map<String, Class<?>> loadedClasses = new HashMap<>();

    private String classesDirectoryPath = "C:\\Users";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskListView.setItems(tasks);
        statusListView.setItems(statuses);
        resultListView.setItems(results);
        try {
            loadClasses();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void loadClasses() throws MalformedURLException {
        File directory = new File(classesDirectoryPath);
        try {
            List<Class<?>> classes = loadClassesFromDirectory(directory);
            for (Class<?> clazz : classes) {
                String info = getClassInfo(clazz);
                classComboBox.getItems().add(clazz.getSimpleName() + " - " + info);
                loadedClasses.put(clazz.getSimpleName(), clazz);
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addTask(ActionEvent event) {
        tasks.add(taskTextField.getText());
        statuses.add("Gotowe do wykonania");
        results.add("");
    }

    @FXML
    private void executeTask(ActionEvent event) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String selectedTask = taskListView.getSelectionModel().getSelectedItem();
        statuses.set(tasks.indexOf(selectedTask), "Przetwarzane...");
        String selectedClass = classComboBox.getSelectionModel().getSelectedItem().split(" - ")[0];
        Class<?> clazz = loadedClasses.get(selectedClass);
        Method submitTaskMethod;
        boolean successful_start;

        submitTaskMethod = clazz.getDeclaredMethod("submitTask", String.class, StatusListener.class);
        Constructor<?> cp = clazz.getDeclaredConstructor(String.class);
        Object obj = cp.newInstance(selectedTask);
        successful_start = (boolean) submitTaskMethod.invoke(obj, "", new MyStatusListener());

        if (successful_start)
            System.out.println("Processing started correctly");
        else
            System.out.println("Processing ended with an error");

        Method getResultMethod = clazz.getDeclaredMethod("getResult");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            String result = null;
            while (true) {
                try {
                    result = (String) getResultMethod.invoke(obj);
                } catch (IllegalAccessException | IllegalArgumentException |
                         InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (result != null) {
                    String finalResult = result;
                    Platform.runLater(() -> {
                        int taskIndex = tasks.indexOf(selectedTask);
                        statuses.set(taskIndex, "Wykonane!");
                        results.set(taskIndex, finalResult);
                    });
                    executor.shutdown();
                    break;
                }
            }
        });
    }

    @FXML
    private void unloadClass(ActionEvent event) {
        String selectedClass = classComboBox.getSelectionModel().getSelectedItem();
        loadedClasses.remove(selectedClass);
        classComboBox.getItems().remove(selectedClass);
        System.gc();
    }

    private List<Class<?>> loadClassesFromDirectory(File directory) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        try (CustomClassLoader classLoader = new CustomClassLoader(directory.toURI().toURL())) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".class") && !file.getName().contains("Main")) {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    classes.add(classLoader.loadClass(className));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classes;
    }

    private String getClassInfo(Class<?> c) {
        try {
            Constructor<?> constructor;
            Object obj = null;
            switch (c.getName()) {
                case "com.mprzenzak.processors.Adder" -> {
                    constructor = c.getDeclaredConstructor(String.class);
                    obj = constructor.newInstance("0+0");
                }
                case "com.mprzenzak.processors.Coder" -> {
                    constructor = c.getDeclaredConstructor(String.class);
                    obj = constructor.newInstance("0");
                }
                case "com.mprzenzak.processors.TextConverter" -> {
                    constructor = c.getDeclaredConstructor(String.class);
                    obj = constructor.newInstance("");
                }
            }
            Method getInfoMethod = c.getMethod("getInfo");
            var a = (String) getInfoMethod.invoke(obj);
            return (String) getInfoMethod.invoke(obj);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public void openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDirectory = directoryChooser.showDialog(null);
        classesDirectoryPath = selectedDirectory.getAbsolutePath();
        initialize(null, null);
    }
}
