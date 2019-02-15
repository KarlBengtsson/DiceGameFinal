/**
 * Result class that displays result in the result window
 */

package com.example.thirtygame;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Results extends AppCompatActivity {
    private int tot = 0;
    private String result;
    private String totalResult;
    private int[] SpinnerResult = new int [] {0,0,0,0,0,0,0,0,0,0};
    private TextView low, four, five, six, seven, eight, nine, ten, eleven, twelve, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_results );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        SpinnerResult = intent.getIntArrayExtra( MainActivity.EXTRA_MESSAGE );

        low = (TextView) findViewById( R.id.resultLow );
        four = (TextView) findViewById( R.id.result4 );
        five = (TextView) findViewById( R.id.result5 );
        six = (TextView) findViewById( R.id.result6 );
        seven = (TextView) findViewById( R.id.result7 );
        eight = (TextView) findViewById( R.id.result8 );
        nine = (TextView) findViewById( R.id.result9 );
        ten = (TextView) findViewById( R.id.result10 );
        eleven = (TextView) findViewById( R.id.result11 );
        twelve = (TextView) findViewById( R.id.result12 );
        total = (TextView) findViewById( R.id.resultTot );

        updateResult();

    }

    /**
     * Method to update teh result displayed in the result view
     */
    public void updateResult() {
        for (int i = 0; i < 10; i++) {
            int score = SpinnerResult[i];
            if (score == 0) {
                result = "";
            }
            else if (score == -1){
                result = "0";
                score = 0;
            }
            else {
                result = Integer.toString(score);
            }
            tot += score;
            int spinner = i + 3;
            switch (spinner) {
                case 3:
                    low.setText( result );
                    break;
                case 4:
                    four.setText( result );
                    break;
                case 5:
                    five.setText( result );
                    break;
                case 6:
                    six.setText( result );
                    break;
                case 7:
                    seven.setText( result );
                    break;
                case 8:
                    eight.setText( result );
                    break;
                case 9:
                    nine.setText( result );
                    break;
                case 10:
                    ten.setText( result );
                    break;
                case 11:
                    eleven.setText( result );
                    break;
                case 12:
                    twelve.setText( result );
                    break;

            }
        }
        totalResult = Integer.toString( tot );
        total.setText( totalResult );

    }
}

