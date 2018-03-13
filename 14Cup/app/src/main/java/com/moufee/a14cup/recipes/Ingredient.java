package com.moufee.a14cup.recipes;

/**
 * Created by tyler on 3/4/18.
 */

public class Ingredient {
    public String text;
    public boolean checked;

    Ingredient(){
        this.checked = true;
    }

    public boolean isChecked() {
        return checked;
    }

    public void toggleChecked() {
        this.checked = !this.checked;
    }

    public Ingredient(String name){
        this.text = name;
    }

    public String getText(){
        return this.text;
    }
}
