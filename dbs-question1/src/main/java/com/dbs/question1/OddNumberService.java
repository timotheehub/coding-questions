package com.dbs.question1;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The OddNumberService returns odd numbers for a range.
 */
@Service
public class OddNumberService {

    /**
     * Returns odd numbers for a range.
     *
     *  @param  min     the minimum value of the range (included).
     *  @param  max     the maximum value of the range (included).
     *  @return         the list of odd numbers for the range.
     */
    public List<Integer> getOddNumbers(int min, int max) {
        // Use IntStream to generate numbers from 0 to 10 and filter odd numbers
        return IntStream.rangeClosed(min, max)
                .filter(num -> num % 2 != 0)
                .boxed()
                .collect(Collectors.toList());
    }
}
