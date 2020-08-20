package com.example.wellnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public void insertUser(User newUser);

    @Query("Select * from user")
    public List<User> getAllUsers();

    @Query("Select * from user where username=:usernameToFind")
    public User findUserByUsername(String usernameToFind);

    @Query("Select * from user where username=:usernameToFind and password=:passwordToFind")
    public User findUserByUsernameAndPassword(String usernameToFind, String passwordToFind);

}
