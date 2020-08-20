package com.example.wellnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert
    void insertExercise(Exercise newExercise);

    @Query("Select * from exercise")
    List<Exercise> getAllExercises();
}
