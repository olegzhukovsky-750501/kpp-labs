package com.labproject.controller;

import com.labproject.cache.CacheService;
import com.labproject.counter.CounterService;
import com.labproject.entity.Word;
import com.labproject.params.MyString;
import com.labproject.params.StringsList;
import com.labproject.service.WordException;
import com.labproject.service.WordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;


@RestController
public class WordController {

    private WordService service;

    @Autowired
    public WordController(WordService service)
    {
        this.service = service;
    }

    private Logger logger = Logger.getLogger(WordController.class);

    private CacheService cache = new CacheService();

    private CounterService counterService = new CounterService();

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/word/scan")

    public ResponseEntity getScanResult(@RequestParam(value = "string") String name) {
        if(name.isEmpty())
        {
            logger.info("Cannot take an empty string");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot take an empty string");
        }


        counterService.increment();


        Word cacheWord = cache.getFromCache(name);

        if(cacheWord != null)
        {
            logger.info("uses cache");
            logger.info("HTTP status 200, response :" + cacheWord.toString());
            return ResponseEntity.ok(cacheWord);
        }
        else
        {
            try
            {
                Word word = service.scan(new MyString(name));
                cache.add(name, word);
                logger.info("load to cache");
                logger.info("HTTP status 200, response :" + word.toString());
                return ResponseEntity.ok(word);
            } catch (WordException exc)
            {
                logger.info("Incorrect string: required word");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect string: required word");
            }
        }
    }

    @PostMapping("/words/scan")
    public ResponseEntity getScanResults(@RequestBody StringsList stringsList)
    {
        return ResponseEntity.ok(service.scan(stringsList));
    }
}