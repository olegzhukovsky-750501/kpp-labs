package com.labproject.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "asyncresps")
public class AsyncResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "response_id")
    private String responseId;

    @Column(name = "content")
    private String content;

    @Column(name = "length")
    private int length;

    @Column(name = "isPalindrome")
    private boolean isPalindrome;

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void setPalindrome(boolean palindrome) {
        isPalindrome = palindrome;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsyncResponse that = (AsyncResponse) o;
        return length == that.length &&
                isPalindrome == that.isPalindrome &&
                Objects.equals(responseId, that.responseId) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, length, isPalindrome);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
