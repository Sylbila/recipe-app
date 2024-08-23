package com.example.projeckuas;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insert(Recipe recipe);

    @Query("SELECT * FROM recipe")
    List<Recipe> getAllRecipes();
}
