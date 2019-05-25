package com.labproject.service;

import com.labproject.entity.Word;
import com.labproject.params.MyString;
import org.junit.Assert;
import org.junit.Test;

public class WordServiceTest {

    @Test
    public void scan() {
        WordService service = new WordService();

        Word actual = new Word(1, "Hello", 5, false);
        Word expected = service.scan(new MyString("Hello"));

        Assert.assertEquals(expected, actual);
    }
}