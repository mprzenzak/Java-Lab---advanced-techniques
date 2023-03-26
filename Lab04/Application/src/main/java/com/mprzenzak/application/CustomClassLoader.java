package com.mprzenzak.application;

import java.io.*;
import java.net.URL;

public class CustomClassLoader extends ClassLoader implements AutoCloseable {

    private final String classesPath;

    public CustomClassLoader(URL basePath) {
        this.classesPath = basePath.getPath();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException("Class " + name + " not found.");
        }
        return defineClass("com.mprzenzak.processors." + name, classData, 0, classData.length);
    }

    private byte[] loadClassData(String className) {
        String fileName = className.replace('.', File.separatorChar) + ".class";
        File classFile = new File(classesPath, fileName);
        if (!classFile.exists()) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(classFile);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
