package com.labproject.service;

import com.labproject.entity.Word;
import org.junit.Assert;
import org.junit.Test;

public class WordServiceTest {

    @Test
    public void scan() {
        WordService service = new WordService();

        Word actual = new Word(1, "Hello", 5, false);
        Word expected = service.scan("Hello");

        Assert.assertEquals(expected, actual);
    }
}