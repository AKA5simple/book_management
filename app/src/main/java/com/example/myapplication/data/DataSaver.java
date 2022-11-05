package com.example.myapplication.data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DataSaver {
    public void Save(Context context, ArrayList<ShopItem> data)
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
    public ArrayList<ShopItem> Load(Context context)
    {
        ArrayList<ShopItem> data=new ArrayList<>();
        try{
            FileInputStream fileIn=context.openFileInput("data.dat");
            ObjectInputStream in=new ObjectInputStream(fileIn);
            data=(ArrayList<ShopItem>) in.readObject();
            in.close();
            fileIn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
