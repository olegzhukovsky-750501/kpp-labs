package com.labproject.validator;

import com.labproject.params.MyString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public boolean isValid(MyString str){
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE); //Строка может содержать только буквы латинского алфавита
        Matcher m = p.matcher(str.getStr());

        return !(m.find());
    }
}
