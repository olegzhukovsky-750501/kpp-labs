package com.labproject.params;

import java.util.ArrayList;

public class StringsList {
    private ArrayList<MyString> strings = new ArrayList<>();

    public StringsList(){
    }

    public StringsList(ArrayList<MyString> strings){
        this.strings = strings;
    }

    public ArrayList<MyString> getStrings(){
        return strings;
    }

    public void setStrings(ArrayList<MyString> strings){
        this.strings = strings;
    }
}
