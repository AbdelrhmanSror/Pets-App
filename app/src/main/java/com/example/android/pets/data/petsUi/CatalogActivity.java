package com.example.android.pets.data.petsUi;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.R;
import com.example.android.pets.data.petDataBase.pet;
import com.example.android.pets.data.petsModel.petViewModel;

import java.util.List;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements View.OnClickListener,petAdapter.onitemclick {
    static petViewModel petviewModel;
     TextView noword;
     petAdapter p;
     public static final int INSERT=0;// TO know what is the purpose of process insert or update.
     public static final int UPDATE=1;
     public static final String STATE="state";
     public static final String PET="pet";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        updateUi();

    }


    public void updateUi()
    {
        noword=findViewById(R.id.noword);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        RecyclerView r=findViewById(R.id.recylerview);
        p=new petAdapter(this);
        r.setAdapter(p);
        r.addItemDecoration(itemDecorator);
        helper.attachToRecyclerView(r);
        r.setLayoutManager(new LinearLayoutManager(this));
        petviewModel = ViewModelProviders.of(this).get(petViewModel.class);
        petviewModel.GetAllpets().observe(this,  new Observer<List<pet>>() {
            @Override
            public void onChanged(@Nullable List<pet> pets) {
                p.insert(pets);
                VisibiltyofWord();

            }
        });

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case R.id.action_delete_all_entries:
                petviewModel.deleteAll();
                  return true;
        }
        return super.onOptionsItemSelected(item);
    }

//listner when user clicks on floating action point to insert.
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
        intent.putExtra(STATE,INSERT);
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
ItemTouchHelper helper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }
//when user swipe an item in recycler view the item will be deleted.
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        petviewModel.delete(p.getPetAtposition(viewHolder.getAdapterPosition()));
    }
});

    /**
     * this method is responsible for showing  the word "no word "when there is no item in recycler view
     * and it hides when there are items.
     */
    public void VisibiltyofWord()
    {
        if(p.getItemCount()>0)
        {
            noword.setVisibility(View.GONE);
        }
         else
        {
            noword.setVisibility(View.VISIBLE);
        }

    }

    /**listner when user click on item to update we send an intent with the item details to EditorActivity
     *
     * @param pos is the postion of the item that is being clicked
     */
    @Override
    public void onClick(int pos) {
        petviewModel.setItemposition(pos);
        Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
        intent.putExtra(STATE,UPDATE);
        Bundle bundle=new Bundle();
        bundle.putSerializable(PET,p.getPetAtposition(pos));
        intent.putExtras(bundle);
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
}