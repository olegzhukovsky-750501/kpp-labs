package com.labproject.params;

public class MyString {
    private String str;

    public MyString(String str){
        this.str = str;
    }

    public MyString(){

    }

    public String getStr(){
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "MyString{" +
                "str ='" + str + '}';
    }
}
