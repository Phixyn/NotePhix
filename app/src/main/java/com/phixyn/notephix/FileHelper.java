package com.phixyn.notephix;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {

    private static final String FILENAME = "notephix_tasks.md";

    public static void writeData(ArrayList<String> tasks, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
            // fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readData(Context context) {
        ArrayList<String> tasksList = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasksList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            tasksList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return tasksList;
    }
}
