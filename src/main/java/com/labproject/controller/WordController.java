package com.labproject.controller;

import com.labproject.entity.Word;
import com.labproject.service.WordException;
import com.labproject.service.WordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/word/scan")

    public ResponseEntity getScanResult(@RequestParam(value = "string") String name) {
        if(name.isEmpty())
        {
            logger.info("Cannot take an empty string");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot take an empty string");
        }
        try
        {
            Word word = service.scan(name);
            logger.info("HTTP status 200, response :" + word.toString());
            return ResponseEntity.ok(word);
        } catch (WordException exc)
        {
            logger.info("Incorrect string: required word");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect string: required word");
        }
    }
}