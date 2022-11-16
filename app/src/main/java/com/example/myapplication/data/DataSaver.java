package com.example.myapplication.data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataSaver {
    public void Save(Context context, ArrayList<BookItem> data)
    {
        try {
            FileOutputStream dataStream=context.openFileOutput("data.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out=new ObjectOutputStream(dataStream);
            out.writeObject(data);
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull
    public ArrayList<BookItem> Load(Context context)
    {
        ArrayList<BookItem> data=new ArrayList<>();
        try{
            FileInputStream fileIn=context.openFileInput("data.dat");
            ObjectInputStream in=new ObjectInputStream(fileIn);
            data=(ArrayList<BookItem>) in.readObject();
            in.close();
            fileIn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
