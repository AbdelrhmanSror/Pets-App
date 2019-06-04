package com.example.android.pets.data.petDataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "pet")
 public class pet implements Serializable {

      public pet(@NonNull String name,String breed,int gender,int weight)
      {
             this.name=name;
             this.breed=breed;
             this.gender=gender;
             this.weight=weight;
      }


    protected void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
              @ColumnInfo(name="id")
              private int id=0;
              @NonNull
              @ColumnInfo(name = "name")
               private String name;
               @ColumnInfo(name = "breed")
               private String breed;
               @ColumnInfo(name ="gender")
               private int gender;
               @ColumnInfo(name = "weight")
               private int weight;


    @NonNull
    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }
    public int getId() {
        return id;
    }

    //values for gender
        public static final int GENDER_UNKOWN=0;
        public static final int GENDER_MALE=1;
        public static final int GENDER_FEMALE=2;



    }

