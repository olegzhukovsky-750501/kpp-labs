package com.labproject.service;

import com.labproject.cache.CacheService;
import com.labproject.entity.AsyncResponse;
import com.labproject.entity.Word;
import com.labproject.params.MyString;
import com.labproject.params.StringsList;
import com.labproject.repository.AsyncResponseRepository;
import com.labproject.repository.WordRepository;
import com.labproject.responses.WordsList;
import com.labproject.validator.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WordService {

    private static final Logger logger = Logger.getLogger(WordService.class);

    private Validator validator = new Validator();

    private CacheService cache = new CacheService();

    @Autowired
    private WordRepository wordRepo;

    @Autowired
    private AsyncResponseRepository asyncResponseRepository;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Word scan(MyString str) throws WordException
    {
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE); //Строка может содержать только буквы латинского алфавита
        Matcher m = p.matcher(str.getStr());

        boolean b = m.find();

        if(b)
        {
            throw new WordException("Incorrect data: string can contain only a-z");
        }

        Word cacheWord = cache.getWordFromCache(str.getStr());

        if(cacheWord != null)
        {
            logger.info("uses cache");
            logger.info("HTTP status 200, response :" + cacheWord.toString());
            return cacheWord;
        }

        String bufReverse = new StringBuffer(str.getStr()).reverse().toString().toLowerCase();
        boolean isPalindrome = ((str.getStr().toLowerCase()).compareTo(bufReverse)) == 0;

        Word word = new Word(str.getStr(), str.getStr().length(), isPalindrome);
        cache.addWord(str.getStr(), word);
        return word;
    }

    public Word scan(MyString str, String id)
    {
        String bufReverse = new StringBuffer(str.getStr()).reverse().toString().toLowerCase();
        boolean isPalindrome = ((str.getStr().toLowerCase()).compareTo(bufReverse)) == 0;

        Word word = new Word(str.getStr(), str.getStr().length(), isPalindrome);
        word = wordRepo.save(word);

        AsyncResponse asyncResponse = new AsyncResponse();
        asyncResponse.setResponseId(id);
        asyncResponse.setContent(word.getContent());
        asyncResponse.setLength(word.getLength());
        asyncResponse.setId(word.getId());
        asyncResponse.setPalindrome(word.getIsPalindrome());
        asyncResponseRepository.save(asyncResponse);

        return word;
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

    public List<Word> getAll(){
        return wordRepo.findAll();
    }

    public Future<WordsList> processList(StringsList stringsList, String id){
        return executorService.submit(() ->{
            WordsList wordsList = new WordsList();
            stringsList.getStrings().forEach(s ->wordsList.getWords().add(scan(s, id)));

            return wordsList;
        });
    }

    public String asyncCalc(StringsList params) {
        String id = UUID.randomUUID().toString();

        Future<WordsList> result = processList(params, id);
        cache.addResult(id, result);

        return id;
    }

    public List<AsyncResponse> getResponseById(String id) {
        return asyncResponseRepository.findByResponseId(id);
    }

}