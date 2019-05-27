package com.labproject.cache;

import com.labproject.entity.Word;
import com.labproject.responses.WordsList;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class CacheService
{
    private final Integer MAX_CACHE_SIZE = 20;
    private ConcurrentHashMap<String, Word> cacheMap = new ConcurrentHashMap<>();
    private HashMap<String, Future<WordsList>> resultMap = new HashMap<>();

    public void addWord(String name, Word word)
    {
        if(cacheMap.size() > MAX_CACHE_SIZE)
        {
            cacheMap.clear();
        }
        cacheMap.put(name, word);
    }

    public void addResult(String id, Future<WordsList> future){
        resultMap.put(id, future);
    }

    public Word getWordFromCache(String name) {
        return cacheMap.get(name);
    }
}