package com.labproject.controller;

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

import java.util.List;


@RestController
public class WordController {
    private WordService service;

    @Autowired
    public WordController(WordService service)
    {
        this.service = service;
    }

    private CounterService counterService = new CounterService();

    private static final Logger logger = Logger.getLogger(WordController.class);

    @RequestMapping("/word/scan")

    public ResponseEntity getScanResult(@RequestParam(value = "string") String name) {
        if(name.isEmpty())
        {
            logger.info("Cannot take an empty string");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot take an empty string");
        }


        counterService.increment();

        try
        {
            Word word = service.scan(new MyString(name), Long.toString(counterService.getCounter()));

            if (word == null)
            {
                logger.info("Incorrect parameters");
            }
            logger.info("HTTP status 200, response :" + word.toString());
            return ResponseEntity.ok(word);
        } catch (WordException exc)
        {
            logger.info("Incorrect string: required word");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect string: required word");
        }
    }

    @PostMapping("/words/scan")
    public ResponseEntity getScanResults(@RequestBody StringsList stringsList)
    {
        return ResponseEntity.ok(service.asyncCalc(stringsList));
    }

    @GetMapping(path="/words/all")
    public List<Word> getAll(){
        return service.getAll();
    }

    @GetMapping("/word/async")
    public ResponseEntity getAsyncResponse(@RequestParam(name = "id") String id){
        return ResponseEntity.ok(service.getResponseById(id));
    }
}