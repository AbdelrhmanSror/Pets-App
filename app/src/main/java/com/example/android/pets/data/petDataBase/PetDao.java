package com.example.android.pets.data.petDataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
 public interface PetDao {

@Insert(onConflict=OnConflictStrategy.IGNORE)
void insert(pet mpet);

@Query("DELETE FROM pet ")
void DeleteAll();

@Delete
void Delete(pet mpet);

@Query("Update pet SET name=:name,breed=:breed,gender=:gender,weight=:weight WHERE id=:id ")
void update(String name,String breed,int gender,int weight,int id);

@Query("SELECT * from pet")
LiveData<List<pet>>getAllpets();



}
