package com.labproject.counter;

import org.springframework.stereotype.Service;

@Service
public class CounterService
{
    private long counter;

    public synchronized void increment()
    {
        counter++;
    }

    public long getCounter()
    {
        return counter;
    }
}
