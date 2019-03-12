/**
 * This is the main activity of the gam Thirty.
 */

package com.example.thirtygame;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Model diceModel = new Model();
    private Button rollDice;
    private Button nextRound;
    private Button newGame;
    private Spinner spinner1;
    private ImageButton image1, image2, image3, image4, image5, image6;
    private Die die1 = new Die();
    private Die die2 = new Die();
    private Die die3 = new Die();
    private Die die4 = new Die();
    private Die die5 = new Die();
    private Die die6 = new Die();

    private int mrollCount = 0;
    private int mturnCount = 0;
    private int spinselect = 0;

    private int[] results = new int[] {1,2,3,4,5,6};
    private int[] SpinnerResult = new int [] {0,0,0,0,0,0,0,0,0,0};
    private boolean[] isDieSelected = new boolean[] {false, false, false, false, false, false};
    private ImageButton[] Dices;
    private Die[] Dices2;
    private List<Integer> usedSpinner = new ArrayList<>();

    public static final String EXTRA_MESSAGE = "result vector";
    private static final String KEY_DICES = "Dice";
    private static final String KEY_DICES2 = "Dice Value";
    private static final String KEY_SPINNER = "Used Spinner";
    private static final String KEY_ROLLCOUNT = "Roll count";
    private static final String KEY_TURNCOUNT = "Turn count";
    private static final String KEY_RESULT = "Result";
    private static final String TAG = "Thirty";

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Roll Dice button
        setRollDiceListener();

        //Next Round Button
        setNextRoundListener();

        //New Game Button
        setNewGameListener();

        //Spinner to select Category for score
        setSpinnerListener();

        //Dice
        setDiceListener();

        if (savedInstanceState != null) {
            mrollCount = savedInstanceState.getInt(KEY_ROLLCOUNT);
            mturnCount = savedInstanceState.getInt(KEY_TURNCOUNT);
            SpinnerResult = savedInstanceState.getIntArray(KEY_RESULT);
            isDieSelected = savedInstanceState.getBooleanArray(KEY_DICES);
            usedSpinner = savedInstanceState.getIntegerArrayList(KEY_SPINNER);
            results = savedInstanceState.getIntArray(KEY_DICES2);
            updateDice(isDieSelected, results);
        }
    }

    /**
     * Method to Handle the spinner
     */
    private void setSpinnerListener() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter);
    }

    /**
     * Method to handle the New Game Button
     *
      */
    private void setNewGameListener() {
        //State not saved when new game button is pushed
        newGame = (Button) findViewById( R.id.newGame );
        newGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ToastDispLong("New Game Started");
                mrollCount = 0;
                mturnCount = 0;
                for (int i = 0; i < Dices.length; i++) {
                    final int k = i;
                    final int j = i + 1;
                    diceModel.reset( Dices[k], Dices2[k], j );
                    usedSpinner.clear();
                    isDieSelected[k] = false;
                    results[k] = j;
                }
                for (int i = 0; i < SpinnerResult.length; i++) {
                    SpinnerResult[i] = 0;
                }

            }
        });
    }

    /**
     * Method to handle the Next Round Button
     *
     * Button to count result and go on to next round, if the button is pressed without the player selecting
     * a category from the spinner, or if the category from the spinner has already been used this game,
     * the player is instructed to make another selection form the spinner
     */

    private void setNextRoundListener() {
        nextRound = (Button) findViewById( R.id.NextRound);
        nextRound.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mturnCount == 10) {
                    ToastDispShort("Game Over");
                }
                else {
                    if (mrollCount == 0) {
                        ToastDispShort("Press Roll Dice Button to begin round");
                    }
                    else if (usedSpinner.contains( spinselect )) {
                        ToastDispShort("Category already used, please choose another");
                    } else if(spinselect == 0) {
                        ToastDispShort("Select Category from drop down menu");
                    } else {
                        mturnCount++;
                        countResult();
                        for (int i = 0; i < Dices.length; i++) {
                            final int k = i;
                            final int j = i + 1;
                            diceModel.reset( Dices[k], Dices2[k], j );
                            results [k] = j;
                            isDieSelected [k] = false;
                        }
                        mrollCount = 0;

                    }
                }
            }
        });
    }

    /**
     * Method to handle the Roll Dice Button.
     *
     * If a player tries to roll the dice more than 3 times in one round or if the game is over
     * The player is instructed to either go on to next round or view results.
     *
     */
    private void setRollDiceListener() {
        rollDice = (Button) findViewById(R.id.rollDice);
        rollDice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mrollCount == 3 && mturnCount == 9) {
                    ToastDispLong("Last Round completed, choose category, press Next Round followed by result to view your result or New Game to" +
                            "start a new game.");
                }
                else if (mrollCount == 3) {
                    ToastDispLong("Round completed, choose category and press Next Round button to save your result and start next round.");
                } else if(mturnCount == 10) {
                    ToastDispLong("Game over, press Result to view your result or New Game to start New Game.");
                }
                else {
                    mrollCount++;
                    image1 = diceModel.diceRoll( image1, die1 ); results[0] = die1.getValue();
                    image2 = diceModel.diceRoll( image2, die2 ); results[1] = die2.getValue();
                    image3 = diceModel.diceRoll( image3, die3 ); results[2] = die3.getValue();
                    image4 = diceModel.diceRoll( image4, die4 ); results[3] = die4.getValue();
                    image5 = diceModel.diceRoll( image5, die5 ); results[4] = die5.getValue();
                    image6 = diceModel.diceRoll( image6, die6 ); results[5] = die6.getValue();

                }
            }
        });
    }

    /**
     * Method to handle the dice imagebuttons
     *
     * If a player tries to select a die before the dice have been rolled, they are instructed
     * to press the roll dice button first
     */
    private void setDiceListener() {
        image1 = (ImageButton) findViewById(R.id.imageButton);
        image2 = (ImageButton) findViewById(R.id.imageButton2);
        image3 = (ImageButton) findViewById(R.id.imageButton3);
        image4 = (ImageButton) findViewById(R.id.imageButton4);
        image5 = (ImageButton) findViewById(R.id.imageButton5);
        image6 = (ImageButton) findViewById(R.id.imageButton6);

        Dices = new ImageButton[]{image1, image2, image3, image4, image5, image6};
        Dices2 = new Die[] {die1, die2, die3, die4, die5, die6};

        for (int i = 0; i < Dices.length; i++) {
            final int k = i;
            Dices[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Attempt to selct a die before the dice have been rolled prompts the player to roll the dice
                    if (mrollCount == 0 && mturnCount > 0) {
                        ToastDispShort("Press Roll Dice Button to begin round");
                    } else if(mrollCount == 0) {
                        ToastDispShort("Press Roll Dice Button to begin the game");
                    } else {
                        diceModel.toggleColor(Dices[k], Dices2[k] );
                        isDieSelected[k] = Dices2[k].isRed();
                    }
                }
            });
        }
    }

    /**
     * Method to calculate the result of a round
     */
    public void countResult () {
        for (int i = 0; i < 6; i++) {
            results[i] = Dices2[i].getValue();
            isDieSelected[i] = Dices2[i].isRed();
        }
        updateResult();
    }

    /**
     * Method to update the result displayed in the result view
     */
    public void updateResult() {
        int selection = spinselect;
        int res1 = diceModel.calcResult(results, isDieSelected, selection );
        SpinnerResult [selection - 1] = res1;
        if (res1 == -1) {
            ToastDispShort("The last round gave you zero points");
        }
        else {
            String string = Integer.toString( res1 );
            ToastDispShort("The last round gave you " + string + " points");
        }
        usedSpinner.add(selection);
    }

    /**
     * Method to view the results in the result view
     * @param view
     */
    public void viewResult(View view) {
        Intent intent = new Intent(this, Results.class);
        intent.putExtra( EXTRA_MESSAGE, SpinnerResult );
        startActivity(intent);
    }

    /**
     * Method to update Dice when activity is recreated
     * @param color
     * @param values
     */
    public void updateDice(boolean [] color, int [] values) {
        for (int i = 0; i < 6; i++) {
            Dices2[i].setValue(values[i]);
            if (color[i]) {
                diceModel.toggleColor(Dices[i], Dices2[i]);
            } else {
                diceModel.toggleColor(Dices[i], Dices2[i]);
                diceModel.toggleColor(Dices[i], Dices2[i]);
            }
        }
    }

    /**
     * Method used to display short toasts during the game
     * @param string
     */
    public void ToastDispShort (String string) {
        Toast toast = Toast.makeText( getApplicationContext(),
                string, Toast.LENGTH_SHORT );
        toast.setGravity( Gravity.CENTER, 0, 0 );
        toast.show();
    }

    /**
     * Method used to display long toasts during the game
     * @param string
     */
    public void ToastDispLong (String string) {
        Toast toast = Toast.makeText( getApplicationContext(),
                string, Toast.LENGTH_LONG );
        toast.setGravity( Gravity.CENTER, 0, 0 );
        toast.show();
    }

    /**
     * Method to determine selection from spinner
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinselect = spinner1.getSelectedItemPosition();
    }

    /**
     * If nothing is selected from spinner
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onSaveInstanceState() called");
        savedInstanceState.putInt(KEY_ROLLCOUNT, mrollCount);
        savedInstanceState.putInt(KEY_TURNCOUNT, mturnCount);
        savedInstanceState.putIntArray(KEY_RESULT, SpinnerResult);
        savedInstanceState.putIntArray(KEY_DICES2, results);
        savedInstanceState.putBooleanArray(KEY_DICES, isDieSelected);
        savedInstanceState.putIntegerArrayList(KEY_SPINNER, (ArrayList<Integer>) usedSpinner);
        super.onSaveInstanceState(savedInstanceState);


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "RestoreInstanceState() called");
        // Restore state members from saved instance
        mrollCount = savedInstanceState.getInt(KEY_ROLLCOUNT);
        mturnCount = savedInstanceState.getInt(KEY_TURNCOUNT);
        SpinnerResult = savedInstanceState.getIntArray(KEY_RESULT);
        isDieSelected = savedInstanceState.getBooleanArray(KEY_DICES);
        usedSpinner = savedInstanceState.getIntegerArrayList(KEY_SPINNER);
        results = savedInstanceState.getIntArray(KEY_DICES2);
        super.onRestoreInstanceState(savedInstanceState);
        //updateDice(isDieSelected, results);
    }

}
