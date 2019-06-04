package com.example.android.pets.data.petsModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.pets.data.petDataBase.PetDao;
import com.example.android.pets.data.petDataBase.pet;
import com.example.android.pets.data.petDataBase.petDataBase;

import java.util.List;

  class petRepository {
    private PetDao petDao;
    private LiveData<List<pet>>getAllpets;
    public petRepository(Application context)
    {
        petDataBase db= petDataBase.getDataBase(context);
        petDao=db.petdao();
        getAllpets=petDao.getAllpets();
    }

    public LiveData<List<pet>> GetAllpets() {
        return getAllpets;
    }

    void insert(pet mPet)
    {
        new insertAsyncTask(petDao).execute(mPet);
    }
    void deleteAll()
    {
        new deleteAllAsyncTask(petDao).execute();
    }
    void delete(pet mpet)
    {
        new deleteAsyncTask(petDao).execute(mpet);
    }
    void update(pet mpet,int id)
    {
        new updateAsyncTask(petDao,id).execute(mpet);
    }

    private static class insertAsyncTask extends AsyncTask<pet,Void,Void>
    {
        PetDao mpetDao;
        public insertAsyncTask(PetDao petDao)
        {
            this.mpetDao=petDao;
        }
        @Override
        protected Void doInBackground(pet... pets) {
            mpetDao.insert(pets[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>
    {
        PetDao mpetDao;
        public deleteAllAsyncTask(PetDao petDao)
        {
            this.mpetDao=petDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mpetDao.DeleteAll();
            return null;
        }
    }
      private static class deleteAsyncTask extends AsyncTask<pet,Void,Void>
      {
          PetDao mpetDao;
          public deleteAsyncTask(PetDao petDao)
          {
              this.mpetDao=petDao;
          }

          @Override
          protected Void doInBackground(pet... pets) {
              mpetDao.Delete(pets[0]);
              return null;
          }
      }
      private static class updateAsyncTask extends AsyncTask<pet,Void,Void>
      {
          PetDao mpetDao;
          int id;
          public updateAsyncTask(PetDao petDao,int id)
          {
              this.mpetDao=petDao;
              this.id=id;
          }

          @Override
          protected Void doInBackground(pet... pets) {

              mpetDao.update(pets[0].getName(),pets[0].getBreed(),pets[0].getGender(),pets[0].getWeight(),id);
              return null;
          }
      }

}
