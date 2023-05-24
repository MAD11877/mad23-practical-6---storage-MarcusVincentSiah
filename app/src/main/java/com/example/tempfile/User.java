package com.example.tempfile;

import android.icu.lang.UProperty;

public class User {
    //static int idStatic = 1;
    public User(int id, String name, String description, boolean followed){
        this.id= id;
        this.name = name;
        this.description = description;
        this.followed = followed;
    }

    public User(String name, String description, boolean followed){
        this.name = name;
        this.description = description;
        this.followed = followed;
    }

    public String name;
    public String getName(){
        return name;
    }
    public void setName(String newName){
        this.name = newName;
    }

    public String description;
    public String getDescription(){
        return description;
    }
    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public int id;
    public int getId(){
        return id;
    }
    public void setId(int newId){
        this.id = newId;
    }

    public boolean followed;
    public boolean getFollowed(){
        return followed;
    }
    public void setFollowed(boolean newFollowed){
        this.followed = newFollowed;
    }

}
