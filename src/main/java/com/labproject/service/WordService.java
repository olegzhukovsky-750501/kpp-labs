package com.labproject.service;

import com.labproject.entity.Word;
import com.labproject.params.MyString;
import com.labproject.params.StringsList;
import com.labproject.responses.WordsList;
import com.labproject.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WordService {

    private final AtomicLong counter = new AtomicLong();

    private Validator validator = new Validator();

    public Word scan(MyString word) throws WordException
    {
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE); //Строка может содержать только буквы латинского алфавита
        Matcher m = p.matcher(word.getStr());

        boolean b = m.find();

        if(b)
        {
            throw new WordException("Incorrect data: string can contain only a-z");
        }

        String bufReverse = new StringBuffer(word.getStr()).reverse().toString().toLowerCase();
        boolean isPalindrome = ((word.getStr().toLowerCase()).compareTo(bufReverse)) == 0;
        return new Word(counter.incrementAndGet(), word.getStr(), word.getStr().length(), isPalindrome);
    }

    public WordsList scan(StringsList stringsList)
    {
        List<Word> list  = stringsList.getStrings().stream()
                .filter(p->validator.isValid(p))
                .map(this::scan)
                .collect(Collectors.toList());
        System.out.println(list.size());

        WordsList response = new WordsList();
        response.setWords(list);
        System.out.println("Amount of input strings: " + stringsList.getStrings().size());
        System.out.println("Amount of invalid strings: " +
                stringsList.getStrings().stream().filter(param->!validator.isValid(param)).count());
        if(!list.isEmpty()) {

            System.out.println("Max of results: " + list.stream().max(Comparator.comparing(Word::getLength)).get());
            System.out.println("Min of results: " + list.stream().min(Comparator.comparing(Word::getLength)).get());

            Map<Word, Integer> mostPopularResultMap = new HashMap<>();

            response.getWords().forEach(word -> {
                if(mostPopularResultMap.containsKey(word)){
                    mostPopularResultMap.put(word, mostPopularResultMap.get(word) + 1);
                }
                else {
                    mostPopularResultMap.put(word, 1);
                }
            });

            int maxNumber = mostPopularResultMap.values().stream()
                    .max(Integer::compareTo)
                    .orElse(1);

            List<Word> popular = new ArrayList<>();

            mostPopularResultMap.forEach((a, b) ->{
                if(b == maxNumber){
                    popular.add(a);
                }
            });

            System.out.println("Most popular result:" + popular);

        }

        return response;
    }

}