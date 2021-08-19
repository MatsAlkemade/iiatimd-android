package com.example.iiatimd_android.Fragments;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CocktailDAO {

    @Query("SELECT * FROM cocktail")
    List<Cocktail> getAll();

    @Insert
    void InsertCocktail(Cocktail cocktail);

    @Delete
    void delete(Cocktail cocktail);
}
