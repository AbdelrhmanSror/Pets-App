package com.example.android.pets.data.petsUi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.pets.R;
import com.example.android.pets.data.petDataBase.pet;

import java.util.ArrayList;

import static com.example.android.pets.data.petsUi.CatalogActivity.INSERT;
import static com.example.android.pets.data.petsUi.CatalogActivity.PET;
import static com.example.android.pets.data.petsUi.CatalogActivity.STATE;
import static com.example.android.pets.data.petsUi.CatalogActivity.UPDATE;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnTouchListener {

    public static String SELECT="Select";
    public static String UNKNOWN="Unknown";
    public static String MALE="Male";
    public static String FEMALE="Female";


    /**id of item in DataBase that was clicked int recyclerview to update.*/
    private int ID;
    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter th♠e pet's breed */
    private EditText mBreedEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mGenderSpinner;
   /**integer to store the selected gender position*/
    private int mGender;
    /** integer to determine if the process for inserting or updating*/
    int state;
    /**an arraylist to store the list of item in spinner*/
    ArrayList<String>spinnerItem=new ArrayList<>();

    ArrayAdapter <String>genderSpinnerAdapter;
     String select;//if this variable was initialised with any value then user have not yet select an item from spinner.

    int count=0;//use it in on touch so delete first item in spinner for first and last time(select) only in insertion process.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        // Find all relevant views that we will need to read user input from
        mNameEditText =  findViewById(R.id.edit_pet_name);
        mBreedEditText =  findViewById(R.id.edit_pet_breed);
        mWeightEditText =  findViewById(R.id.edit_pet_weight);
        mGenderSpinner =  findViewById(R.id.spinner_gender);
        setupSpinner();



    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        genderSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItem);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        // Set the integer mSelected to the constant values
        //if the intent is meant for updating then the below method will be executed.
        SetupDataBasedOnState();
        //here we notify the adapter that the data is changed he addes the list of spinner item to the spinner layout
        genderSpinnerAdapter.notifyDataSetChanged();
        mGenderSpinner.setOnItemSelectedListener(this);
        mGenderSpinner.setOnTouchListener(this);
    }
    //call this when updating an existing item
    public void SetupDataBasedOnState()
    {
        pet mpet;
        Intent intent=getIntent();
        state=intent.getIntExtra(STATE,0);
        if(state!=UPDATE)
        {
            spinnerItem.add(SELECT);
        }
        spinnerItem.add(UNKNOWN);
        spinnerItem.add(MALE);
        spinnerItem.add(FEMALE);
        if(state== UPDATE)
        {
            mpet=(pet) intent.getSerializableExtra(PET);
            mNameEditText.setText(mpet.getName());
            mBreedEditText.setText(mpet.getBreed());
            mGenderSpinner.setSelection(mpet.getGender());
            mWeightEditText.setText(String.valueOf(mpet.getWeight()));
            ID=mpet.getId();

        }
    }

    /**
     * Get user input from editor and save new pet into database.
     */
    private void insertPet() {
        CatalogActivity.petviewModel.insert(getPet());

    }
    private void updatePet()
    {
// give id so it can update the specific row
        CatalogActivity.petviewModel.update(getPet(),ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                if(CheckingValidityOfEntries(getPet()))
                {
                    switch (state)
                    {
                    case UPDATE:updatePet();
                    break;
                    case INSERT:insertPet();
                    break;
                    }
                // Exit activity
                //if entries is valid then finish the activity.
                    finish();

                    return true;
                }
                return false;

                // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
               onBackPressed();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }
        //this overriden method is called first at first time the activity is opened and then select the first item to be "select"
        //  then whenever user touched spinner to select an item the the statement "select" disappears.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = (String) parent.getItemAtPosition(position);
        if (!TextUtils.isEmpty(selection)) {
            if (selection.equals(MALE)) {
                mGender = pet.GENDER_MALE;
            } else if (selection.equals(FEMALE)) {
                mGender = pet.GENDER_FEMALE;
            } else if(selection.equals(UNKNOWN)){
                mGender = pet.GENDER_UNKOWN;
            }
            else
            {
                select=SELECT;
            }


        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private boolean CheckingValidityOfEntries(pet mpet)
    {

        boolean valid=true;
        if(TextUtils.isEmpty(mpet.getName()))
        {
            mNameEditText.setError("name is invaild");
            valid=false;

        }
        //confirm that when user is inserting a pet he must select a gender
        //first time the editor activity is opened the the first item in spinner wil be "Select"
        // so if this item did not change this means that user has not select item from spinner
        if (spinnerItem.get(0).equals(SELECT))
        {
            ((TextView)mGenderSpinner.getSelectedView()).setError("nothing is selected");
            valid=false;
        }
        if(getPet().getWeight()<=0)
        {
            mWeightEditText.setError("Invalid weight");
            valid=false;
        }
        if(TextUtils.isEmpty(getPet().getBreed())&&valid)
        {
            mBreedEditText.setText("unknown breed");
        }

        return valid;
    }
    private pet getPet()
    {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String breedString = mBreedEditText.getText().toString().trim();
        String weightString = mWeightEditText.getText().toString().trim();
        int weight = Integer.parseInt("0"+weightString);//adding 0 to prevent number format exception if the edit text was null
        return new pet(nameString,breedString,mGender,weight);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //when user touches the spinner we must check if the count that refer to deletion procees in spinner is 0 or not
        //if not then previous deletion process has been made
        //also check the purpose of operation if it was for updating then no deletion will be made and use the selcted item of spinner that exist in database.
        if (event.getAction() == MotionEvent.ACTION_UP &&count==0&&state==INSERT) {
            spinnerItem.remove(0);
            genderSpinnerAdapter.notifyDataSetChanged();
            count++;
        }

        return false;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(R.string.DialogMessage);
        builder.setPositiveButton(R.string.positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null) dialog.dismiss();
            }
        }).setNegativeButton(R.string.negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }
}