package com.labproject.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= "words")
public class Word {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "content")
    private String content;

    @Column(name = "length")
    private int length;

    @Column(name = "isPalindrome")
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

    public Word(){
    }

    public Word(String content, int length, boolean isPalindrome)
    {
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
        return length == word.length &&
                isPalindrome == word.isPalindrome &&
                Objects.equals(content, word.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, length, isPalindrome);
    }
}
