package com.freimanvs.restaurants.sql;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public static void writeToFile(String what, String where) {
        writeToFile(what, Paths.get(where));
    }

    public static void writeToFile(String what, Path where) {
        try (BufferedWriter bw = Files.newBufferedWriter(where)) {
            bw.write(what);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(String path) {
        readFromFile(Paths.get(path));
    }

    public static void readFromFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            br.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String what, File where) {
        createNewFile(where);
        try(OutputStream outputStream = new FileOutputStream(where);
            Writer writer = new OutputStreamWriter(outputStream)) {
            writer.write(what);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createNewFile(File file) {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
