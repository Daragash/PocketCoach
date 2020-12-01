package com.example.pocketcoach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/***************************************************************************************************
 ***************************************************************************************************
 * MAIN ACTIVITY
 * this Class is the Main Activity of the PocketCoach App
 * from this Acitivtiy you can go to any other Activity of the App
 * the Main Activity is used to select an exercise or go to the Record History
 * There is a menu Button to Log out and clickable Text to reat the Imprint
 ***************************************************************************************************
 **************************************************************************************************/
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    TextView textViewLogInStatus;      // to show the Log in Status of the user
    TextView textViewImprint;          // for the Impressum
    ListView listViewExercise;         // ListView for the possible exercises
    Button   buttonRecordHistory;      // Button to open the RecordHistoryActivity
    String[] exercisesArray;           // List of the possible exercises

    private FirebaseAuth firebaseAuth; // to check if the user is logged in

    /***********************************************************************************************
     * ON CREATE
     * This function runs when the Activity is created
     **********************************************************************************************/
    @RequiresApi(api = Build.VERSION_CODES.M)
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

        buttonRecordHistory.setBackgroundColor(getColor(R.color.pocket_coach_blue)); // add color to the Button

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
                this, android.R.layout.simple_list_item_1, exercisesArray){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position,convertView,parent);
                // set Color to white of the ListVew items
                item.setTextColor(getColor(R.color.white));
                item.setBackgroundColor(getColor(R.color.black));
                item.setTypeface(item.getTypeface(), Typeface.BOLD);
                return item;
            }
        };

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
                        // go to ChoosePullUp Activity with intent
                        Intent intentPullUp = new Intent(
                                MainActivity.this,
                                ChoosePullUpActivity.class);
                        startActivity(intentPullUp);
                        break;
                    case "Bizeps Curl":
                        // go to ChooseBizepsCurle Activity with intent
                       Intent intentBizepsCurl = new Intent(
                               MainActivity.this,
                               ChooseBizepsCurlActivity.class);
                       startActivity(intentBizepsCurl);
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