package com.dbs.question1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OddNumberServiceTest {

    @Autowired
    private OddNumberService oddNumberService;

    @Test
    public void testGetOddNumbers() {
        // Retrieve the odd numbers
        List<Integer> oddNumbers = oddNumberService.getOddNumbers(0, 10);

        // Check the odd numbers
        List<Integer> expectedOddNumbers = List.of(1, 3, 5, 7, 9);
        assertEquals(expectedOddNumbers, oddNumbers, "The odd numbers from 0 to 10 should match");
    }
}