/**
 * Model of the game, rolls dice, updates the diecolor and calculates the results
 */

package com.example.thirtygame;

import android.widget.ImageButton;
import java.util.Random;


public class Model {
    public static final Random rand = new Random();
    private int value = 0;

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
        x = x + 2;
        int total = 0;
        if (x == 3) {
            for (int i = 0; i < 6; i++) {
                if (die[i] == true && results[i]  < 4) {
                    total += results[i];
                }
            } if (total == 0) {
                return -1;
            }
            return total;

        }
        else {
            for (int i = 0; i < 6; i++) {
                if (die[i] == true) {
                    total += results[i];
                }
            }
            if (total == 0) {
                return -1;
            }
            else if (total % x == 0) {
                return total;
            }
            return -1;
        }
    }

}