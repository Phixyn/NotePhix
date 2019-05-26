package com.phixyn.notephix

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.ArrayList

object FileHelper {
    private val FILENAME = "notephix_tasks.md"

    fun writeData(tasks: ArrayList<String>, context: Context) {
        try {
            val fos = context.openFileOutput(
                FILENAME, Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(tasks)
            oos.close()
            // fos.close();
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readData(context: Context): ArrayList<String>? {
        var tasksList: ArrayList<String>? = null

        try {
            val fis = context.openFileInput(FILENAME)
            val ois = ObjectInputStream(fis)
            tasksList = ois.readObject() as ArrayList<String>
        } catch (e: FileNotFoundException) {
            tasksList = ArrayList()
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return tasksList
    }
}
