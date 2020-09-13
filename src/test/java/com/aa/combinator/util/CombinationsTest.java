package com.aa.combinator.util;

import com.aa.combinator.exception.InvalidMessageFormat;
import com.aa.combinator.vo.Output;
import com.aa.combinator.vo.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mural on 12/09/2020.
 */
public class CombinationsTest {
    private Gson gson = null;
    private Combinations combinations;

    @Before
    public void setup() throws Exception {
        gson = new GsonBuilder().setPrettyPrinting().create();
        combinations = new Combinations();
    }

    @Test
    public void testCombinations_validInput() {
        String combinations = this.combinations.process(mockInput(new String[]{"A", "B", "C", "D"}));
        assertNotNull(" Combinations cannot be null ", combinations);
        assertTrue(" Combinations not correct ", getResult(combinations).getResponse().length == 16);
    }

    @Test(expected = InvalidMessageFormat.class)
    public void testCombinations_nullInput() {
        combinations.process("A,B,C,D");
    }


    private Output getResult(String result) {
        return gson.fromJson(result, Output.class);
    }

    private String mockInput(String[] input) {
        Request request = new Request(input);
        return gson.toJson(request);
    }

}
