package com.example.programming2_c1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommonFunctionsTest {



    @Test
    void validationInteger() {

        //anything above 2147483647 , will be considered as false
        ArrayList<String> value = new ArrayList<>(Arrays.asList(
                "2147483648",
                "3147483647",
                "4147483647",
                "5147483647",
                "6147483647"
        ));

        for (String s : value){
            assertFalse(CommonFunctions.validationInteger(s,"Test"));
        }
    }

    @Test
    void validationLong() {
        //"1111111111" considered as both long and integer
        ArrayList<String> value = new ArrayList<>(Arrays.asList(
                "1111111111",
                "2222222222",
                "3333333333",
                "4444444444",
                "5555555555"
        ));

        for (String s : value){
            assertTrue(CommonFunctions.validationLong(s,"Test"));
        }
    }
}