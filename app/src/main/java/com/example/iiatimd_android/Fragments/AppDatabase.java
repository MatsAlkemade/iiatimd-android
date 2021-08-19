package com.example.iiatimd_android.Fragments;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cocktail.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CocktailDAO cocktailDAO();

     private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null ){
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context){
        return Room.databaseBuilder(context,AppDatabase.class,"cocktails").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }
}
