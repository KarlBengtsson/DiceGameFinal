/**
 * Model of the game, rolls dice, updates the diecolor and calculates the results
 */

package com.example.thirtygame;

import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Model {
    public static final Random rand = new Random();
    private int value = 0;
    private int Result;

    public Model() {

    }

    /**
     * Method called upon when the Roll Dice button is clicked, first check die color, then roll
     * if die is selected(red) the die is not rolled
     * @param image
     * @param die
     * @return image of new die
     */
    public ImageButton diceRoll (ImageButton image, Die die) {
        if (!die.isWhite()) {
            return image;
        } else {
            value = rand.nextInt( 6 ) + 1;
            die.setValue(value);
            die.colorToWhite(image, value);
            return image;
        }
    }

    /**
     * MEthod to change the color of a die when it is selected
     * @param image
     * @param die
     * @return image of new die
     */
    public ImageButton toggleColor(ImageButton image, Die die) {
        die.toggleColor();
        int value = die.getValue();
        if (!die.isWhite()) {
            die.colorToRed(image, value);
        }
        else {
            die.colorToWhite(image, value);
        }
        return image;
    }

    /**
     * Reset the dice
     * @param image
     * @param die
     * @param i
     * @return image of new die
     */
    public ImageButton reset(ImageButton image, Die die, int i) {
        die.setValue(i);
        die.reset();
        die.colorToWhite( image, i );
        return image;
    }

    /**
     * Calculate the results of each round
     * @param results
     * @param die
     * @param x
     * @return result
     */
    public int calcResult(int[] results, boolean[] die, int x) {
        Result = 0;
        //Sätt x = värdet från spinner
        x += 2;
        int total = 0;

        //If player chooses 3 from spinner, add die value <= 3 to result
        if (x == 3) {
            for (int i = 0; i < 6; i++) {
                if (results[i]  < 4) {
                    total += results[i];
                }
            } if (total == 0) {
                return -1;
            }
            return total;
        }
        else {
            ArrayList <Integer> calcResult = new ArrayList<>();
            //If die value = selection from spinner, add die value to result
            for (int i=0; i < 6; i++) {
                if (results[i] == x) {
                    total += results[i];
                }
                //If die value is less than or equal to selection from spinner,
                // add die value to ArrayList.
               else if (results[i] < x) {
                    calcResult.add(results[i]);
                }
            }
            //sort remaining dice in order largest to smallest
            Collections.sort(calcResult, Collections.reverseOrder());
            //Calculate score recursively
            int a = recursiveCalculator(calcResult, x, 0);
            // add result to total and return total result
            total += a;
            if(total != 0) {
                return total;
            } else {
                return -1;
            }
        }
    }

    /**
     * Recursive method for calculating the score of remaining dice.
     * @param resultlist
     * @param x
     * @param total
     * @return
     */
    private int recursiveCalculator(ArrayList<Integer> resultlist, int x, int total) {
        for (int i = 0; i< resultlist.size() - 1; i++) {
            total = 0;
            //takes 1st number in list and tries to add remaining numbers to see if any
            // sum is equal to x (category selected from spinner)
            total += resultlist.get(i);
            for ( int j = i + 1; j < resultlist.size(); j++) {
                total += resultlist.get(j);
                //if a sum is found , this is added to the result and the sumbers used to
                // create sum are removed from the list.
                if (total == x) {
                    resultlist.remove(j);
                    resultlist.remove(i);
                    Result += total;
                    total = 0;
                    //recursive method is called again with the remaining numbers.
                    recursiveCalculator(resultlist, x, total);
                    i = 0;
                    j = 0;
                }
                else if ( total > x) {
                    total -= resultlist.get(j);
                }
            }
        }
        return Result;
    }
}
