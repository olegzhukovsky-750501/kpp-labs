package com.labproject.cache;

import com.labproject.entity.Word;

import java.util.concurrent.ConcurrentHashMap;

public class CacheService
{
    private final Integer MAX_CACHE_SIZE = 20;
    private ConcurrentHashMap<String, Word> cacheMap = new ConcurrentHashMap<>();

    public void add(String name, Word word)
    {
        if(cacheMap.size() > MAX_CACHE_SIZE)
        {
            cacheMap.clear();
        }
        cacheMap.put(name, word);
    }
    public Word getFromCache(String name)
    {
        return cacheMap.get(name);
    }
}