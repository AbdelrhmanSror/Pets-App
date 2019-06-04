package com.example.android.pets.data.petsModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.pets.data.petDataBase.pet;

import java.util.List;

public class petViewModel extends AndroidViewModel {
    private petRepository mpetRespository;
    private LiveData<List<pet>>getAllpets;
    private int itemposition=0;
    public petViewModel(@NonNull Application application)
    {
        super(application);
        mpetRespository=new petRepository(application);
        getAllpets=mpetRespository.GetAllpets();
    }

    public LiveData<List<pet>> GetAllpets() {
        return getAllpets;
    }
    public void deleteAll()
    {
        mpetRespository.deleteAll();
    }
    public void delete(pet mpet) { mpetRespository.delete(mpet); }
    public void insert(pet mPet)
    {

        mpetRespository.insert(mPet);
    }
    public void update(pet mpet,int id) { mpetRespository.update(mpet,id); }
    public int getItemposition()
    {
        return itemposition;
    }

    public void setItemposition(int itemposition) {
        this.itemposition = itemposition;
    }
}
