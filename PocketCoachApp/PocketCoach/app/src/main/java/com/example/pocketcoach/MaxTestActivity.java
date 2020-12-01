package com.example.pocketcoach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MaxTestActivity extends AppCompatActivity {

    EditText editTextWeight;
    EditText editTextRep;

    Double maxRepAllowed = 33.33;
    Integer repCount;
    Double weight;
    Double oneRepMax;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_test);

        editTextWeight = (EditText) findViewById(R.id.editTextNumberWeight);
        editTextRep = (EditText) findViewById(R.id.editTextNumberRep);

    }

    public void onClickButtonCalc1RM (View view){

        repCount = Integer.parseInt(editTextRep.getText().toString());
        weight = Double.parseDouble(editTextWeight.getText().toString());

        if(repCount > 20) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.warning_to_high_reps),
                    Toast.LENGTH_LONG).show();
        }else{
            oneRepMax = calculateOneRepMax(maxRepAllowed, repCount, weight);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.return_text_one_rep_max) + "" +
                    String.valueOf(oneRepMax) + "" +
                    getString(R.string.kilogramm),
                    Toast.LENGTH_LONG).show();
        }

    }

    private double calculateOneRepMax (Double maxRepAllowed,Integer repCount, Double weight ){
        //calculates the percentage of the oneRepMax
        Double percentageOf1RM = (1- (repCount/maxRepAllowed))*100;
        Double oneRepMax = (weight*100)/percentageOf1RM;
        return oneRepMax;
    }

}