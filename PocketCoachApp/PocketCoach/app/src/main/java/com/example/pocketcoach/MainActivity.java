package com.example.pocketcoach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    TextView textViewLogInStatus;      // to show the Log in Status of the user
    TextView textViewImprint;          // for the Impressum
    ListView listViewExercise;         // ListView for the possible exercises
    Button   buttonRecordHistory;      // Button to open the RecordHistoryActivity
    String[] exercisesArray;           // List of the possible exercises

    private FirebaseAuth firebaseAuth; // to check if the user is logged in



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLogInStatus = (TextView) findViewById(R.id.textViewLogInStatus);      // initialization of the TextView for the LogIn Status
        textViewImprint     = (TextView) findViewById(R.id.textViewImprint);          // initialization of the TextView for the Imprint
        listViewExercise    = (ListView) findViewById(R.id.listViewExercise);         // initialization of the ListView
        buttonRecordHistory = (Button)   findViewById(R.id.buttonRecordHistory);      // initialization of the Record History Button
        exercisesArray      = getResources().getStringArray(R.array.exercises_array); // get the Array from res
        firebaseAuth        = FirebaseAuth.getInstance();                             // gets the log in status of the user

        // the following if else statement is used to checkif the user is logged in
        // not logged in -> forward user to login
        if(firebaseAuth.getCurrentUser() == null) {
            textViewLogInStatus.setText(getString(R.string.not_logged_in));

            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        // logged in -> user gets access to the Main Activity
        }else {
            textViewLogInStatus.setText(getString(R.string.logged_in));
        }

        // initialization of the Array Adaper for the listViewExercise ListView
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                exercisesArray);
        listViewExercise.setAdapter(arrayAdapter); // Links ListView to the arrayAdapeter
        listViewExercise.setClickable(true);       // make the List View Items Clickable
        // add OnClickListener to the ListView
        listViewExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String exerciseString = listViewExercise.getItemAtPosition(position).toString(); // get the exercise
                // Switch case to open the different exercises with their intents
                // To Add an Exercise this swith case statement hast to be extended
                switch (exerciseString){
                    case "Pull up":
                        Toast.makeText(getApplicationContext(),
                                "pullup wurde gedrückt",
                                Toast.LENGTH_LONG).show();
                        break;
                    case "Bizeps Curl":
                        Toast.makeText(getApplicationContext(),
                                "bizeps curl wurde gedrückt",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),
                                exerciseString,
                                Toast.LENGTH_LONG).show();

                }
            }
        });

        textViewImprint.isClickable(); // to make the textView reacating on Clicks
        textViewImprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // if textViewImprint is Clicked the Imprint activity will be called
                Intent intentImprint = new Intent(MainActivity.this, ImprintActivity.class);
                startActivity(intentImprint);
            }
        });


    }

    /***********************************************************************************************
     * ON CREATE OPTIONS MENU
     * Includes the drop down menu
     **********************************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logoutmenu, menu);
        return true;
    }

    /***********************************************************************************************
     * ON OPTION ITEM SELECTED
     * gives a function to the menu
     * by pressing the menu the user is logged out
     * after the log Out Process the user is forwarded to the SignInActivity
     **********************************************************************************************/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.log_out_info_negativ),
                    Toast.LENGTH_SHORT).show();
            //forward user to login
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),
                    getString(R.string.log_out_info_positiv),
                    Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            //forward user to login
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /***********************************************************************************************
     * ON CLICK BUTTON RECORD HISTORY
     * if the Button buttonRecordHistory is pressed
     * the user is forwarded to the RecordHistory Activity
     **********************************************************************************************/
    public void onClickButtonRecordHistory(View view){
        Intent intentRecordHistory = new Intent(MainActivity.this, RecordHistory.class);
        startActivity(intentRecordHistory);
    }
}