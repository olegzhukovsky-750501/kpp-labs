package com.labproject.service;

import com.labproject.entity.Word;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WordService {

    private final AtomicLong counter = new AtomicLong();

    public Word scan(String word) throws WordException
    {
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE); //Строка может содержать только буквы латинского алфавита
        Matcher m = p.matcher(word);
        boolean b = m.find();

        if(b)
        {
            throw new WordException("Incorrect data: string can contain only a-z");
        }

        String bufReverse = new StringBuffer(word).reverse().toString().toLowerCase();
        boolean isPalindrome = ((word.toLowerCase()).compareTo(bufReverse)) == 0;
        return new Word(counter.incrementAndGet(), word, word.length(), isPalindrome);
    }

}