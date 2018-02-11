package com.moufee.a14cup.validation;

/**
 * this is a validation class that checks the user input and makes sure that it is valid
 * Created by andrewberendsen on 2/11/18.
 */

public class DataValidation {

    //constructor to make an object of this class
    public DataValidation() {

    }

    private String valid(String str) {
        if (str == null) {
            return "the string that you entered is null!";
        } else if (str.length() == 0) {
            return "the string that you entered is blank!";
        } else if (str.length() > 256) {
            return "the string that you entered is too long!";
        }

        return "";
    }
}
