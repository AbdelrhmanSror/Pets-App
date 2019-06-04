package com.example.android.pets.data.petsUi;

import android.support.v7.util.DiffUtil;

import com.example.android.pets.data.petDataBase.pet;

import java.util.List;

public class petDiffUtils extends DiffUtil.Callback {
    List<pet> oldlist;
    List<pet> newlist;
    public petDiffUtils(List<pet>oldpet, List<pet>newpet)
    {
        this.oldlist =oldpet;
        this.newlist =newpet;
    }


    @Override
    public int getOldListSize() {
        return oldlist==null?0:oldlist.size();
    }

    @Override
    public int getNewListSize() {
        return newlist.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItempos, int newItempos) {
        return oldlist.get(oldItempos).getId()== newlist.get(newItempos).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItempos, int newItempos) {
        return oldlist.get(oldItempos)== newlist.get(newItempos);
    }


}
