package com.labproject.responses;


import com.labproject.entity.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordsList {
    private List<Word> words = new ArrayList<>();



    public WordsList(){
    }

    public void setWords(List<Word> words){
        this.words = words;
    }

    public List<Word> getWords(){
        return words;
    }

    @Override
    public String toString() {
        return "WordsList{" +
                "words=" + words +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordsList wordsList = (WordsList) o;
        return Objects.equals(words, wordsList.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}
