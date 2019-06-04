package com.example.android.pets.data.petDataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {pet.class},version = 2,exportSchema = false)
  public abstract class petDataBase extends RoomDatabase {
    public abstract PetDao petdao();
    private static volatile petDataBase Instance;
    public static  petDataBase getDataBase(final Context context) {
        if (Instance == null) {
            synchronized (petDataBase.class) {
                Instance = Room.databaseBuilder(context.getApplicationContext(),
                            petDataBase.class, "pet").fallbackToDestructiveMigration()
                            .build();

            }
        }
        return Instance;
    }




}
