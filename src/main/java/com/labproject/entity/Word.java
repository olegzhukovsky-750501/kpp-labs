package com.labproject.entity;


import java.util.Objects;

public class Word {

    private long id;
    private String content;
    private int length;
    private boolean isPalindrome;

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", length=" + length +
                ", isPalindrome=" + isPalindrome +
                '}';
    }

    public Word(long id, String content, int length, boolean isPalindrome)
    {
        this.id = id;
        this.content = content;
        this.length = length;
        this.isPalindrome = isPalindrome;
    }

    public long getId()
    {
        return id;
    }

    public String getContent()
    {
        return content;
    }

    public int getLength()
    {
        return length;
    }

    public boolean getIsPalindrome()
    {
        return isPalindrome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return  id == word.id &&
                length == word.length &&
                isPalindrome == word.isPalindrome &&
                content.equals(word.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, length, isPalindrome);
    }
}
