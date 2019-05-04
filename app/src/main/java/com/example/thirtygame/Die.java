/**
 * Class describing a die
 */

package com.example.thirtygame;

import android.widget.ImageButton;

public class Die {
    private int value;
    private boolean White;
    private boolean used;

    public Die() {
        this.White = true;
        this.value = 1;
        this.used = false;
    }

    /**
     * Method to change a die from red to white/white to red
     */
    public void toggleColor() {
        if (!White) {
            White = true;
        }
        else {
            White = false;
        }
    }

    /**
     * Check if the die is white
     * @return true if die is white
     */
    public boolean isWhite() {
        return (White == true);
    }

    /**
     * Check if die is red
     * @return true if die is red
     */
    public boolean isRed() {
        return (White != true);
    }

    /**
     * Check if die has been used
     * @return true if die is used
     */

    /*public boolean isUsed() {
        return (used==true);
    }

    public void dieUsed() {
        used = true;
    }*/

    /**
     * Retrieve the value of the die
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the die value after it is rolled
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Change die from red to white
     * @param image
     * @param value
     * @return image of new die
     */
    public ImageButton colorToWhite(ImageButton image, int value) {
        switch (value) {
            case 1:
                image.setImageResource( R.drawable.white1 );
                break;
            case 2:
                image.setImageResource( R.drawable.white2 );
                break;
            case 3:
                image.setImageResource( R.drawable.white3 );
                break;
            case 4:
                image.setImageResource( R.drawable.white4 );
                break;
            case 5:
                image.setImageResource( R.drawable.white5 );
                break;
            case 6:
                image.setImageResource( R.drawable.white6 );
                break;
        }
        return image;
    }

    /**
     * change die from white to red
     * @param image
     * @param value
     * @return image of new die
     */
    public ImageButton colorToRed(ImageButton image, int value) {
        switch (value) {
            case 1:
                image.setImageResource( R.drawable.red1 );
                break;
            case 2:
                image.setImageResource( R.drawable.red2 );
                break;
            case 3:
                image.setImageResource( R.drawable.red3 );
                break;
            case 4:
                image.setImageResource( R.drawable.red4 );
                break;
            case 5:
                image.setImageResource( R.drawable.red5 );
                break;
            case 6:
                image.setImageResource( R.drawable.red6 );
                break;
        }
        return image;
    }

    /**
     * Make die grey
     * @param image
     * @param value
     * @return image of new die
     */
    public ImageButton colorToGrey(ImageButton image, int value) {
        switch (value) {
            case 1:
                image.setImageResource( R.drawable.grey1 );
                break;
            case 2:
                image.setImageResource( R.drawable.grey2 );
                break;
            case 3:
                image.setImageResource( R.drawable.grey3 );
                break;
            case 4:
                image.setImageResource( R.drawable.grey4 );
                break;
            case 5:
                image.setImageResource( R.drawable.grey5 );
                break;
            case 6:
                image.setImageResource( R.drawable.grey6 );
                break;
        }
        return image;
    }

    /**
     * Reset die to white
     */
    public void reset() {
        White = true;
        //used = false;
    }

}




