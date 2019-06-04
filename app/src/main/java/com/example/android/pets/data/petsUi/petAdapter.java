package com.example.android.pets.data.petsUi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.pets.R;
import com.example.android.pets.data.petDataBase.pet;

import java.util.List;

public class petAdapter extends RecyclerView.Adapter<petAdapter.ViewHolder> {
Context context;
List<pet>pets;
static onitemclick listner;
public petAdapter(Context context)
{
    this.context=context;
    listner=((CatalogActivity)context);
}
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name;
        TextView breed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.text_view_pet);
            breed=itemView.findViewById(R.id.text_view_breed);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listner.onClick(getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public petAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root= LayoutInflater.from(context).inflate(R.layout.adapter,viewGroup,false);

        return (new ViewHolder(root));
    }


    @Override
    public void onBindViewHolder(@NonNull petAdapter.ViewHolder viewHolder, int i) {
        TextView name=viewHolder.name;
        TextView breed=viewHolder.breed;
        if(pets!=null)
    {
        name.setText(pets.get(i).getName());
        breed.setText(pets.get(i).getBreed());

    }
    }
    public void insert(List<pet> pets)
    {
        DiffUtil.DiffResult petDiff=DiffUtil.calculateDiff(new petDiffUtils(this.pets,pets));
        this.pets=pets;
        petDiff.dispatchUpdatesTo(this);
    }


    @Override
    public int getItemCount()
    {
        if(pets!=null)
        {
        return pets.size(); }
    else
    {
        return 0;
    }

    }

    public List<pet> getPets() {
        return pets;
    }

    public pet getPetAtposition(int position)
{
    return pets.get(position);
}
public interface onitemclick
{
     void onClick(int position);
}
}
