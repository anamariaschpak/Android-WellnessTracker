package com.example.wellnesstracker;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "note", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = CASCADE), indices = {@Index("userId")})
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String content;
    private int userId;

    public Note(String date, String content, int userId) {
        this.date = date;
        this.content = content;
        this.userId = userId;
    }

    @Ignore
    public Note(int id, int userId, String date, String content) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
